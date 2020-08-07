package de.geekeey.packed.registry;

import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.util.registry.Registry;

import java.util.Set;
import java.util.function.Supplier;

public class BlockEntities {

    public static final BlockEntityType<CustomBarrelEntity> BARREL_3_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_4_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_5_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_6_9;

    static {
        BARREL_3_9 = create("barrel_3_9", create(CustomBarrelEntity::create3x9, Blocks.BARRELS_3_9));
        BARREL_4_9 = create("barrel_4_9", create(CustomBarrelEntity::create4x9, Blocks.BARRELS_4_9));
        BARREL_5_9 = create("barrel_5_9", create(CustomBarrelEntity::create5x9, Blocks.BARRELS_5_9));
        BARREL_6_9 = create("barrel_6_9", create(CustomBarrelEntity::create6x9, Blocks.BARRELS_6_9));
    }

    private static <T extends BlockEntity> Builder<T> create(Supplier<T> supplier, Set<? extends Block> blocks) {
        return Builder.create(supplier, blocks.toArray(Block[]::new));
    }

    private static <T extends BlockEntity> BlockEntityType<T> create(String identifier, Builder<T> builder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Initializer.id(identifier), builder.build(null));
    }

    public static void register() {

    }
}
