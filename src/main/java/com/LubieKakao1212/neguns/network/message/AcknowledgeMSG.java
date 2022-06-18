package com.LubieKakao1212.neguns.network.message;

import net.minecraftforge.network.NetworkEvent;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class AcknowledgeMSG implements IntSupplier {

    private int loginIndex = 0;

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    @Override
    public int getAsInt() {
        return 0;
    }
}

