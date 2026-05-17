package com.example;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static net.minecraft.client.render.model.json.ModelTransformationMode.GUI;

public class SkillXPSystem {
    private static final Map<UUID, Class<?>> lastKilledMob = new HashMap<>();

    public void addExp(PlayerEntity player, SkillAction action, Entity target) {
        switch (action) {
            case MINE_ORE, MINE_STONE -> addById(player, PlayerSkills.MINER_ID);
            case KILL_PLAYER, KILL_MOB -> {
                if (action == SkillAction.KILL_MOB) {
                    if (target == null) return;
                    UUID playerId = player.getUuid();
                    if (target.getClass().equals(lastKilledMob.get(playerId))) return;
                    lastKilledMob.put(playerId, target.getClass());
                }
                addById(player, PlayerSkills.WARRIOR_ID);
            }
            case FARM_CROP -> addById(player, PlayerSkills.FARMER_ID);
            case BOW_HIT -> addById(player, PlayerSkills.ARCHER_ID);
            case ANVIL_REPAIR -> addById(player, PlayerSkills.BLACKSMITH_ID);
        }
    }

    public static int getLevel(float experience) {
        int level = 1;
        float lastExp = 100;
        while (true) {
            float nextLevelExp = (float) Math.pow(1.1f, level) * 100 + lastExp;
            if (experience >= nextLevelExp) {
                level++;
                lastExp = nextLevelExp;
            } else {
                break;
            }
        }
        return level;
    }

    private void addById(PlayerEntity player, UUID skillId) {

        Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();
        SkillState state = skills.get(skillId);

        if (state == null) {
            PlayerSkills.ensureInitialized(player);
            state = skills.get(skillId);
        }

        state.totalScore += 1.5f;
        state.level = getLevel(state.totalScore);

    }
}