package de.geekeey.packed.block.entity;

import de.geekeey.packed.block.misc.FuckYouInv;
import de.geekeey.packed.block.misc.Upgradable;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.helpers.StorageTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
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

public class VariantCrateBlockEntity extends BlockEntity implements FuckYouInv, BlockEntityClientSerializable, Upgradable {

    private static final Logger LOGGER = LogManager.getLogger();

    private StorageTier tier;
    private WoodVariant variant;

    private DefaultedList<ItemStack> inventory;
    private Item item;

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

    @Override
    public void markDirty() {
        Item i = this.inventory.stream()
                .filter(s -> !s.isEmpty())
                .findFirst()
                .map(ItemStack::getItem)
                .orElse(Items.AIR);
        if (!this.item.equals(i)) {
            this.item = i;
            this.sync();
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        if (tag.contains("tier", 8)) {
            StorageTier tier = StorageTier.REGISTRY.get(new Identifier(tag.getString("tier")));
            if (tier != null) {
                setTier(tier);
                this.inventory = DefaultedList.ofSize(tier.getStackAmount(), ItemStack.EMPTY);
            }
        }

        if (tag.contains("item", 10)) {
            CompoundTag item = tag.getCompound("item");

            String id = item.getString("id");
            this.item = Registry.ITEM.get(new Identifier(id));

            int count = item.getInt("count");

            if (count / 64 > getTier().getStackAmount()) {
                int stacks = count / 64;
                stacks = stacks * 64 < count ? stacks + 1 : stacks;
                this.inventory = DefaultedList.ofSize(stacks, ItemStack.EMPTY);
                LOGGER.warn("Inventory is too small for items, maybe tier is missing? (bypass)");
            }


            int index = 0;
            int size;
            int c = count;
            while (c > 0) {
                size = Math.min(this.item.getMaxCount(), c);
                this.inventory.set(index++, new ItemStack(this.item, size));
                c -= size;
            }
        }


        if (tag.contains("variant", 8)) {
            WoodVariant variant = WoodVariant.REGISTRY.get(new Identifier(tag.getString("variant")));
            if (variant != null)
                setVariant(variant);
        }

        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if (item != Items.AIR) {
            CompoundTag item = new CompoundTag();
            Identifier identifier = Registry.ITEM.getId(this.item);
            item.putString("id", identifier.toString());

            item.putInt("count", getItemAmount());

            tag.put("item", item);
        }
        tag.putString("tier", getTier().getIdentifier().toString());
        tag.putString("variant", getVariant().getIdentifier().toString());
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag compound) {
        String id = compound.getString("item");
        this.item = Registry.ITEM.get(new Identifier(id));
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compound) {
        Identifier identifier = Registry.ITEM.getId(getItem());
        compound.putString("item", identifier.toString());
        return compound;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return (getItem().equals(Items.AIR) || getItem().equals(stack.getItem())) && stack.isStackable() && !stack.hasTag();
    }

    public @NotNull StorageTier getTier() {
        return tier;
    }

    public void setTier(@NotNull StorageTier tier) {
        this.inventory = Upgradable.ExtendInventory(this.inventory, tier.getStackAmount());
        this.tier = tier;
    }

    public @NotNull WoodVariant getVariant() {
        return variant;
    }

    @Override
    public void setVariant(@NotNull WoodVariant variant) {
        this.variant = variant;
    }

    public @NotNull Item getItem() {
        return item;
    }
}
