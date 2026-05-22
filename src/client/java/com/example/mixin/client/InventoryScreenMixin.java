package com.example.mixin.client;

import com.example.*;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

import static com.example.PlayerSkills.*;
import static com.example.PlayerSkills.ARCHER_ID;
import static com.example.PlayerSkills.BLACKSMITH_ID;
import static com.example.souds.ModSounds.OPEN_MENU;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends HandledScreen<PlayerScreenHandler> {
    protected InventoryScreenMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addButton(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Skills"),
                button -> {
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    if (player != null) {
                        player.playSound(OPEN_MENU, 1f, 1f);
                    }

                    GUI gui = new GUI (Text.literal("level-up-menu"));
                    gui.minerWidth = gui.getWidth(ClientSkillsCache.MINER_SCORE);
                    gui.warriorWidth = gui.getWidth(ClientSkillsCache.WARRIOR_SCORE);
                    gui.farmerWidth = gui.getWidth(ClientSkillsCache.ARCHER_SCORE);
                    gui.archerWidth = gui.getWidth(ClientSkillsCache.FARMER_SCORE);
                    gui.blacksmithWidth = gui.getWidth(ClientSkillsCache.BLACKSMITH_SCORE);

                    MinecraftClient.getInstance().setScreen(gui);
                }
        ).dimensions(this.x + 182, this.y + 8, 50, 20).build());
    }
}