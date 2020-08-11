package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.init.helpers.ChestTier;
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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.block.Blocks.CHEST;

public class CustomChest extends ChestBlock {

    private final ChestTier tier;
    private final WoodVariant variant;

    public CustomChest(ChestTier tier, WoodVariant variant) {
        super(FabricBlockSettings.copyOf(CHEST), tier.getBlockEntityType());
        this.tier = tier;
        this.variant = variant;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return tier.newBlockEntity();
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return this.getBlockEntitySource(state, world, pos, false).apply(NAME_RETRIEVER).orElse(null);
    }

    public ChestTier getTier() {
        return tier;
    }

    public WoodVariant getVariant() {
        return variant;
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
