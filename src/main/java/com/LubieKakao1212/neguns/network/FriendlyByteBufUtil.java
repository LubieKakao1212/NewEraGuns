package com.LubieKakao1212.neguns.network;

import com.google.gson.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

//TODO Remove or finish
public class FriendlyByteBufUtil {

    public static void writeString(FriendlyByteBuf buffer, String string) {
        buffer.writeInt(string.length());
        buffer.writeCharSequence(string, Charset.forName("UTF-8"));
    }

    public static String readString(FriendlyByteBuf buffer) {
        int length = buffer.readInt();
        return buffer.readCharSequence(length, Charset.forName("UTF-8")).toString();
    }

    public static <T> void writeList(FriendlyByteBuf buffer, List<T> values, BiConsumer<FriendlyByteBuf, T> encoder) {
        buffer.writeInt(values.size());
        for(T value : values) {
            encoder.accept(buffer, value);
        }
    }

    public static <T> List<T> readList(FriendlyByteBuf buffer, Function<FriendlyByteBuf, T> decoder) {
        int size = buffer.readInt();
        List<T> output = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            output.add(decoder.apply(buffer));
        }
        return output;
    }




    /*public static void writeJson(FriendlyByteBuf buffer, JsonElement json) {
        if(json.isJsonObject()) {
            buffer.writeByte(0);
            writeJsonObject(buffer, json.getAsJsonObject());
        }else if(json.isJsonArray()) {
            buffer.writeByte(1);
            writeJsonArray(buffer, json.getAsJsonArray());
        }
    }

    public static void writeJsonObject(FriendlyByteBuf buffer, JsonObject jsonObject) {
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            buffer.writeCharSequence(entry.getKey(), Charset.defaultCharset());
            writeJson(buffer, entry.getValue());
        }
    }

    public static void writeJsonArray(FriendlyByteBuf buffer, JsonArray jsonArray) {
        for(JsonElement element : jsonArray) {
            writeJson(buffer, element);
        }
    }

    public static void writeJsonPrimitive(FriendlyByteBuf buffer, JsonPrimitive jsonPrimitive) {
        if(jsonPrimitive.isNumber()) {
            buffer.writeByte(0);
            buffer.writeDouble(jsonPrimitive.getAsDouble());
        }*//*else if() {
        }*//*
    }*/
}
