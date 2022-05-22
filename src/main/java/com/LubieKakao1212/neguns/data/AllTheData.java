package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.data.serializer.GunComponentDeserializer;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AllTheData {

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(IGunComponent.class, new GunComponentDeserializer())
            .create();

    public static final GunTypeInfo DEFAULT_GUN_TYPE;

    public static final DataCache<GunTypeInfo> gunTypes = new DataCache<>(gson, "guns/type", GunTypeInfo.class);

    static {
        DEFAULT_GUN_TYPE = gson.fromJson("{" +
                "\"trigger\": []," +
                "\"model\": \"neguns:potato\"" +
                "}", GunTypeInfo.class);
    }
}
