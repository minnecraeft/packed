package de.geekeey.packed.client;

import com.mojang.blaze3d.systems.RenderSystem;
import de.geekeey.packed.Packed;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static java.lang.Math.max;

@Environment(EnvType.CLIENT)
public class GenericScreen extends HandledScreen<ExtendedGenericContainerScreenHandler> {

    private static final int PLAYER_INVENTORY_HEIGHT = 90;
    private static final int PLAYER_INVENTORY_WIDTH = 176;

    private static final int CONTAINER_TOP_PADDING = 17;
    private static final int CONTAINER_BOTTOM_PADDING = 7;

    private static final int CONTAINER_LEFT_PADDING = 7;
    private static final int CONTAINER_RIGHT_PADDING = 7;

    private static final int CONTAINER_HORIZONTAL_PADDING = CONTAINER_LEFT_PADDING + CONTAINER_RIGHT_PADDING;

    private static final int FIX_HEIGHT_MIN = PLAYER_INVENTORY_HEIGHT + CONTAINER_TOP_PADDING + CONTAINER_BOTTOM_PADDING;

    private static final int SLOT_SIZE = 18;

    private static final Identifier TEXTURE = Packed.id("textures/gui/panel.png");
//    private static final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");

    private final int rows;
    private final int columns;

    private final int containerHeight;
    private final int containerWidth;

    private final int containerInventoryOffsetX;
    private final int playerInventoryOffsetX;

    public GenericScreen(ExtendedGenericContainerScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        rows = handler.getRows();
        columns = handler.getColumns();

        containerHeight = 17 + rows * 18 + 7;
        containerWidth = 7 + columns * 18 + 7;

        int containerInventoryWidth = CONTAINER_HORIZONTAL_PADDING + columns * SLOT_SIZE;

        backgroundWidth = max(PLAYER_INVENTORY_WIDTH, containerInventoryWidth);
        backgroundHeight = FIX_HEIGHT_MIN + rows * SLOT_SIZE;

        if (containerInventoryWidth < PLAYER_INVENTORY_WIDTH) {
            containerInventoryOffsetX = (PLAYER_INVENTORY_WIDTH - containerInventoryWidth) / 2;
            playerInventoryOffsetX = 0;
        } else {
            containerInventoryOffsetX = 0;
            playerInventoryOffsetX = (containerInventoryWidth - PLAYER_INVENTORY_WIDTH) / 2;
        }

        // change player inventory title x offset if our container shifts the
        // player inventory to center the whole screen
        playerInventoryTitleX += playerInventoryOffsetX;
        // 94 is the distance from to bottom of the inventory up to the
        // space between the container and the player inventory
        playerInventoryTitleY = backgroundHeight - 94;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE);

        int offsetX = (width - backgroundWidth) / 2;
        int offsetY = (height - backgroundHeight) / 2;

        int containerInventoryOffsetX = this.containerInventoryOffsetX;

        int playerInventoryOffsetX = this.playerInventoryOffsetX;
        int playerInventoryOffsetY = containerHeight;

        int cp = 18;
        int rp = 17;

        drawTexture(matrices, offsetX + containerInventoryOffsetX, offsetY, 0, 0, 25, 17);
        for (int column = 1; column < columns; column++) {
            drawTexture(matrices, offsetX + containerInventoryOffsetX + 7 + cp, offsetY, 7, 0, 18, 17);
            cp += 18;
        }
        drawTexture(matrices, offsetX + containerInventoryOffsetX + 7 + cp, offsetY, 25, 0, 7, 17);

        for (int row = 0; row < rows; row++) {
            cp = 18;
            drawTexture(matrices, offsetX + containerInventoryOffsetX, offsetY + rp, 0, 17, 25, 18);
            for (int column = 1; column < columns; column++) {
                drawTexture(matrices, offsetX + containerInventoryOffsetX + 7 + cp, offsetY + rp, 7, 17, 18, 18);
                cp += 18;
            }
            drawTexture(matrices, offsetX + containerInventoryOffsetX + 7 + cp, offsetY + rp, 25, 17, 7, 18);
            rp += 18;
        }

        int itx = containerInventoryOffsetX / 18;
        int xp = containerInventoryOffsetX + 7;

        if (this.containerInventoryOffsetX > 0) {

            for (int column = 0; column < columns; column++) {
                drawTexture(matrices, offsetX + xp, offsetY + rp, 7, 49, 18, 7);
                xp += 18;
            }

            xp = 7;
            for (int column = 0; column < itx; column++) {
                drawTexture(matrices, offsetX + xp, offsetY + rp, 7, 42, 18, 7);
                drawTexture(matrices, offsetX + PLAYER_INVENTORY_WIDTH - 18 - xp, offsetY + rp, 7, 42, 18, 7);
                xp += 18;
            }

            // corners
            drawTexture(matrices, offsetX, offsetY + rp, 0, 42, 7, 7);
            drawTexture(matrices, offsetX + 2 * containerInventoryOffsetX + 7 + cp, offsetY + rp, 25, 42, 7, 7);
            // transition
            drawTexture(matrices, offsetX + containerInventoryOffsetX, offsetY + rp, 64, 42, 7, 7);
            drawTexture(matrices, offsetX + containerInventoryOffsetX + cp + 7, offsetY + rp, 88, 42, 7, 7);

            drawTexture(matrices, offsetX + playerInventoryOffsetX, offsetY + playerInventoryOffsetY - 1, 0, 56, PLAYER_INVENTORY_WIDTH, PLAYER_INVENTORY_HEIGHT);
        } else if (this.playerInventoryOffsetX > 0) {

            for (int column = 0; column < columns; column++) {
                int i = column * 18 + 7;
                if (i < playerInventoryOffsetX || i + 18 >= playerInventoryOffsetX + PLAYER_INVENTORY_WIDTH)
                    drawTexture(matrices, offsetX + i, offsetY + rp, 39, 42, 18, 7);
            }

            // corners
            drawTexture(matrices, offsetX, offsetY + rp, 32, 42, 7, 7);
            drawTexture(matrices, offsetX + 2 * containerInventoryOffsetX + 7 + cp, offsetY + rp, 57, 42, 7, 7);
            // transition
            drawTexture(matrices, offsetX + playerInventoryOffsetX + 1, offsetY + rp, 96, 42, 7, 7);
            drawTexture(matrices, offsetX - playerInventoryOffsetX + 1 + cp + 7, offsetY + rp, 120, 42, 7, 7);

            drawTexture(matrices, offsetX + playerInventoryOffsetX, offsetY + playerInventoryOffsetY - 7 , 0, 50, PLAYER_INVENTORY_WIDTH, PLAYER_INVENTORY_HEIGHT + 6);
        }
        else{
            drawTexture(matrices, offsetX + playerInventoryOffsetX, offsetY + playerInventoryOffsetY - 7 , 0, 50, PLAYER_INVENTORY_WIDTH, PLAYER_INVENTORY_HEIGHT + 6);
            drawTexture(matrices,offsetX,offsetY+playerInventoryOffsetY-10,0,56,7,20);
            drawTexture(matrices,offsetX+containerWidth-CONTAINER_LEFT_PADDING,offsetY+playerInventoryOffsetY-10,PLAYER_INVENTORY_WIDTH-CONTAINER_LEFT_PADDING,56,7,20);
        }

        drawTexture(matrices, offsetX + xp, offsetY + rp, 7, 42, containerInventoryOffsetX - itx * 18 - 7, 7);
    }
}
