package com.github.minnecraeft.packed.init.helpers;

import com.github.minnecraeft.packed.Packed;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public enum StorageTiers implements StorageTier {

    DEFAULT("default", 1, 9, 3, 32),
    TIER_1("tier1", 2, 9, 4, 64),
    TIER_2("tier2", 3, 9, 5, 96),
    TIER_3("tier3", -1, 9, 6, 128);

    private final Identifier identifier;
    private final int upgrade;
    private final int inventoryWidth;
    private final int inventoryHeight;
    private final int stackSize;

    StorageTiers(String id, int upgrade, int inventoryWidth, int inventoryHeight, int stackSize) {
        this.identifier = Packed.identifier(id);
        this.upgrade = upgrade;
        this.inventoryWidth = inventoryWidth;
        this.inventoryHeight = inventoryHeight;
        this.stackSize = stackSize;
    }

    static {
        for (StorageTier tier : values()) {
            Registry.register(REGISTRY, tier.getIdentifier(), tier);
        }
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public Optional<StorageTier> getUpgradeTier() {
        return Optional.ofNullable(check(this.upgrade, values()));
    }

    @Override
    public int getInventoryWidth() {
        return inventoryWidth;
    }

    @Override
    public int getInventoryHeight() {
        return inventoryHeight;
    }

    @Override
    public int getStackAmount() {
        return stackSize;
    }

    private static @Nullable <T> T check(int index, T[] values) {
        if (index < 0 || index >= values().length) return null;
        return values[index];
    }

}
