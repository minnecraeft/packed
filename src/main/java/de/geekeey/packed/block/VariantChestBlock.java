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
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;

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

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock()) && !(newState.getBlock() instanceof VariantChestBlock)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }
            //This super call will kill the BlockEntity, so this only gets called when we're not upgrading our block
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    /*
    copied from ChestBlock class, this is done because otherwise the game would crash with a no such method error
    however: i cannot explain you why this happens. So dont touch it
     */
    public DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> getBlockEntitySource(BlockState state, World world, BlockPos pos, boolean ignoreBlocked) {
        BiPredicate<WorldAccess,BlockPos> biPredicate2;
        if (ignoreBlocked) {
            biPredicate2 = (worldAccess, blockPos) -> false;
        } else {
            biPredicate2 = ChestBlock::isChestBlocked;
        }

        return DoubleBlockProperties.toPropertySource(this.entityTypeRetriever.get(), ChestBlock::getDoubleBlockType, ChestBlock::getFacing, FACING, state, world, pos, biPredicate2);
    }
}
