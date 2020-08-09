package de.geekeey.packed.init;

import de.geekeey.packed.init.helpers.BarrelTiers;
import de.geekeey.packed.init.helpers.ChestTiers;
import de.geekeey.packed.init.helpers.BarrelVariantBlocks;
import de.geekeey.packed.init.helpers.ChestVariantBlocks;
import net.fabricmc.loader.FabricLoader;

public class PackedBlocks {

    public static final ChestVariantBlocks CHEST_DEFAULT_TIER;
    public static final ChestVariantBlocks CHEST_TIER_1;
    public static final ChestVariantBlocks CHEST_TIER_2;
    public static final ChestVariantBlocks CHEST_TIER_3;

    public static final BarrelVariantBlocks BARREL_DEFAULT_TIER;
    public static final BarrelVariantBlocks BARREL_TIER_1;
    public static final BarrelVariantBlocks BARREL_TIER_2;
    public static final BarrelVariantBlocks BARREL_TIER_3;

    static {
        CHEST_DEFAULT_TIER = new ChestVariantBlocks(ChestTiers.DEFAULT);
        CHEST_TIER_1 = new ChestVariantBlocks(ChestTiers.TIER1);
        CHEST_TIER_2 = new ChestVariantBlocks(ChestTiers.TIER2);
        CHEST_TIER_3 = new ChestVariantBlocks(ChestTiers.TIER3);

        BARREL_DEFAULT_TIER = new BarrelVariantBlocks(BarrelTiers.DEFAULT);
        BARREL_TIER_1 = new BarrelVariantBlocks(BarrelTiers.TIER1);
        BARREL_TIER_2 = new BarrelVariantBlocks(BarrelTiers.TIER2);
        BARREL_TIER_3 = new BarrelVariantBlocks(BarrelTiers.TIER3);
    }

    public static void register() {
        // keep so class will get called and initialized
    }
}
