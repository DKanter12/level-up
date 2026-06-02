package com.example;

import com.example.packets.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.networking.client.ClientPlayNetworkAddon;

public class ClientSkillsReceiver {
    public static void register(){
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SYNC_SKILLS, ((client, handler,
           buf, responseSender) -> {
            float miner = buf.readFloat();
            float warrior = buf.readFloat();
            float farmer = buf.readFloat();
            float archer = buf.readFloat();
            float blacksmith = buf.readFloat();
            float rider = buf.readFloat();
            client.execute(() -> {
                ClientSkillsCache.MINER_SCORE = miner;
                ClientSkillsCache.WARRIOR_SCORE = warrior;
                ClientSkillsCache.FARMER_SCORE = farmer;
                ClientSkillsCache.ARCHER_SCORE = archer;
                ClientSkillsCache.BLACKSMITH_SCORE = blacksmith;
                ClientSkillsCache.RIDER_SCORE = rider;
            });
        }));
    }
}
