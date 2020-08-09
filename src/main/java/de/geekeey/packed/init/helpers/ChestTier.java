package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.entity.CustomChestEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;

import java.util.function.Supplier;

public interface ChestTier {

    Supplier<BlockEntityType<? extends ChestBlockEntity>> getBlockEntityType();

    Supplier<CustomChestEntity> newBlockEntity();

    String identifier(String name);

}
