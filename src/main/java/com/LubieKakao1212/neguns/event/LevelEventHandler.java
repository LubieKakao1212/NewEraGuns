package com.LubieKakao1212.neguns.event;

import com.LubieKakao1212.neguns.capability.GunCapabilityProvider;
import com.LubieKakao1212.neguns.capability.GunCaps;
import com.LubieKakao1212.neguns.capability.gun.Gun;
import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if(!player.getLevel().isClientSide) {
            final ItemStack usedItem = player.getUseItem();

            if(usedItem != null) {
                usedItem.getCapability(GunCaps.GUN).ifPresent(
                        (gun) -> {
                            GunTypeInfo gunType = gun.getGunType();
                            gunType.triggerHold(usedItem, new EntityChain().add(event.player), gun);
                        }
                );
            }
        }
    }

}
