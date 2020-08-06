package de.geekeey.packed.mixin;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BarrelBlockEntity.class)
public class BarrelMixin {

    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
    ))
    public boolean isOfProxy(BlockState state, Block block) {
        System.out.println("Hallo Barrel");
        return state.getBlock() instanceof BarrelBlock;
    }
}
