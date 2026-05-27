package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.UUID;

import static com.example.PlayerSkills.*;
import static com.example.souds.ModSounds.OPEN_MENU;

public class ExampleModClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ClientSkillsReceiver.register();
        registerGui();
    }


    public void registerGui(){
        KeyBinding keyBinding =  KeyBindingHelper.registerKeyBinding(new KeyBinding("Open",InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,"category.level-up.keys"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(keyBinding.wasPressed()){

                PlayerEntity player = MinecraftClient.getInstance().player;

                if(player != null) {
                    player.playSound(OPEN_MENU, 1f, 1f);
                }

                GUI gui = new GUI (Text.literal("level-up-menu"));
                gui.minerWidth = gui.getWidth(ClientSkillsCache.MINER_SCORE);
                gui.warriorWidth = gui.getWidth(ClientSkillsCache.WARRIOR_SCORE);
                gui.farmerWidth = gui.getWidth(ClientSkillsCache.FARMER_SCORE);
                gui.archerWidth = gui.getWidth(ClientSkillsCache.ARCHER_SCORE);
                gui.blacksmithWidth = gui.getWidth(ClientSkillsCache.BLACKSMITH_SCORE);

                MinecraftClient.getInstance().setScreen(gui);
            }
        });
    }
}