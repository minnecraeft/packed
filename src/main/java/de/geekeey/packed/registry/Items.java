package de.geekeey.packed.registry;

import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Item OAK_BARREL;
    public static final Item SPRUCE_BARREL;
    public static final Item BIRCH_BARREL;
    public static final Item JUNGLE_BARREL;
    public static final Item ACACIA_BARREL;
    public static final Item DARK_OAK_BARREL;

    static {
        OAK_BARREL = register("barrel_oak", Blocks.OAK_BARREL, ItemGroup.DECORATIONS);
        SPRUCE_BARREL = register("barrel_spruce", Blocks.SPRUCE_BARREL, ItemGroup.DECORATIONS);
        BIRCH_BARREL = register("barrel_birch", Blocks.BIRCH_BARREL, ItemGroup.DECORATIONS);
        JUNGLE_BARREL = register("barrel_jungle", Blocks.JUNGLE_BARREL, ItemGroup.DECORATIONS);
        ACACIA_BARREL = register("barrel_acacia", Blocks.ACACIA_BARREL, ItemGroup.DECORATIONS);
        DARK_OAK_BARREL = register("barrel_dark_oak", Blocks.DARK_OAK_BARREL, ItemGroup.DECORATIONS);
    }

    private static Item register(String identifier, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, Initializer.id(identifier), new BlockItem(block, new Item.Settings().group(group)));
    }

    public static void register() {
    }

}
