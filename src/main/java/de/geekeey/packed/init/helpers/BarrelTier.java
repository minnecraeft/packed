package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.entity.CustomBarrelEntity;

import java.util.function.Supplier;

public interface BarrelTier {

    Supplier<CustomBarrelEntity> newBlockEntity();

    String identifier(String name);

}
