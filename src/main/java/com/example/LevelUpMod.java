package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelUpMod implements ModInitializer {
    public static final String MODID = "level-up";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
      PlayerSkills.playerSkills();
        ModEvents.register();

        LOGGER.info("Hello Fabric world!");
    }
}