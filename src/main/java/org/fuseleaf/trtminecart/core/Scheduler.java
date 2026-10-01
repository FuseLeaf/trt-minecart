package org.fuseleaf.trtminecart.core;

import org.fuseleaf.trtminecart.feature.RoadbedLaying;
import org.fuseleaf.trtminecart.feature.TrackLaying;
import org.fuseleaf.trtminecart.feature.Tunneling;

import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;

public class Scheduler {

    public static void tick(MinecartHopper cart) {
        if (cart.level().isClientSide()) {
            return;
        }

        Tunneling.tick(cart);
        RoadbedLaying.tick(cart);
        TrackLaying.tick(cart);
    }
}
