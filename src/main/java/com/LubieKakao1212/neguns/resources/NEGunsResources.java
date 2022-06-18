package com.LubieKakao1212.neguns.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import software.bernie.geckolib3.GeckoLib;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NEGunsResources {

    private static final String transformsPath = "models/gun";
    private static final String transformsExtension = ".json";

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer())
            .registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer())
            .create();

    private static Map<ResourceLocation, ItemTransforms> transformCache;

    public static void register() {
        ReloadableResourceManager reloadable = (ReloadableResourceManager) Minecraft.getInstance()
                .getResourceManager();
        reloadable.registerReloadListener(NEGunsResources::reload);
        //reloadable.registerReloadListener();
    }

    public static ItemTransforms getTransforms(ResourceLocation location) {
        return transformCache.getOrDefault(location, ItemTransforms.NO_TRANSFORMS);
    }

    private static CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        Map<ResourceLocation, ItemTransforms> transforms = new HashMap<>();
        return CompletableFuture.allOf(
                NEGunsResources.<ItemTransforms>loadResources(backgroundExecutor, resourceManager, transformsPath,
                        (location) -> loadAndParse(location, resourceManager, ItemTransforms.class),
                        (location, transform) -> {
                            String path = location.getPath();
                            int prefix = transformsPath.length() + 1;
                            int len = path.length() - prefix - transformsExtension.length();

                            transforms.put(new ResourceLocation(location.getNamespace(), path.substring(prefix, prefix + len)), transform);
                        })
                .thenCompose(stage::wait).thenAcceptAsync((empty) -> transformCache = transforms, gameExecutor)
        );
    }

    //Copied from GeckoLib
    private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager, String type, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
        return CompletableFuture.supplyAsync(
                () -> resourceManager.listResources(type, fileName -> fileName.endsWith(".json")), executor)
                .thenApplyAsync(resources -> {
                    Map<ResourceLocation, CompletableFuture<T>> tasks = new HashMap<>();

                    for (ResourceLocation resource : resources) {
                        CompletableFuture<T> existing = tasks.put(resource,
                                CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));

                        if (existing != null) {// Possibly if this matters, the last one will win
                            System.err.println("Duplicate resource for " + resource);
                            existing.cancel(false);
                        }
                    }

                    return tasks;
                }, executor).thenAcceptAsync(tasks -> {
                    for (Map.Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) {
                        // Shouldn't be any duplicates as they are caught above
                        map.accept(entry.getKey(), entry.getValue().join());
                    }
                }, executor);
    }

    //Copied from GeckoLib
    private static <T> T loadAndParse(ResourceLocation location, ResourceManager manager, Type parseType) {
        try {
            InputStream inputStream = manager.getResource(location).getInputStream();

            String content;

            try {
                content = IOUtils.toString(inputStream, Charset.defaultCharset());
            } catch (Throwable var6) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }
                }

                throw var6;
            }

            if (inputStream != null) {
                inputStream.close();
            }

            return gson.fromJson(content, parseType);
        } catch (Exception var7) {
            GeckoLib.LOGGER.error("Couldn't load " + location, var7);
            throw new RuntimeException(new FileNotFoundException(location.toString()));
        }
    }
}
