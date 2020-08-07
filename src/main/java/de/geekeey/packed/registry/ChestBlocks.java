package de.geekeey.packed.registry;

import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.enums.ChestTiers;
import de.geekeey.packed.initialisers.Initializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.block.Blocks.CHEST;

public class ChestBlocks {
    public Block oak;
    public Block birch;
    public Block spruce;
    public Block acacia;
    public Block jungle;
    public Block darkOak;

    public Set<Block> all = new HashSet<>();

    public ChestBlocks(ChestTiers tiers) {
        oak = register("oak_chest_"+tiers.name().toLowerCase(), createChest(tiers));
        birch = register("birch_chest_"+tiers.name().toLowerCase(), createChest(tiers));
        spruce = register("spruce_chest_"+tiers.name().toLowerCase(), createChest(tiers));
        acacia = register("acacia_chest_"+tiers.name().toLowerCase(), createChest(tiers));
        jungle = register("jungle_chest_"+tiers.name().toLowerCase(), createChest(tiers));
        darkOak = register("dark_oak_chest_"+tiers.name().toLowerCase(), createChest(tiers));
    }

    private static <T extends Block> T register(String identifier, T block) {
        return Registry.register(Registry.BLOCK, Initializer.id(identifier), block);
    }

    private CustomChest createChest(ChestTiers tiers) {
        CustomChest customChest = new CustomChest(FabricBlockSettings.copyOf(CHEST), tiers.supplier(), tiers.factory());
        all.add(customChest);
        return customChest;
    }
}
