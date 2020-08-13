package de.geekeey.packed.block.entity;

import de.geekeey.packed.block.misc.ImplementedInventory;
import de.geekeey.packed.init.PackedEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class StorageBarrelEntity extends BlockEntity implements ImplementedInventory {
    private static final int maxItems = 32*64;
    private DefaultedList<ItemStack> inventory;

    public StorageBarrelEntity() {
        super(PackedEntities.STORAGE_BARREL_ENTITY);
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
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
        var item = tag.getCompound("item");

        var id = item.getString("id");
        var count = item.getInt("count");

        inventory.set(0,new ItemStack(Registry.ITEM.get(new Identifier(id)),count));
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        var item = inventory.get(0);

        var newtag = new CompoundTag();

        Identifier identifier = Registry.ITEM.getId(item.getItem());
        newtag.putString("id", identifier.toString());
        newtag.putInt("count", item.getCount());

        tag.put("item",newtag);

        return super.toTag(tag);
    }
}
