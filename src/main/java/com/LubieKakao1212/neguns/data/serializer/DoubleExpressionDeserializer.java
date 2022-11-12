package com.LubieKakao1212.neguns.data.serializer;

import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.google.gson.*;

import java.lang.reflect.Type;

public class DoubleExpressionDeserializer implements JsonDeserializer<DoubleOrExpression> {

    @Override
    public DoubleOrExpression deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        if(primitive.isString()) {
            return new DoubleOrExpression(primitive.getAsString());
        }else
        {
            return new DoubleOrExpression(primitive.getAsDouble());
        }
    }
}
