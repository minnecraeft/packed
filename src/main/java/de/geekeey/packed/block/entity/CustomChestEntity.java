package de.geekeey.packed.block.entity;

import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class CustomChestEntity extends ChestBlockEntity implements ExtendedScreenHandlerFactory {

    public int rows;
    public int columns;

    public CustomChestEntity() {
        super(PackedEntities.CHEST_COMMON);
        System.out.println("CustomChestEntity.CustomChestEntity");
    }

    public CustomChestEntity(int rows, int columns) {
        super(PackedEntities.CHEST_COMMON);
        this.rows = rows;
        this.columns = columns;
        setInvStackList(DefaultedList.ofSize(rows * columns, ItemStack.EMPTY));
        System.out.println("CustomChestEntity.CustomChestEntity");
        System.out.println("rows = " + rows + ", columns = " + columns);
    }

    public CustomChestEntity(BlockEntityType<?> type, int rows, int columns) {
        super(type);
        this.rows = rows;
        this.columns = columns;
        setInvStackList(DefaultedList.ofSize(rows * columns, ItemStack.EMPTY));
        System.out.println("CustomChestEntity.CustomChestEntity");
        System.out.println("type = " + type + ", rows = " + rows + ", columns = " + columns);
    }

    public static CustomChestEntity create3x9() {
        return new CustomChestEntity(PackedEntities.CHEST_3_9, 3, 9);
    }

    public static CustomChestEntity create4x9() {
        return new CustomChestEntity(PackedEntities.CHEST_4_9, 4, 9);
    }

    public static CustomChestEntity create5x9() {
        return new CustomChestEntity(PackedEntities.CHEST_5_9, 5, 9);
    }

    public static CustomChestEntity create6x9() {
        return new CustomChestEntity(PackedEntities.CHEST_6_9, 6, 9);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        super.onOpen(player);
    }

    @Override
    public int size() {
        return rows * columns;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        rows = tag.getInt("rows");
        columns = tag.getInt("columns");
        if (rows * columns != getInvStackList().size())
            setInvStackList(DefaultedList.ofSize(rows * columns, ItemStack.EMPTY));
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("rows", rows);
        tag.putInt("columns", columns);
        return super.toTag(tag);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(rows);
        packetByteBuf.writeInt(columns);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        System.out.println("CustomChestEntity.createScreenHandler");
        System.out.println("syncId = " + syncId + ", playerInventory = " + playerInventory);
        return new ExtendedGenericContainerScreenHandler(syncId, playerInventory, this, rows, columns);
    }

    protected Text getContainerName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
