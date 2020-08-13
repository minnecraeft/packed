package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ChestTiers implements ChestTier {

    DEFAULT("default", 3, 9),
    TIER_1("tier1", 4, 9),
    TIER_2("tier2", 5, 9),
    TIER_3("tier3", 6, 9);

    private final Identifier identifier;
    private final int rows;
    private final int columns;

    ChestTiers(String id, int rows, int columns) {
        this.identifier = Packed.id(id);
        this.rows = rows;
        this.columns = columns;
    }

    static {
        for (ChestTiers tier : values()) {
            Registry.register(ChestTier.REGISTRY, tier.identifier, tier);
        }
    }

    public static Identifier identifier(ChestTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_chest_%s", variant.identifier(), tier.identifier().getPath()));
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return columns;
    }

}
