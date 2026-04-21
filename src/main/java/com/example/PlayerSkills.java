package com.example;

import java.util.*;

import static com.mojang.text2speech.Narrator.LOGGER;


public class PlayerSkills {
    public static final UUID MINER = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static final UUID WARRIOR = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static final UUID FARMER = UUID.fromString("33333333-3333-3333-3333-333333333333");

    public static final UUID ARCHER = UUID.fromString("44444444-4444-4444-4444-444444444444");

    public static final UUID BLACKSMITH = UUID.fromString("55555555-5555-5555-5555-555555555555");

    public static List<SkillState> skills = new ArrayList<>();


    public static void playerSkills() {
        skills.add(new SkillState(MINER, 0.0f, 1));
        skills.add(new SkillState(WARRIOR, 0.0f, 1));
        skills.add(new SkillState(FARMER, 0.0f, 1));
        skills.add(new SkillState(ARCHER, 0.0f, 1));
        skills.add(new SkillState(BLACKSMITH, 0.0f, 1));
    }

}
