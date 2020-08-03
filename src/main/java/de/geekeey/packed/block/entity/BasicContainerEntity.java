package de.geekeey.packed.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public abstract class BasicContainerEntity extends LootableContainerBlockEntity {

    // Implementors will override the size method to tell us how big our inventory needs to be
    private DefaultedList<ItemStack> inventory;

    protected BasicContainerEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
        inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        inventory = list;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state,tag);
        if (!this.deserializeLootTable(tag)) {
            Inventories.fromTag(tag, inventory);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if (!this.serializeLootTable(tag)) {
            Inventories.toTag(tag, inventory);
        }
        return super.toTag(tag);
    }


}
