package de.geekeey.packed;

import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.client.GenericScreen;
import de.geekeey.packed.client.render.CustomChestEntityRenderer;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.PackedItems;
import de.geekeey.packed.init.PackedScreenHandlers;
import de.geekeey.packed.init.helpers.ChestTier;
import de.geekeey.packed.init.helpers.ChestTiers;
import de.geekeey.packed.init.helpers.WoodItemVariants;
import de.geekeey.packed.init.helpers.WoodVariants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

@Environment(EnvType.CLIENT)
public class PackedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(PackedScreenHandlers.GENERIC, GenericScreen::new);

        // register entity type renderer and second register item rendere to use this entity type renderer internally
        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_3_9, PackedClient::createDefaultRenderer);
        register(PackedItems.CHEST_DEFAULT_TIER, CustomChestEntity.create3x9());

        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_4_9, PackedClient::createDefaultRenderer);
        register(PackedItems.CHEST_TIER_1, CustomChestEntity.create4x9());

        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_5_9, PackedClient::createDefaultRenderer);
        register(PackedItems.CHEST_TIER_2, CustomChestEntity.create5x9());

        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CHEST_6_9, PackedClient::createDefaultRenderer);
        register(PackedItems.CHEST_TIER_3, CustomChestEntity.create6x9());

        //registration of chest textures
        for (WoodVariants value : WoodVariants.values()) {
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/"+value.identifier()+"/normal")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/"+value.identifier()+"/left")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/"+value.identifier()+"/right")));
        }

        //Registration of lock textures
        for (ChestTiers value : ChestTiers.values()) {
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/"+value.identifier()+"_normal")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/"+value.identifier()+"_left")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/"+value.identifier()+"_right")));
        }
    }

    private static CustomChestEntityRenderer createDefaultRenderer(BlockEntityRenderDispatcher dispatcher) {
        return new CustomChestEntityRenderer(dispatcher, Blocks.CHEST);
    }

    private static void register(WoodItemVariants<ChestTier, CustomChest> items, CustomChestEntity entity) {
        items.forEach(item -> {
            Block block = item.getBlock();
            CustomChestEntityRenderer renderer = new CustomChestEntityRenderer(BlockEntityRenderDispatcher.INSTANCE, block);
            BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, matrices, vertices, light, overlay) -> {
                renderer.render(entity, 0.0f, matrices, vertices, light, overlay);
            });
        });
    }

}
