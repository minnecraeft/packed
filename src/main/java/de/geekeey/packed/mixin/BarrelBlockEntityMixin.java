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

    //This is necessary as the tick method has a hardcoded Block check against Blocks.Barrel. We extend it to include
    //subclasses
    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
    ))
    public boolean isOfProxy(BlockState state, Block block) {
        return state.getBlock() instanceof BarrelBlock;
    }

    //This is necessary as countViewers only checks for Vanilla genericContainerScreenHandler classes, but our
    //extended ScreenHandler class does not subclass this. Therefore custom logic is needed.
    //This logic is identical to the ChestBlockEntity Mixin
    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/entity/ChestBlockEntity;countViewers(Lnet/minecraft/world/World;Lnet/minecraft/block/entity/LockableContainerBlockEntity;III)I"
    ))
    private int countViewersProxy(World world, LockableContainerBlockEntity container, int ticksOpen, int x, int y) {
        return ChestBlockEntityExtra.countViewersHandler(world,container,ticksOpen,x,y);
    }


}
