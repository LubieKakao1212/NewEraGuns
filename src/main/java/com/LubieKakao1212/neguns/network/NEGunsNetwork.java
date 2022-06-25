package com.LubieKakao1212.neguns.network;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.network.message.AcknowledgeMSG;
import com.LubieKakao1212.neguns.network.message.SyncGunDataMSG;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.event.EventNetworkChannel;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class NEGunsNetwork {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NewEraGunsMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static final ResourceLocation EVENT_NETWORK_CHANNEL_ID = new ResourceLocation(NewEraGunsMod.MODID, "event");

    public static final EventNetworkChannel EVENT_NETWORK_CHANNEL = NetworkRegistry.newEventChannel(
            EVENT_NETWORK_CHANNEL_ID,
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static void init() {
        int id = 0;
        //INSTANCE.registerMessage(id++, SyncGunDataMSG.class, SyncGunDataMSG::encode, SyncGunDataMSG::decode, SyncGunDataMSG::handle);
        INSTANCE.messageBuilder(SyncGunDataMSG.class, id++)
                //.markAsLoginPacket()
                .encoder(SyncGunDataMSG::encode)
                .decoder(SyncGunDataMSG::decode)
                .consumer((p, c) -> {
                    p.handle(c);
                    //HandshakeHandler.indexFirst((handshakeHandler, pkt, ctxSupplier) -> p.handle(ctxSupplier)).accept(p, c);
                })
                .loginIndex(SyncGunDataMSG::getAsInt, SyncGunDataMSG::setLoginIndex)
                .buildLoginPacketList(
                (isLocal) -> {
                    List<Pair<String, SyncGunDataMSG>> output = new ArrayList<>();
                    if(!isLocal) {
                        output.add(Pair.of("Sync Gun Data", new SyncGunDataMSG(AllTheData.gunTypes.getAll())));
                    }
                    return output;
                }).add();

        INSTANCE.messageBuilder(AcknowledgeMSG.class, id++)
                .loginIndex(AcknowledgeMSG::getAsInt, AcknowledgeMSG::setLoginIndex)
                .encoder((p, b) -> { })
                .decoder((b) -> new AcknowledgeMSG())
                .consumer((p, c) -> {
                    if (c.get().getDirection().getReceptionSide().isServer())
                    {
                        HandshakeHandler.indexFirst((handshakeHandler, pkt, ctxSupplier) -> { ctxSupplier.get().setPacketHandled(true); }).accept(p, c);
                    }
                })
                .add();
    }
}
