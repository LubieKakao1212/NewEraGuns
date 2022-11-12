package com.LubieKakao1212.neguns.data.util.condition;


import com.LubieKakao1212.neguns.expression.MultiTypeEvaluator;

public class ExpressionCondition {

    private String expression;

    public boolean solve(MultiTypeEvaluator evaluator) {
        return MultiTypeEvaluator.safeBoolean(evaluator.evaluate(expression));
    }

}
