package com.example;

import com.mojang.text2speech.Narrator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.*;
import java.util.logging.Logger;

import static com.mojang.text2speech.Narrator.LOGGER;

public class  SkillXPSystem {

    private static final Map<UUID, Class<?>> lastKilledMob = new HashMap<>();
    private static final float MIN_EXPERIENCE = 100f;
    private static final float TEN_PERCENT = 1.1f;
    public static float lastExperienceLevelUp;
    public void addExp(PlayerEntity player, SkillAction action, Entity target) {
        switch (action) {
            case MINE_ORE, MINE_STONE:
                addById(player, PlayerSkills.MINER_ID);
                break;

            case KILL_PLAYER:
                addById(player, PlayerSkills.WARRIOR_ID);
                break;

            case KILL_MOB:
                if (target == null) return;

                UUID playerId = player.getUuid();
                Class<?> mobClass = target.getClass();

                if (mobClass.equals(lastKilledMob.get(playerId))) {
                    return;
                }

                lastKilledMob.put(playerId, mobClass);
                addById(player, PlayerSkills.WARRIOR_ID);
                break;

            case FARM_CROP:
                addById(player, PlayerSkills.FARMER_ID);
                break;

            case BOW_HIT:
                addById(player, PlayerSkills.ARCHER_ID);
                break;

            case ANVIL_REPAIR:
                addById(player, PlayerSkills.BLACKSMITH_ID);
                break;
        }
    }

    public static int levelUp(float experience) {
        int level = 1;
        float lastExp = 100;

        while (true) {
            float nextLevelExp = (float) Math.pow(1.1f, level ) * 100 + lastExp;

            if (experience == MIN_EXPERIENCE) {
                level = 2;
            }

            if (experience >= nextLevelExp) {
                level++;
                lastExp = nextLevelExp;
            }

            else {
                break;
            }
        }

        lastExperienceLevelUp = lastExp;
        return level;
    }


    private void addById(PlayerEntity player, UUID id) {
        SkillState skillState = PlayerSkills.playerSkills.get(id);

        if (skillState == null) {
            LOGGER.warn("Навык с ID {} не найден!", id);
            return;
        }

        LOGGER.info("До изменения: {}", skillState.level + " " + skillState.totalScore);

        skillState.totalScore += 1f;
        skillState.level = levelUp(skillState.totalScore);

        LOGGER.info("До изменения: {}", skillState.level + " " + skillState.totalScore);
        PlayerSkills.playerSkills.put(id,skillState);
        ((IPlayerSkills) player).setSkillsMap(PlayerSkills.playerSkills);

        if (!player.getWorld().isClient()) {
            PlayerSkills.playerSkills.put(id,skillState);
            ((IPlayerSkills) player).setSkillsMap(PlayerSkills.playerSkills);
        }

    }



}