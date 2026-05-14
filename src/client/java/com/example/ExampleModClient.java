package com.example;

import com.mojang.authlib.yggdrasil.request.AbuseReportRequest;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {
    ServerPlayerEntity player;

    @Override
    public void onInitializeClient() {

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            player = handler.player;
        });

      KeyBinding keyBinding =  KeyBindingHelper.registerKeyBinding(new KeyBinding("Open",InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,"category.level-up.keys") );
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(keyBinding.wasPressed()){
                GUI gui = new GUI (Text.literal("level-up-menu"));
                gui.setPlayer(player);
                MinecraftClient.getInstance().setScreen(gui);
            }
        });
    }
}