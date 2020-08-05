package de.geekeey.packed.registry;

import de.geekeey.packed.initialisers.Initializer;
import de.geekeey.packed.block.TestContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class Blocks {
    public static final Block TEST_CONTAINER;

    static {
        TEST_CONTAINER = new TestContainer(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.CHEST));
    }

    public static void register(){
        Registry.register(Registry.BLOCK, Initializer.id("test_container"),TEST_CONTAINER);
    }

}
