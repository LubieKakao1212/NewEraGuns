/*package com.LubieKakao1212.neguns.network.message;

import com.LubieKakao1212.neguns.network.NEGunsNetwork;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import javax.naming.Context;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SyncDataMSG {

    public Map<ResourceLocation, JsonElement> data;

    public SyncDataMSG() {
        data = new HashMap<>();
    }

    public SyncDataMSG(Map<ResourceLocation, JsonElement> data) {
        this.data = data;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handleDataSync(this)));
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(data.size());
        for(Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
            String encoded = entry.getKey().toString() + "\u0008" + entry.getValue().toString();

            buffer.writeInt(encoded.length());
            buffer.writeCharSequence(encoded, Charset.forName("UTF-8"));
        }
    }

    public static SyncDataMSG decode(FriendlyByteBuf buffer) {
        SyncDataMSG packetOut = new SyncDataMSG();
        int count = buffer.readInt();
        for(int i=0; i<count; i++) {
            int length = buffer.readInt();
            String encoded = buffer.readCharSequence(length, Charset.forName("UTF-8")).toString();
            String[] split = encoded.split("\u0008");

            packetOut.data.put(new ResourceLocation(split[0]), JsonParser.parseString(split[1]));
        }

        return packetOut;
    }
}
*/