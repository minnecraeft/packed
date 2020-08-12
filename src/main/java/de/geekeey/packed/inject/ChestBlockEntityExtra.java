package de.geekeey.packed.inject;

import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ChestBlockEntityExtra {
    //This is the logic we redirect towards in ChestBlockEntityMixin and BarrelBlockEntityMixin, as Mixins don't allow
    //non-Mixin classes in the mixin package this needs to be outside so we don't have to write the same code twice
    public static int countViewersHandler(World world, LockableContainerBlockEntity container, int ticksOpen, int x, int y) {
        int i = 0;
        float f = 5.0F;
        List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, new Box((double) ((float) ticksOpen - 5.0F), (double) ((float) x - 5.0F), (double) ((float) y - 5.0F), (double) ((float) (ticksOpen + 1) + 5.0F), (double) ((float) (x + 1) + 5.0F), (double) ((float) (y + 1) + 5.0F)));
        Iterator var8 = list.iterator();

        while (true) {
            Inventory inventory;
            do {
                PlayerEntity playerEntity;
                do {
                    if (!var8.hasNext()) {
                        return i;
                    }

                    playerEntity = (PlayerEntity) var8.next();
                } while (!(playerEntity.currentScreenHandler instanceof GenericContainerScreenHandler || playerEntity.currentScreenHandler instanceof ExtendedGenericContainerScreenHandler));

                //As our extendedScreenHandler class does not extend the vanilla variant. We need to check for both classes
                //if the current screen handler is not extened it must be a vanilla one due to loop conditions
                //this means that the cast below our if statement is safe
                if (playerEntity.currentScreenHandler instanceof ExtendedGenericContainerScreenHandler) {
                    inventory = ((ExtendedGenericContainerScreenHandler) playerEntity.currentScreenHandler).getInventory();
                    continue;
                }

                inventory = ((GenericContainerScreenHandler) playerEntity.currentScreenHandler).getInventory();
            } while (inventory != container && (!(inventory instanceof DoubleInventory) || !((DoubleInventory) inventory).isPart(container)));

            ++i;
        }
    }
}
