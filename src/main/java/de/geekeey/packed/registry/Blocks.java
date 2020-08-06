package de.geekeey.packed.registry;

import de.geekeey.packed.block.CustomBarrel;
import de.geekeey.packed.block.TestContainer;
import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class Blocks {
    public static final Block TEST_CONTAINER;
    public static final Block CUSTOM_BARREL4x9;
    public static final Block CUSTOM_BARREL5x9;
    public static final Block CUSTOM_BARREL6x9;

    static {
        TEST_CONTAINER = new TestContainer(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.CHEST));
        CUSTOM_BARREL4x9 = new CustomBarrel(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.BARREL), CustomBarrelEntity::create4x9);
        CUSTOM_BARREL5x9 = new CustomBarrel(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.BARREL), CustomBarrelEntity::create5x9);
        CUSTOM_BARREL6x9 = new CustomBarrel(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.BARREL), CustomBarrelEntity::create6x9);

    }

    public static void register() {
        Registry.register(Registry.BLOCK, Initializer.id("test_container"), TEST_CONTAINER);
        Registry.register(Registry.BLOCK, Initializer.id("custom_barrel4x9"), CUSTOM_BARREL4x9);
        Registry.register(Registry.BLOCK, Initializer.id("custom_barrel5x9"), CUSTOM_BARREL5x9);
        Registry.register(Registry.BLOCK, Initializer.id("custom_barrel6x9"), CUSTOM_BARREL6x9);
    }

}
