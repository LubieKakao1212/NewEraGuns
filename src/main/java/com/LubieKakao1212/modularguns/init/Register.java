package com.LubieKakao1212.modularguns.init;

import com.LubieKakao1212.modularguns.capability.type.IGunType;
import com.LubieKakao1212.modularguns.item.DebugItem;
import com.LubieKakao1212.modularguns.item.GunItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, "mdguns");

    static {
        ITEMS.register("debug", () -> new DebugItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
        ITEMS.register("gun", () -> new GunItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    }

    public static void init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IGunType.class);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void modelRegistry(ModelRegistryEvent event) {

    }

}
