package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.Optional;

import static net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder.createSimple;

public interface StorageBarrelTier {
    SimpleRegistry<StorageBarrelTier> REGISTRY = createSimple(StorageBarrelTier.class, Packed.id("storage_barrel_tiers")).buildAndRegister();

    Optional<StorageBarrelTier> upgrade();

    Identifier identifier();

    int capacity();
}
