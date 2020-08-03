package de.geekeey.packed.client;

import de.geekeey.packed.ScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ScreenHandler.GENERIC3x18,GenericScreen::new);
        ScreenRegistry.register(ScreenHandler.GENERIC6x9,GenericScreen::new);
    }
}
