package com.LubieKakao1212.neguns.event;

import com.LubieKakao1212.neguns.data.AllTheData;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ReloadEventHandler {

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent reloadListenerEvent) {
        reloadListenerEvent.addListener(AllTheData.gunTypes);
    }

}
