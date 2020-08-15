package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum StorageBarrelTiers implements StorageBarrelTier {

    DEFAULT("default", 32 * 64),
    TIER_1("tier1", 64 * 64),
    TIER_2("tier2", 96 * 64),
    TIER_3("tier3", 128 * 64);

    private final Identifier identifier;
    private final int capacity;

    StorageBarrelTiers(String id, int capacity) {
        this.identifier = Packed.id(id);
        this.capacity = capacity;
    }

    static {
        for (StorageBarrelTiers tier : values()) {
            Registry.register(StorageBarrelTier.REGISTRY, tier.identifier, tier);
        }
    }

    public static Identifier identifier(StorageBarrelTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_storage_barrel_%s", variant.identifier(), tier.identifier().getPath()));
    }


    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
