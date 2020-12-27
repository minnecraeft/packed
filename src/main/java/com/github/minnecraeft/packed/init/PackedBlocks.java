package com.github.minnecraeft.packed.init;

import com.github.minnecraeft.packed.Packed;
import com.github.minnecraeft.packed.block.VariantBarrelBlock;
import com.github.minnecraeft.packed.block.VariantChestBlock;
import com.github.minnecraeft.packed.block.VariantCrateBlock;
import com.github.minnecraeft.packed.init.helpers.StorageTier;
import com.github.minnecraeft.packed.init.helpers.StorageTiers;
import com.github.minnecraeft.packed.init.helpers.WoodBlockVariants;
import com.github.minnecraeft.packed.init.helpers.WoodVariant;
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
        return Packed.identifier(String.format("%s_chest_%s", variant.getIdentifier().getPath(), tier.getIdentifier().getPath()));
    }

    public static Identifier barrel(StorageTier tier, WoodVariant variant) {
        return Packed.identifier(String.format("%s_barrel_%s", variant.getIdentifier().getPath(), tier.getIdentifier().getPath()));
    }

    public static Identifier crate(StorageTier tier, WoodVariant variant) {
        return Packed.identifier(String.format("%s_crate_%s", variant.getIdentifier().getPath(), tier.getIdentifier().getPath()));
    }

    @SuppressWarnings("EmptyMethod")
    public static void register() {
        // keep so class will get called and initialized
    }
}
