package com.LubieKakao1212.modularguns.data.behaviour.data;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBehaviourData {

    protected transient Map<String, Object> vars = new HashMap<>();
    
    public static abstract class Deserializer<T extends AbstractBehaviourData> implements JsonDeserializer<T> {
        @Override
        public T deserialize(JsonElement jsonIn, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = jsonIn.getAsJsonObject();
            T behaviourData = createInstance(json, context);

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    JsonPrimitive primitive = (JsonPrimitive) entry.getValue();

                    if(primitive.isString()) {
                        behaviourData.vars.put(entry.getKey(), primitive.getAsString());
                        continue;
                    }

                    if(primitive.isBoolean()) {
                        behaviourData.vars.put(entry.getKey(), primitive.getAsBoolean());
                        continue;
                    }

                    if(primitive.isNumber()){
                        behaviourData.vars.put(entry.getKey(), primitive.getAsDouble());
                        continue;
                    }
                }
            }

            finishParse(json, context, behaviourData);
            return behaviourData;
        }

        protected abstract T createInstance(JsonObject obj, JsonDeserializationContext context);

        protected abstract void finishParse(JsonObject obj, JsonDeserializationContext context, T behaviourData);

    }

    @Override
    public String toString() {
        return "AbstractBehaviourData{" +
                "vars=" + vars +
                '}';
    }
}
