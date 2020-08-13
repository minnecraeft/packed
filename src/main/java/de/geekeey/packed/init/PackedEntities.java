package de.geekeey.packed.init;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import de.geekeey.packed.Packed;
import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.block.entity.StorageBarrelEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PackedEntities {

    public static final BlockEntityType<CustomBarrelEntity> BARREL_3_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_4_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_5_9;
    public static final BlockEntityType<CustomBarrelEntity> BARREL_6_9;

    public static final ImmutableSet<BlockEntityType<CustomBarrelEntity>> BARREL_ENTITY_TYPES;

    public static final BlockEntityType<CustomChestEntity> CUSTOM_CHEST;

    public static final BlockEntityType<StorageBarrelEntity> STORAGE_BARREL_ENTITY;

    static {
        BARREL_3_9 = register("barrel_3_9", create(CustomBarrelEntity::create3x9, PackedBlocks.BARREL_DEFAULT_TIER));
        BARREL_4_9 = register("barrel_4_9", create(CustomBarrelEntity::create4x9, PackedBlocks.BARREL_TIER_1));
        BARREL_5_9 = register("barrel_5_9", create(CustomBarrelEntity::create5x9, PackedBlocks.BARREL_TIER_2));
        BARREL_6_9 = register("barrel_6_9", create(CustomBarrelEntity::create6x9, PackedBlocks.BARREL_TIER_3));

        BARREL_ENTITY_TYPES = ImmutableSet.of(BARREL_3_9, BARREL_4_9, BARREL_5_9, BARREL_6_9);

        List<Block> blocks = new ArrayList<>();
        Iterables.addAll(blocks, PackedBlocks.CHEST_DEFAULT_TIER);
        Iterables.addAll(blocks, PackedBlocks.CHEST_TIER_1);
        Iterables.addAll(blocks, PackedBlocks.CHEST_TIER_2);
        Iterables.addAll(blocks, PackedBlocks.CHEST_TIER_3);

        CUSTOM_CHEST = register("chest", create(CustomChestEntity::new, blocks));

        STORAGE_BARREL_ENTITY = register("storage_barrel", Builder.create(StorageBarrelEntity::new, PackedBlocks.STORAGE_BARREL));
    }

    private static <T extends BlockEntity, B extends Block> Builder<T> create(Supplier<T> supplier, Iterable<B> blocks) {
        return Builder.create(supplier, Iterables.toArray(blocks, Block.class));
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String identifier, Builder<T> builder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Packed.id(identifier), builder.build(null));
    }

    public static void register() {
        // keep so class will get called and initialized
    }

}
