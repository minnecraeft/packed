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

    private static final int rows = 10;
    private static final int columns = 5;

    protected TestContainerEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public TestContainerEntity() {
        super(BlockEntities.TEST_CONTAINER_ENTITY);
    }

    @Override
    protected ScreenHandler createScreenHandler(int id, PlayerInventory inventory) {
        return new ExtendedGenericContainerScreenHandler(id, inventory, this, rows, columns);
    }

    @Override
    public int size() {
        return columns * rows;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(rows);
        buf.writeInt(columns);
    }
}
