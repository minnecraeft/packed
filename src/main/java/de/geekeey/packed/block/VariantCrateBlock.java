package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.VariantCrateBlockEntity;
import de.geekeey.packed.init.helpers.StorageTier;
import de.geekeey.packed.init.helpers.WoodVariant;
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
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
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
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static net.minecraft.block.Blocks.BARREL;

public class VariantCrateBlock extends BlockWithEntity {

    private static final int dropSize = 16;
    private static final int shiftingDropSize = 64;
    private static final int doubleUseInterval = 10;

    //Last player who used this barrel, this is used for inserting all inventory stacks when player double right clicks
    private UUID lastUsedPlayer;
    private long lastUsedTime;

    //StorageBarrel can only point towards horizontal cardinal directions
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private final StorageTier tier;
    private final WoodVariant variant;

    public VariantCrateBlock(@NotNull StorageTier tier, @NotNull WoodVariant variant) {
        super(FabricBlockSettings.copyOf(BARREL));
        this.tier = tier;
        this.variant = variant;
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    public @NotNull StorageTier getTier() {
        return tier;
    }

    public @NotNull WoodVariant getVariant() {
        return variant;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new VariantCrateBlockEntity(getTier(), getVariant());
    }

    /**
     * When the player right clicks the block we insert the entire Stack in the players Hand into the barrel,
     * If the Items in the Barrel do not match those in the players Hand we do nothing.
     **/
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            VariantCrateBlockEntity crate = (VariantCrateBlockEntity) world.getBlockEntity(pos);

            assert crate != null;
            ItemStack stack = player.getStackInHand(hand);

            if (!crate.isFull()) {
                ItemStack insert;
                // First case:
                //  The Barrel already has an Item in it and the player right clicks with an identical item we will
                //  insert all items in the players hand into the barrel and remove the items from the player inventory
                //  we do not check against nbt here as there can't be an item in the barrel with nbt because of the
                //  following conditions
                if ((crate.getItem().equals(Items.AIR) || crate.getItem().equals(stack.getItem())) && stack.isStackable() && !stack.hasTag()) {
                    insert = crate.insert(stack);
                    stack.setCount(insert.getCount());
                }

                // if the player has clicked twice in a certain period, clear the inventory
                else if (player.getUuid().equals(lastUsedPlayer) && world.getTime() - lastUsedTime < doubleUseInterval) {
                    var inv = player.inventory;
                    for (int i = 0; i < inv.size(); i++) {
                        var currentStack = inv.getStack(i);
                        if (!currentStack.isEmpty() && crate.getItem().equals(currentStack.getItem())) {
                            insert = crate.insertAll(currentStack);
                            currentStack.setCount(insert.getCount());
                            if (!insert.isEmpty()) break;
                        }
                    }
                }
            }
            // save the last used player and time so we can check against them next time.
            lastUsedPlayer = player.getUuid();
            lastUsedTime = world.getTime();
            return ActionResult.SUCCESS;
        }
        // Consume is needed so that client does not place block in front of barrel
        return ActionResult.CONSUME;
    }

    //Helper function to insert items into our storageBarrel,
    //returns weather itemStack could be inserted fully
    private boolean insertInBarrel(ItemStack insert, VariantCrateBlockEntity inv) {
        var barrelStack = inv.getStack(0);
        var barrelCount = barrelStack.getCount();

        if (barrelStack.getItem() != insert.getItem())
            throw new IllegalArgumentException("Stack to be inserted must have same item as barrel content");

        int remainingSpace = inv.getMaxCountPerStack() - barrelStack.getCount();
        //This transaction would fill the barrel completely
        if (remainingSpace < insert.getCount()) {
            barrelStack.setCount(inv.getMaxCountPerStack());
            insert.setCount(insert.getCount() - remainingSpace);
            return false;
        }
        //There is enough space in the barrel for the entire stack
        else {
            barrelStack.setCount(barrelCount + insert.getCount());
            insert.setCount(0);
            return true;
        }
    }

    /**
     * When player starts hitting block we extract Items from the Barrel, the amount is dependent on the
     * {@link VariantCrateBlock#dropSize} variable. When there is no space in the player inventory the items
     * get dropped onto the ground
     **/
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            VariantCrateBlockEntity crate = (VariantCrateBlockEntity) world.getBlockEntity(pos);
            assert crate != null;

            var size = player.isSneaking() ? Math.min(shiftingDropSize, crate.getItem().getMaxCount()) : dropSize;
            //removed stack from inventory

            ItemStack result = ItemStack.EMPTY;

            for (int i = 0; i < crate.size(); i++) {
                ItemStack stack = crate.removeStack(i, size);
                if (stack.isEmpty()) continue;
                if (stack.getCount() == size) {
                    result = stack;
                    break;
                }
                if (result.isEmpty()) {
                    result = stack;
                } else {
                    result.increment(stack.getCount());
                    if (result.getCount() == size) break;
                }
            }

            if (crate.getItem().equals(Items.AIR)) {
                //Sync here because the item to be displayed in the front changed as the barrel is now empty
                crate.sync();
            }
            player.inventory.offerOrDrop(world, result);
        }
    }

    /**
     * When block is broken, this method will drop all contained items onto the ground
     **/
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock()) && !(newState.getBlock() instanceof VariantCrateBlock)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }
            //This super call will kill the BlockEntity, so this only gets called when we're not upgrading our block
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

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
