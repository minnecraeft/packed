package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class CustomChest extends ChestBlock {

    private final Supplier<CustomChestEntity> supplier;

    public CustomChest(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier, Supplier<CustomChestEntity> factory) {
        super(settings, supplier);
        this.supplier = factory;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return supplier.get();
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return this.getBlockEntitySource(state, world, pos, false).apply(NAME_RETRIEVER).orElse(null);
    }

    private static final DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>> NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>>() {
        public Optional<NamedScreenHandlerFactory> getFromBoth(final ChestBlockEntity first, final ChestBlockEntity second) {
            final Inventory inventory = new DoubleInventory(first, second);

            int rows;
            int columns;

            CustomChestEntity fcce = (CustomChestEntity) first;
            CustomChestEntity scce = (CustomChestEntity) second;
            //TODO There needs to be a better way for this
            if(fcce.rows == 3){
                rows = 6;
                columns = 9;
            }
            else{
                rows = fcce.rows;
                columns = fcce.columns + scce.columns;
            }


            return Optional.of(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
                    packetByteBuf.writeInt(rows);
                    packetByteBuf.writeInt(columns);
                }

                @Nullable
                public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    if (first.checkUnlocked(playerEntity) && second.checkUnlocked(playerEntity)) {
                        first.checkLootInteraction(playerInventory.player);
                        second.checkLootInteraction(playerInventory.player);
                        return new ExtendedGenericContainerScreenHandler(i, playerInventory, inventory, rows, columns);
                    } else {
                        return null;
                    }
                }

                public Text getDisplayName() {
                    if (first.hasCustomName()) {
                        return first.getDisplayName();
                    } else {
                        return (Text) (second.hasCustomName() ? second.getDisplayName() : new TranslatableText("container.chestDouble"));
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
