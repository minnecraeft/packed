package com.github.minnecraeft.packed;

import com.github.minnecraeft.packed.init.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Packed implements ModInitializer {

    public static final String IDENTIFIER = "packed";

    public static Identifier identifier(String path) {
        return new Identifier(IDENTIFIER, path);
    }

    @Override
    public void onInitialize() {
        PackedEntities.register();
        PackedBlocks.register();
        PackedItems.register();
        PackedRecipes.register();
        PackedScreenHandlers.register();
    }
}
