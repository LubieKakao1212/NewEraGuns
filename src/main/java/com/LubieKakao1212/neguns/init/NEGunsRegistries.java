package com.LubieKakao1212.neguns.init;

import com.LubieKakao1212.neguns.gun.component.GunComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NEGunsRegistries {

    static Supplier<IForgeRegistry<GunComponentType>> gunComponentRegistry;

    public static IForgeRegistry<GunComponentType> getGunComponentRegistry() {
        return gunComponentRegistry.get();
    }

    public static class Keys {
        public static final ResourceLocation GUN_COMPONENT_TYPES = new ResourceLocation("neguns:gun_component_type");
    }
}
