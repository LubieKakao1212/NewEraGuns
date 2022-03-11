package com.LubieKakao1212.modularguns.init;

import com.LubieKakao1212.modularguns.item.DebugItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

public class Register {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, "mdguns");

    static {
        ITEMS.register("debug", () -> new DebugItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    }

    public static void init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
