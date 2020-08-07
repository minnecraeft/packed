package de.geekeey.packed.enums;

import de.geekeey.packed.block.entity.CustomBarrelEntity;

import java.util.function.Supplier;

public enum BarrelTiers{
    DEFAULT {
        @Override
        public Supplier<CustomBarrelEntity> factory() {
            return CustomBarrelEntity::create3x9;
        }
    }, TIER1 {
        @Override
        public Supplier<CustomBarrelEntity> factory() {
            return CustomBarrelEntity::create4x9;
        }
    }, TIER2 {
        @Override
        public Supplier<CustomBarrelEntity> factory() {
            return CustomBarrelEntity::create5x9;
        }
    }, TIER3 {
        @Override
        public Supplier<CustomBarrelEntity> factory() {
            return CustomBarrelEntity::create6x9;
        }
    };

    public abstract Supplier<CustomBarrelEntity> factory();

}
