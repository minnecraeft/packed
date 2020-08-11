package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.entity.CustomBarrelEntity;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public enum BarrelTiers implements BarrelTier {

    DEFAULT("default", CustomBarrelEntity::create3x9),
    TIER1("tier1", CustomBarrelEntity::create4x9),
    TIER2("tier2", CustomBarrelEntity::create5x9),
    TIER3("tier3", CustomBarrelEntity::create6x9);

    private final String identifier;
    private final Supplier<CustomBarrelEntity> supplier;

    BarrelTiers(String identifier, Supplier<CustomBarrelEntity> supplier) {
        this.identifier = identifier;
        this.supplier = supplier;
    }

    public static Identifier identifier(BarrelTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_barrel_%s", variant.identifier(), tier.identifier()));
    }

    @Override
    public CustomBarrelEntity newBlockEntity() {
        return supplier.get();
    }

    @Override
    public String identifier() {
        return identifier;
    }
}
