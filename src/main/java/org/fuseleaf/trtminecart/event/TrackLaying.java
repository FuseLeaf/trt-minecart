package org.fuseleaf.trtminecart.event;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TrackLaying {

    public static void tick(MinecartHopper cart) {
        Level level = cart.level();

        if (level.isClientSide()) {
            return;
        }

        int index = 0;

        while (!cart.getItem(index).is(ItemTags.RAILS)) {
            index++;

            if (index + 1 > cart.getContainerSize()) {
                return;
            }
        }

        if (cart.isOnRails()) {
            return;
        }

        ItemStack itemStack = cart.getItem(index);
        BlockItem blockItem = (BlockItem)itemStack.getItem();
        BlockState blockState = blockItem.getBlock().defaultBlockState();
        BlockPos targetPos = BlockPos.containing(cart.position());
        BlockState targetState = level.getBlockState(targetPos);

        if (!targetState.canBeReplaced() || !blockState.canSurvive(level, targetPos)) {
            return;
        }

        level.setBlockAndUpdate(targetPos, blockState);
        itemStack.consume(1, null);
    }
}
