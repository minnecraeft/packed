package de.geekeey.packed.block.entity;

import de.geekeey.packed.block.misc.ImplementedInventory;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.helpers.StorageBarrelTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class StorageBarrelEntity extends BlockEntity implements ImplementedInventory, BlockEntityClientSerializable {

    private static final int maxItems = 32 * 64;
    private final DefaultedList<ItemStack> inventory;

    private StorageBarrelTier tier;
    private WoodVariant variant;

    public StorageBarrelEntity() {
        super(PackedEntities.STORAGE_BARREL_ENTITY);
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public StorageBarrelEntity(StorageBarrelTier tier, WoodVariant variant) {
        super(PackedEntities.STORAGE_BARREL_ENTITY);
        this.tier = tier;
        this.variant = variant;
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public boolean isFull(){
        return inventory.get(0).getCount() >= getMaxCountPerStack();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int getMaxCountPerStack() {
        return tier.capacity();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        if (tag.contains("item", 10)) {
            var item = tag.getCompound("item");

            var id = item.getString("id");
            var count = item.getInt("count");

            Item type = Registry.ITEM.get(new Identifier(id));
            inventory.set(0, new ItemStack(type, count));
        }

        if (tag.contains("tier", 8)) {
            this.tier = StorageBarrelTier.REGISTRY.get(new Identifier(tag.getString("tier")));
        }

        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        var stack = inventory.get(0);

        var item = new CompoundTag();

        Identifier identifier = Registry.ITEM.getId(stack.getItem());
        item.putString("id", identifier.toString());
        item.putInt("count", stack.getCount());

        tag.put("item", item);
        tag.putString("tier", tier.identifier().toString());

        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag compound) {
        var id = compound.getString("item");
        var item = Registry.ITEM.get(new Identifier(id));
        inventory.set(0, new ItemStack(item));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compound) {
        Identifier identifier = Registry.ITEM.getId(inventory.get(0).getItem());
        compound.putString("item", identifier.toString());
        return compound;
    }

    public StorageBarrelTier getTier() {
        return tier;
    }

    public void setTier(StorageBarrelTier tier) {
        this.tier = tier;
    }

    public WoodVariant getVariant() {
        return variant;
    }
}
