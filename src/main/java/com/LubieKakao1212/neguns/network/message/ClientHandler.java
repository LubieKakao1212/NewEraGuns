package com.LubieKakao1212.neguns.network.message;

import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;

public class ClientHandler {

    /*//TODO Generalize and move to qulib
    public static void handleDataSync(SyncDataMSG message) {
        AllTheData.gunTypes.sync(message.data);
    }*/

    public static void handleGunDataSync(SyncGunDataMSG syncGunDataMSG) {
        AllTheData.gunTypes.sync(syncGunDataMSG.data);

        NEGunsDataCache.recacheAnimations();

    }

}
