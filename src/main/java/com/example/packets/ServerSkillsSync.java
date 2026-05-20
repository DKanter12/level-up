package com.example.packets;

import com.example.IPlayerSkills;
import com.example.PlayerSkills;
import com.example.SkillState;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;

public class ServerSkillsSync {
    public static void send (PlayerEntity player){
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
           var skills = ((IPlayerSkills) player).getSkillsMap();

        buf.writeFloat(skills.get(PlayerSkills.MINER_ID).totalScore);
        buf.writeFloat(skills.get(PlayerSkills.WARRIOR_ID).totalScore);
        buf.writeFloat(skills.get(PlayerSkills.FARMER_ID).totalScore);
        buf.writeFloat(skills.get(PlayerSkills.ARCHER_ID).totalScore);
        buf.writeFloat(skills.get(PlayerSkills.BLACKSMITH_ID).totalScore);

        ServerPlayNetworking.send((ServerPlayerEntity) player, ModPackets.SYNC_SKILLS, buf);

    }
}
