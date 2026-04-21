package com.example;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Set;

public class ModEvents {

    private static final Set<Block> ORE_BLOCKS = Set.of(
            Blocks.DIAMOND_ORE,
            Blocks.IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.COAL_ORE
    );

    private static final Set<Block> CROP_BLOCKS = Set.of(
            Blocks.WHEAT,
            Blocks.CARROTS,
            Blocks.POTATOES
    );

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (source.getAttacker() instanceof PlayerEntity player) {
                SkillXPSystem.addExp(player, SkillAction.KILL_MOB, entity);
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

            Block block = state.getBlock();

            if (block == Blocks.STONE) {
                SkillXPSystem.addExp(player, SkillAction.MINE_STONE, null);
                return;
            }

            if (ORE_BLOCKS.contains(block)) {
                SkillXPSystem.addExp(player, SkillAction.MINE_ORE, null);
                return;
            }

            if (CROP_BLOCKS.contains(block)) {
                SkillXPSystem.addExp(player, SkillAction.FARM_CROP, null);
            }
        });

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {

            Entity attacker = source.getAttacker();

            if (attacker instanceof PlayerEntity player) {
                boolean isArrow = source.getSource() != null
                        && source.getSource().getClass().getSimpleName().contains("Arrow");

                if (isArrow) {
                    SkillXPSystem.addExp(player, SkillAction.BOW_HIT, entity);
                }
            }

            return true;
        });
    }
}