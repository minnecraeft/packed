package com.github.minnecraeft.packed.init;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class PackedTags {
    public static Tag<Block> COMMON_BARREL = TagRegistry.block(new Identifier("c", "barrel"));
}
