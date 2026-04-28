package com.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

import static com.mojang.text2speech.Narrator.LOGGER;


public class PlayerSkills {
    public static final UUID MINER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static final UUID WARRIOR_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static final UUID FARMER_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");

    public static final UUID ARCHER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");

    public static final UUID BLACKSMITH_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    public static Map<UUID, SkillState> playerSkills = new HashMap<>();


    public static   Map<UUID, SkillState> newSkill(UUID playerUuid) {
        PlayerData playerData = new PlayerData();
        Map<UUID, SkillState> skills = playerData.getNewSkills(playerUuid);

        setPlayerSkills(skills);
        return playerSkills;
    }

    private static void setPlayerSkills(Map<UUID, SkillState> skills) {
            playerSkills = skills;

            skills.putIfAbsent(MINER_ID, new SkillState(0.0f, 1));
            skills.putIfAbsent(WARRIOR_ID, new SkillState(0.0f, 1));
            skills.putIfAbsent(FARMER_ID, new SkillState(0.0f, 1));
            skills.putIfAbsent(ARCHER_ID, new SkillState(0.0f, 1));
            skills.putIfAbsent(BLACKSMITH_ID, new SkillState(0.0f, 1));

    }

    public static void hasSkill(UUID playerUUid) {

        Map<UUID, Map<UUID, SkillState>> skills = PlayerData.playerSkills;

        if (skills.containsKey(playerUUid)) {
            playerSkills =  PlayerData.getSkills(playerUUid);
            LOGGER.info("1 "+playerSkills.toString());
        }
        else {
            playerSkills =  newSkill(playerUUid);
            LOGGER.info("2"+playerSkills.toString());
        }
    }
}
