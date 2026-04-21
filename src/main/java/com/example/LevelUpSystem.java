package com.example;

import static com.mojang.text2speech.Narrator.LOGGER;

public class LevelUpSystem {
    private static final float MIN_EXPERIENCE = 100f;
    private static final float TEN_PERCENT = 1.1f;


    public static void levelUp(SkillState skill, int index) {
        if (checkLevelUp(skill.totalScore)) {
            SkillState newSkill = new SkillState(skill.id, skill.totalScore, skill.level + 1);
            PlayerSkills.skills.set(index, newSkill);
         LOGGER.info(String.format("Level up skill %d", skill.level));
        }
    }

    private static boolean checkLevelUp(float experience) {
        while (experience >= MIN_EXPERIENCE) {
            if (experience / TEN_PERCENT == MIN_EXPERIENCE) {
                return true;
            } else {
                experience = experience / 1.1f;
            }
        }
        return false;
    }
}
