package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.CustomBarrelEntity;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class CustomBarrel extends BarrelBlock {

    public static final BarrelSize SIZE_3_9 = new BarrelSize(CustomBarrelEntity::create3x9);
    public static final BarrelSize SIZE_4_9 = new BarrelSize(CustomBarrelEntity::create4x9);
    public static final BarrelSize SIZE_5_9 = new BarrelSize(CustomBarrelEntity::create5x9);
    public static final BarrelSize SIZE_6_9 = new BarrelSize(CustomBarrelEntity::create6x9);

    private final Supplier<CustomBarrelEntity> factory;

    public CustomBarrel(Settings settings, Supplier<CustomBarrelEntity> supplier) {
        super(settings);
        this.factory = supplier;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return factory.get();
    }

    public static class BarrelSize {
        public final Supplier<CustomBarrelEntity> supplier;

        public BarrelSize(Supplier<CustomBarrelEntity> supplier) {
            this.supplier = supplier;
        }
    }

}
