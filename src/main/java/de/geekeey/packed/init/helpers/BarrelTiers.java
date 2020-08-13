package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.entity.CustomBarrelEntity;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

/**
 * The default implementation for the {@link BarrelTier BarrelTiers} which are used in this mod.
 */
public enum BarrelTiers implements BarrelTier {

    /**
     * The default tier which should create the default block.
     * In our case a 3 by 9 container.
     */
    DEFAULT("default", CustomBarrelEntity::create3x9),
    /**
     * The tier 1 which has a single row more than the default.
     * In our case a 4 by 9 container.
     */
    TIER1("tier1", CustomBarrelEntity::create4x9),
    /**
     * The tier 2 which has two more rows than the default.
     * In our case a 5 by 9 container.
     */
    TIER2("tier2", CustomBarrelEntity::create5x9),
    /**
     * The tier 3 which has three more rows than the default.
     * In our case a 6 by 9 container.
     * This is the highest tier currently available.
     */
    TIER3("tier3", CustomBarrelEntity::create6x9);

    private final String identifier;
    private final Supplier<CustomBarrelEntity> supplier;

    BarrelTiers(String identifier, Supplier<CustomBarrelEntity> supplier) {
        this.identifier = identifier;
        this.supplier = supplier;
    }

    /**
     * Creates a new {@link Identifier} in the namespace of {@link Packed} which will uniquely identify a CustomBarrel
     * by its {@link BarrelTier} and its {@link WoodVariant}.
     *
     * @param tier    The barrel tier of the barrel
     * @param variant The wood variant of the barrel
     * @return A new Identifier in the form of <code>{variant}_barrel_{tier}</code>
     */
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
