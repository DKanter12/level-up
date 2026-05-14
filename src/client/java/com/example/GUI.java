package com.example;

import com.google.common.collect.Lists;
import net.minecraft.advancement.criterion.StartedRidingCriterion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.LevelUpMod.MODID;
import static com.example.PlayerSkills.MINER_ID;
import static com.example.SkillXPSystem.levelUp;

public class GUI extends Screen {

   public ServerPlayerEntity player;

    public void setPlayer(ServerPlayerEntity player){
        this.player = player;
    }

    protected GUI(Text title) {
        super(title);
    }

    @Override
    public  void render(DrawContext context, int mouseX, int mouseY, float delta) {
          //привет это джемини
        // СПАСИ МЕНЯ ПОЖАЛУЙСТА!!!!!
        Map<UUID, SkillState> skills = ((IPlayerSkills) player).getSkillsMap();


        context.drawText(textRenderer, Text.translatable("class.miner"), 65, 10, 0xFFFF00, true);
        context.drawText(textRenderer, Text.translatable("class.miner.line1"), 65, 20, 16777215, true);
        context.drawText(textRenderer, Text.translatable("class.miner.line2"), 65, 30, 16777215, true);

        context.drawText(textRenderer, Text.translatable("class.warrior"), 65, 50, 0xFFFF00, true);
        context.drawText(textRenderer, Text.translatable("class.warrior.line1"), 65, 60, 16777215, true);
        context.drawText(textRenderer, Text.translatable("class.warrior.line2"), 65, 70, 16777215, true);

        context.drawText(textRenderer, Text.translatable("class.farmer"), 65, 90, 0xFFFF00, true);
        context.drawText(textRenderer, Text.translatable("class.farmer.line1"), 65, 100, 16777215, true);
        context.drawText(textRenderer, Text.translatable("class.farmer.line2"), 65, 110, 16777215, true);

        context.drawText(textRenderer, Text.translatable("class.archer"), 65, 130, 0xFFFF00, true);
        context.drawText(textRenderer, Text.translatable("class.archer.line1"), 65, 140, 16777215, true);
        context.drawText(textRenderer, Text.translatable("class.archer.line2"), 65, 150, 16777215, true);

        context.drawText(textRenderer, Text.translatable("class.blacksmith"), 65, 170, 0xFFFF00, true);
        context.drawText(textRenderer, Text.translatable("class.blacksmith.line1"), 65, 180, 16777215, true);
        context.drawText(textRenderer, Text.translatable("class.blacksmith.line2"), 65, 190, 16777215, true);

        context.drawTexture(new Identifier(MODID, "textures/miner_icon.png"),      25, 8,   0, 0, 34, 34, 34, 34);
        context.drawTexture(new Identifier(MODID, "textures/warrior_icon.png"),    25, 48,  0, 0, 34, 34, 34, 34);
        context.drawTexture(new Identifier(MODID, "textures/farmer_icon.png"),     25, 88,  0, 0, 34, 34, 34, 34);
        context.drawTexture(new Identifier(MODID, "textures/archer_icon.png"),     25, 128, 0, 0, 34, 34, 34, 34);
        context.drawTexture(new Identifier(MODID, "textures/blacksmith_icon.png"), 25, 168, 0, 0, 34, 34, 34, 34);

        context.drawTexture(new Identifier(MODID, "textures/progress_bar/progress_bar_frame.png"),   258, 18,  0, 0, getWidth(1,skills.get(MINER_ID).totalScore), 22, 156, 22);
        context.drawTexture(new Identifier(MODID, "textures/progress_bar/progress_bar.png"),   261, 21,  0, 0, 78, 16, 150, 16);

    }
      public int getWidth ( float totalScore){
          levelUp(totalScore)
          float onePercent = nextLevelUpNumber / 100;
          int lastNumberPercent = 1;
          float width = 1.56f * lastNumberPercent;

          while (onePercent * lastNumberPercent < totalScore){
              lastNumberPercent++;
          }
          return (int) width;
      }
}
