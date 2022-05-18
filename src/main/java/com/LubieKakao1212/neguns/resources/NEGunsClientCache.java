package com.LubieKakao1212.neguns.resources;

import com.LubieKakao1212.neguns.item.render.GunModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class NEGunsClientCache {

    private static Map<ResourceLocation, GunModel> modelCache = new HashMap<>();
    public static final GunModel defaultModel;
    public static final ResourceLocation defaultModelId = new ResourceLocation("neguns:potato");

    static {
        defaultModel = new GunModel(defaultModelId);
    }

    private static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return CompletableFuture.runAsync(() -> {
            modelCache = new HashMap<>();
            modelCache.put(defaultModelId, defaultModel);
        });
    }

    public static GunModel getOrCreateModel(ResourceLocation id) {
        GunModel model = modelCache.get(id);
        if(model == null) {
            model = new GunModel(id);
            modelCache.put(id, model);
        }
        return model;
    }
}
