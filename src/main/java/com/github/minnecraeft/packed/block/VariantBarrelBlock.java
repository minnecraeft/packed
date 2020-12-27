package com.github.minnecraeft.packed.block;

import com.github.minnecraeft.packed.block.entity.VariantBarrelBlockEntity;
import com.github.minnecraeft.packed.init.helpers.StorageTier;
import com.github.minnecraeft.packed.init.helpers.WoodVariant;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.block.Blocks.BARREL;

public class VariantBarrelBlock extends BarrelBlock {

    private final StorageTier tier;
    private final WoodVariant variant;

    public VariantBarrelBlock(@NotNull StorageTier tier, @NotNull WoodVariant variant) {
        super(FabricBlockSettings.copyOf(BARREL));
        this.tier = tier;
        this.variant = variant;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new VariantBarrelBlockEntity(getTier(), getVariant());
    }

    public @NotNull StorageTier getTier() {
        return tier;
    }

    public @NotNull WoodVariant getVariant() {
        return variant;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock()) && !(newState.getBlock() instanceof VariantBarrelBlock)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }
            //This super call will kill the BlockEntity, so this only gets called when we're not upgrading our block
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
