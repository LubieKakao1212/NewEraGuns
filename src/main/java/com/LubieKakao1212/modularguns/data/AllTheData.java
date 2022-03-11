package com.LubieKakao1212.modularguns.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AllTheData {

    public static final Gson gson = new GsonBuilder()
            .create();

    public static final DataCache<GunType> gunTypes = new DataCache<>(gson, "guns", GunType.class);

}
