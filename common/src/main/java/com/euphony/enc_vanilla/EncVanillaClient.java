package com.euphony.enc_vanilla;

import com.euphony.enc_vanilla.client.EVClientEvents;
import com.euphony.enc_vanilla.client.init.KeyMapping;

public class EncVanillaClient {
    public static void init() {
        KeyMapping.registerKeyMapping();

        EVClientEvents.init();
    }
}
