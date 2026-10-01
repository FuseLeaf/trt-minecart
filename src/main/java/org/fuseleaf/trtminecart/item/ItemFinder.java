package org.fuseleaf.trtminecart.item;

import java.util.function.Predicate;

import net.minecraft.world.entity.vehicle.minecart.MinecartHopper;
import net.minecraft.world.item.ItemStack;

public class ItemFinder {

    public static ItemStack find(MinecartHopper cart, Predicate<ItemStack> predicate) {
        for (int index = 0; index + 1 <= cart.getContainerSize(); index++) {
            ItemStack itemStack = cart.getItem(index);

            if (predicate.test(itemStack)) {
                return itemStack;
            }
        }

        return ItemStack.EMPTY;
    }
}
