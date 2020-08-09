package de.geekeey.packed.block.entity;

import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class CustomBarrelEntity extends BarrelBlockEntity implements ExtendedScreenHandlerFactory {

    public int rows;
    public int columns;

    public CustomBarrelEntity(BlockEntityType<?> type, int rows, int columns) {
        super(type);
        this.rows = rows;
        this.columns = columns;
        setInvStackList(DefaultedList.ofSize(rows * columns, ItemStack.EMPTY));
    }

    public static CustomBarrelEntity create3x9() {
        return new CustomBarrelEntity(PackedEntities.BARREL_3_9, 3, 9);
    }

    public static CustomBarrelEntity create4x9() {
        return new CustomBarrelEntity(PackedEntities.BARREL_4_9, 4, 9);
    }

    public static CustomBarrelEntity create5x9() {
        return new CustomBarrelEntity(PackedEntities.BARREL_5_9, 5, 9);
    }

    public static CustomBarrelEntity create6x9() {
        return new CustomBarrelEntity(PackedEntities.BARREL_6_9, 6, 9);
    }

    @Override
    public int size() {
        return rows * columns;
    }

    protected Text getContainerName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ExtendedGenericContainerScreenHandler(syncId, playerInventory, this, rows, columns);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf buf) {
        buf.writeInt(rows);
        buf.writeInt(columns);
    }
}
