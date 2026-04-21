package com.example;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.HitResult;

public class ModEvents {

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (source.getAttacker() instanceof PlayerEntity player) {

                SkillXPSystem.addExp(player, SkillAction.KILL_MOB, entity);
            }
        });

        // ⛏ КОПКА
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

            if (state.getBlock() == Blocks.STONE) {
                SkillXPSystem.addExp(player, SkillAction.MINE_STONE, null);
            }

            if (state.getBlock() == Blocks.DIAMOND_ORE ||
                    state.getBlock() == Blocks.IRON_ORE ||
                    state.getBlock() == Blocks.GOLD_ORE ||
                    state.getBlock() == Blocks.COAL_ORE) {

                SkillXPSystem.addExp(player, SkillAction.MINE_ORE, null);
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

            if (state.getBlock() == Blocks.WHEAT ||
                    state.getBlock() == Blocks.CARROTS ||
                    state.getBlock() == Blocks.POTATOES) {

                SkillXPSystem.addExp(player, SkillAction.FARM_CROP, null);
            }
        });


        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            Entity attacker = source.getAttacker();

            if (attacker instanceof PlayerEntity player) {

                if (source.getSource() != null &&
                        source.getSource().getClass().getSimpleName().contains("Arrow")) {

                    SkillXPSystem.addExp(player, SkillAction.BOW_HIT, entity);
                }
            }
            return true;
        });
    }
}