package org.fuseleaf.trtminecart.destroy;

import org.fuseleaf.trtminecart.item.ItemFinder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Tunneling {

    public static void tick(MinecartHopper cart) {
        ItemStack pickaxeItem = ItemFinder.find(
            cart,
            item -> item.is(ItemTags.PICKAXES)
        );

        if (pickaxeItem.isEmpty()) {
            return;
        }

        Level level = cart.level();
        BlockPos targetPos = BlockPos.containing(cart.position()).relative(cart.getMotionDirection());
        Direction cartSide = cart.getMotionDirection().getClockWise();

        for (int y = 0; y < 3; y++) {
            for (int x = -1; x <= 1; x++) {
                if (pickaxeItem == null) {
                    return;
                }

                BlockPos pos = targetPos.relative(cartSide, x).above(y);
                BlockState state = level.getBlockState(pos);

                if (
                    state.isAir()
                    || BaseRailBlock.isRail(level, pos)
                    || BaseRailBlock.isRail(level, pos.above())
                    || state.getDestroySpeed(level, pos) < 0
                    || state.getCollisionShape(level, pos).isEmpty()
                ) {
                    continue;
                }

                level.destroyBlock(pos, pickaxeItem.isCorrectToolForDrops(state));
                pickaxeItem.hurtAndBreak(
                    1,
                    (ServerLevel)level,
                    null,
                    brokenItem -> {
                        level.playSound((Entity)cart, pos, SoundEvents.ITEM_BREAK.value(), SoundSource.NEUTRAL);
                    }
                );
            }
        }
    }
}
