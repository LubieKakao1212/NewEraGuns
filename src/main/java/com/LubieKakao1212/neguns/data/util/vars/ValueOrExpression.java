package com.LubieKakao1212.neguns.data.util.vars;

import com.LubieKakao1212.neguns.gun.state.GunState;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.ibm.icu.impl.UResource;

public class ValueOrExpression<T> {

    private String expression;
    private T value;

    public ValueOrExpression(String expression) {
        this.expression = expression;
        this.value = null;
    }

    public ValueOrExpression(T value) {
        this.expression = null;
        this.value = value;
    }

    public T get(GunState state, AbstractEvaluator evaluator) {
        if(value != null) {
            return value;
        }else
        {
            return (T)evaluator.evaluate(expression, state);
        }
    }

}
