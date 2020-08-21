package de.geekeey.packed.block.misc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface FuckYouInv extends Inventory {

    DefaultedList<ItemStack> stacks();

    @Override
    default boolean isEmpty() {
        return stacks().stream()
                .allMatch(ItemStack::isEmpty);
    }

    default boolean isFull() {
        return stacks().stream()
                .noneMatch(stack -> stack.isEmpty() || stack.getCount() < stack.getMaxCount());
    }

    default int getItemAmount() {
        return stacks().stream()
                .mapToInt(ItemStack::getCount)
                .sum();
    }

    @Override
    default int size() {
        return stacks().size();
    }

    @Override
    default ItemStack getStack(int slot) {
        return slot >= 0 && slot < size() ? stacks().get(slot) : ItemStack.EMPTY;
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        stacks().set(slot, stack);
        if (!stack.isEmpty()) {
            int max = getMaxCountPerStack();
            if (stack.getCount() > max) stack.setCount(max);
        }
        markDirty();
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(stacks(), slot, amount);
        if (!stack.isEmpty()) {
            markDirty();
        }
        return stack;
    }

    @Override
    default ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(stacks(), slot);
        if (!stack.isEmpty()) {
            markDirty();
        }
        return stack;
    }

    default ItemStack insertAll(ItemStack stack) {
        ItemStack clone = stack.copy();
        while (!isFull() && !clone.isEmpty()) {
            clone = insert(clone);
        }
        return clone.isEmpty() ? ItemStack.EMPTY : clone;
    }

    default ItemStack insert(ItemStack stack) {
        ItemStack clone = stack.copy();
        insertToOld(clone);
        if (!clone.isEmpty()) {
            insertToNew(clone);
            return clone.isEmpty() ? ItemStack.EMPTY : clone;
        }
        return ItemStack.EMPTY;
    }

    default void insertToOld(ItemStack insert) {
        for (int i = 0; i < size(); ++i) {
            ItemStack stack = getStack(i);
            if (stack.getItem().equals(insert.getItem())) {
                int cap = Math.min(getMaxCountPerStack(), stack.getMaxCount());
                int len = Math.min(insert.getCount(), cap - stack.getCount());
                if (len > 0) {
                    stack.increment(len);
                    insert.decrement(len);
                    markDirty();
                }
                if (insert.isEmpty()) return;
            }
        }
    }

    default void insertToNew(ItemStack insert) {
        for (int i = 0; i < size(); ++i) {
            ItemStack stack = getStack(i);
            if (stack.isEmpty()) {
                setStack(i, insert.copy());
                insert.setCount(0);
                return;
            }
        }
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    default void markDirty() {
    }

    @Override
    default void clear() {
        stacks().clear();
        markDirty();
    }

}
