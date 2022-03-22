package com.LubieKakao1212.modularguns.data.behaviour.data;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBehaviourData {

    protected transient Map<String, Object> vars = new HashMap<>();
    
    public static abstract class Adapter<T extends AbstractBehaviourData> extends TypeAdapter<T> {

        private final Gson gson;

        public Adapter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            //We do not need to write stuff
        }

        @Override
        public T read(JsonReader reader) throws IOException {
            reader.beginObject();

            JsonObject json = gson.fromJson(reader, JsonObject.class);

            T behaviour = createInstance(json);

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    JsonPrimitive primitive = (JsonPrimitive) entry.getValue();

                    if(primitive.isString()) {
                        behaviour.vars.put(entry.getKey(), primitive.getAsString());
                        continue;
                    }

                    if(primitive.isBoolean()) {
                        behaviour.vars.put(entry.getKey(), primitive.getAsBoolean());
                        continue;
                    }

                    if(primitive.isNumber()){
                        behaviour.vars.put(entry.getKey(), primitive.getAsDouble());
                        continue;
                    }
                }
            }

            finishParse(json);

            reader.endObject();
            return null;
        }

        protected abstract T createInstance(JsonObject obj);

        protected abstract void finishParse(JsonObject obj);

    }
}