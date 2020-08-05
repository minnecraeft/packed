package de.geekeey.packed.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TestContainerEntityRenderer extends BlockEntityRenderer<TestContainerEntity> {
    //Music disc we want to render
    private static ItemStack stack = new ItemStack(Items.MUSIC_DISC_13, 1);

    public TestContainerEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(TestContainerEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
            matrices.translate(0.5, 1.01, 0.5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 8));
            matrices.multiply(Vector3f.NEGATIVE_X.getDegreesQuaternion(90));
            matrices.scale(1.2f, 1, 1.2f);

            int lightabove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightabove, overlay, matrices, vertexConsumers);
            matrices.pop();
    }
}
