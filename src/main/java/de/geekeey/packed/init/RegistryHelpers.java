package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public final class RegistryHelpers {

    private RegistryHelpers() {
    }

    static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, Packed.id(name), item);
    }


}
