package com.example;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillXPSystem {

    private static final Map<UUID, Class<?>> lastKilledMob = new HashMap<>();

    public static void addExp(PlayerEntity player, SkillAction action, Entity target) {


        switch (action) {
            case MINE_ORE, MINE_STONE:
                addById(PlayerSkills.MINER_ID, 1f);
                break;

            case KILL_PLAYER:
                addById(PlayerSkills.WARRIOR_ID, 1f);
                break;

            case KILL_MOB:
                if (target == null) return;

                UUID playerId = player.getUuid();
                Class<?> mobClass = target.getClass();

                if (mobClass.equals(lastKilledMob.get(playerId))) {
                    return;
                }

                lastKilledMob.put(playerId, mobClass);

                addById(PlayerSkills.WARRIOR_ID, 1f);
                break;

            case FARM_CROP:
                addById(PlayerSkills.FARMER_ID, 1f);
                break;

            case BOW_HIT:
                addById(PlayerSkills.ARCHER_ID, 1f);
                break;


            case ANVIL_REPAIR:
                addById(PlayerSkills.BLACKSMITH_ID, 1f);
                break;
        }
    }


    private static void addById(UUID id, float amount) {

        for (int i = 0; i < PlayerSkills.skills.size(); i++) {
            SkillState skill = PlayerSkills.skills.get(i);

            if (skill.id.equals(id)) {
                SkillState newSkill = new SkillState(skill.id, skill.totalScore + amount, skill.level);
                PlayerSkills.skills.set(i, newSkill);
                LevelUpSystem.levelUp(newSkill,i);

                return;
            }
        }
    }
}