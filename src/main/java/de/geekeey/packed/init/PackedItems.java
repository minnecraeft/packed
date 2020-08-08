package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.init.helpers.BarrelTiers;
import de.geekeey.packed.init.helpers.BarrelVariantItems;
import de.geekeey.packed.init.helpers.ChestTiers;
import de.geekeey.packed.init.helpers.ChestVariantItems;

public class PackedItems {

    public static final BarrelVariantItems BARREL_DEFAULT_TIER;
    public static final BarrelVariantItems BARREL_TIER_1;
    public static final BarrelVariantItems BARREL_TIER_2;
    public static final BarrelVariantItems BARREL_TIER_3;

    public static final ChestVariantItems CHEST_DEFAULT_TIER;
    public static final ChestVariantItems CHEST_TIER_1;
    public static final ChestVariantItems CHEST_TIER_2;
    public static final ChestVariantItems CHEST_TIER_3;

    static {
        BARREL_DEFAULT_TIER = new BarrelVariantItems(BarrelTiers.DEFAULT, PackedBlocks.BARREL_DEFAULT_TIER, Packed.packed);
        BARREL_TIER_1 = new BarrelVariantItems(BarrelTiers.TIER1, PackedBlocks.BARREL_TIER_1, Packed.packed);
        BARREL_TIER_2 = new BarrelVariantItems(BarrelTiers.TIER2, PackedBlocks.BARREL_TIER_2, Packed.packed);
        BARREL_TIER_3 = new BarrelVariantItems(BarrelTiers.TIER3, PackedBlocks.BARREL_TIER_3, Packed.packed);

        CHEST_DEFAULT_TIER = new ChestVariantItems(ChestTiers.DEFAULT, PackedBlocks.CHEST_DEFAULT_TIER, Packed.packed);
        CHEST_TIER_1 = new ChestVariantItems(ChestTiers.TIER1, PackedBlocks.CHEST_TIER_1, Packed.packed);
        CHEST_TIER_2 = new ChestVariantItems(ChestTiers.TIER2, PackedBlocks.CHEST_TIER_2, Packed.packed);
        CHEST_TIER_3 = new ChestVariantItems(ChestTiers.TIER3, PackedBlocks.CHEST_TIER_3, Packed.packed);
    }

    public static void register() {
        // keep so class will get called and initialized
    }

}
