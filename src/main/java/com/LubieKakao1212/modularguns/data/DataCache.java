package com.LubieKakao1212.modularguns.data;

import com.LubieKakao1212.modularguns.ModularGunsMod;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.network.IContainerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DataCache<T> extends SimpleJsonResourceReloadListener {

    protected final String contentId;

    protected Map<ResourceLocation, T> content;

    protected final Gson gson;
    protected final Class<T> dataType;

    public DataCache(Gson gson, String contentId, Class<T> dataType) {
        super(gson, contentId);
        this.contentId = contentId;
        this.gson = gson;
        this.dataType = dataType;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceMap, ResourceManager resourceLocation, ProfilerFiller profiler) {
        content = resourceMap.entrySet().stream().collect(Collectors.toMap((entry) -> entry.getKey(),(entry) -> gson.fromJson(entry.getValue(), dataType)));
    }

    public T get(ResourceLocation id) {
        return content.get(id);
    }

    @Override
    public String toString() {
        return "DataCache{" +
                "content=" + content +
                '}';
    }
}
