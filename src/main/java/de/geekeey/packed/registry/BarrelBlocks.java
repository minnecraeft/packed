package de.geekeey.packed.registry;

import de.geekeey.packed.block.CustomBarrel;
import de.geekeey.packed.enums.BarrelTiers;
import de.geekeey.packed.initialisers.Initializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.block.Blocks.CHEST;

public class BarrelBlocks {
    public Block oak;
    public Block birch;
    public Block spruce;
    public Block acacia;
    public Block jungle;
    public Block darkOak;

    public Set<Block> all = new HashSet<>();

    public BarrelBlocks(BarrelTiers tiers) {
        oak = register("oak_barrel_"+tiers.name().toLowerCase(), createBarrel(tiers));
        birch = register("birch_barrel_"+tiers.name().toLowerCase(), createBarrel(tiers));
        spruce = register("spruce_barrel_"+tiers.name().toLowerCase(), createBarrel(tiers));
        acacia = register("acacia_barrel_"+tiers.name().toLowerCase(), createBarrel(tiers));
        jungle = register("jungle_barrel_"+tiers.name().toLowerCase(), createBarrel(tiers));
        darkOak = register("dark_oak_barrel_"+tiers.name().toLowerCase(), createBarrel(tiers));
    }

    private static <T extends Block> T register(String identifier, T block) {
        return Registry.register(Registry.BLOCK, Initializer.id(identifier), block);
    }

    private CustomBarrel createBarrel(BarrelTiers tiers) {
        CustomBarrel custombarrel = new CustomBarrel(FabricBlockSettings.copyOf(CHEST), tiers.factory());
        all.add(custombarrel);
        return custombarrel;
    }
}
