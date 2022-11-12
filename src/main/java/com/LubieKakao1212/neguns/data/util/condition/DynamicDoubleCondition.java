package com.LubieKakao1212.neguns.data.util.condition;

import com.LubieKakao1212.neguns.gun.state.GunState;
import com.fathzer.soft.javaluator.AbstractEvaluator;

public class DynamicDoubleCondition implements IDynamicVariableCondition<Double> {

    private String expression;

    @Override
    public boolean solve(Double value, GunState state, AbstractEvaluator evaluator) {
        return (boolean) evaluator.evaluate(expression, state);
    }
}
