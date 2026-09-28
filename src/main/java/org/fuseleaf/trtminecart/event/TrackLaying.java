package org.fuseleaf.trtminecart.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TrackLaying {

    public static void tick(MinecartHopper cart) {
        Level level = cart.level();

        if (level.isClientSide()) {
            return;
        }

        int index = 0;

        while (!isRailItem(cart.getItem(index))) {
            index++;

            if (index + 1 > cart.getContainerSize()) {
                return;
            }
        }

        if (cart.isOnRails()) {
            return;
        }

        ItemStack itemStack = cart.getItem(index);
        BlockPos targetPos = BlockPos.containing(cart.position());
        BlockState targetState = level.getBlockState(targetPos);

        if (!targetState.canBeReplaced() || !(itemStack.getItem() instanceof BlockItem blockItem)) {
            return;
        }

        level.setBlockAndUpdate(targetPos, blockItem.getBlock().defaultBlockState());
        itemStack.consume(1, null);
    }

    private static boolean isRailItem(ItemStack item) {
        return (
            item.is(Items.POWERED_RAIL)
            || item.is(Items.DETECTOR_RAIL)
            || item.is(Items.RAIL)
            || item.is(Items.ACTIVATOR_RAIL)
        );
    }
}
