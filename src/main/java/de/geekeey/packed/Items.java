package de.geekeey.packed;

import de.geekeey.packed.block.TestContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Item TEST_CONTAINER_ITEM;

    static {
        TEST_CONTAINER_ITEM = new BlockItem(Blocks.TEST_CONTAINER, new Item.Settings().group(ItemGroup.DECORATIONS));
    }

    public static void register() {
        Registry.register(Registry.ITEM, Initializer.id("test_container"), TEST_CONTAINER_ITEM);
    }

}
