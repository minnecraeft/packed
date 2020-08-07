package de.geekeey.packed.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ExtendedGenericContainerScreenHandler extends ScreenHandler {
    private static final int SLOT_SIZE = 18;
    private static final int LEFT_RIGHT_PADDING = 8;
    private static final int TOP_PADDING = 18;
    private static final int INV_CHEST_GAP = 13;
    private static final int INV_HOTBAR_GAP = 4;

    private static final int INV_ROWS = 3;
    private static final int INV_COLUMNS = 9;
    private static final int HOTBAR_COLUMNS = 9;

    private final Inventory inventory;
    private final int rows;
    private final int columns;


    public ExtendedGenericContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, int rows, int columns) {
        super(de.geekeey.packed.registry.ScreenHandler.GENERIC, syncId);
        checkSize(inventory, rows * columns);
        this.rows = rows;
        this.columns = columns;
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int inventoryandhotbarstartx = (int) ((columns - INV_COLUMNS) / 2.0 * SLOT_SIZE);
        int cheststartx = LEFT_RIGHT_PADDING;
        int inventorystarty = TOP_PADDING + rows * SLOT_SIZE + INV_CHEST_GAP;
        int hotbarstarty = inventorystarty + INV_ROWS * SLOT_SIZE + INV_HOTBAR_GAP;

        if (inventoryandhotbarstartx < 0) {
            //chest columns less than inventories:
            //Here we indent the chest, not the inventory
            cheststartx = -inventoryandhotbarstartx + LEFT_RIGHT_PADDING;
            inventoryandhotbarstartx = 0;
        }


        int n;
        int m;

        for (n = 0; n < this.rows; ++n) {
            for (m = 0; m < columns; ++m) {
                this.addSlot(new Slot(inventory, m + n * columns, cheststartx + m * SLOT_SIZE, TOP_PADDING + n * SLOT_SIZE));
            }
        }
        //This creates the slots for the player inventory
        for (n = 0; n < INV_ROWS; ++n) {
            for (m = 0; m < INV_COLUMNS; ++m) {
                this.addSlot(new Slot(playerInventory, m + n * INV_COLUMNS + HOTBAR_COLUMNS, +LEFT_RIGHT_PADDING + inventoryandhotbarstartx + m * SLOT_SIZE, inventorystarty + n * SLOT_SIZE));
            }
        }

        //This creates the player hotbar
        for (n = 0; n < HOTBAR_COLUMNS; ++n) {
            this.addSlot(new Slot(playerInventory, n, +LEFT_RIGHT_PADDING + inventoryandhotbarstartx + n * SLOT_SIZE, hotbarstarty));
        }
    }

    public static ExtendedGenericContainerScreenHandler create(int id, PlayerInventory inv, PacketByteBuf buf) {
        int rows = buf.readInt();
        int columns = buf.readInt();
        return new ExtendedGenericContainerScreenHandler(id, inv, new SimpleInventory(rows * columns), rows, columns);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
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
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.rows * columns) {
                if (!this.insertItem(itemStack2, this.rows * columns, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.rows * columns, false)) {
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
