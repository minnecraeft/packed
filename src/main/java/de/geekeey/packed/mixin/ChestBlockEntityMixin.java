package de.geekeey.packed.mixin;

import de.geekeey.packed.inject.ChestBlockEntityExtra;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin {
    @Shadow
    private DefaultedList<ItemStack> inventory;

    @Redirect(method = "tickViewerCount", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/entity/ChestBlockEntity;countViewers(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/LockableContainerBlockEntity;III)I"
    ))
    //Needs to be redirected to account for our own screenHandler classes
    private static int countViewers(World world, LockableContainerBlockEntity container, int ticksOpen, int x, int y) {
        return ChestBlockEntityExtra.countViewersHandler(world,container,ticksOpen,x,y);
    }

}
