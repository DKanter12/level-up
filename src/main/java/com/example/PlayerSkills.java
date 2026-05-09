package com.example;

import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class PlayerSkills {
    public static final Logger LOGGER = LoggerFactory.getLogger("level-up-skills");

    public static final UUID MINER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID WARRIOR_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID FARMER_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public static final UUID ARCHER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public static final UUID BLACKSMITH_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    public static void ensureInitialized(PlayerEntity player) {
        Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();


        skills.putIfAbsent(MINER_ID, new SkillState(0.0f, 1));
        skills.putIfAbsent(WARRIOR_ID, new SkillState(0.0f, 1));
        skills.putIfAbsent(FARMER_ID, new SkillState(0.0f, 1));
        skills.putIfAbsent(ARCHER_ID, new SkillState(0.0f, 1));
        skills.putIfAbsent(BLACKSMITH_ID, new SkillState(0.0f, 1));
    }
}