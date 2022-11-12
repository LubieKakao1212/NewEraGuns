package com.LubieKakao1212.neguns.gun.component;

import com.LubieKakao1212.neguns.data.AllTheData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Function;

public class GunComponentType extends ForgeRegistryEntry<GunComponentType> {

    public static GunComponentType create(Class<? extends IGunComponent> componentClass) {
        return new GunComponentType((jsonIn) -> AllTheData.gson.fromJson(jsonIn, componentClass));
    }

    public static GunComponentType create(Function<JsonElement, IGunComponent> componentFactory) {
        return new GunComponentType(componentFactory);
    }

    private Function<JsonElement, IGunComponent> factory;

    private GunComponentType(Function<JsonElement, IGunComponent> factory) {
        this.factory = factory;
    }

    public IGunComponent createComponent(JsonObject element) {
        return factory.apply(element);
    }

}
