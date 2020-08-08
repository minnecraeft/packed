package de.geekeey.packed.mixin;

import de.geekeey.packed.inject.ChestBlockEntityExtra;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BarrelBlockEntity.class)
public class BarrelBlockEntityMixin {

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
    ))
    public boolean isOfProxy(BlockState state, Block block) {
        return state.getBlock() instanceof BarrelBlock;
    }

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/entity/ChestBlockEntity;countViewers(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/LockableContainerBlockEntity;III)I"
    ))
    //Needs to be redirected to account for our own screenHandler classes
    private int countViewersProxy(World world, LockableContainerBlockEntity container, int ticksOpen, int x, int y) {
        return ChestBlockEntityExtra.countViewersHandler(world,container,ticksOpen,x,y);
    }


}
