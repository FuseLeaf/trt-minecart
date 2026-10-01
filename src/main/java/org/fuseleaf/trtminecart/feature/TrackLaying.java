package org.fuseleaf.trtminecart.feature;

import org.fuseleaf.trtminecart.item.ItemFinder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TrackLaying {

    public static void tick(MinecartHopper cart) {
        if (cart.isOnRails()) {
            return;
        }

        ItemStack trackItem = ItemFinder.find(
            cart,
            item -> item.is(ItemTags.RAILS)
        );

        if (trackItem.isEmpty()) {
            return;
        }

        Level level = cart.level();
        BlockPos targetPos = BlockPos.containing(cart.position());
        final BlockState TRACK_BLOCK_DEFAULT_STATE = ((BlockItem)(trackItem.getItem())).getBlock().defaultBlockState();

        if (!level.getBlockState(targetPos).canBeReplaced() || !TRACK_BLOCK_DEFAULT_STATE.canSurvive(level, targetPos)) {
            return;
        }

        level.setBlockAndUpdate(targetPos, TRACK_BLOCK_DEFAULT_STATE);
        trackItem.consume(1, null);
    }
}
