package de.geekeey.packed.init.helpers;

public enum WoodVariants implements WoodVariant {

    OAK("oak"),
    SPRUCE("spruce"),
    BIRCH("birch"),
    ACACIA("acacia"),
    JUNGLE("jungle"),
    DARK_OAK("dark_oak"),
    CRIMSON("crimson"),
    WARPED("warped");

    private final String identifier;

    WoodVariants(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
