package com.LubieKakao1212.neguns.gun.state;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.expression.EvaluationException;
import com.LubieKakao1212.neguns.init.NEGunsRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StateVariable implements INBTSerializable<CompoundTag> {

    public static StateVariable.Type makeType(Class varClass, Function<Object, Tag> serialize, Function<Tag, Object> deserialize) {
        Type type = new Type();
        type.variableClass = varClass;
        type.serialize = serialize;
        type.deserialize = deserialize;
        return type;
    }

    private Object value = null;
    private Type type = null;

    public StateVariable() { }

    public StateVariable(Object value) {
        this.value = value;
        this.type = Type.CLASS_TYPE_MAP.get(value.getClass());
        if(type == null) {
            NewEraGunsMod.LOGGER.error("Attempting to store a variable of an invalid type: " + value.getClass());
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tagOut = new CompoundTag();
        tagOut.put("value", type.serialize.apply(value));
        tagOut.putString("type", type.getRegistryName().toString());
        return tagOut;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ResourceLocation typeKey = new ResourceLocation(nbt.getString("type"));
        type = NEGunsRegistries.getStateVariableRegistry().getValue(typeKey);
        value = type.deserialize.apply(nbt.get("value"));
    }

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public static class Type extends ForgeRegistryEntry<Type> {
        public static final Map<Class, Type> CLASS_TYPE_MAP = new HashMap<>();

        private Class variableClass;
        private Function<Object, Tag> serialize;
        private Function<Tag, Object> deserialize;

        public Class getVariableType() {
            return variableClass;
        }

    }

}
