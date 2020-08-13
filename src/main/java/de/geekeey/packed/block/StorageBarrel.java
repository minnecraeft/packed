package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.StorageBarrelEntity;
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

public class StorageBarrel extends BlockWithEntity {
    //StorageBarrel can only point towards horizontal cardinal directions
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final int dropSize = 8;

    public StorageBarrel(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new StorageBarrelEntity();
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

            //we do not allow items which are not stackable in the barrel.
            if (barrelStack.isEmpty() && stackInHand.isStackable()) {
                inv.setStack(0, stackInHand.copy());
                inv.sync();
                stackInHand.setCount(0);
                return ActionResult.SUCCESS;
            } else if (barrelStack.getItem().equals(stackInHand.getItem()) && stackInHand.isStackable()) {
                int barrelCount = inv.getStack(0).getCount();

                int remainingSpace = inv.getMaxCountPerStack() - inv.getStack(0).getCount();

                if (remainingSpace < stackInHand.getCount()) {
                    inv.getStack(0).setCount(inv.getMaxCountPerStack());
                    stackInHand.setCount(stackInHand.getCount() - remainingSpace);
                } else {
                    inv.getStack(0).setCount(barrelCount + stackInHand.getCount());
                    stackInHand.setCount(0);
                }
                return ActionResult.SUCCESS;
            }

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

            //removed stack from inventory
            assert inv != null;
            ItemStack stack = inv.removeStack(0, dropSize);
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
