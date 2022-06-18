package com.LubieKakao1212.neguns.resources;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.neguns.item.render.FakeGunModel;
import com.LubieKakao1212.neguns.item.render.GunModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.file.AnimationFile;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class NEGunsDataCache {

    public static final ResourceLocation FAKE_ANIMATION_ID = new ResourceLocation(NewEraGunsMod.MODID, "fake");
    public static final FakeGunModel FAKE_MODEL = new FakeGunModel();
    public static final AnimationCache GUN_ANIMATIONS = new AnimationCache();
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

    public static void recacheAnimations() {
        GUN_ANIMATIONS.clear();
        for (Map.Entry<ResourceLocation, GunTypeInfo> type : AllTheData.gunTypes.getAll().entrySet()) {
            for(String anim : type.getValue().animations) {
                GUN_ANIMATIONS.registerAnimation(type.getValue().getModel(), anim);
            }
        }
        GUN_ANIMATIONS.setup();

        createFakeAnimationFile();
    }

    public static void createFakeAnimationFile() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT ,() -> () ->{
            AnimationFile file = new AnimationFile();
            for(String anim : GUN_ANIMATIONS.getAllNames()) {
                Animation animation = new Animation();
                animation.animationName = anim;
                file.putAnimation(anim, animation);
            }

            Map<ResourceLocation, AnimationFile> animationMap = GeckoLibCache.getInstance().getAnimations();

            animationMap.put(FAKE_ANIMATION_ID, file);
        });

    }
}
