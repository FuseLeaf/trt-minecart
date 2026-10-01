package org.fuseleaf.trtminecart.mixin;

import org.fuseleaf.trtminecart.core.Scheduler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;

@Mixin(MinecartHopper.class)
public class MinecartHopperMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void injectTick(CallbackInfo ci) {
        Scheduler.tick((MinecartHopper)(Object)this);
    }
}
