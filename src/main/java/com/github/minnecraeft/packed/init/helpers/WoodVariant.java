package com.github.minnecraeft.packed.init.helpers;

import com.github.minnecraeft.packed.Packed;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;

import static net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder.createSimple;


/**
 * WoodVariant represents one kind of wood. The vanilla kinds are defined in {@link WoodVariants}.
 */
public interface WoodVariant {
    SimpleRegistry<WoodVariant> REGISTRY = createSimple(WoodVariant.class, Packed.identifier("wood_variants")).buildAndRegister();

    /**
     * The identifier identifies a concrete WoodVariant uniquely.
     *
     * @return the identifier for the current variant of wood
     */
    Identifier getIdentifier();
}
