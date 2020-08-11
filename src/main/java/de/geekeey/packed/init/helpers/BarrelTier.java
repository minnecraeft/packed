package de.geekeey.packed.init.helpers;

import de.geekeey.packed.block.entity.CustomBarrelEntity;

public interface BarrelTier {

    CustomBarrelEntity newBlockEntity();

    String identifier();

}
