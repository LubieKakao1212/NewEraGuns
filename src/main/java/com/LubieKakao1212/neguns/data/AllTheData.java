package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.data.serializer.GunComponentDeserializer;
import com.LubieKakao1212.neguns.data.serializer.ValueExpressionDeserializer;
import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.data.util.vars.VectorOrExpression;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.network.NEGunsNetwork;
import com.LubieKakao1212.neguns.network.message.SyncGunDataMSG;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.network.PacketDistributor;

public class AllTheData {

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(IGunComponent.class, new GunComponentDeserializer())
            .registerTypeAdapter(DoubleOrExpression.class, new ValueExpressionDeserializer(DoubleOrExpression::new))
            .registerTypeAdapter(VectorOrExpression.class, new ValueExpressionDeserializer(VectorOrExpression::new))
            .create();

    public static final GunTypeInfo DEFAULT_GUN_TYPE;

    public static final SyncingDataCache<GunTypeInfo> gunTypes = new SyncingDataCache<>(gson, "guns/type", GunTypeInfo.class, (data) -> {
        if(NewEraGunsMod.serverStarted) {
            NEGunsNetwork.INSTANCE.send(PacketDistributor.ALL.noArg(), new SyncGunDataMSG(data));
        }

        NEGunsDataCache.recacheAnimations();

        
    });

    static {
        DEFAULT_GUN_TYPE = gson.fromJson("{" +
                "\"trigger\": []," +
                "\"model\": \"neguns:potato\"" +
                "}", GunTypeInfo.class);
    }

    public void clientSetup() {
        NewEraGunsMod.LOGGER.info(gunTypes);
    }

}
