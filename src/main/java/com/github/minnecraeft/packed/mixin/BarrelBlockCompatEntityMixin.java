package com.github.minnecraeft.packed.mixin;

import com.github.minnecraeft.packed.init.PackedTags;
import com.github.minnecraeft.packed.inject.ChestBlockEntityExtra;
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
public class BarrelBlockCompatEntityMixin {

    // This is necessary as the tick method has a hardcoded Block check against Blocks.Barrel.
    // We now check for a common barrel tag instead, this might cause problems with charm.
    @Redirect(method = "tick", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
    ))
    public boolean isOfProxy(BlockState state, Block block) {
        return state.getBlock().isIn(PackedTags.COMMON_BARREL);
    }

}
