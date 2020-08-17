package de.geekeey.packed.init.helpers;


import net.minecraft.util.registry.Registry;

/**
 * The default implementation for the {@link WoodVariant WoodVariants} which are representing the vanilla variants.
 */
public enum WoodVariants implements WoodVariant {

    /**
     * The vanilla oak variant.
     */
    OAK("oak"),
    /**
     * The vanilla spruce variant.
     */
    SPRUCE("spruce"),
    /**
     * The vanilla birch variant.
     */
    BIRCH("birch"),
    /**
     * The vanilla acacia variant.
     */
    ACACIA("acacia"),
    /**
     * The vanilla jungle variant.
     */
    JUNGLE("jungle"),
    /**
     * The vanilla dark_oak variant.
     */
    DARK_OAK("dark_oak"),
    /**
     * The vanilla crimson variant.
     */
    CRIMSON("crimson"),
    /**
     * The vanilla warped variant.
     */
    WARPED("warped");

    static {
        for (WoodVariant variant : values()){
            Registry.register(WoodVariant.REGISTRY,variant.identifier(),variant);
        }
    }

    private final String identifier;

    WoodVariants(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String identifier() {
        return identifier;
    }
}
