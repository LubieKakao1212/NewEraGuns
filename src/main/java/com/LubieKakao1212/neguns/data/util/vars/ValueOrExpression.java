package com.LubieKakao1212.neguns.data.util.vars;

import com.LubieKakao1212.neguns.gun.state.GunState;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.ibm.icu.impl.UResource;
import org.apache.commons.lang3.NotImplementedException;

public abstract class ValueOrExpression<T> {

    private String expression;
    private T value;

    public ValueOrExpression() {
        this.expression = null;
        this.value = null;
    }

    /**
     * Used for setting default values
     * @param value
     */
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

    /**
     * Do not override
     * @param json
     */
    public void readValue(JsonElement json) {
        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString()) {
                expression = primitive.getAsString();
                return;
            }
        }
        value = toValue(json);
    }

    protected abstract T toValue(JsonElement json);
}
