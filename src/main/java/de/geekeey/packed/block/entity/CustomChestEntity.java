package de.geekeey.packed.block.entity;

import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.helpers.ChestTier;
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

public class CustomChestEntity extends ChestBlockEntity implements ExtendedScreenHandlerFactory {

    private ChestTier tier;
    private WoodVariant variant;

    /**
     * IMPORTANT: This constructor is only for the entity type serialisation.
     * Do not use this anywhere else it depends on the deserialization of {@link #fromTag(BlockState, CompoundTag)}.
     */
    public CustomChestEntity() {
        super(PackedEntities.CUSTOM_CHEST);
    }

    public CustomChestEntity(ChestTier tier, WoodVariant variant) {
        super(PackedEntities.CUSTOM_CHEST);
        this.tier = tier;
        this.variant = variant;
        setInvStackList(DefaultedList.ofSize(tier.rows() * tier.columns(), ItemStack.EMPTY));
    }

    public ChestTier getTier() {
        return tier;
    }

    public WoodVariant getVariant() {
        return variant;
    }

    @Override
    public int size() {
        return getTier().rows() * getTier().columns();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        if (tag.contains("tier", 8)) {
            this.tier = ChestTier.REGISTRY.get(new Identifier(tag.getString("tier")));
            if (size() != getInvStackList().size())
                setInvStackList(DefaultedList.ofSize(size(), ItemStack.EMPTY));
        }
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putString("tier", tier.identifier().toString());
        return tag;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(getTier().rows());
        buf.writeInt(getTier().columns());
    }

    @Override
    protected ScreenHandler createScreenHandler(int id, PlayerInventory inventory) {
        return new ExtendedGenericContainerScreenHandler(id, inventory, this, getTier().rows(), getTier().columns());
    }

    protected Text getContainerName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
}
