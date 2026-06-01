package com.example;


import com.example.packets.ServerSkillsSync;
import com.example.souds.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.example.LevelUpMod.LOGGER;

public class SkillXPSystem {
    private static int lastLevel;
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
            case RIDE -> addById(player, PlayerSkills.RIDER_ID);
        }
    }

    private static void playLevelUpSound(PlayerEntity player){
        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.LEVEL_UP_SOUND, net.minecraft.sound.SoundCategory.PLAYERS, 1f, 1f);
    }


    public static int getLevel(float experience, PlayerEntity player) {

        player.playSound(ModSounds.LEVEL_UP_SOUND, 1f,1f);
        int level = 1;
        float lastExp = 100;
         if (experience >= 100 && experience < 210){
            level = 2;
        }
        else {
            while (true) {
                float nextLevelExp = (float) Math.pow(1.1f, level) * 100 + lastExp;
                if (experience >= nextLevelExp) {
                    level++;
                    lastExp = nextLevelExp;
                } else {
                    break;
                }
            }
        }


        if (lastLevel < level){
            playLevelUpSound(player);
        }
        lastLevel = level;
        return level;
    }

    private void addById(PlayerEntity player, UUID skillId) {

        Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();
        SkillState state = skills.get(skillId);

        if (state == null) {
            PlayerSkills.ensureInitialized(player);
            state = skills.get(skillId);
        }

        state.totalScore += 1f;
        lastLevel = state.level;
        state.level = getLevel(state.totalScore, player);
        ServerSkillsSync.send(player);


    }
}