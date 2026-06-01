package com.example;

import com.example.commands.ModCommands;
import com.example.packets.ServerSkillsSync;
import com.example.souds.ModSounds;
import jdk.jshell.Snippet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

import static com.example.PlayerSkills.RIDER_ID;

public class LevelUpMod implements ModInitializer {
    public static final String MODID = "level-up";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final int TICKS_INTERVAL = 1;
    private static final Identifier PIG_ID = new Identifier("minecraft", "pig_one_cm");
    private static final Identifier HORSE_ID = new Identifier("minecraft", "horse_one_cm");

    public  ServerPlayerEntity player;

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModCommands.register();

        ServerTickEvents.END_SERVER_TICK.register(server -> onServerTick(server));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            player = handler.player;


            PlayerSkills.ensureInitialized(player);
            ServerSkillsSync.send(player);
            ModEvents.register(player);
        });
        LOGGER.info("Mod 'Level Up' (Fabric) успешно загружен!");
    }
    private void onServerTick(MinecraftServer server) {
        int tick = server.getTicks();
        if (tick % TICKS_INTERVAL != 0) return;

        for (ServerWorld world : server.getWorlds()) {
            for (Entity entity : world.iterateEntities()) {
                if (entity.getType() == EntityType.PIG || entity.getType() == EntityType.HORSE) {
                    if (!entity.getPassengerList().isEmpty()) {
                        for (Entity passenger : entity.getPassengerList()) {
                            if (passenger instanceof PlayerEntity) {
                                Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();
                                setRiderExp(player);
                                ((net.minecraft.entity.LivingEntity) entity)
                                        .addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, TICKS_INTERVAL + 5,
                                                skills.get(RIDER_ID).level, false, false, true));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    private static void setRiderExp(ServerPlayerEntity player){
     long horseDistant = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.HORSE_ONE_CM));
     long pigDistant = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PIG_ONE_CM));

        long stat = horseDistant + pigDistant;
        if(stat % 100 == 0){
            Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();
            SkillXPSystem skillXPSystem = new SkillXPSystem();
            skillXPSystem.addExp(player, SkillAction.RIDE, null);

        }
    }
}