package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.VariantBarrelBlock;
import de.geekeey.packed.block.VariantChestBlock;
import de.geekeey.packed.block.VariantCrateBlock;
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

    public static final WoodBlockVariants<StorageTier, VariantCrateBlock> CRATE_DEFAULT;
    public static final WoodBlockVariants<StorageTier, VariantCrateBlock> CRATE_TIER_1;
    public static final WoodBlockVariants<StorageTier, VariantCrateBlock> CRATE_TIER_2;
    public static final WoodBlockVariants<StorageTier, VariantCrateBlock> CRATE_TIER_3;

    static {
        CHEST_DEFAULT = new WoodBlockVariants<>(StorageTiers.DEFAULT, PackedBlocks::chest, VariantChestBlock::new);
        CHEST_TIER_1 = new WoodBlockVariants<>(StorageTiers.TIER_1, PackedBlocks::chest, VariantChestBlock::new);
        CHEST_TIER_2 = new WoodBlockVariants<>(StorageTiers.TIER_2, PackedBlocks::chest, VariantChestBlock::new);
        CHEST_TIER_3 = new WoodBlockVariants<>(StorageTiers.TIER_3, PackedBlocks::chest, VariantChestBlock::new);

        BARREL_DEFAULT = new WoodBlockVariants<>(StorageTiers.DEFAULT, PackedBlocks::barrel, VariantBarrelBlock::new);
        BARREL_TIER_1 = new WoodBlockVariants<>(StorageTiers.TIER_1, PackedBlocks::barrel, VariantBarrelBlock::new);
        BARREL_TIER_2 = new WoodBlockVariants<>(StorageTiers.TIER_2, PackedBlocks::barrel, VariantBarrelBlock::new);
        BARREL_TIER_3 = new WoodBlockVariants<>(StorageTiers.TIER_3, PackedBlocks::barrel, VariantBarrelBlock::new);

        CRATE_DEFAULT = new WoodBlockVariants<>(StorageTiers.DEFAULT, PackedBlocks::crate, VariantCrateBlock::new);
        CRATE_TIER_1 = new WoodBlockVariants<>(StorageTiers.TIER_1, PackedBlocks::crate, VariantCrateBlock::new);
        CRATE_TIER_2 = new WoodBlockVariants<>(StorageTiers.TIER_2, PackedBlocks::crate, VariantCrateBlock::new);
        CRATE_TIER_3 = new WoodBlockVariants<>(StorageTiers.TIER_3, PackedBlocks::crate, VariantCrateBlock::new);
    }

    public static Identifier chest(StorageTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_chest_%s", variant.identifier(), tier.getIdentifier().getPath()));
    }

    public static Identifier barrel(StorageTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_barrel_%s", variant.identifier(), tier.getIdentifier().getPath()));
    }

    public static Identifier crate(StorageTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_crate_%s", variant.identifier(), tier.getIdentifier().getPath()));
    }

    public static void register() {
        // keep so class will get called and initialized
    }
}
