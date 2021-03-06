package com.LubieKakao1212.neguns.data.serializer;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.gun.component.GunComponentType;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.component.components.FallbackComponent;
import com.LubieKakao1212.neguns.gun.component.components.helper.Sequence;
import com.LubieKakao1212.neguns.init.NEGunsRegistries;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;

public class GunComponentDeserializer implements JsonDeserializer<IGunComponent> {

    @Override
    public IGunComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();

            Sequence component = new Sequence();

            for(JsonElement jsonElement : jsonArray) {
                component.Add(deserializeComponent(jsonElement.getAsJsonObject(), context));
            }

            return component;
        }
        JsonObject jsonObj = json.getAsJsonObject();
        return deserializeComponent(jsonObj, context);
    }

    private IGunComponent deserializeComponent(JsonObject jsonObj, JsonDeserializationContext context) {
        ResourceLocation id = new ResourceLocation(jsonObj.get("type").getAsString());
        GunComponentType component = NEGunsRegistries.getGunComponentRegistry().getValue(id);
        if(component == null){
            NewEraGunsMod.LOGGER.warn("Gun component: "+id+" is not registered.");
            return new FallbackComponent();
        }
        return component.createComponent(jsonObj);
    }

}
