package de.geekeey.packed.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class ExtendedGenericContainerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final int rows;
    private final int colums;

    public ExtendedGenericContainerScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows, int colums) {
        super(type,syncId);
        checkSize(inventory, rows * colums);
        this.rows = rows;
        this.colums = colums;
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        int i = (this.rows - 4) * 18;

        int n;
        int m;

        for(n = 0; n < this.rows; ++n) {
            for(m = 0; m < colums; ++m) {
                this.addSlot(new Slot(inventory, m + n * 9, 8 + m * 18, 18 + n * 18));
            }
        }

        //This creates the slots for the player inventory
        for(n = 0; n < 3; ++n) {
            for(m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m + n * 9 + 9, 8 + m * 18, 103 + n * 18 + i));
            }
        }
        //This creates the player hotbar
        for(n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 161 + i));
        }

    }

    public static ExtendedGenericContainerScreenHandler create (int syncId, PlayerInventory playerInventory,int rows,int colums){
        return new ExtendedGenericContainerScreenHandler(de.geekeey.packed.ScreenHandler.GENERIC3x18,syncId,playerInventory,new SimpleInventory(rows*colums),rows,colums);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.rows * colums) {
                if (!this.insertItem(itemStack2, this.rows * colums, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.rows * colums, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }
}
