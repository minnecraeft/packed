package de.geekeey.packed;

import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.client.GenericScreen;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.PackedItems;
import de.geekeey.packed.init.PackedScreenHandlers;
import de.geekeey.packed.init.helpers.ChestTier;
import de.geekeey.packed.init.helpers.WoodItemVariants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class PackedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(PackedScreenHandlers.GENERIC, GenericScreen::new);

        // register entity type renderer and second register item rendere to use this entity type renderer internally
        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_3_9, ChestBlockEntityRenderer::new);
        register(PackedItems.CHEST_DEFAULT_TIER, CustomChestEntity.create3x9());

        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_4_9, ChestBlockEntityRenderer::new);
        register(PackedItems.CHEST_TIER_1, CustomChestEntity.create4x9());
        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_5_9, ChestBlockEntityRenderer::new);
        register(PackedItems.CHEST_TIER_2, CustomChestEntity.create5x9());
        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_6_9, ChestBlockEntityRenderer::new);
        register(PackedItems.CHEST_TIER_3, CustomChestEntity.create6x9());
    }

    private static void register(WoodItemVariants<ChestTier, CustomChest> items, CustomChestEntity entity) {
        items.forEach(item -> {
            BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, matrices, vertices, light, overlay) -> {
                BlockEntityRenderDispatcher.INSTANCE.renderEntity(entity, matrices, vertices, light, overlay);
            });
        });
    }

}
