package de.geekeey.packed.init;

import com.google.common.collect.Iterables;
import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.Packed;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        BARREL_3_9 = create("barrel_3_9", create(CustomBarrelEntity::create3x9, PackedBlocks.BARREL_DEFAULT_TIER));
        BARREL_4_9 = create("barrel_4_9", create(CustomBarrelEntity::create4x9, PackedBlocks.BARREL_TIER_1));
        BARREL_5_9 = create("barrel_5_9", create(CustomBarrelEntity::create5x9, PackedBlocks.BARREL_TIER_2));
        BARREL_6_9 = create("barrel_6_9", create(CustomBarrelEntity::create6x9, PackedBlocks.BARREL_TIER_3));

        CHEST_3_9 = create("chest_3_9", create(CustomChestEntity::create3x9, PackedBlocks.CHEST_DEFAULT_TIER));
        CHEST_4_9 = create("chest_4_9", create(CustomChestEntity::create4x9, PackedBlocks.CHEST_TIER_1));
        CHEST_5_9 = create("chest_5_9", create(CustomChestEntity::create5x9, PackedBlocks.CHEST_TIER_2));
        CHEST_6_9 = create("chest_6_9", create(CustomChestEntity::create6x9, PackedBlocks.CHEST_TIER_3));
    }

    private static <T extends BlockEntity, B extends Block> Builder<T> create(Supplier<T> supplier, Iterable<B> blocks) {
        return Builder.create(supplier, cast(blocks));
    }

    private static Block[] cast(Iterable<? extends Block> blocks) {
       return StreamSupport.stream(blocks.spliterator(), false).map(input -> (Block) input).toArray(Block[]::new);
    }

    private static <T extends BlockEntity> BlockEntityType<T> create(String identifier, Builder<T> builder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Packed.id(identifier), builder.build(null));
    }

    public static void register() {
        // keep so class will get called and initialized
    }

}
