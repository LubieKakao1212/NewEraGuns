package com.LubieKakao1212.modularguns.data.behaviour.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HitBehaviourData extends AbstractBehaviourData {



    public HitBehaviourData() {

    }

    public static class Adapter extends AbstractBehaviourData.Adapter<HitBehaviourData> {

        public Adapter(Gson gson) {
            super(gson);
        }

        @Override
        protected HitBehaviourData createInstance(JsonObject obj) {
            return new HitBehaviourData();
        }

        @Override
        protected void finishParse(JsonObject obj) {

        }
    }


}