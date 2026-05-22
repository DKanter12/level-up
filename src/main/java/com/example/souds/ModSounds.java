package com.example.souds;

import net.fabricmc.fabric.api.networking.v1.S2CPlayChannelEvents;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static com.example.LevelUpMod.MODID;

public class ModSounds {

   public static final Identifier LEVEL_UP_SOUND_ID = new Identifier(MODID, "level_up_sound");
    public static final SoundEvent LEVEL_UP_SOUND = registerSound(SoundEvent.of(LEVEL_UP_SOUND_ID));

    private static SoundEvent registerSound(SoundEvent soundEvent){
        return Registry.register(Registries.SOUND_EVENT, LEVEL_UP_SOUND_ID, soundEvent);
    }

    public static void register(){

    }
}
