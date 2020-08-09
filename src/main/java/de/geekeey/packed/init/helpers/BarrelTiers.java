package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.entity.CustomBarrelEntity;

import java.util.function.Supplier;

public enum BarrelTiers implements BarrelTier {

    DEFAULT(CustomBarrelEntity::create3x9),
    TIER1(CustomBarrelEntity::create4x9),
    TIER2(CustomBarrelEntity::create5x9),
    TIER3(CustomBarrelEntity::create6x9);

    private final Supplier<CustomBarrelEntity> supplier;

    BarrelTiers(Supplier<CustomBarrelEntity> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Supplier<CustomBarrelEntity> newBlockEntity() {
        return supplier;
    }

    @Override
    public String identifier(String name) {
        return name + '_' + name().toLowerCase();
    }
}
