package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.VariantChestBlockEntity;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.helpers.StorageTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.block.Blocks.CHEST;

public class VariantChestBlock extends ChestBlock {

    private final StorageTier tier;
    private final WoodVariant variant;

    public VariantChestBlock(@NotNull StorageTier tier, @NotNull WoodVariant variant) {
        super(FabricBlockSettings.copyOf(CHEST), () -> PackedEntities.CUSTOM_CHEST);
        this.tier = tier;
        this.variant = variant;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new VariantChestBlockEntity(getTier(), getVariant());
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return this.getBlockEntitySource(state, world, pos, false).apply(NAME_RETRIEVER).orElse(null);
    }

    public @NotNull StorageTier getTier() {
        return tier;
    }

    public @NotNull WoodVariant getVariant() {
        return variant;
    }

    private static final DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>> NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>>() {
        public Optional<NamedScreenHandlerFactory> getFromBoth(final ChestBlockEntity a, final ChestBlockEntity b) {
            final Inventory inventory = new DoubleInventory(a, b);

            int rows;
            int columns;

            VariantChestBlockEntity chestA = (VariantChestBlockEntity) a;
            VariantChestBlockEntity chestB = (VariantChestBlockEntity) b;
            // TODO: Changes for some proper checks with more logic
            if (chestA.getTier().getInventoryHeight() == 3) {
                rows = 6;
                columns = 9;
            } else {
                rows = chestA.getTier().getInventoryHeight();
                columns = chestA.getTier().getInventoryWidth() + chestB.getTier().getInventoryWidth();
            }


            return Optional.of(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                    buf.writeInt(rows);
                    buf.writeInt(columns);
                }

                @Nullable
                public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    if (a.checkUnlocked(playerEntity) && b.checkUnlocked(playerEntity)) {
                        a.checkLootInteraction(playerInventory.player);
                        b.checkLootInteraction(playerInventory.player);
                        return new ExtendedGenericContainerScreenHandler(i, playerInventory, inventory, rows, columns);
                    } else {
                        return null;
                    }
                }

                public Text getDisplayName() {
                    if (a.hasCustomName()) {
                        return a.getDisplayName();
                    } else {
                        return b.hasCustomName() ? b.getDisplayName() : new TranslatableText("container.chestDouble");
                    }
                }
            });
        }

        public Optional<NamedScreenHandlerFactory> getFrom(ChestBlockEntity chestBlockEntity) {
            return Optional.of(chestBlockEntity);
        }

        public Optional<NamedScreenHandlerFactory> getFallback() {
            return Optional.empty();
        }
    };

}
