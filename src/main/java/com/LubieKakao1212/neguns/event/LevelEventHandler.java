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
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;
import java.util.function.Function;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //NewEraGunsMod.LOGGER.info("tick");
    }

    @SubscribeEvent
    public static void onPlayerUseItemStart(LivingEntityUseItemEvent.Start event) {
        handle(event, event.getItem(), (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            if (!gunType.trigger(stack, new EntityChain().add(shooter), gun)) {
                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerUseItemTick(LivingEntityUseItemEvent.Tick event) {
        handle(event, event.getItem(), (gun, stack, shooter, rawEvent) -> {
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
        handle(event, event.getItem(), (gun, stack, shooter, rawEvent) -> {
            GunTypeInfo gunType = gun.getGunType();
            gunType.triggerRelease(stack, new EntityChain().add(shooter), gun);
        });
    }

    //Fires to late
    @SubscribeEvent
    public static void onPlayerEquipmentChange(LivingEquipmentChangeEvent event) {
        //TODO resolve
        if(event.getSlot() == EquipmentSlot.MAINHAND) {
            ItemStack heldStack = event.getEntityLiving().getUseItem();
            ItemStack fromStack = event.getFrom();
            ItemStack toStack = event.getTo();
            final UUID[] heldGunId = new UUID[1];
            final UUID[] fromGunId = new UUID[1];
            final UUID[] toGunId = new UUID[1];
            heldStack.getCapability(GunCaps.GUN).ifPresent((gun) -> {
                heldGunId[0] = gun.getState().getInstanceId();
            });
            fromStack.getCapability(GunCaps.GUN).ifPresent((gun) -> {
                fromGunId[0] = gun.getState().getInstanceId();
            });
            toStack.getCapability(GunCaps.GUN).ifPresent((gun) -> {
                toGunId[0] = gun.getState().getInstanceId();
            });
            if(heldGunId[0] == null || fromGunId[0] == null)
            {
                return;
            }
            if(fromGunId[0].equals(heldGunId[0]) && !fromGunId[0].equals(toGunId[0])) {
                event.getEntityLiving().releaseUsingItem();
            }
        }
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
