package com.LubieKakao1212.neguns.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

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
        if(content != null) {
            content.forEach((id, entry) -> {
                if (entry instanceof IInvalidatable) {
                    ((IInvalidatable) entry).invalidate();
                }
            });
        }
        content = resourceMap.entrySet().stream().collect(Collectors.toMap((entry) -> entry.getKey(),(entry) -> gson.fromJson(entry.getValue(), dataType)));
    }

    public T get(ResourceLocation id) {
        return content.get(id);
    }

    public T getOrDefault(ResourceLocation id, T defaultValue) {
        T value = content.get(id);
        return value != null ? value : defaultValue;
    }

    public Map<ResourceLocation, T> getAll() {
        return content;
    }

    @Override
    public String toString() {
        return "DataCache{" +
                "content=" + content +
                '}';
    }
}
