package com.LubieKakao1212.modularguns.event;

import com.LubieKakao1212.modularguns.data.AllTheData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ReloadEventHandler {

    @SubscribeEvent
    public static void OnAddReloadListener(AddReloadListenerEvent reloadListenerEvent) {
        reloadListenerEvent.addListener(AllTheData.gunTypes);
        reloadListenerEvent.addListener(AllTheData.hitBehaviourDataCache);
    }

}
