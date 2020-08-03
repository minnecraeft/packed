package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.BasicContainerEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BasicContainerBlock extends BlockWithEntity {

    protected BasicContainerBlock(Settings settings) {
        super(settings);
    }

    //Call the responsible ScreenHandler when the chest is right clicked
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    //Inheritance from BlockWithEntity results in the rendertype being invisible, so we have to fix that
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    //When block is broken, we drop all Items on the floor and update surrounding comparators
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity be = world.getBlockEntity(pos);

            if (be instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) be);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if(itemStack.hasCustomName()){
            BlockEntity be = world.getBlockEntity(pos);
            if(be instanceof BasicContainerEntity){
                ((BasicContainerEntity)be).setCustomName(itemStack.getName());
            }
        }
    }

    //Calculate comparator output by analysing the block entity inventory
    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
}
