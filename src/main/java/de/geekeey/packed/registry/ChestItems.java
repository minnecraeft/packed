package de.geekeey.packed.registry;

import de.geekeey.packed.enums.ChestTiers;
import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ChestItems {
    public Item oak;
    public Item  birch;
    public Item  spruce;
    public Item  acacia;
    public Item  jungle;
    public Item  darkOak;

    public ChestItems(ChestTiers tiers, ChestBlocks blocks, ItemGroup group) {
        oak = register("oak_chest_"+tiers.name().toLowerCase(), blocks.oak,group);
        birch = register("birch_chest_"+tiers.name().toLowerCase(), blocks.birch,group);
        spruce = register("spruce_chest_"+tiers.name().toLowerCase(), blocks.spruce,group);
        acacia = register("acacia_chest_"+tiers.name().toLowerCase(), blocks.acacia,group);
        jungle = register("jungle_chest_"+tiers.name().toLowerCase(), blocks.jungle,group);
        darkOak = register("dark_oak_chest_"+tiers.name().toLowerCase(), blocks.darkOak,group);
    }

    private static Item register(String identifier, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, Initializer.id(identifier), new BlockItem(block, new Item.Settings().group(group)));
    }

    public static void register() {
    }

}
