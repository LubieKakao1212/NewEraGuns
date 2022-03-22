package com.LubieKakao1212.modularguns.data.behaviour.condition;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class ConditionSet {

    private Map<String, IVariableCondition> conditions;

    private ConditionSet(Map<String, IVariableCondition> conditions) {
        this.conditions = conditions;
    }

    public boolean solve(Map<String, Object> vars) {
        for(Map.Entry<String, IVariableCondition> entry : conditions.entrySet()) {
            if(!entry.getValue().solve(vars.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public static ConditionSet fromJson(Gson gson, JsonObject jsonIn, Map<String, Object> existingVars) {
        Map<String, IVariableCondition> conditions = new HashMap<>();
        for(Map.Entry<String, JsonElement> entry : jsonIn.entrySet()) {
            String variableId = entry.getKey();
            Object var = existingVars.get(variableId);
            if (var == null) {
                continue;
            }
            Class c = var.getClass();

            if (c == Double.class) {
                IVariableCondition doubleCondition = gson.fromJson(entry.getValue(), DoubleCondition.class);
                conditions.put(variableId, doubleCondition);
            } else if (c == String.class) {
                // TODO
            } else if (c == Boolean.class) {
                // TODO
            }
        }

        return new ConditionSet(conditions);
    }
}