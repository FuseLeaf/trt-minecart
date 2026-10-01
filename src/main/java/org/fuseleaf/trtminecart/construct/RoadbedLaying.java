package org.fuseleaf.trtminecart.construct;

import org.fuseleaf.trtminecart.item.ItemFinder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RoadbedLaying {

    public static void tick(MinecartHopper cart) {
        ItemStack roadbedBlockItem = ItemFinder.find(
            cart,
            item -> item.getItem() instanceof BlockItem
        );

        if (roadbedBlockItem.isEmpty()) {
            return;
        }

        Level level = cart.level();
        BlockPos targetPos = BlockPos.containing(cart.position()).relative(cart.getMotionDirection());
        Direction cartSide = cart.getMotionDirection().getClockWise();
        final BlockState ROADBED_BLOCK_DEFAULT_STATE = ((BlockItem)(roadbedBlockItem.getItem())).getBlock().defaultBlockState();

        for (int x = -1; x <= 1; x++) {
            if (roadbedBlockItem == null) {
                return;
            }

            BlockPos pos = targetPos.relative(cartSide, x).below();
            BlockState state = level.getBlockState(pos);

            if (!state.canBeReplaced()) {
                continue;
            }

            level.setBlockAndUpdate(pos, ROADBED_BLOCK_DEFAULT_STATE);
            roadbedBlockItem.consume(1, null);
        }
    }
}
