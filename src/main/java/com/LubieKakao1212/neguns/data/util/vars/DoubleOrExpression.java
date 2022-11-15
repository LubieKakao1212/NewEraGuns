package com.LubieKakao1212.neguns.data.util.vars;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.google.gson.JsonElement;

public class DoubleOrExpression extends ValueOrExpression<Double> {

    public DoubleOrExpression() {
    }

    public DoubleOrExpression(Double value) {
        super(value);
    }

    @Override
    protected Double toValue(JsonElement json) {
        if(json.isJsonPrimitive()) {
            return json.getAsDouble();
        }
        NewEraGunsMod.LOGGER.error("Malformed JSON: Expected double, got " + json);
        return null;
    }
}
