package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

import static net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder.createSimple;

public interface ChestTier {

    SimpleRegistry<ChestTier> REGISTRY = createSimple(ChestTier.class, Packed.id("chest_tiers")).buildAndRegister();

    Identifier identifier();

    int rows();

    int columns();

}
