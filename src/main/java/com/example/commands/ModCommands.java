package com.example.commands;

import com.example.IPlayerSkills;
import com.example.PlayerSkills;
import com.example.SkillState;
import com.example.SkillXPSystem;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.PlayerSkills.*;
import static com.example.SkillXPSystem.lastExperienceLevelUp;
import static com.mojang.text2speech.Narrator.LOGGER;

public class ModCommands {
    private static boolean setScororeResult = false;

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("skills")
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .executes(context -> {
                                ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, "target");

                                String currentSkills = "Скиллы игрока " + targetPlayer.getName().getString() + ":\n" +
                                        classInformation("Miner", MINER_ID) +
                                        classInformation("Warrior", WARRIOR_ID) +
                                        classInformation("Farmer", FARMER_ID) +
                                        classInformation("Archer", ARCHER_ID) +
                                        classInformation("Blacksmith", BLACKSMITH_ID);

                                context.getSource().sendFeedback(
                                        () -> Text.literal(currentSkills),
                                        false
                                );

                                return 1;
                            })
                    )
            );
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommand(dispatcher );
            resetallscore(dispatcher);
        });


    }

    private static String classInformation(String className, UUID uuid) {
        SkillState state = PlayerSkills.playerSkills.get(uuid);
        if (state == null) {
            return className + ": 0 level 0 exp\n";
        }
        return className + ": " + state.level + " level " + state.totalScore + " exp\n";
    }

    private static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("setscrore")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .then(CommandManager.argument("message", StringArgumentType.string())
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> {

                                            var player = EntityArgumentType.getPlayer(context, "player");
                                            String message = StringArgumentType.getString(context, "message");
                                            int amount = IntegerArgumentType.getInteger(context, "amount");
                                            setScrore(message, amount);
                                            if (setScororeResult) {
                                                player.sendMessage(
                                                        net.minecraft.text.Text.literal("Опыт игрока " + player.getName().getString() + " задан в значение "
                                                                + amount + " в навыке " + message),
                                                        false
                                                );
                                            } else {
                                                player.sendMessage(
                                                        net.minecraft.text.Text.literal("Скилл с именем " + message + " не найден"), false);
                                            }

                                            return 1;
                                        })
                                )
                        )
                )
        );
    }

    private static void setScrore(String className, float exp) {
        SkillState state = new SkillState();
        state.totalScore = exp;

        state.level = SkillXPSystem.levelUp(exp);

        switch (className) {
            case "Miner":
                playerSkills.put(MINER_ID, state);
                setScororeResult = true;
                break;
            case "Warrior":
                playerSkills.put(WARRIOR_ID, state);
                setScororeResult = true;
                break;
            case "Farmer":
                playerSkills.put(FARMER_ID, state);
                setScororeResult = true;
                break;
            case "Archer":
                playerSkills.put(ARCHER_ID, state);
                setScororeResult = true;
                break;
            case "Blacksmith":
                playerSkills.put(BLACKSMITH_ID, state);
                setScororeResult = true;
                break;
        }
    }

    private static void resetallscore(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("resetallscore")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(context -> {
                            var player = EntityArgumentType.getPlayer(context, "player");

                            PlayerSkills.playerSkills.clear();
                            ((IPlayerSkills) player).setSkillsMap(PlayerSkills.playerSkills);
                            player.sendMessage(
                                    net.minecraft.text.Text.literal("Сброшены данные навыков для игрока " + player.getName().getString()),
                                    false
                            );
                            return 0;
                        })));
    }
}