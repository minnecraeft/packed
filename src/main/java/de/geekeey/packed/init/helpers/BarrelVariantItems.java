package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class BarrelVariantItems {

    public final Item oak;
    public final Item birch;
    public final Item spruce;
    public final Item acacia;
    public final Item jungle;
    public final Item darkOak;
    public final Item crimson;
    public final Item warped;

    public BarrelVariantItems(BarrelTier tier, BarrelVariantBlocks blocks, ItemGroup group) {
        oak = register(tier.identifier("oak_barrel"), blocks.oak, group);
        birch = register(tier.identifier("birch_barrel"), blocks.birch, group);
        spruce = register(tier.identifier("spruce_barrel"), blocks.spruce, group);
        acacia = register(tier.identifier("acacia_barrel"), blocks.acacia, group);
        jungle = register(tier.identifier("jungle_barrel"), blocks.jungle, group);
        darkOak = register(tier.identifier("dark_oak_barrel"), blocks.darkOak, group);
        crimson = register(tier.identifier("crimson_barrel"), blocks.crimson, group);
        warped = register(tier.identifier("warped_barrel"), blocks.warped, group);
    }

    private static Item register(String identifier, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, Packed.id(identifier), new BlockItem(block, new Item.Settings().group(group)));
    }
}
