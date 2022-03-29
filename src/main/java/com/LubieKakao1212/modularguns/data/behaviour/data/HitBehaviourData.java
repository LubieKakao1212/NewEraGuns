package com.LubieKakao1212.modularguns.data.behaviour.data;

import com.LubieKakao1212.modularguns.data.behaviour.condition.ConditionSet;
import com.LubieKakao1212.modularguns.data.behaviour.hit.EntityHitBehaviour;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public class HitBehaviourData extends AbstractBehaviourData {

    public EntityHitBehaviour entity;

    public HitBehaviourData() {

    }

    @Override
    public String toString() {
        return "HitBehaviourData{" +
                "vars=" + vars +
                ", entity=" + entity +
                '}';
    }

    public static class Deserializer extends AbstractBehaviourData.Deserializer<HitBehaviourData> {

        @Override
        protected HitBehaviourData createInstance(JsonObject obj, JsonDeserializationContext context) {
            return new HitBehaviourData();
        }

        @Override
        protected void finishParse(JsonObject obj, JsonDeserializationContext context, HitBehaviourData behaviourData) {
            JsonObject entity = obj.get("entity").getAsJsonObject();
            behaviourData.entity = context.deserialize(entity, EntityHitBehaviour.class);

            JsonObject entityEnd = entity.get("end").getAsJsonObject();
            behaviourData.entity.end = ConditionSet.fromJson(context, entityEnd, behaviourData.vars);
        }
    }
}