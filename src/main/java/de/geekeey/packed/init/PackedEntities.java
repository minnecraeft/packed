package de.geekeey.packed.init;

import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.Packed;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.util.registry.Registry;

import java.util.Set;
import java.util.function.Supplier;

public class PackedEntities {

    public static final BlockEntityType<CustomBarrelEntity> BARREL_3_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_4_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_5_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_6_9;

    public static final BlockEntityType<CustomChestEntity> CHEST_3_9;
    public static final BlockEntityType<CustomChestEntity> CHEST_4_9;
    public static final BlockEntityType<CustomChestEntity> CHEST_5_9;
    public static final BlockEntityType<CustomChestEntity> CHEST_6_9;

    static {
        BARREL_3_9 = create("barrel_3_9", create(CustomBarrelEntity::create3x9, PackedBlocks.BARREL_DEFAULT_TIER.variants));
        BARREL_4_9 = create("barrel_4_9", create(CustomBarrelEntity::create4x9, PackedBlocks.BARREL_TIER_1.variants));
        BARREL_5_9 = create("barrel_5_9", create(CustomBarrelEntity::create5x9, PackedBlocks.BARREL_TIER_2.variants));
        BARREL_6_9 = create("barrel_6_9", create(CustomBarrelEntity::create6x9, PackedBlocks.BARREL_TIER_3.variants));

        CHEST_3_9 = create("chest_3_9", create(CustomChestEntity::create3x9, PackedBlocks.CHEST_DEFAULT_TIER.variants));
        CHEST_4_9 = create("chest_4_9", create(CustomChestEntity::create4x9, PackedBlocks.CHEST_TIER_1.variants));
        CHEST_5_9 = create("chest_5_9", create(CustomChestEntity::create5x9, PackedBlocks.CHEST_TIER_2.variants));
        CHEST_6_9 = create("chest_6_9", create(CustomChestEntity::create6x9, PackedBlocks.CHEST_TIER_3.variants));
    }

    private static <T extends BlockEntity> Builder<T> create(Supplier<T> supplier, Set<? extends Block> blocks) {
        return Builder.create(supplier, blocks.toArray(Block[]::new));
    }

    private static <T extends BlockEntity> BlockEntityType<T> create(String identifier, Builder<T> builder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Packed.id(identifier), builder.build(null));
    }

    public static void register() {
        // keep so class will get called and initialized
    }

}
