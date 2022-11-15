package com.LubieKakao1212.neguns.data.serializer;

import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.data.util.vars.ValueOrExpression;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.function.Supplier;

public class ValueExpressionDeserializer<T extends ValueOrExpression>  implements JsonDeserializer<T> {

    private Supplier<T> factory;

    public ValueExpressionDeserializer(Supplier<T> factory) {
        this.factory = factory;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        T value = factory.get();
        value.readValue(json);
        return value;
    }
}
