package de.geekeey.packed.registry;

import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Item TEST_CONTAINER_ITEM;
    public static final Item CUSTOM_BARREL_ITEM4x9;
    public static final Item CUSTOM_BARREL_ITEM5x9;
    public static final Item CUSTOM_BARREL_ITEM6x9;

    static {
        TEST_CONTAINER_ITEM = new BlockItem(Blocks.TEST_CONTAINER, new Item.Settings().group(ItemGroup.DECORATIONS));
        CUSTOM_BARREL_ITEM4x9 = new BlockItem(Blocks.CUSTOM_BARREL4x9, new Item.Settings().group(ItemGroup.DECORATIONS));
        CUSTOM_BARREL_ITEM5x9 = new BlockItem(Blocks.CUSTOM_BARREL5x9, new Item.Settings().group(ItemGroup.DECORATIONS));
        CUSTOM_BARREL_ITEM6x9 = new BlockItem(Blocks.CUSTOM_BARREL6x9, new Item.Settings().group(ItemGroup.DECORATIONS));
    }

    public static void register() {
        Registry.register(Registry.ITEM, Initializer.id("test_container"), TEST_CONTAINER_ITEM);
        Registry.register(Registry.ITEM, Initializer.id("custom_barrel4x9"), CUSTOM_BARREL_ITEM4x9);
        Registry.register(Registry.ITEM, Initializer.id("custom_barrel5x9"), CUSTOM_BARREL_ITEM5x9);
        Registry.register(Registry.ITEM, Initializer.id("custom_barrel6x9"), CUSTOM_BARREL_ITEM6x9);
    }

}
