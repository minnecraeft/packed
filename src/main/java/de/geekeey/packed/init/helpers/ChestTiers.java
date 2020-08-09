package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.init.PackedEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;

import java.util.function.Supplier;

public enum ChestTiers implements ChestTier {

    DEFAULT(() -> PackedEntities.CHEST_3_9, CustomChestEntity::create3x9),
    TIER1(() -> PackedEntities.CHEST_4_9, CustomChestEntity::create4x9),
    TIER2(() -> PackedEntities.CHEST_5_9, CustomChestEntity::create5x9),
    TIER3(() -> PackedEntities.CHEST_6_9, CustomChestEntity::create6x9);

    private final Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityTypeSupplier;
    private final Supplier<CustomChestEntity> factory;

    ChestTiers(Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityTypeSupplier, Supplier<CustomChestEntity> factory) {
        this.blockEntityTypeSupplier = blockEntityTypeSupplier;
        this.factory = factory;
    }

    @Override
    public Supplier<BlockEntityType<? extends ChestBlockEntity>> getBlockEntityType() {
        return blockEntityTypeSupplier;
    }

    @Override
    public Supplier<CustomChestEntity> newBlockEntity() {
        return factory;
    }

    @Override
    public String identifier(String name) {
        return name + '_' + name().toLowerCase();
    }

}
