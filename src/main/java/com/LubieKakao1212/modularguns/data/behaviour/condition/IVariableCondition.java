package com.LubieKakao1212.modularguns.data.behaviour.condition;

import java.util.Map;

public interface IVariableCondition<T> {

    boolean solve(T value);

    static boolean solve(Map<String, IVariableCondition> conditions, Map<String, Object> vars) {
        for(Map.Entry<String, IVariableCondition> entry : conditions.entrySet()) {
            if(!entry.getValue().solve(vars.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
