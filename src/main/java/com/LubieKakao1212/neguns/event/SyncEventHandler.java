package com.LubieKakao1212.neguns.event;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.network.FriendlyByteBufUtil;
import com.LubieKakao1212.neguns.network.NEGunsNetwork;
import com.LubieKakao1212.neguns.network.message.SyncGunDataMSG;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SyncEventHandler {

/*
    public static void event(PlayerEvent.PlayerLoggedInEvent event) {
        NewEraGunsMod.LOGGER.info("Logged in on client: " + event.getPlayer().level.isClientSide);
        if(!event.getPlayer().level.isClientSide) {
            NEGunsNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()), new SyncGunDataMSG(AllTheData.gunTypes.getAll()));
        }
    }
*/

    @SubscribeEvent
    public static void gatherPayload(NetworkEvent.GatherLoginPayloadsEvent event) {
        if(!event.isLocal()) {
            FriendlyByteBuf payload = new FriendlyByteBuf(Unpooled.buffer());
            new SyncGunDataMSG(AllTheData.gunTypes.getAll()).encode(payload);
            event.add(payload, NEGunsNetwork.EVENT_NETWORK_CHANNEL_ID, "Sync Gun Data");
        }
    }

    /*@SubscribeEvent
    public static void handlePayload(NetworkEvent.ClientCustomPayloadLoginEvent event) {
        FriendlyByteBuf payload = event.getPayload();
        SyncGunDataMSG.decode(payload).handle(event.getSource());
    }

    @SubscribeEvent
    public static void handlePayload(NetworkEvent.ServerCustomPayloadLoginEvent event) {
        FriendlyByteBuf payload = event.getPayload();
        SyncGunDataMSG.decode(payload).handle(event.getSource());
    }*/

    @SubscribeEvent
    public static void handlePayload(NetworkEvent.LoginPayloadEvent event) {
        NetworkEvent.Context ctx = event.getSource().get();
        if (ctx.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            FriendlyByteBuf payload = event.getPayload();
            SyncGunDataMSG.decode(payload).handle(event.getSource());
            HandshakeMessages.C2SAcknowledge response = new HandshakeMessages.C2SAcknowledge();

            ctx.getPacketDispatcher().sendPacket(new ResourceLocation("fml:handshake"), new FriendlyByteBuf(Unpooled.buffer()));

            //NEGunsNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> ctx.getSender()), response);
        }
    }
}
