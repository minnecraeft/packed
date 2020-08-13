package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.entity.CustomChestEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;

import java.util.function.Supplier;

/**
 * ChestTier represents one tier witch has specific properties like custom texture or custom size. These attributes
 * must be reflected in the {@link ChestTier#identifier()}. The {@link CustomChestEntity} corresponding to the tier
 * will always be constructed using the {@link ChestTier#newBlockEntity()} factory method and the
 * {@link ChestTier#getBlockEntityType()} as parameter for the {@link ChestBlockEntity#ChestBlockEntity(BlockEntityType)}.
 */
public interface ChestTier {

    /**
     * Supplies the {@link BlockEntityType} as required by the constructor of {@link ChestBlockEntity}.
     *
     * @return The required supplier with the correct entity type for the current {@link CustomChestEntity}
     */
    Supplier<BlockEntityType<? extends ChestBlockEntity>> getBlockEntityType();

    /**
     * Creates a new CustomChestEntity for the current ChestTier.
     *
     * @return a newly instantiated Block Entity
     */
    CustomChestEntity newBlockEntity();

    /**
     * The identifier identifies a concrete ChestTier uniquely.
     *
     * @return the identifier for the current tier
     */
    String identifier();

}
