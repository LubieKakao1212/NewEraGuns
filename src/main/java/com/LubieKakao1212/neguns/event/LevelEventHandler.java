package com.LubieKakao1212.neguns.event;

import com.LubieKakao1212.neguns.capability.GunCaps;
import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //NewEraGunsMod.LOGGER.info("tick");
    }

    @SubscribeEvent
    public static void onLivingUseItemStart(LivingEntityUseItemEvent.Start event) {
        handle(event, event.getItem(), (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            if (!gunType.trigger(stack, shooter, gun)) {
                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onLivingUseItemTick(LivingEntityUseItemEvent.Tick event) {
        handle(event, event.getItem(), (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            if (gunType.triggerHold(stack, shooter, gun)) {
                event.setDuration(10);
            }
            else {
                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onLivingUseItemStop(LivingEntityUseItemEvent.Stop event) {
        handle(event, event.getItem(), (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            gunType.triggerRelease(stack, shooter, gun);
        });
    }

    private static <T extends LivingEvent> void handle(T event, ItemStack usedItem, TriggerAction<T> action) {
        LivingEntity entity = event.getEntityLiving();

        if(!entity.getLevel().isClientSide && entity instanceof Player) {
            Player player = (Player)entity;

            if (usedItem != null) {
                usedItem.getCapability(GunCaps.GUN).ifPresent(
                        (gun) -> {
                            action.handle(gun, usedItem, player, event);
                        }
                );
            }
        }
    }

    @FunctionalInterface
    private interface TriggerAction<T extends LivingEvent> {
        //TODO Allow non player entities to also use this event
        void handle(IGun gun, ItemStack gunItem, Player shooter, T rawEvent);
    }

}
