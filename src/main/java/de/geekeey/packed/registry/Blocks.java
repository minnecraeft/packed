package de.geekeey.packed.registry;

import de.geekeey.packed.enums.BarrelTiers;
import de.geekeey.packed.enums.ChestTiers;

public class Blocks {

    public static final ChestBlocks CHEST_DEFAULT_TIER;
    public static final ChestBlocks CHEST_TIER_1;
    public static final ChestBlocks CHEST_TIER_2;
    public static final ChestBlocks CHEST_TIER_3;

    public static final BarrelBlocks BARREL_DEFAULT_TIER;
    public static final BarrelBlocks BARREL_TIER_1;
    public static final BarrelBlocks BARREL_TIER_2;
    public static final BarrelBlocks BARREL_TIER_3;

    static {
        CHEST_DEFAULT_TIER = new ChestBlocks(ChestTiers.DEFAULT);
        CHEST_TIER_1 = new ChestBlocks(ChestTiers.TIER1);
        CHEST_TIER_2 = new ChestBlocks(ChestTiers.TIER2);
        CHEST_TIER_3 = new ChestBlocks(ChestTiers.TIER3);

        BARREL_DEFAULT_TIER = new BarrelBlocks(BarrelTiers.DEFAULT);
        BARREL_TIER_1 = new BarrelBlocks(BarrelTiers.TIER1);
        BARREL_TIER_2 = new BarrelBlocks(BarrelTiers.TIER2);
        BARREL_TIER_3 = new BarrelBlocks(BarrelTiers.TIER3);
    }

    public static void register() {

    }
}
