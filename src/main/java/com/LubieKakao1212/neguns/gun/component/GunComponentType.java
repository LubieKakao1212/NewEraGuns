package com.LubieKakao1212.neguns.gun.component;

import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.gun.component.components.conditions.FEAmountCondition;
import com.google.gson.JsonElement;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Function;

public class GunComponentType extends ForgeRegistryEntry<GunComponentType> {

    public static GunComponentType create(Class<? extends IGunComponent> componenetClass) {
        return new GunComponentType((jsonIn) -> AllTheData.gson.fromJson(jsonIn, componenetClass));
    }

    public static GunComponentType create(Function<JsonElement, IGunComponent> componentFactory) {
        return new GunComponentType(componentFactory);
    }

    private Function<JsonElement, IGunComponent> factory;

    private GunComponentType(Function<JsonElement, IGunComponent> factory) {
        this.factory = factory;
    }

    public IGunComponent createComponent(JsonElement element) {
        return factory.apply(element);
    }

}
