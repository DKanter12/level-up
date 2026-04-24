package com.example;

import java.util.*;


public class PlayerSkills {
    public static final UUID MINER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static final UUID WARRIOR_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static final UUID FARMER_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");

    public static final UUID ARCHER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");

    public static final UUID BLACKSMITH_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    public static List<SkillState> skills = new ArrayList<>();


    public static void playerSkills() {
        skills.add(new SkillState(MINER_ID, 0.0f, 1));
        skills.add(new SkillState(WARRIOR_ID, 0.0f, 1));
        skills.add(new SkillState(FARMER_ID, 0.0f, 1));
        skills.add(new SkillState(ARCHER_ID, 0.0f, 1));
        skills.add(new SkillState(BLACKSMITH_ID, 0.0f, 1));
    }

}
