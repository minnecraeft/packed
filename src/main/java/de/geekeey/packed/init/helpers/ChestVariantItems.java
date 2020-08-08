package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ChestVariantItems {

    public final Item oak;
    public final Item birch;
    public final Item spruce;
    public final Item acacia;
    public final Item jungle;
    public final Item darkOak;

    public ChestVariantItems(ChestTier tier, ChestVariantBlocks blocks, ItemGroup group) {
        oak = register(tier.identifier("oak_chest"), blocks.oak, group);
        birch = register(tier.identifier("birch_chest"), blocks.birch, group);
        spruce = register(tier.identifier("spruce_chest"), blocks.spruce, group);
        acacia = register(tier.identifier("acacia_chest"), blocks.acacia, group);
        jungle = register(tier.identifier("jungle_chest"), blocks.jungle, group);
        darkOak = register(tier.identifier("dark_oak_chest"), blocks.darkOak, group);
    }

    private static Item register(String identifier, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, Packed.id(identifier), new BlockItem(block, new Item.Settings().group(group)));
    }

}
