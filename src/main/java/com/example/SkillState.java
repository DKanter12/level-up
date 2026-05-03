package com.example;

import net.minecraft.nbt.NbtCompound;

public class SkillState {
    public float totalScore;
    public int level;

    public SkillState(float totalScore, int level) {
        this.totalScore = totalScore;
        this.level = level;
    }

    public SkillState() {
    }

    public NbtCompound writeToNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("level", this.level);
        nbt.putFloat("totalScore", this.totalScore);
        return nbt;
    }

    // ЭТОТ МЕТОД ТОЖЕ СОЗДАЕМ САМИ
    public static SkillState fromNbt(NbtCompound nbt) {
        SkillState skills = new SkillState();
        skills.level = nbt.getInt("level");
        skills.totalScore = nbt.getInt("totalScore");
        return skills;
    }
}
