package com.example;

import java.util.UUID;

public class SkillState {
    public float totalScore;
    public int level;
    public UUID id;

    public SkillState(UUID id, float totalScore, int level) {
        this.id = id;
        this.totalScore = totalScore;
        this.level = level;
    }
}
