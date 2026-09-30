package org.fuseleaf.trtminecart.event;

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

    private static int miningTicks = 0;

    public static void tick(MinecartHopper cart) {
        Level level = cart.level();

        if (level.isClientSide()) {
            return;
        }

        ItemStack itemStack = cart.getItem(0);

        if (!itemStack.is(ItemTags.PICKAXES)) {
            return;
        }

        BlockPos targetPos = BlockPos.containing(cart.position()).relative(cart.getMotionDirection());
        Direction side = cart.getMotionDirection().getClockWise();

        miningTicks++;

        if (miningTicks < 20) {
            return;
        }

        miningTicks = 0;

        for (int y = 0; y < 3; y++) {
            for (int x = -1; x <= 1; x++) {
                if (itemStack == null) {
                    return;
                }

                BlockPos pos = targetPos.relative(side, x).above(y);
                BlockState state = level.getBlockState(pos);

                if (
                    state.isAir()
                    || BaseRailBlock.isRail(state)
                    || state.getDestroySpeed(level, pos) < 0
                ) {
                    continue;
                }

                level.destroyBlock(pos, itemStack.isCorrectToolForDrops(state));
                itemStack.hurtAndBreak(
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
