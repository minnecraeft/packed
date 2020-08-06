package de.geekeey.packed.block;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class CustomBarrel extends BarrelBlock {

    private final Supplier<BlockEntity> factory;

    public CustomBarrel(Settings settings, Supplier<BlockEntity> supplier) {
        super(settings);
        this.factory = supplier;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return factory.get();
    }
}
