package com.example.commands;

import com.example.IPlayerSkills;
import com.example.PlayerSkills;
import com.example.SkillState;
import com.example.SkillXPSystem;
import com.example.packets.ServerSkillsSync;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Map;
import java.util.UUID;

import static com.example.PlayerSkills.*;

public class ModCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("skills")
                    .then(CommandManager.argument("target", EntityArgumentType.player())
                            .executes(context -> {
                                ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
                                Map<UUID, SkillState> skills = ((IPlayerSkills) target).getSkillsMap();
                                String info = "Скиллы " + target.getName().getString() + ":\n" +
                                        format(skills, "Miner", MINER_ID) +
                                        format(skills, "Warrior", WARRIOR_ID) +
                                        format(skills, "Farmer", FARMER_ID) +
                                        format(skills, "Archer", ARCHER_ID) +
                                        format(skills, "Blacksmith", BLACKSMITH_ID);
                                context.getSource().sendFeedback(() -> Text.literal(info), false);
                                return 1;
                            })
                    )
            );

            registerSetScore(dispatcher);
            registerReset(dispatcher);
        });
    }

    private static String format(Map<UUID, SkillState> skills, String name, UUID id) {
        SkillState s = skills.get(id);
        return s == null ? name + ": 0 lvl\n" : name + ": " + s.level + " lvl (" + s.totalScore + " exp)\n";
    }

    private static void registerSetScore(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("setscore")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .then(CommandManager.argument("skillName", StringArgumentType.string())
                                .then(CommandManager.argument("amount", FloatArgumentType.floatArg())
                                        .executes(context -> {
                                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                            String skillName = StringArgumentType.getString(context, "skillName");
                                            float amount = FloatArgumentType.getFloat(context, "amount");
                                               if (amount >= 0) {
                                                   Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();
                                                   UUID skillId = switch (skillName.toLowerCase()) {
                                                       case "miner" -> MINER_ID;
                                                       case "warrior" -> WARRIOR_ID;
                                                       case "farmer" -> FARMER_ID;
                                                       case "archer" -> ARCHER_ID;
                                                       case "blacksmith" -> BLACKSMITH_ID;

                                                       default -> null;
                                                   };
                                                   if (skillId != null) {
                                                       skills.put(skillId, new SkillState(amount, SkillXPSystem.getLevel(amount, player)));
                                                       ServerSkillsSync.send(player);
                                                       context.getSource().sendFeedback(() -> Text.literal("Установлен опыт " + amount + " для " + skillName), false);
                                                   } else {
                                                       context.getSource().sendError(Text.literal("Неизвестный навык!"));
                                                   }
                                                   return 1;
                                               }
                                               else {
                                                   context.getSource().sendError(Text.literal("Опыт не может быть отрицательным!"));
                                                   return 1;
                                               }
                                        })
                                )
                        )
                )
        );
    }

    private static void registerReset(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(CommandManager.literal("resetallscore")
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(context -> {
                            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                            ((IPlayerSkills) player).getSkillsMap().clear();
                            PlayerSkills.ensureInitialized(player);
                            context.getSource().sendFeedback(() -> Text.literal("Сброшено для " + player.getName().getString()), false);
                            return 1;
                        })));
    }
}