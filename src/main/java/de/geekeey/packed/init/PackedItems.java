package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.CustomBarrel;
import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.block.StorageBarrel;
import de.geekeey.packed.init.helpers.BarrelTier;
import de.geekeey.packed.init.helpers.BarrelTiers;
import de.geekeey.packed.init.helpers.ChestTier;
import de.geekeey.packed.init.helpers.ChestTiers;
import de.geekeey.packed.init.helpers.StorageBarrelTier;
import de.geekeey.packed.init.helpers.StorageBarrelTiers;
import de.geekeey.packed.init.helpers.WoodItemVariants;
import de.geekeey.packed.item.StorageUpgrader;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class PackedItems {

    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_DEFAULT_TIER;
    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_TIER_1;
    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_TIER_2;
    public static final WoodItemVariants<BarrelTier, CustomBarrel> BARREL_TIER_3;

    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_DEFAULT_TIER;
    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_TIER_1;
    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_TIER_2;
    public static final WoodItemVariants<ChestTier, CustomChest> CHEST_TIER_3;

    public static final WoodItemVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_DEFAULT;
    public static final WoodItemVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_TIER_1;
    public static final WoodItemVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_TIER_2;
    public static final WoodItemVariants<StorageBarrelTier, StorageBarrel> STORAGE_BARREL_TIER_3;

    public static final StorageUpgrader STORAGE_UPGRADER;

    static {
        BARREL_DEFAULT_TIER = new WoodItemVariants<>(BarrelTiers::identifier, PackedBlocks.BARREL_DEFAULT_TIER, Packed.ITEM_GROUP);
        BARREL_TIER_1 = new WoodItemVariants<>(BarrelTiers::identifier, PackedBlocks.BARREL_TIER_1, Packed.ITEM_GROUP);
        BARREL_TIER_2 = new WoodItemVariants<>(BarrelTiers::identifier, PackedBlocks.BARREL_TIER_2, Packed.ITEM_GROUP);
        BARREL_TIER_3 = new WoodItemVariants<>(BarrelTiers::identifier, PackedBlocks.BARREL_TIER_3, Packed.ITEM_GROUP);

        CHEST_DEFAULT_TIER = new WoodItemVariants<>(ChestTiers::identifier, PackedBlocks.CHEST_DEFAULT_TIER, Packed.ITEM_GROUP);
        CHEST_TIER_1 = new WoodItemVariants<>(ChestTiers::identifier, PackedBlocks.CHEST_TIER_1, Packed.ITEM_GROUP);
        CHEST_TIER_2 = new WoodItemVariants<>(ChestTiers::identifier, PackedBlocks.CHEST_TIER_2, Packed.ITEM_GROUP);
        CHEST_TIER_3 = new WoodItemVariants<>(ChestTiers::identifier, PackedBlocks.CHEST_TIER_3, Packed.ITEM_GROUP);

        STORAGE_BARREL_DEFAULT = new WoodItemVariants<>(StorageBarrelTiers::identifier, PackedBlocks.STORAGE_BARREL_DEFAULT, Packed.ITEM_GROUP);
        STORAGE_BARREL_TIER_1 = new WoodItemVariants<>(StorageBarrelTiers::identifier, PackedBlocks.STORAGE_BARREL_TIER_1, Packed.ITEM_GROUP);
        STORAGE_BARREL_TIER_2 = new WoodItemVariants<>(StorageBarrelTiers::identifier, PackedBlocks.STORAGE_BARREL_TIER_2, Packed.ITEM_GROUP);
        STORAGE_BARREL_TIER_3 = new WoodItemVariants<>(StorageBarrelTiers::identifier, PackedBlocks.STORAGE_BARREL_TIER_3, Packed.ITEM_GROUP);

        STORAGE_UPGRADER = Registry.register(Registry.ITEM,Packed.id("storage_upgrader"),new StorageUpgrader(new Item.Settings().group(Packed.ITEM_GROUP)));
    }

    public static void register() {
        // keep so class will get called and initialized
    }

}
