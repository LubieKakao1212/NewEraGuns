package com.LubieKakao1212.neguns.data.util.vars;

public class DoubleOrExpression extends ValueOrExpression<Double> {

    public DoubleOrExpression(String expression) {
        super(expression);
    }

    public DoubleOrExpression(Double value) {
        super(value);
    }
}
