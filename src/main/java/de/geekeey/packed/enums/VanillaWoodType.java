package de.geekeey.packed.enums;

public enum VanillaWoodType implements WoodType {
    OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK;

    @Override
    public String identifier() {
        return name().toLowerCase();
    }
}
