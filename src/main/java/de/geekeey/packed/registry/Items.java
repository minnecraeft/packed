package de.geekeey.packed.registry;

import de.geekeey.packed.enums.BarrelTiers;
import de.geekeey.packed.enums.ChestTiers;
import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final BarrelItems BARREL_DEFAULT_TIER;
    public static final BarrelItems BARREL_TIER_1;
    public static final BarrelItems BARREL_TIER_2;
    public static final BarrelItems BARREL_TIER_3;

    public static final ChestItems CHEST_DEFAULT_TIER;
    public static final ChestItems CHEST_TIER_1;
    public static final ChestItems CHEST_TIER_2;
    public static final ChestItems CHEST_TIER_3;

    static {
        BARREL_DEFAULT_TIER = new BarrelItems(BarrelTiers.DEFAULT, Blocks.BARREL_DEFAULT_TIER, Initializer.packed);
        BARREL_TIER_1 = new BarrelItems(BarrelTiers.TIER1, Blocks.BARREL_TIER_1, Initializer.packed);
        BARREL_TIER_2 = new BarrelItems(BarrelTiers.TIER2, Blocks.BARREL_TIER_2, Initializer.packed);
        BARREL_TIER_3 = new BarrelItems(BarrelTiers.TIER3, Blocks.BARREL_TIER_3, Initializer.packed);

        CHEST_DEFAULT_TIER = new ChestItems(ChestTiers.DEFAULT, Blocks.CHEST_DEFAULT_TIER, Initializer.packed);
        CHEST_TIER_1 = new ChestItems(ChestTiers.TIER1, Blocks.CHEST_TIER_1, Initializer.packed);
        CHEST_TIER_2 = new ChestItems(ChestTiers.TIER2, Blocks.CHEST_TIER_2, Initializer.packed);
        CHEST_TIER_3 = new ChestItems(ChestTiers.TIER3, Blocks.CHEST_TIER_3, Initializer.packed);
    }

    private static Item register(String identifier, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, Initializer.id(identifier), new BlockItem(block, new Item.Settings().group(group)));
    }

    public static void register() {
    }

}
