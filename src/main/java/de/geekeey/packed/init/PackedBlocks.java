package de.geekeey.packed.init;

import de.geekeey.packed.block.CustomBarrel;
import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.block.StorageBarrel;
import de.geekeey.packed.init.helpers.BarrelTier;
import de.geekeey.packed.init.helpers.BarrelTiers;
import de.geekeey.packed.init.helpers.ChestTier;
import de.geekeey.packed.init.helpers.ChestTiers;
import de.geekeey.packed.init.helpers.StorageBarrelTier;
import de.geekeey.packed.init.helpers.StorageBarrelTiers;
import de.geekeey.packed.init.helpers.WoodBlockVariants;

public class PackedBlocks {

    public static final WoodBlockVariants<ChestTier, CustomChest> CHEST_DEFAULT_TIER;
    public static final WoodBlockVariants<ChestTier, CustomChest> CHEST_TIER_1;
    public static final WoodBlockVariants<ChestTier, CustomChest> CHEST_TIER_2;
    public static final WoodBlockVariants<ChestTier, CustomChest> CHEST_TIER_3;

    public static final WoodBlockVariants<BarrelTier, CustomBarrel> BARREL_DEFAULT_TIER;
    public static final WoodBlockVariants<BarrelTier, CustomBarrel> BARREL_TIER_1;
    public static final WoodBlockVariants<BarrelTier, CustomBarrel> BARREL_TIER_2;
    public static final WoodBlockVariants<BarrelTier, CustomBarrel> BARREL_TIER_3;

    public static final WoodBlockVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_DEFAULT;
    public static final WoodBlockVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_TIER_1;
    public static final WoodBlockVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_TIER_2;
    public static final WoodBlockVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_TIER_3;

    static {
        CHEST_DEFAULT_TIER = new WoodBlockVariants<>(ChestTiers.DEFAULT, ChestTiers::identifier, CustomChest::new);
        CHEST_TIER_1 = new WoodBlockVariants<>(ChestTiers.TIER_1, ChestTiers::identifier, CustomChest::new);
        CHEST_TIER_2 = new WoodBlockVariants<>(ChestTiers.TIER_2, ChestTiers::identifier, CustomChest::new);
        CHEST_TIER_3 = new WoodBlockVariants<>(ChestTiers.TIER_3, ChestTiers::identifier, CustomChest::new);

        BARREL_DEFAULT_TIER = new WoodBlockVariants<>(BarrelTiers.DEFAULT, BarrelTiers::identifier, CustomBarrel::new);
        BARREL_TIER_1 = new WoodBlockVariants<>(BarrelTiers.TIER1, BarrelTiers::identifier, CustomBarrel::new);
        BARREL_TIER_2 = new WoodBlockVariants<>(BarrelTiers.TIER2, BarrelTiers::identifier, CustomBarrel::new);
        BARREL_TIER_3 = new WoodBlockVariants<>(BarrelTiers.TIER3, BarrelTiers::identifier, CustomBarrel::new);

        STORAGE_BARREL_DEFAULT = new WoodBlockVariants<>(StorageBarrelTiers.DEFAULT, StorageBarrelTiers::identifier, StorageBarrel::new);
        STORAGE_BARREL_TIER_1 = new WoodBlockVariants<>(StorageBarrelTiers.TIER_1, StorageBarrelTiers::identifier, StorageBarrel::new);
        STORAGE_BARREL_TIER_2 = new WoodBlockVariants<>(StorageBarrelTiers.TIER_2, StorageBarrelTiers::identifier, StorageBarrel::new);
        STORAGE_BARREL_TIER_3 = new WoodBlockVariants<>(StorageBarrelTiers.TIER_3, StorageBarrelTiers::identifier, StorageBarrel::new);
    }

    public static void register() {
        // keep so class will get called and initialized
    }
}
