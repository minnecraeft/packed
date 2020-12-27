package com.github.minnecraeft.packed.init;

import com.github.minnecraeft.packed.Packed;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public final class RegistryHelpers {

    private RegistryHelpers() {
    }

    static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, Packed.identifier(name), item);
    }


}
