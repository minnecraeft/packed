package de.geekeey.packed;

import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Initializer implements ModInitializer {

    public static final String ID = "packed";

    @Override
    public void onInitialize() {
        BlockEntities.register();
        Blocks.register();
        Items.register();


    }

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }
}
