package de.geekeey.packed.init.helpers;

/**
 * WoodVariant represents one kind of wood. The vanilla kinds are defined in {@link WoodVariants}.
 */
public interface WoodVariant {
    /**
     * The identifier identifies a concrete WoodVariant uniquely.
     *
     * @return the identifier for the current variant of wood
     */
    String identifier();
}
