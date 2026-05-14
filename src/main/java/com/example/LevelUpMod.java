package com.example;

import com.example.commands.ModCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelUpMod implements ModInitializer {
    public static final String MODID = "level-up";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public  ServerPlayerEntity player;

    @Override
    public void onInitialize() {
        ModEvents.register();
        ModCommands.register();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            player = handler.player;
            PlayerSkills.ensureInitialized(player);
        });



        LOGGER.info("Mod 'Level Up' (Fabric) успешно загружен!");
    }
}