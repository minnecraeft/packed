package de.geekeey.packed.block.entity;

import de.geekeey.packed.registry.BlockEntities;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class CustomChestEntity extends ChestBlockEntity implements ExtendedScreenHandlerFactory {

    public int rows;
    public int columns;

    public CustomChestEntity(BlockEntityType<?> blockEntityType, int rows, int columns) {
        super(blockEntityType);
        this.rows = rows;
        this.columns = columns;
        setInvStackList(DefaultedList.ofSize(rows * columns, ItemStack.EMPTY));
    }

    public static CustomChestEntity create3x9() {
        return new CustomChestEntity(BlockEntities.CHEST_3_9, 3, 9);
    }

    public static CustomChestEntity create4x9() {
        return new CustomChestEntity(BlockEntities.CHEST_4_9, 4, 9);
    }

    public static CustomChestEntity create5x9() {
        return new CustomChestEntity(BlockEntities.CHEST_5_9, 5, 9);
    }

    public static CustomChestEntity create6x9() {
        return new CustomChestEntity(BlockEntities.CHEST_6_9, 6, 9);
    }

    @Override
    public int size() {
        return rows * columns;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(rows);
        packetByteBuf.writeInt(columns);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ExtendedGenericContainerScreenHandler(syncId, playerInventory, this, rows, columns);
    }

    protected Text getContainerName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
