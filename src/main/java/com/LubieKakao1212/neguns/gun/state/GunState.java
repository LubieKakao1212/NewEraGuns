package com.LubieKakao1212.neguns.gun.state;

import com.fathzer.soft.javaluator.AbstractVariableSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class GunState implements AbstractVariableSet<Object>, INBTSerializable<CompoundTag> {

    public static final String PERMANENT_KEY = "permanent";
    public static final String HOLD_KEY = "hold";

    public static final String PERM_VAR_REGEX = "(p[A-Z]\\w+)";
    public static final String HOLD_VAR_REGEX = "(h[A-Z]\\w+)";
    public static final String TEMP_VAR_REGEX = "(t[A-Z]\\w+)";

    //TODO Variable scopes

    /**
     * Permanent variables are stored until they are explicitly changed or removed
     * They can be only numeric (for now)
     */
    private HashMap<String, Double> permanentVars = new HashMap<>();

    /**
     * Hold variables are stored until gun stops shooting
     * They can be only numeric (for now)
     */
    private HashMap<String, Double> holdVars = new HashMap<>();

    /**
     * Temporary variables are stored only for one tick
     * They can be of any type not only numeric
     */
    private HashMap<String, Object> temporaryVars = new HashMap<>();

    public void put(String key, Double value) {
        if(key.matches(PERM_VAR_REGEX)) {
            putPermanent(key, value);
        }else if(key.matches(HOLD_VAR_REGEX))
        {
            putPermanent(key, value);
        }else if(key.matches(TEMP_VAR_REGEX))
        {
            putTemporary(key, value);
        }else
        {
            putTemporary(key, value);
        }
    }

    /**
     * Sets a temporary variable value
     * @param key
     * @param value
     */
    public void putTemporary(String key, Object value) {
        temporaryVars.put(key, value);
    }

    /**
     * Sets a hold variable value
     * @param key
     * @param value
     */
    public void putHold(String key, Double value) {
        holdVars.put(key, value);
    }

    /**
     * Sets a permanent variable value
     * @param key
     * @param value
     */
    public void putPermanent(String key, Double value) {
        permanentVars.put(key, value);
    }

    @Override
    public Object get(String s) {
        Object v = temporaryVars.get(s);
        /*Double v = null;
        if(tmpV instanceof Double) {
            v = (Double)tmpV;
        }*/
        if(v == null) v = holdVars.get(s);
        if(v == null) v = permanentVars.get(s);
        return v;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag serialized = new CompoundTag();
        CompoundTag permanent = new CompoundTag();

        for(Map.Entry<String, Double> var : permanentVars.entrySet()) {
            permanent.putDouble(var.getKey(), var.getValue());
        }

        CompoundTag hold = new CompoundTag();
        for(Map.Entry<String, Double> var : holdVars.entrySet()) {
            hold.putDouble(var.getKey(), var.getValue());
        }

        serialized.put(PERMANENT_KEY, permanent);
        serialized.put(HOLD_KEY, hold);
        return serialized;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        permanentVars.clear();
        holdVars.clear();
        temporaryVars.clear();

        if(nbt.contains(PERMANENT_KEY, Tag.TAG_COMPOUND)) {
            CompoundTag tag = nbt.getCompound(PERMANENT_KEY);
            for (String key : tag.getAllKeys()) {
                permanentVars.put(key, tag.getDouble(key));
            }
        }

        if(nbt.contains(HOLD_KEY, Tag.TAG_COMPOUND)) {
            CompoundTag tag = nbt.getCompound(HOLD_KEY);
            for (String key : tag.getAllKeys()) {
                holdVars.put(key, tag.getDouble(key));
            }
        }
    }
}
