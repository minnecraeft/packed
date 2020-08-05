package de.geekeey.packed.initialisers;

import de.geekeey.packed.client.GenericScreen;
import de.geekeey.packed.registry.ScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ScreenHandler.GENERIC, GenericScreen::new);
    }
}
