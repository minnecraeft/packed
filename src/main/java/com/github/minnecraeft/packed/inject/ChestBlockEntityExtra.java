package com.github.minnecraeft.packed.inject;

import com.github.minnecraeft.packed.screen.ExtendedGenericContainerScreenHandler;
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
        List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, new Box((float) ticksOpen - 5.0F, (float) x - 5.0F, (float) y - 5.0F, (float) (ticksOpen + 1) + 5.0F, (float) (x + 1) + 5.0F, (float) (y + 1) + 5.0F));
        Iterator<PlayerEntity> players = list.iterator();

        while (true) {
            Inventory inventory;
            do {
                PlayerEntity playerEntity;
                do {
                    if (!players.hasNext()) {
                        return i;
                    }

                    playerEntity = players.next();
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
