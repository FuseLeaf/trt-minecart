package org.fuseleaf.trtminecart.event;

import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;

public class EventManager {

    public static void tick(MinecartHopper cart) {
        Tunneling.tick(cart);
        RoadbedLaying.tick(cart);
        TrackLaying.tick(cart);
    }
}
