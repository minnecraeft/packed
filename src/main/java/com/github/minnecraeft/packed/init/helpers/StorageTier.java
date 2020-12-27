package com.github.minnecraeft.packed.init.helpers;

import com.github.minnecraeft.packed.Packed;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.Optional;

import static net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder.createSimple;

public interface StorageTier {

    SimpleRegistry<StorageTier> REGISTRY = createSimple(StorageTier.class, Packed.identifier("storage_tiers")).buildAndRegister();

    Identifier getIdentifier();

    Optional<StorageTier> getUpgradeTier();

    int getInventoryWidth();

    int getInventoryHeight();

    int getStackAmount();
}
