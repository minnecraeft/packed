package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.VariantBarrelBlock;
import de.geekeey.packed.block.VariantChestBlock;
import de.geekeey.packed.block.VariantStorageBarrel;
import de.geekeey.packed.init.helpers.*;
import net.minecraft.util.Identifier;

public class PackedBlocks {

    public static final WoodBlockVariants<StorageTier, VariantChestBlock> CHEST_DEFAULT;
    public static final WoodBlockVariants<StorageTier, VariantChestBlock> CHEST_TIER_1;
    public static final WoodBlockVariants<StorageTier, VariantChestBlock> CHEST_TIER_2;
    public static final WoodBlockVariants<StorageTier, VariantChestBlock> CHEST_TIER_3;

    public static final WoodBlockVariants<StorageTier, VariantBarrelBlock> BARREL_DEFAULT;
    public static final WoodBlockVariants<StorageTier, VariantBarrelBlock> BARREL_TIER_1;
    public static final WoodBlockVariants<StorageTier, VariantBarrelBlock> BARREL_TIER_2;
    public static final WoodBlockVariants<StorageTier, VariantBarrelBlock> BARREL_TIER_3;

    public static final WoodBlockVariants<StorageTier, VariantStorageBarrel> STORAGE_BARREL_DEFAULT;
    public static final WoodBlockVariants<StorageTier, VariantStorageBarrel> STORAGE_BARREL_TIER_1;
    public static final WoodBlockVariants<StorageTier, VariantStorageBarrel> STORAGE_BARREL_TIER_2;
    public static final WoodBlockVariants<StorageTier, VariantStorageBarrel> STORAGE_BARREL_TIER_3;

    static {
        CHEST_DEFAULT = new WoodBlockVariants<>(StorageTiers.DEFAULT, PackedBlocks::chest, VariantChestBlock::new);
        CHEST_TIER_1 = new WoodBlockVariants<>(StorageTiers.TIER_1, PackedBlocks::chest, VariantChestBlock::new);
        CHEST_TIER_2 = new WoodBlockVariants<>(StorageTiers.TIER_2, PackedBlocks::chest, VariantChestBlock::new);
        CHEST_TIER_3 = new WoodBlockVariants<>(StorageTiers.TIER_3, PackedBlocks::chest, VariantChestBlock::new);

        BARREL_DEFAULT = new WoodBlockVariants<>(StorageTiers.DEFAULT, PackedBlocks::barrel, VariantBarrelBlock::new);
        BARREL_TIER_1 = new WoodBlockVariants<>(StorageTiers.TIER_1, PackedBlocks::barrel, VariantBarrelBlock::new);
        BARREL_TIER_2 = new WoodBlockVariants<>(StorageTiers.TIER_2, PackedBlocks::barrel, VariantBarrelBlock::new);
        BARREL_TIER_3 = new WoodBlockVariants<>(StorageTiers.TIER_3, PackedBlocks::barrel, VariantBarrelBlock::new);

        STORAGE_BARREL_DEFAULT = new WoodBlockVariants<>(StorageTiers.DEFAULT, PackedBlocks::storageBarrel, VariantStorageBarrel::new);
        STORAGE_BARREL_TIER_1 = new WoodBlockVariants<>(StorageTiers.TIER_1, PackedBlocks::storageBarrel, VariantStorageBarrel::new);
        STORAGE_BARREL_TIER_2 = new WoodBlockVariants<>(StorageTiers.TIER_2, PackedBlocks::storageBarrel, VariantStorageBarrel::new);
        STORAGE_BARREL_TIER_3 = new WoodBlockVariants<>(StorageTiers.TIER_3, PackedBlocks::storageBarrel, VariantStorageBarrel::new);
    }

    public static Identifier chest(StorageTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_chest_%s", variant.identifier(), tier.getIdentifier().getPath()));
    }

    public static Identifier barrel(StorageTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_barrel_%s", variant.identifier(), tier.getIdentifier().getPath()));
    }

    public static Identifier storageBarrel(StorageTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_storage_barrel_%s", variant.identifier(), tier.getIdentifier().getPath()));
    }

    public static void register() {
        // keep so class will get called and initialized
    }
}
