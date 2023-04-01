package com.LubieKakao1212.neguns.init;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {

    public static void init() {
        Items.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        GunComponents.GUN_COMPONENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        StateVariables.STATE_VARIABLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        Particles.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IGun.class);
    }

}
