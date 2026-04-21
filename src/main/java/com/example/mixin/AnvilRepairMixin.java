package com.example.mixin;

import com.example.SkillAction;
import com.example.SkillXPSystem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilRepairMixin {

    @Inject(method = "onTakeOutput", at = @At("HEAD"))
    private void onRepairTake(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        // Проверяем, что это серверная сторона и предмет не пустой
        if (!player.getWorld().isClient && !stack.isEmpty()) {
            // Вызываем твою систему опыта
            SkillXPSystem.addExp(player, SkillAction.ANVIL_REPAIR, null);
        }
    }
}