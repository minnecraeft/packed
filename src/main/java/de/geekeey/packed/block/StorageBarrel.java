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

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            Inventory inv = (Inventory) world.getBlockEntity(pos);

            ItemStack stackInHand = player.getStackInHand(hand);
            ItemStack barrelStack = inv.getStack(0);

            if (barrelStack == stackInHand || barrelStack.isEmpty()) {
                //we need this to set the final item count in the barrel
                int barrelCount = inv.getStack(0).getCount();

                inv.setStack(0, stackInHand.copy());
                inv.getStack(0).setCount(stackInHand.getCount() + barrelCount);
                stackInHand.setCount(0);
                return ActionResult.SUCCESS;
            }
            return ActionResult.FAIL;
        }
        return ActionResult.CONSUME;
    }

    /**
     * When player starts hitting block we extract Items from the Barrel,
     **/
    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            Inventory inv = (Inventory) world.getBlockEntity(pos);

            //removed stack from inventory
            ItemStack stack = inv.removeStack(0, dropSize);

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
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    //Inheritance from BlockWithEntity results in the rendertype being invisible, so we have to fix that
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
