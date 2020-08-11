package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.init.PackedEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public enum ChestTiers implements ChestTier {

    DEFAULT("default", CustomChestEntity::create3x9, () -> PackedEntities.CHEST_3_9),
    TIER1("tier1", CustomChestEntity::create4x9, () -> PackedEntities.CHEST_4_9),
    TIER2("tier2", CustomChestEntity::create5x9, () -> PackedEntities.CHEST_5_9),
    TIER3("tier3", CustomChestEntity::create6x9, () -> PackedEntities.CHEST_6_9);

    private final String identifier;
    private final Supplier<BlockEntityType<? extends ChestBlockEntity>> type;
    private final Supplier<CustomChestEntity> supplier;

    ChestTiers(String identifier, Supplier<CustomChestEntity> supplier, Supplier<BlockEntityType<? extends ChestBlockEntity>> type) {
        this.identifier = identifier;
        this.supplier = supplier;
        this.type = type;
    }

    public static Identifier identifier(ChestTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_chest_%s", variant.identifier(), tier.identifier()));
    }

    @Override
    public Supplier<BlockEntityType<? extends ChestBlockEntity>> getBlockEntityType() {
        return type;
    }

    @Override
    public CustomChestEntity newBlockEntity() {
        return supplier.get();
    }

    @Override
    public String identifier() {
        return identifier;
    }

}
