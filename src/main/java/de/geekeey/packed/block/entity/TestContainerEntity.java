package de.geekeey.packed.block.entity;

import de.geekeey.packed.registry.BlockEntities;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class TestContainerEntity extends BasicContainerEntity implements ExtendedScreenHandlerFactory {

    private static final int inventoryrows = 6;
    private static final int inventorycolumns = 9;

    protected TestContainerEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public TestContainerEntity() {
        super(BlockEntities.TEST_CONTAINER_ENTITY);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ExtendedGenericContainerScreenHandler(syncId, playerInventory, this, inventoryrows, inventorycolumns);
    }

    @Override
    public int size() {
        return inventorycolumns * inventoryrows;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(inventoryrows);
        packetByteBuf.writeInt(inventorycolumns);
    }
}
