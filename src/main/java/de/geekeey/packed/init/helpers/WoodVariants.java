package de.geekeey.packed.init.helpers;


import de.geekeey.packed.Packed;
import net.minecraft.util.Identifier;
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
            Registry.register(WoodVariant.REGISTRY,variant.getIdentifier(),variant);
        }
    }

    private final Identifier identifier;

    WoodVariants(String id) {
        this.identifier = Packed.id(id);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }
}
