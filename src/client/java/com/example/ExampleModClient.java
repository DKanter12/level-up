package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.UUID;

import static com.example.PlayerSkills.*;

public class ExampleModClient implements ClientModInitializer {
    ServerPlayerEntity player;

    @Override
    public void onInitializeClient() {
        registerGui();
    }

    public void registerGui(){
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            player = handler.player;
        });

        KeyBinding keyBinding =  KeyBindingHelper.registerKeyBinding(new KeyBinding("Open",InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,"category.level-up.keys"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(keyBinding.wasPressed()){
                GUI gui = new GUI (Text.literal("level-up-menu"));
                Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();

                gui.minerWidth = gui.getWidth(skills.get(MINER_ID).totalScore);
                gui.warriorWidth = gui.getWidth(skills.get(WARRIOR_ID).totalScore);
                gui.farmerWidth = gui.getWidth(skills.get(FARMER_ID).totalScore);
                gui.archerWidth = gui.getWidth(skills.get(ARCHER_ID).totalScore);
                gui.blacksmithWidth = gui.getWidth(skills.get(BLACKSMITH_ID).totalScore);

                MinecraftClient.getInstance().setScreen(gui);
            }
        });
    }
}