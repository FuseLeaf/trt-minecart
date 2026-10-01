package org.fuseleaf.trtminecart.core;

import org.fuseleaf.trtminecart.construct.RoadbedLaying;
import org.fuseleaf.trtminecart.construct.TrackLaying;
import org.fuseleaf.trtminecart.destroy.Tunneling;

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
