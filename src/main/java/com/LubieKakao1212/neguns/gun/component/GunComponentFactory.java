package com.LubieKakao1212.neguns.gun.component;

import com.google.gson.JsonElement;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Function;

public class GunComponentFactory extends ForgeRegistryEntry<GunComponentFactory> {

    private Function<JsonElement, IGunComponent> factory;

    public GunComponentFactory(Function<JsonElement, IGunComponent> factory) {
        this.factory = factory;
    }

    IGunComponent create(JsonElement element) {
        return factory.apply(element);
    }

}
