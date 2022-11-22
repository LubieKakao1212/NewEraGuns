package com.LubieKakao1212.neguns.init;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.item.DebugItem;
import com.LubieKakao1212.neguns.item.GunItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NewEraGunsMod.MODID);

    static {
        ITEMS.register("debug", () -> new DebugItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
        ITEMS.register("gun", () -> new GunItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)));
    }
}
