package com.example;

import com.mojang.text2speech.Narrator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.*;
import java.util.logging.Logger;

import static com.mojang.text2speech.Narrator.LOGGER;

public class SkillXPSystem {

    private static final Map<UUID, Class<?>> lastKilledMob = new HashMap<>();
    private static final float MIN_EXPERIENCE = 100f;
    private static final float TEN_PERCENT = 1.1f;

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

    private boolean hasLevelUp(float experience, int level) {
        float levelExp = experience - (TEN_PERCENT * (level + 1) * 100);

        while (experience >= MIN_EXPERIENCE) {
            if (experience / TEN_PERCENT == MIN_EXPERIENCE ||(levelExp < 1 && levelExp > 0)) {
                return true;
            }
            else if (experience == MIN_EXPERIENCE) {
                return true;
            }
            else {
                experience = experience / 1.1f;
            }
        }
        return false;
    }


    private void addById(PlayerEntity player, UUID id) {
        SkillState skillState = PlayerSkills.playerSkills.get(id);

        if (skillState == null) {
            LOGGER.warn("Навык с ID {} не найден!", id);
            return;
        }

        LOGGER.info("До изменения: {}", skillState.level + " " + skillState.totalScore);

        skillState.totalScore += 1f;

        if (hasLevelUp(skillState.totalScore, skillState.level)) {
            skillState.level++;
        }

        LOGGER.info("До изменения: {}", skillState.level + " " + skillState.totalScore);

        if (!player.getWorld().isClient()) {
            PlayerData data = PlayerData.get((ServerWorld) player.getWorld());
            data.setSkills(player.getUuid(), PlayerSkills.playerSkills);
        }
    }



}