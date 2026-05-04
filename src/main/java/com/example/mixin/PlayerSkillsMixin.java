package com.example.mixin;

import com.example.IPlayerSkills;
import com.example.SkillState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerSkillsMixin implements IPlayerSkills {

    @Unique
    private Map<UUID, SkillState> skillsMap = new HashMap<>();

    @Override
    public  Map<UUID, SkillState> getSkillsMap() {
        return this.skillsMap;
    }

    @Override
    public void setSkillsMap(Map<UUID, SkillState> map) {
        this.skillsMap = map;
    }

    // Сохранение: превращаем Map<UUID, SkillsState> в NBT
    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    public void writeSkillsToNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound allSkillsNbt = new NbtCompound();

        for (Map.Entry<UUID, SkillState> entry : skillsMap.entrySet()) {
            allSkillsNbt.put(entry.getKey().toString(), entry.getValue().writeToNbt());
        }

        nbt.put("PlayerSkillsData", allSkillsNbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    public void readSkillsFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("PlayerSkillsData")) {
            this.skillsMap = new HashMap<>();
            NbtCompound allSkillsNbt = nbt.getCompound("PlayerSkillsData");

            for (String skillUuidStr : allSkillsNbt.getKeys()) {
                UUID skillUuid = UUID.fromString(skillUuidStr);
                NbtCompound skillDataNbt = allSkillsNbt.getCompound(skillUuidStr);

                this.skillsMap.put(skillUuid, SkillState.fromNbt(skillDataNbt));
            }
        }
    }
}