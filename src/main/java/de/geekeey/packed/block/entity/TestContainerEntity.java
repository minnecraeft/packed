package de.geekeey.packed.block.entity;

import de.geekeey.packed.registry.BlockEntities;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class TestContainerEntity extends BasicContainerEntity implements ExtendedScreenHandlerFactory {

    protected TestContainerEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public TestContainerEntity() {
        super(BlockEntities.TEST_CONTAINER_ENTITY);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new Generic3x3ContainerScreenHandler(syncId,playerInventory,this);
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        System.out.println("writing buffer");
        packetByteBuf.writeInt(1);
    }
}
