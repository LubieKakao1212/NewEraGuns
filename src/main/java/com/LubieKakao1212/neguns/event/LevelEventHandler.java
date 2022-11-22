package com.LubieKakao1212.neguns.event;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.capability.GunCapabilityProvider;
import com.LubieKakao1212.neguns.capability.GunCaps;
import com.LubieKakao1212.neguns.capability.gun.Gun;
import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.init.NEGunsRegistries;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //NewEraGunsMod.LOGGER.info("tick");
    }

    @SubscribeEvent
    public static void onPlayerUseItemStart(LivingEntityUseItemEvent.Start event) {
        handle(event, (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            if (!gunType.trigger(stack, new EntityChain().add(shooter), gun)) {
                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerUseItemTick(LivingEntityUseItemEvent.Tick event) {
        handle(event, (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            if (gunType.triggerHold(stack, new EntityChain().add(shooter), gun)) {
                event.setDuration(10);
            }
            else {
                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerUseItemStop(LivingEntityUseItemEvent.Stop event) {
        handle(event, (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            gunType.triggerRelease(stack, new EntityChain().add(shooter), gun);
        });
    }

    private static <T extends LivingEntityUseItemEvent> void handle(T event, TriggerAction<T> action) {
        LivingEntity entity = event.getEntityLiving();

        if(!entity.getLevel().isClientSide && entity instanceof Player) {
            Player player = (Player)entity;

            final ItemStack usedItem = event.getItem();

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
    private static interface TriggerAction<T extends LivingEntityUseItemEvent> {
        //TODO Allow non player entities to also use this event
        void handle(IGun gun, ItemStack gunItem, Player shooter, T rawEvent);
    }

}
