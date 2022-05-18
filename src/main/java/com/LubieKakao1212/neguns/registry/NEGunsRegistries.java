package com.LubieKakao1212.neguns.registry;

import com.LubieKakao1212.neguns.gun.component.GunComponentFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NEGunsRegistries {

    private static Supplier<IForgeRegistry<GunComponentFactory>> gunComponentRegistry;

    public static void registryEvent(NewRegistryEvent event) {
        Supplier<IForgeRegistry<GunComponentFactory>> registry = event.create(new RegistryBuilder<GunComponentFactory>().setName(new ResourceLocation("neguns:gun_components")));
    }

    public static IForgeRegistry<GunComponentFactory> getGunComponentRegistry() {
        return gunComponentRegistry.get();
    }
}
