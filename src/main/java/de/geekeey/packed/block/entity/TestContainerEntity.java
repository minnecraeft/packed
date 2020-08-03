package de.geekeey.packed.block.entity;

import de.geekeey.packed.BlockEntities;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class TestContainerEntity extends BasicContainerEntity {

    protected TestContainerEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public TestContainerEntity() {
        super(BlockEntities.TEST_CONTAINER_ENTITY);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ExtendedGenericContainerScreenHandler(de.geekeey.packed.ScreenHandler.GENERIC3x18,syncId,playerInventory,this,3,18);
    }

    @Override
    public int size() {
        return 54;
    }
}
