package de.geekeey.packed.registry;

import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.block.entity.TestContainerEntity;
import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BlockEntities {

    public static final BlockEntityType<TestContainerEntity> TEST_CONTAINER_ENTITY;
    public static final BlockEntityType<CustomBarrelEntity> CUSTOM_BARREL_ENTITY4x9;
    public static final BlockEntityType<CustomBarrelEntity> CUSTOM_BARREL_ENTITY5x9;
    public static final BlockEntityType<CustomBarrelEntity> CUSTOM_BARREL_ENTITY6x9;

    static {
        TEST_CONTAINER_ENTITY = BlockEntityType.Builder.create(TestContainerEntity::new, Blocks.TEST_CONTAINER).build(null);
        CUSTOM_BARREL_ENTITY4x9 = BlockEntityType.Builder.create(CustomBarrelEntity::create4x9, Blocks.CUSTOM_BARREL4x9).build(null);
        CUSTOM_BARREL_ENTITY5x9 = BlockEntityType.Builder.create(CustomBarrelEntity::create5x9, Blocks.CUSTOM_BARREL5x9).build(null);
        CUSTOM_BARREL_ENTITY6x9 = BlockEntityType.Builder.create(CustomBarrelEntity::create6x9, Blocks.CUSTOM_BARREL6x9).build(null);
    }

    public static void register() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Initializer.id("test_container"), TEST_CONTAINER_ENTITY);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Initializer.id("custom_barrel4x9"), CUSTOM_BARREL_ENTITY4x9);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Initializer.id("custom_barrel5x9"), CUSTOM_BARREL_ENTITY5x9);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Initializer.id("custom_barrel6x9"), CUSTOM_BARREL_ENTITY6x9);
    }
}
