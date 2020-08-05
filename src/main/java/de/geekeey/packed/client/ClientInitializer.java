package de.geekeey.packed.client;

import de.geekeey.packed.BlockEntities;
import de.geekeey.packed.ScreenHandler;
import de.geekeey.packed.block.entity.TestContainerEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.screen.GenericContainerScreenHandler;

@Environment(EnvType.CLIENT)
public class ClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ScreenHandler.GENERIC3x18,GenericScreen::new);
        ScreenRegistry.register(ScreenHandler.BASIC, BasicScreen::new);

        BlockEntityRendererRegistry.INSTANCE.register(BlockEntities.TEST_CONTAINER_ENTITY, TestContainerEntityRenderer::new);
    }
}
