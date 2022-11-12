package com.LubieKakao1212.neguns.data.util.condition;

import com.LubieKakao1212.neguns.gun.state.GunState;
import com.fathzer.soft.javaluator.AbstractEvaluator;

public interface IDynamicVariableCondition<T> {

    boolean solve(T value, GunState state, AbstractEvaluator evaluator);
}
