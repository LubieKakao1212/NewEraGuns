package com.LubieKakao1212.neguns.gun.dummy;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//TODO Move to QuLib
public class EntityChain {

    @NotNull
    private List<Entity> chain;

    public EntityChain() {
        chain = new ArrayList<>();
    }

    @NotNull
    public Entity first() {
        return chain.get(0);
    }

    @NotNull
    public Entity last() {
        return chain.get(chain.size() - 1);
    }

    public EntityChain add(@NotNull Entity entity) {
        chain.add(entity);
        return this;
    }

}
