package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.data.behaviour.data.HitBehaviourData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AllTheData {

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(HitBehaviourData.class, new HitBehaviourData.Deserializer())
            .create();

    public static final DataCache<GunTypeInfo> gunTypes = new DataCache<>(gson, "guns/type", GunTypeInfo.class);

    public static final DataCache<HitBehaviourData> hitBehaviourDataCache = new DataCache<>(gson, "guns/behaviour/hit", HitBehaviourData.class);

}
