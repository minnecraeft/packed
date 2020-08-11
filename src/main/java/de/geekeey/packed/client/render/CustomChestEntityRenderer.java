package de.geekeey.packed.client.render;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.block.entity.CustomChestEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CustomChestEntityRenderer extends BlockEntityRenderer<CustomChestEntity> {
    private static final Identifier CHEST_ATLAS_TEXTURE = TexturedRenderLayers.CHEST_ATLAS_TEXTURE;

    private final ModelPart lidSingleModel;
    private final ModelPart baseSingleModel;
    private final ModelPart lockSingleModel;

    private final ModelPart lidRightModel;
    private final ModelPart baseRightModel;
    private final ModelPart lockRightModel;

    private final ModelPart lidLeftModel;
    private final ModelPart baseLeftModel;
    private final ModelPart lockLeftModel;

    private final Block fallback;

    public CustomChestEntityRenderer(BlockEntityRenderDispatcher dispatcher, Block fallback) {
        super(dispatcher);
        this.fallback = fallback;

        this.baseSingleModel = new ModelPart(64, 64, 0, 19);
        this.baseSingleModel.addCuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);

        this.lidSingleModel = new ModelPart(64, 64, 0, 0);
        this.lidSingleModel.addCuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.lidSingleModel.pivotY = 9.0F;
        this.lidSingleModel.pivotZ = 1.0F;

        this.lockSingleModel = new ModelPart(64, 64, 0, 0);
        this.lockSingleModel.addCuboid(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.lockSingleModel.pivotY = 8.0F;

        this.baseRightModel = new ModelPart(64, 64, 0, 19);
        this.baseRightModel.addCuboid(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);

        this.lidRightModel = new ModelPart(64, 64, 0, 0);
        this.lidRightModel.addCuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.lidRightModel.pivotY = 9.0F;
        this.lidRightModel.pivotZ = 1.0F;

        this.lockRightModel = new ModelPart(64, 64, 0, 0);
        this.lockRightModel.addCuboid(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.lockRightModel.pivotY = 8.0F;

        this.baseLeftModel = new ModelPart(64, 64, 0, 19);
        this.baseLeftModel.addCuboid(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);

        this.lidLeftModel = new ModelPart(64, 64, 0, 0);
        this.lidLeftModel.addCuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.lidLeftModel.pivotY = 9.0F;
        this.lidLeftModel.pivotZ = 1.0F;

        this.lockLeftModel = new ModelPart(64, 64, 0, 0);
        this.lockLeftModel.addCuboid(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.lockLeftModel.pivotY = 8.0F;
    }

    public void render(CustomChestEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        World world = entity.getWorld();
        boolean cache = world != null;
        BlockState state = cache ? entity.getCachedState() : fallback.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType type = state.contains(ChestBlock.CHEST_TYPE) ? state.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        Block block = state.getBlock();

        if (block instanceof CustomChest) {
            CustomChest chestBlock = (CustomChest) block;
            boolean doubleChest = type != ChestType.SINGLE;
            matrices.push();

            float facing = state.get(ChestBlock.FACING).asRotation();
            matrices.translate(0.5D, 0.5D, 0.5D);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-facing));
            matrices.translate(-0.5D, -0.5D, -0.5D);

            DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> source;
            if (cache) {
                source = chestBlock.getBlockEntitySource(state, world, entity.getPos(), true);
            } else {
                source = DoubleBlockProperties.PropertyRetriever::getFallback;
            }

            float g = source.apply(ChestBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
            g = 1.0F - g;
            g = 1.0F - g * g * g;
            int i = source.apply(new LightmapCoordinatesRetriever<>()).applyAsInt(light);

            SpriteIdentifier sprite = getChestTexture(chestBlock, type);
            VertexConsumer consumer = sprite.getVertexConsumer(vertices, RenderLayer::getEntityCutout);

            SpriteIdentifier lockSprite = getLockTexture(chestBlock, type);
            VertexConsumer lockConsumer = lockSprite.getVertexConsumer(vertices, RenderLayer::getEntityCutout);

            if (doubleChest) {
                if (type == ChestType.LEFT) {
                    this.renderParts(matrices, consumer, lockConsumer, this.lidLeftModel, this.lockLeftModel, this.baseLeftModel, g, i, overlay);
                } else {
                    this.renderParts(matrices, consumer, lockConsumer, this.lidRightModel, this.lockRightModel, this.baseRightModel, g, i, overlay);
                }
            } else {
                this.renderParts(matrices, consumer, lockConsumer, this.lidSingleModel, this.lockSingleModel, this.baseSingleModel, g, i, overlay);
            }

            matrices.pop();
        }
    }

    private void renderParts(MatrixStack matrices, VertexConsumer chestVerticies, VertexConsumer lockVerticies, ModelPart lidModel, ModelPart lockModel, ModelPart baseModel, float f, int light, int overlay) {
        lidModel.pitch = -(f * 1.5707964F);
        lockModel.pitch = lidModel.pitch;
        lidModel.render(matrices, chestVerticies, light, overlay);
        lockModel.render(matrices, lockVerticies, light, overlay);
        baseModel.render(matrices, chestVerticies, light, overlay);
    }

    private static SpriteIdentifier getChestTexture(CustomChest chest, ChestType type) {
        switch (type) {
            case LEFT:
                return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, Packed.id("entity/chest/" + chest.getVariant().getIdentifier() + "/left"));
            case RIGHT:
                return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, Packed.id("entity/chest/" + chest.getVariant().getIdentifier() + "/right"));
            case SINGLE:
            default:
                return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, Packed.id("entity/chest/" + chest.getVariant().getIdentifier() + "/normal"));
        }
    }

    private static SpriteIdentifier getLockTexture(CustomChest chest, ChestType type) {
        switch (type) {
            case LEFT:
                return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, Packed.id("entity/chest/" + chest.getTier().identifier() + "_left"));
            case RIGHT:
                return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, Packed.id("entity/chest/" + chest.getTier().identifier() + "_right"));
            case SINGLE:
            default:
                return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, Packed.id("entity/chest/" + chest.getTier().identifier() + "_normal"));
        }
    }

}
