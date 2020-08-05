package de.geekeey.packed.client;

import com.mojang.blaze3d.systems.RenderSystem;
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

    private static final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");

    private final int rows;
    private final int columns;

    private final int containerOffsetX;
    private final int playerInventoryOffsetX;

    public GenericScreen(ExtendedGenericContainerScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
        rows = handler.getRows();
        columns = handler.getColumns();

        int containerInventoryWidth = CONTAINER_HORIZONTAL_PADDING + columns * SLOT_SIZE;

        backgroundWidth = max(PLAYER_INVENTORY_WIDTH, containerInventoryWidth);
        backgroundHeight = FIX_HEIGHT_MIN + rows * SLOT_SIZE;

        if (containerInventoryWidth < PLAYER_INVENTORY_WIDTH) {
            containerOffsetX = (PLAYER_INVENTORY_WIDTH - containerInventoryWidth) / 2;
            playerInventoryOffsetX = 0;
        } else {
            containerOffsetX = 0;
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

        int originX = offsetX + CONTAINER_LEFT_PADDING + containerOffsetX;
        int originY = offsetY + CONTAINER_TOP_PADDING + rows * SLOT_SIZE;

        // corner top left
        drawTexture(matrices, offsetX + containerOffsetX, offsetY, 0, 0, CONTAINER_LEFT_PADDING, CONTAINER_TOP_PADDING);
        // corner bottom left
        drawTexture(matrices, offsetX + containerOffsetX, originY, 0, 215, CONTAINER_LEFT_PADDING, CONTAINER_TOP_PADDING);

        for (int c = 0; c < columns; c++) {
            // border top
            drawTexture(matrices, originX + SLOT_SIZE * c, offsetY, CONTAINER_LEFT_PADDING, 0, SLOT_SIZE, CONTAINER_TOP_PADDING);
            drawTexture(matrices, originX + SLOT_SIZE * c, originY, CONTAINER_LEFT_PADDING, 215, SLOT_SIZE, CONTAINER_TOP_PADDING);
        }

        // corner top right
        drawTexture(matrices, originX + columns * SLOT_SIZE, offsetY, 169, 0, CONTAINER_RIGHT_PADDING, CONTAINER_TOP_PADDING);
        // corner bottom right
        drawTexture(matrices, originX + columns * SLOT_SIZE, originY, 169, 215, CONTAINER_RIGHT_PADDING, CONTAINER_TOP_PADDING);

        for (int r = 0; r < rows; r++) {
            originY = offsetY + CONTAINER_TOP_PADDING + r * SLOT_SIZE;
            // border left
            drawTexture(matrices, offsetX + containerOffsetX, originY, 0, CONTAINER_TOP_PADDING, CONTAINER_LEFT_PADDING, SLOT_SIZE);
            for (int c = 0; c < columns; c++) {
                // slot background
                drawTexture(matrices, originX + SLOT_SIZE * c, originY, 7, 17, SLOT_SIZE, SLOT_SIZE);
            }
            // border right
            drawTexture(matrices, originX + SLOT_SIZE * columns, originY, 169, CONTAINER_TOP_PADDING, CONTAINER_RIGHT_PADDING, SLOT_SIZE);
        }

        // player inventory
        drawTexture(matrices, offsetX + playerInventoryOffsetX, offsetY + rows * SLOT_SIZE + 17, 0, 126, PLAYER_INVENTORY_WIDTH, 96);
    }
}
