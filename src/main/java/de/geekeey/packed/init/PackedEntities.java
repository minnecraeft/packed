package de.geekeey.packed.init;

import com.google.common.collect.Iterables;
import de.geekeey.packed.Packed;
import de.geekeey.packed.block.entity.VariantBarrelBlockEntity;
import de.geekeey.packed.block.entity.VariantChestBlockEntity;
import de.geekeey.packed.block.entity.VariantStorageBarrelBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PackedEntities {

    public static final BlockEntityType<VariantChestBlockEntity> CUSTOM_CHEST;
    public static final BlockEntityType<VariantBarrelBlockEntity> CUSTOM_BARREL;
    public static final BlockEntityType<VariantStorageBarrelBlockEntity> STORAGE_BARREL;

    static {
        List<Block> barrels = new ArrayList<>();
        Iterables.addAll(barrels, PackedBlocks.BARREL_DEFAULT);
        Iterables.addAll(barrels, PackedBlocks.BARREL_TIER_1);
        Iterables.addAll(barrels, PackedBlocks.BARREL_TIER_2);
        Iterables.addAll(barrels, PackedBlocks.BARREL_TIER_3);

        CUSTOM_BARREL = register("barrel", create(VariantBarrelBlockEntity::new, barrels));

        List<Block> chests = new ArrayList<>();
        Iterables.addAll(chests, PackedBlocks.CHEST_DEFAULT);
        Iterables.addAll(chests, PackedBlocks.CHEST_TIER_1);
        Iterables.addAll(chests, PackedBlocks.CHEST_TIER_2);
        Iterables.addAll(chests, PackedBlocks.CHEST_TIER_3);

        CUSTOM_CHEST = register("chest", create(VariantChestBlockEntity::new, chests));

        List<Block> storageBarrels = new ArrayList<>();
        Iterables.addAll(storageBarrels, PackedBlocks.STORAGE_BARREL_DEFAULT);
        Iterables.addAll(storageBarrels, PackedBlocks.STORAGE_BARREL_TIER_1);
        Iterables.addAll(storageBarrels, PackedBlocks.STORAGE_BARREL_TIER_2);
        Iterables.addAll(storageBarrels, PackedBlocks.STORAGE_BARREL_TIER_3);

        STORAGE_BARREL = register("storage_barrel", create(VariantStorageBarrelBlockEntity::new, storageBarrels));
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
