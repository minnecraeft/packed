package de.geekeey.packed;

import de.geekeey.packed.client.GenericScreen;
import de.geekeey.packed.init.PackedScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class PackedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(PackedScreenHandlers.GENERIC, GenericScreen::new);
    }
}
