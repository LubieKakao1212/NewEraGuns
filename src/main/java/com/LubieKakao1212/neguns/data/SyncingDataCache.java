package com.LubieKakao1212.neguns.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;
import java.util.function.Consumer;

public class SyncingDataCache<T> extends DataCache<T> {

    private Consumer<Map<ResourceLocation, T>> syncHandler;

    public SyncingDataCache(Gson gson, String contentId, Class<T> dataType, Consumer<Map<ResourceLocation, T>> syncHandler) {
        super(gson, contentId, dataType);
        this.syncHandler = syncHandler;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resourceMap, ResourceManager resourceLocation, ProfilerFiller profiler) {
        super.apply(resourceMap, resourceLocation, profiler);
        syncHandler.accept(content);
    }

    public void sync(Map<ResourceLocation, T> resourceMap) {
        content = resourceMap;
    }
}
