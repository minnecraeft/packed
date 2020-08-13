package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.entity.CustomBarrelEntity;

/**
 * BarrelTier represents one tier witch has specific properties like custom texture or custom size. These attributes
 * must be reflected in the {@link BarrelTier#identifier()}. The {@link CustomBarrelEntity} corresponding to the tier
 * will always be constructed using the {@link BarrelTier#newBlockEntity()} factory method.
 */
public interface BarrelTier {

    /**
     * Creates a new {@link CustomBarrelEntity} for the current {@link BarrelTier}.
     *
     * @return a newly instantiated Block Entity
     */
    CustomBarrelEntity newBlockEntity();

    /**
     * The identifier identifies a concrete BarrelTier uniquely.
     *
     * @return the identifier for the current tier
     */
    String identifier();

}
