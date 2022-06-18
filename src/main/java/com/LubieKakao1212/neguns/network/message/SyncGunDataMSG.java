package com.LubieKakao1212.neguns.network.message;

import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.neguns.network.FriendlyByteBufUtil;
import com.LubieKakao1212.neguns.network.NEGunsNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.HandshakeMessages;
import net.minecraftforge.network.LoginWrapper;
import net.minecraftforge.network.NetworkEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class SyncGunDataMSG implements IntSupplier {

    public Map<ResourceLocation, GunTypeInfo> data;
    private int loginIndex = 0;

    public SyncGunDataMSG() {
        data = new HashMap<>();
    }

    public SyncGunDataMSG(Map<ResourceLocation, GunTypeInfo> data) {
        this.data = data;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handleGunDataSync(this));
            NEGunsNetwork.INSTANCE.reply(new AcknowledgeMSG(), ctx);
        });
        ctx.setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(data.size());
        for(Map.Entry<ResourceLocation, GunTypeInfo> entry : data.entrySet()) {
            //id
            FriendlyByteBufUtil.writeString(buffer, entry.getKey().toString());
            //Model
            FriendlyByteBufUtil.writeString(buffer, entry.getValue().getModel().toString());

            FriendlyByteBufUtil.writeList(buffer, entry.getValue().animations, FriendlyByteBufUtil::writeString);
        }
        buffer.writeInt(loginIndex);
    }

    public static SyncGunDataMSG decode(FriendlyByteBuf buffer) {
        SyncGunDataMSG packetOut = new SyncGunDataMSG();
        int count = buffer.readInt();
        for(int i=0; i<count; i++) {
            ResourceLocation id = new ResourceLocation(FriendlyByteBufUtil.readString(buffer));
            ResourceLocation model = new ResourceLocation(FriendlyByteBufUtil.readString(buffer));
            List<String> animations = FriendlyByteBufUtil.readList(buffer, FriendlyByteBufUtil::readString);

            GunTypeInfo gunType = new GunTypeInfo();

            gunType.setModel(model);
            gunType.animations.addAll(animations);

            packetOut.data.put(id, gunType);
        }

        return packetOut;
    }

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    @Override
    public int getAsInt() {
        return 0;
    }
}
