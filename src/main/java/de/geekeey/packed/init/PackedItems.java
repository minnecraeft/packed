package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.CustomBarrel;
import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.init.helpers.*;

public class PackedItems {

    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_DEFAULT_TIER;
    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_TIER_1;
    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_TIER_2;
    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_TIER_3;

    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_DEFAULT_TIER;
    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_TIER_1;
    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_TIER_2;
    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_TIER_3;

    static {
        BARREL_DEFAULT_TIER = new WoodItemVariants<>(BarrelTiers.DEFAULT, BarrelTiers::identifier, PackedBlocks.BARREL_DEFAULT_TIER, Packed.ITEM_GROUP);
        BARREL_TIER_1 = new WoodItemVariants<>(BarrelTiers.TIER1, BarrelTiers::identifier, PackedBlocks.BARREL_TIER_1, Packed.ITEM_GROUP);
        BARREL_TIER_2 = new WoodItemVariants<>(BarrelTiers.TIER2, BarrelTiers::identifier, PackedBlocks.BARREL_TIER_2, Packed.ITEM_GROUP);
        BARREL_TIER_3 = new WoodItemVariants<>(BarrelTiers.TIER3, BarrelTiers::identifier, PackedBlocks.BARREL_TIER_3, Packed.ITEM_GROUP);

        CHEST_DEFAULT_TIER = new WoodItemVariants<>(ChestTiers.DEFAULT, ChestTiers::identifier, PackedBlocks.CHEST_DEFAULT_TIER, Packed.ITEM_GROUP);
        CHEST_TIER_1 = new WoodItemVariants<>(ChestTiers.TIER1, ChestTiers::identifier, PackedBlocks.CHEST_TIER_1, Packed.ITEM_GROUP);
        CHEST_TIER_2 = new WoodItemVariants<>(ChestTiers.TIER2, ChestTiers::identifier, PackedBlocks.CHEST_TIER_2, Packed.ITEM_GROUP);
        CHEST_TIER_3 = new WoodItemVariants<>(ChestTiers.TIER3, ChestTiers::identifier, PackedBlocks.CHEST_TIER_3, Packed.ITEM_GROUP);
    }

    public static void register() {
        // keep so class will get called and initialized
    }

}
