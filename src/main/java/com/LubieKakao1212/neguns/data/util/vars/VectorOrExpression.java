package com.LubieKakao1212.neguns.data.util.vars;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.joml.Vector3d;

public class VectorOrExpression extends ValueOrExpression<Vector3d> {

    public VectorOrExpression() {
    }

    public VectorOrExpression(Vector3d value) {
        super(value);
    }

    @Override
    protected Vector3d toValue(JsonElement json) {
        if(json.isJsonArray()) {
            JsonArray jarr = json.getAsJsonArray();
            if(jarr.size() >= 3) {
                return new Vector3d(
                        jarr.get(0).getAsDouble(),
                        jarr.get(1).getAsDouble(),
                        jarr.get(2).getAsDouble()
                );
            }
        }
        NewEraGunsMod.LOGGER.error("Malformed JSON: Expected three element array, got " + json);
        return null;
    }

}
