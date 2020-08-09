package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.CustomBarrel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.block.Blocks.CHEST;

public class BarrelVariantBlocks {
    public final Block oak;
    public final Block birch;
    public final Block spruce;
    public final Block acacia;
    public final Block jungle;
    public final Block darkOak;
    public final Block crimson;
    public final Block warped;

    public final Set<Block> variants = new HashSet<>();

    public BarrelVariantBlocks(BarrelTier tier) {
        oak = register(tier.identifier("oak_barrel"), createBarrel(tier));
        birch = register(tier.identifier("birch_barrel"), createBarrel(tier));
        spruce = register(tier.identifier("spruce_barrel"), createBarrel(tier));
        acacia = register(tier.identifier("acacia_barrel"), createBarrel(tier));
        jungle = register(tier.identifier("jungle_barrel"), createBarrel(tier));
        darkOak = register(tier.identifier("dark_oak_barrel"), createBarrel(tier));
        crimson = register(tier.identifier("crimson_barrel"), createBarrel(tier));
        warped = register(tier.identifier("warped_barrel"), createBarrel(tier));
    }

    private static <T extends Block> T register(String identifier, T block) {
        return Registry.register(Registry.BLOCK, Packed.id(identifier), block);
    }

    private CustomBarrel createBarrel(BarrelTier tier) {
        CustomBarrel barrel = new CustomBarrel(FabricBlockSettings.copyOf(CHEST), tier.newBlockEntity());
        variants.add(barrel);
        return barrel;
    }
}
