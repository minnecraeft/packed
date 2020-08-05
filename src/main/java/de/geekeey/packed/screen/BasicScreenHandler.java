package de.geekeey.packed.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class BasicScreenHandler extends ScreenHandler {
    private int zahl;
    private Inventory inventory;

    public BasicScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf){
        this(syncId,playerInventory,new SimpleInventory(9));
        System.out.println("Constructor with buf called (Client?)");
        zahl = buf.readInt();
    }
    public BasicScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory){
        super(de.geekeey.packed.ScreenHandler.BASIC,syncId);
        System.out.println("Constructor with inventory called (Server?)");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println(inventory.getStack(i));
        }
        zahl = -1;

        checkSize(inventory, 9);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int m;
        int l;
        for(m = 0; m < 3; ++m) {
            for(l = 0; l < 3; ++l) {
                this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18));
            }
        }

        for(m = 0; m < 3; ++m) {
            for(l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for(m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
