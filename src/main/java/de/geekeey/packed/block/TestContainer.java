package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.TestContainerEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class TestContainer extends BasicContainerBlock {
    public TestContainer(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TestContainerEntity();
    }
}
