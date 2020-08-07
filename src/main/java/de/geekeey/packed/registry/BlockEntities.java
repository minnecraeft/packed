package de.geekeey.packed.registry;

import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.block.entity.CustomChestEntity;
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

    public static final BlockEntityType<CustomChestEntity> CHEST_3_9;
    public static final BlockEntityType<CustomChestEntity> CHEST_4_9;
    public static final BlockEntityType<CustomChestEntity> CHEST_5_9;
    public static final BlockEntityType<CustomChestEntity> CHEST_6_9;

    static {
        BARREL_3_9 = create("barrel_3_9", create(CustomBarrelEntity::create3x9, Blocks.BARREL_DEFAULT_TIER.all));
        BARREL_4_9 = create("barrel_4_9", create(CustomBarrelEntity::create4x9, Blocks.BARREL_TIER_1.all));
        BARREL_5_9 = create("barrel_5_9", create(CustomBarrelEntity::create5x9, Blocks.BARREL_TIER_2.all));
        BARREL_6_9 = create("barrel_6_9", create(CustomBarrelEntity::create6x9, Blocks.BARREL_TIER_3.all));

        CHEST_3_9 = create("chest_3_9", create(CustomChestEntity::create3x9, Blocks.CHEST_DEFAULT_TIER.all));
        CHEST_4_9 = create("chest_4_9", create(CustomChestEntity::create4x9, Blocks.CHEST_TIER_1.all));
        CHEST_5_9 = create("chest_5_9", create(CustomChestEntity::create5x9, Blocks.CHEST_TIER_2.all));
        CHEST_6_9 = create("chest_6_9", create(CustomChestEntity::create6x9, Blocks.CHEST_TIER_3.all));
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
