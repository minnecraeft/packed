package de.geekeey.packed;

import de.geekeey.packed.init.PackedBlocks;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.PackedItems;
import de.geekeey.packed.init.PackedRecipes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Packed implements ModInitializer {

    public static final String ID = "packed";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        PackedEntities.register();
        PackedBlocks.register();
        PackedItems.register();
        PackedRecipes.register();
    }
}
