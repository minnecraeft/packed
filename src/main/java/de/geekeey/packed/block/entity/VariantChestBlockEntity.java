package de.geekeey.packed.block.entity;

import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.helpers.StorageTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

public class VariantChestBlockEntity extends ChestBlockEntity implements ExtendedScreenHandlerFactory {

    private StorageTier tier;
    private WoodVariant variant;

    public VariantChestBlockEntity() {
        super(PackedEntities.CUSTOM_CHEST);
    }

    public VariantChestBlockEntity(@NotNull StorageTier tier, @NotNull WoodVariant variant) {
        super(PackedEntities.CUSTOM_CHEST);
        this.tier = tier;
        this.variant = variant;
        setInvStackList(createInventory(tier));
    }

    private static DefaultedList<ItemStack> createInventory(StorageTier tier) {
        return DefaultedList.ofSize(tier.getInventoryWidth() * tier.getInventoryHeight(), ItemStack.EMPTY);
    }

    public @NotNull StorageTier getTier() {
        return tier;
    }

    public void setTier(@NotNull StorageTier tier) {
        this.tier = tier;
    }

    public @NotNull WoodVariant getVariant() {
        return variant;
    }

    @Override
    public int size() {
        return getTier().getInventoryWidth() * getTier().getInventoryHeight();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        if (tag.contains("tier", 8)) {
            var tier = StorageTier.REGISTRY.get(new Identifier(tag.getString("tier")));
            if (tier != null)
                setTier(tier);
        }
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putString("tier", getTier().getIdentifier().toString());
        return tag;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(getTier().getInventoryHeight());
        buf.writeInt(getTier().getInventoryWidth());
    }

    @Override
    protected ScreenHandler createScreenHandler(int id, PlayerInventory inventory) {
        var rows = getTier().getInventoryHeight();
        var columns = getTier().getInventoryWidth();
        return new ExtendedGenericContainerScreenHandler(id, inventory, this, rows, columns);
    }

    protected Text getContainerName() {
        var name = getCustomName();
        return name != null ? name : new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
