package de.geekeey.packed.block.entity;

import de.geekeey.packed.block.misc.ImplementedInventory;
import de.geekeey.packed.init.PackedEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class StorageBarrelEntity extends BlockEntity implements ImplementedInventory {
    private static final int maxItems = 32*64;
    private DefaultedList<ItemStack> inventory;

    public StorageBarrelEntity() {
        super(PackedEntities.STORAGE_BARREL_ENTITY);
        inventory = DefaultedList.ofSize(1,ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int getMaxCountPerStack() {
        return maxItems;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.fromTag(tag,inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag,inventory);
        return tag;
    }
}
