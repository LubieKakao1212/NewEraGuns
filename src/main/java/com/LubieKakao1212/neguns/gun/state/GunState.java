package com.LubieKakao1212.neguns.gun.state;

import com.LubieKakao1212.neguns.expression.EvaluationException;
import com.fathzer.soft.javaluator.AbstractVariableSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;

/**
 *  Supported variable types:
 *      Double,
 *      Vector3d,
 *      Quaterniond
 *      UUID
 */
public class GunState implements AbstractVariableSet<Object>, INBTSerializable<CompoundTag> {

    public static final String PERMANENT_KEY = "permanent";
    public static final String HOLD_KEY = "hold";
    public static final String INSTANCE_ID_KEY = "gunInstanceId";

    public static final String PERM_VAR_REGEX = "(p:\\w+)";
    public static final String HOLD_VAR_REGEX = "(h:\\w+)";
    public static final String TEMP_VAR_REGEX = "(t:\\w+)";

    public static final String PERM_PREFIX = "p";
    public static final String HOLD_PREFIX = "h";
    public static final String TEMP_PREFIX = "t";

    //TODO Variable scopes

    /**
     * Permanent variables are stored until they are explicitly changed or removed
     */
    private final HashMap<String, StateVariable> permanentVars = new HashMap<>();

    /**
     * Hold variables are stored until gun stops shooting
     */
    private final HashMap<String, StateVariable> holdVars = new HashMap<>();

    /**
     * Temporary variables are stored only for one tick
     */
    private final HashMap<String, StateVariable> temporaryVars = new HashMap<>();

    private final Stack<String> prefixScopes = new Stack<>();

    private UUID gunInstanceId;

    public GunState() {
        gunInstanceId = UUID.randomUUID();
    }

    public void put(String key, Object value) {
        String[] split = splitDestination(key);
        key = split[1];
        if(split[0] == null) {
            putTemporary(key, value);
        }
        else {
            String bucket = split[0];
            switch (bucket) {
                case PERM_PREFIX -> putPermanent(key, value);
                case HOLD_PREFIX -> putHold(key, value);
                case TEMP_PREFIX -> putTemporary(key, value);
                default -> throw new EvaluationException("Invalid variable designation prefix: " + bucket);
            }
        }
    }

    /**
     * Sets a temporary variable value
     * @param key
     * @param value
     */
    public void putTemporary(String key, Object value) {
        put(temporaryVars, key, value);
    }

    /**
     * Sets a hold variable value
     * @param key
     * @param value
     */
    public void putHold(String key, Object value) {
        put(holdVars, key, value);
    }

    /**
     * Sets a permanent variable value
     * @param key
     * @param value
     */
    public void putPermanent(String key, Object value) {
        put(permanentVars, key, value);
    }

    public void pushScope(String scope) {
        prefixScopes.push(scope);
    }

    public void popScope(String scope) {
        if(!prefixScopes.pop().equals(scope)) throw new EvaluationException("Imbalanced scopes");
    }

    @Override
    public Object get(String key) {
        String[] split = splitDestination(key);
        if(split[0] == null) {
            key = appendScope(split[1]);
            StateVariable v = temporaryVars.get(key);
            if(v == null) v = holdVars.get(key);
            if(v == null) v = permanentVars.get(key);
            if(v == null) return null;
            return v.getValue();
        }

        key = appendScope(split[1]);

        String bucket = split[0];
        StateVariable var = switch (bucket) {
            case PERM_PREFIX -> permanentVars.get(key);
            case HOLD_PREFIX -> holdVars.get(key);
            case TEMP_PREFIX -> temporaryVars.get(key);
            default -> throw new EvaluationException("Invalid variable designation prefix: " + bucket);
        };

        return var != null ?  var.getValue() : null;
    }

    public void clear() {
        permanentVars.clear();
        holdVars.clear();
        temporaryVars.clear();
        prefixScopes.clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag serialized = new CompoundTag();
        CompoundTag permanent = new CompoundTag();

        for(Map.Entry<String, StateVariable> var : permanentVars.entrySet()) {
            permanent.put(var.getKey(), var.getValue().serializeNBT());
        }

        CompoundTag hold = new CompoundTag();
        for(Map.Entry<String, StateVariable> var : holdVars.entrySet()) {
            hold.put(var.getKey(), var.getValue().serializeNBT());
        }

        serialized.put(PERMANENT_KEY, permanent);
        serialized.put(HOLD_KEY, hold);
        serialized.putUUID(INSTANCE_ID_KEY, gunInstanceId);
        return serialized;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        clear();

        if(nbt.contains(PERMANENT_KEY, Tag.TAG_COMPOUND)) {
            CompoundTag tag = nbt.getCompound(PERMANENT_KEY);
            for (String key : tag.getAllKeys()) {
                StateVariable var = new StateVariable();
                var.deserializeNBT(tag.getCompound(key));
                permanentVars.put(key, var);
            }
        }

        if(nbt.contains(HOLD_KEY, Tag.TAG_COMPOUND)) {
            CompoundTag tag = nbt.getCompound(HOLD_KEY);
            for (String key : tag.getAllKeys()) {
                StateVariable var = new StateVariable();
                var.deserializeNBT(tag.getCompound(key));
                holdVars.put(key, var);
            }
        }

        if(nbt.contains(INSTANCE_ID_KEY, Tag.TAG_INT_ARRAY)) {
            gunInstanceId = nbt.getUUID(INSTANCE_ID_KEY);
        }
        else {
            gunInstanceId = UUID.randomUUID();
        }
    }

    private void put(Map<String, StateVariable> destination, String key, Object value) {
        destination.put(appendScope(key), new StateVariable(value));
    }

    private String appendScope(String id) {
        String scope = null;
        if(!prefixScopes.empty()){
            scope = prefixScopes.peek();
        }

        if(scope == null)
            return id;

        String[] split = id.split(":", 2);
        if(split.length == 1) {
            return scope + ":" + id;
        }

        if(split[0].length() == 0) {
            return scope + ":" + split[1];
        }

        return id;
    }

    private String[] splitDestination(String key) {
        String[] split = key.split(":", 2);
        if(split.length == 1) {
            return new String[] { null, split[0] };
        }
        else if(split[0].length() == 0)
            return new String[] { null, split[1] };
        return split;
    }

    public UUID getInstanceId() {
        return gunInstanceId;
    }
}
