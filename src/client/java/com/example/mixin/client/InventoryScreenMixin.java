package com.example.mixin.client;

import com.example.ExampleModClient;
import com.example.GUI;
import com.example.IPlayerSkills;
import com.example.SkillState;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
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

                    MinecraftClient client = MinecraftClient.getInstance();

                    if (client.player == null) return;

                    GUI gui = new GUI(Text.literal("level-up-menu"));

                    Map<UUID, SkillState> skills = ((IPlayerSkills) client.player).getSkillsMap();

                    gui.minerWidth = gui.getWidth(skills.get(MINER_ID).totalScore);
                    gui.warriorWidth = gui.getWidth(skills.get(WARRIOR_ID).totalScore);
                    gui.farmerWidth = gui.getWidth(skills.get(FARMER_ID).totalScore);
                    gui.archerWidth = gui.getWidth(skills.get(ARCHER_ID).totalScore);
                    gui.blacksmithWidth = gui.getWidth(skills.get(BLACKSMITH_ID).totalScore);

                    client.setScreen(gui);
                }
        ).dimensions(this.x + 182, this.y + 8, 50, 20).build());
    }
}