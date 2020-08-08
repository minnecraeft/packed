package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.Packed;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.block.Blocks.CHEST;

public class ChestVariantBlocks {

    public final Block oak;
    public final Block birch;
    public final Block spruce;
    public final Block acacia;
    public final Block jungle;
    public final Block darkOak;

    public final Set<Block> variants = new HashSet<>();

    public ChestVariantBlocks(ChestTier tier) {
        oak = register(tier.identifier("oak_chest"), createChest(tier));
        birch = register(tier.identifier("birch_chest"), createChest(tier));
        spruce = register(tier.identifier("spruce_chest"), createChest(tier));
        acacia = register(tier.identifier("acacia_chest"), createChest(tier));
        jungle = register(tier.identifier("jungle_chest"), createChest(tier));
        darkOak = register(tier.identifier("dark_oak_chest"), createChest(tier));
    }

    private static <T extends Block> T register(String identifier, T block) {
        return Registry.register(Registry.BLOCK, Packed.id(identifier), block);
    }

    private CustomChest createChest(ChestTier tier) {
        CustomChest chest = new CustomChest(FabricBlockSettings.copyOf(CHEST), tier.getBlockEntityType(), tier.newBlockEntity());
        variants.add(chest);
        return chest;
    }
}
