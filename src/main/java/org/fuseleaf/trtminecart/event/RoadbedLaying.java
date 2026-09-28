package org.fuseleaf.trtminecart.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RoadbedLaying {

    public static void tick(MinecartHopper cart) {
        Level level = cart.level();

        if (level.isClientSide()) {
            return;
        }

        int index = 0;

        while (!(cart.getItem(index).getItem() instanceof BlockItem)) {
            index++;

            if (index + 1 > cart.getContainerSize()) {
                return;
            }
        }

        ItemStack itemStack = cart.getItem(index);
        BlockPos targetPos = BlockPos.containing(cart.position()).relative(cart.getMotionDirection());
        Direction side = cart.getMotionDirection().getClockWise();

        for (int x = -1; x <= 1; x++) {
            if (itemStack == null || !(itemStack.getItem() instanceof BlockItem blockItem)) {
                return;
            }

            BlockPos pos = targetPos.relative(side, x).below();
            BlockState state = level.getBlockState(pos);

            if (!state.canBeReplaced()) {
                continue;
            }

            level.setBlockAndUpdate(pos, blockItem.getBlock().defaultBlockState());
            itemStack.consume(1, null);
        }
    }
}
