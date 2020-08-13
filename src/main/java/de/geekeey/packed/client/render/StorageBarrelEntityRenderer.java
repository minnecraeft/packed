package de.geekeey.packed.client.render;

import de.geekeey.packed.block.StorageBarrel;
import de.geekeey.packed.block.entity.StorageBarrelEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;

public class StorageBarrelEntityRenderer extends BlockEntityRenderer<StorageBarrelEntity> {
    public StorageBarrelEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(StorageBarrelEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        var indent = 1/8.0;

        //The direction the barrel is facing, this is important for moving the rendered item in the right place
        var facing = entity.getWorld().getBlockState(entity.getPos()).get(StorageBarrel.FACING);

        //moving the item into the right place depending on the direction
        switch (facing){
            case NORTH : matrices.translate(0.5,0.5,indent);
            case SOUTH : matrices.translate(0.5,0.5,1-indent);
            case WEST : matrices.translate(-indent,0.5,0.5);
            case EAST : matrices.translate(1-indent,0.5,0.5);
        }

        //the item we want to render which is contained in the barrel
        var itemStack = entity.getStack(0);
        //the light level in front of the block
        var realLight = WorldRenderer.getLightmapCoordinates(entity.getWorld(),entity.getPos().add(facing.getVector()));
        MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED,realLight, OverlayTexture.DEFAULT_UV,matrices,vertexConsumers);

        matrices.pop();
    }
}
