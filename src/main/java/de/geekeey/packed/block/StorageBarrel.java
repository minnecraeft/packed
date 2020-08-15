package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.StorageBarrelEntity;
import de.geekeey.packed.init.helpers.StorageBarrelTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import de.geekeey.packed.item.StorageUpgrader;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.UUID;

import static net.minecraft.block.Blocks.BARREL;

public class StorageBarrel extends BlockWithEntity {
    private static final int dropSize = 16;
    private static final int shiftingDropSize = 64;
    private static final int doubleUseInterval = 10;

    //Last player who used this barrel, this is used for inserting all inventory stacks when player double right clicks
    private UUID lastUsedPlayer;
    private long lastUsedTime;


    //StorageBarrel can only point towards horizontal cardinal directions
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private final StorageBarrelTier tier;
    private final WoodVariant variant;

    public StorageBarrel(StorageBarrelTier tier, WoodVariant variant) {
        super(FabricBlockSettings.copyOf(BARREL));
        this.tier = tier;
        this.variant = variant;
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new StorageBarrelEntity(tier, variant);
    }

    /**
     * When the player right clicks the block we insert the entire Stack in the players Hand into the barrel,
     * If the Items in the Barrel do not match those in the players Hand we do nothing.
     **/
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            StorageBarrelEntity inv = (StorageBarrelEntity) world.getBlockEntity(pos);

            assert inv != null;
            ItemStack stackInHand = player.getStackInHand(hand);
            ItemStack barrelStack = inv.getStack(0);
            int barrelCount = barrelStack.getCount();
            if(player.getStackInHand(hand).getItem() instanceof StorageUpgrader){
                return ActionResult.SUCCESS;
            }
            //First case: The player has recently right clicked the barrel
            //we will insert all items matching the ones in the barrel from the player inventory
            else if (player.getUuid() == lastUsedPlayer && world.getTime() - lastUsedTime < doubleUseInterval) {
                int slot;
                while ((slot = player.inventory.getSlotWithStack(barrelStack)) > -1) {
                    var removedStack = player.inventory.removeStack(slot);
                    //we don't use the barrelCount variable here because we would need to refresh it every iteration
                    barrelStack.setCount(barrelStack.getCount() + removedStack.getCount());
                }
            }
            //Second case: The Barrel is empty and the player right clicks it with a stackable item without NBT
            //we will insert all items the player has in his hand into the barrel and remove the items from the player
            else if (barrelStack.isEmpty() && stackInHand.isStackable() && !stackInHand.hasTag()) {
                inv.setStack(0, stackInHand.copy());
                inv.sync();
                stackInHand.setCount(0);
            }
            //Third case: The Barrel already has an Item in it and the player right clicks with an identical item
            //we will insert all items in the players hand into the barrel and remove the items from the player
            //we do not check against NBT here as there cant be an item in the barrel with NBT because of the previous if
            else if (barrelStack.getItem().equals(stackInHand.getItem()) && stackInHand.isStackable()) {
                int remainingSpace = inv.getMaxCountPerStack() - barrelStack.getCount();

                //This transaction would fill the barrel completely
                if (remainingSpace < stackInHand.getCount()) {
                    barrelStack.setCount(inv.getMaxCountPerStack());
                    stackInHand.setCount(stackInHand.getCount() - remainingSpace);
                }
                //There is enough space in the barrel for the entire stack
                else {
                    barrelStack.setCount(barrelCount + stackInHand.getCount());
                    stackInHand.setCount(0);
                }
            }

            //save the last used player and time so we can check against them next time.
            lastUsedPlayer = player.getUuid();
            lastUsedTime = world.getTime();
            return ActionResult.SUCCESS;
        }
        //Consume is needed so that client does not place block in front of barrel
        return ActionResult.CONSUME;
    }

    /**
     * When player starts hitting block we extract Items from the Barrel, the amount is dependent on the
     * {@link StorageBarrel#dropSize} variable. When there is no space in the player inventory the items
     * get dropped onto the ground
     **/
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            StorageBarrelEntity inv = (StorageBarrelEntity) world.getBlockEntity(pos);
            assert inv != null;

            //removed stack from inventory
            ItemStack stack;
            if (player.isSneaking()) {
                stack = inv.removeStack(0, shiftingDropSize);
            } else {
                stack = inv.removeStack(0, dropSize);
            }

            if (inv.getStack(0).isEmpty()) {
                inv.removeStack(0);
                inv.sync();
            }

            player.inventory.offerOrDrop(world, stack);
        }

        super.onBlockBreakStart(state, world, pos, player);
    }

    /**
     * When block is broken, this method will drop all contained items onto the ground
     **/
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    //Inheritance from BlockWithEntity results in the renderType being invisible, so we have to fix that
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
