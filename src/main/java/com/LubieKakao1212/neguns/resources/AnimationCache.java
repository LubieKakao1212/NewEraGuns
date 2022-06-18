package com.LubieKakao1212.neguns.resources;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.util.*;

public class AnimationCache {

    private Map<String, Integer> animationToId = new HashMap<>();
    private Map<Integer, String> idToAnimation = new HashMap<>();

    private Map<String, ResourceLocation> animationToModel = new HashMap<>();

    private HashSet<String> unsorted = new HashSet<>();

    public void registerAnimation(ResourceLocation modelId, String animationId) {
        unsorted.add(animationId);
        animationToModel.put(animationId, modelId);
    }

    public void setup() {
        List<String> sorted = unsorted.stream().sorted().toList();

        int currentId = 0;

        for(String name : sorted) {
            currentId++;
            animationToId.put(name, currentId);
            idToAnimation.put(currentId, name);
        }
    }

    public void clear() {
        animationToId.clear();
        idToAnimation.clear();
        unsorted.clear();
    }

    public int getId(String name) {
        return animationToId.getOrDefault(name, 0);
    }

    public String getName(int id) {
        return idToAnimation.getOrDefault(id, "");
    }

    public Collection<String> getAllNames() {
        return animationToId.keySet();
    }

    public ResourceLocation getModelFromAnimation(String animation) {
        return animationToModel.getOrDefault(animation, NEGunsDataCache.defaultModelId);
    }

}
