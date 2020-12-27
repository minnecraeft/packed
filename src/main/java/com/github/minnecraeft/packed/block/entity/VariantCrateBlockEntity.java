package com.github.minnecraeft.packed.block.entity;

import com.github.minnecraeft.packed.block.misc.InventoryDelegate;
import com.github.minnecraeft.packed.block.misc.Upgradable;
import com.github.minnecraeft.packed.helper.ItemStacks;
import com.github.minnecraeft.packed.init.PackedEntities;
import com.github.minnecraeft.packed.init.helpers.StorageTier;
import com.github.minnecraeft.packed.init.helpers.WoodVariant;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class VariantCrateBlockEntity extends BlockEntity implements InventoryDelegate, BlockEntityClientSerializable, Upgradable {

    private static final Logger LOGGER = LogManager.getLogger();

    private StorageTier tier;
    private WoodVariant variant;

    private DefaultedList<ItemStack> inventory;
    private Item item;
    private boolean locked;

    public VariantCrateBlockEntity() {
        super(PackedEntities.CRATE);
        inventory = DefaultedList.of();
        item = Items.AIR;
    }

    public VariantCrateBlockEntity(StorageTier tier, WoodVariant variant) {
        super(PackedEntities.CRATE);
        this.tier = tier;
        this.variant = variant;
        inventory = DefaultedList.ofSize(tier.getStackAmount(), ItemStack.EMPTY);
        item = Items.AIR;
    }

    @Override
    public DefaultedList<ItemStack> stacks() {
        return inventory;
    }

    public void refreshFilter() {
        Optional<Item> stack = stacks().stream()
                .filter(ItemStacks::notEmpty)
                .findFirst()
                .map(ItemStack::getItem);

        if (stack.isPresent()) {
            Item item = stack.get();
            if (item.equals(getItem())) return;
            setItem(item);
            sync();
        } else if (!isLocked()) {
            setItem(Items.AIR);
            sync();
        }
    }

    @Override
    public void markDirty() {
        refreshFilter();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag compound) {
        if (compound.contains("tier", NbtType.STRING)) {
            Identifier id = new Identifier(compound.getString("tier"));
            StorageTier.REGISTRY.getOrEmpty(id).ifPresent(this::setTier);
        }

        if (compound.contains("variant", NbtType.STRING)) {
            Identifier id = new Identifier(compound.getString("variant"));
            WoodVariant.REGISTRY.getOrEmpty(id).ifPresent(this::setVariant);
        }

        if (compound.contains("locked", NbtType.BYTE)) {
            setLocked(compound.getByte("locked") != 0);
        }

        if (compound.contains("item", NbtType.COMPOUND)) {
            CompoundTag item = compound.getCompound("item");

            Identifier id = new Identifier(item.getString("id"));
            setItem(Registry.ITEM.getOrEmpty(id).orElse(Items.AIR));

            int count = item.getInt("count");

            // safe check so no items will be lost on error
            if (count / 64 > getTier().getStackAmount()) {
                int stacks = count / 64;
                stacks = stacks * 64 < count ? stacks + 1 : stacks;
                this.inventory = DefaultedList.ofSize(stacks, ItemStack.EMPTY);
                LOGGER.warn("Inventory is too small for items, maybe tier is missing? (bypass)");
            }

            // populate the inventory with it's items
            for (int s, i = 0, c = count; c > 0; c -= s) {
                s = Math.min(this.getItem().getMaxCount(), c);
                this.inventory.set(i++, new ItemStack(getItem(), s));
            }
        }

        super.fromTag(state, compound);
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        compound.putString("tier", getTier().getIdentifier().toString());
        compound.putString("variant", getVariant().getIdentifier().toString());
        compound.putByte("locked", (byte) (isLocked() ? 1 : 0));
        if (item != Items.AIR) {
            CompoundTag item = new CompoundTag();
            Identifier identifier = Registry.ITEM.getId(getItem());
            item.putString("id", identifier.toString());
            item.putInt("count", getItemAmount());
            compound.put("item", item);
        }
        return super.toTag(compound);
    }

    @Override
    public void fromClientTag(CompoundTag compound) {
        if (compound.contains("item", NbtType.STRING)) {
            Identifier id = new Identifier(compound.getString("item"));
            setItem(Registry.ITEM.getOrEmpty(id).orElse(Items.AIR));
        }
        if (compound.contains("locked", NbtType.BYTE)) {
            setLocked(compound.getByte("locked") != 0);
        }
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compound) {
        Identifier id = Registry.ITEM.getId(getItem());
        compound.putString("item", id.toString());
        compound.putByte("keep", (byte) (isLocked() ? 1 : 0));
        return compound;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (getItem().equals(Items.AIR)) {
            if (isLocked()) return false;
            return !stack.hasTag() && stack.isStackable();
        } else {
            return getItem().equals(stack.getItem()) && !stack.hasTag() && stack.isStackable();
        }
    }

    //region Properties Accessors
    //region Storage Tier
    public @NotNull StorageTier getTier() {
        return tier;
    }

    public void setTier(@NotNull StorageTier tier) {
        this.inventory = Upgradable.resize(this.inventory, tier.getStackAmount());
        this.tier = tier;
    }
    //endregion

    //region Wood Variant
    public @NotNull WoodVariant getVariant() {
        return variant;
    }

    @Override
    public void setVariant(@NotNull WoodVariant variant) {
        this.variant = variant;
    }
    //endregion

    //region Keep
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    //endregion

    //region Item
    public @NotNull Item getItem() {
        return item;
    }

    private void setItem(Item item) {
        this.item = item;
    }
    //endregion
    //endregion
}
