package de.geekeey.packed.enums;

import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.registry.BlockEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;

import java.util.function.Supplier;

public enum ChestTiers {
    DEFAULT {
        @Override
        public Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier() {
            return () -> BlockEntities.CHEST_3_9;
        }

        @Override
        public Supplier<CustomChestEntity> factory() {
            return CustomChestEntity::create3x9;
        }
    }, TIER1 {
        @Override
        public Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier() {
            return () -> BlockEntities.CHEST_4_9;
        }

        @Override
        public Supplier<CustomChestEntity> factory() {
            return CustomChestEntity::create4x9;
        }
    }, TIER2 {
        @Override
        public Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier() {
            return () -> BlockEntities.CHEST_5_9;
        }

        @Override
        public Supplier<CustomChestEntity> factory() {
            return CustomChestEntity::create5x9;
        }
    }, TIER3 {
        @Override
        public Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier() {
            return () -> BlockEntities.CHEST_6_9;
        }

        @Override
        public Supplier<CustomChestEntity> factory() {
            return CustomChestEntity::create6x9;
        }
    };

    public abstract java.util.function.Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier();

    public abstract Supplier<CustomChestEntity> factory();

}
