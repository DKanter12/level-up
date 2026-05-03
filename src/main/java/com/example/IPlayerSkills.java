package com.example;

import java.util.Map;
import java.util.UUID;

public interface IPlayerSkills {
    Map<UUID, SkillState> getSkillsMap();
    void setSkillsMap(Map<UUID, SkillState> map);
}
