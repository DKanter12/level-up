package com.example;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData extends PersistentState {

    public static final Map<UUID, Map<UUID, SkillState>> playerSkills = new HashMap<>();

    public static Map<UUID, SkillState> getNewSkills(UUID playerUuid) {
        return playerSkills.computeIfAbsent(playerUuid, id -> new HashMap<>());
    }

    public static Map<UUID, SkillState> getSkills(UUID playerUuid) {
        return playerSkills.get(playerUuid);
    }


    public void setSkills(UUID playerUuid, Map<UUID, SkillState> skills) {
        playerSkills.put(playerUuid, skills);
        markDirty();
    }

    public static PlayerData createFromNbt(NbtCompound nbt) {
        PlayerData data = new PlayerData();

        if (!nbt.contains("players")) {
            return data;
        }

        NbtCompound players = nbt.getCompound("players");

        for (String key : players.getKeys()) {
            UUID playerUuid = UUID.fromString(key);
            NbtList listTag = players.getList(key, 10);

            Map<UUID, SkillState> skills = new HashMap<>();

            for (int i = 0; i < listTag.size(); i++) {
                NbtCompound skillTag = listTag.getCompound(i);

                UUID skillId = skillTag.getUuid("id");
                SkillState skill = new SkillState(
                        skillTag.getFloat("totalScore"),
                        skillTag.getInt("level")
                );

                skills.put(skillId, skill);
            }

            data.playerSkills.put(playerUuid, skills);
        }

        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound players = new NbtCompound();

        for (Map.Entry<UUID, Map<UUID, SkillState>> playerEntry : playerSkills.entrySet()) {
            NbtList listTag = new NbtList();

            for (Map.Entry<UUID, SkillState> skillEntry : playerEntry.getValue().entrySet()) {
                NbtCompound skillTag = new NbtCompound();
                SkillState skill = skillEntry.getValue();

                skillTag.putUuid("id", skillEntry.getKey());
                skillTag.putFloat("totalScore", skill.totalScore);
                skillTag.putInt("level", skill.level);

                listTag.add(skillTag);
            }

            players.put(playerEntry.getKey().toString(), listTag);
        }

        nbt.put("players", players);
        return nbt;
    }

    public static PlayerData get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(
                PlayerData::createFromNbt,
                PlayerData::new,
                "my_player_data"
        );
    }
}
