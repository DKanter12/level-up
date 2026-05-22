package com.example;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.example.LevelUpMod.MODID;

public class GUI extends Screen {
  public float lastExp = 100;
    public int minerWidth;
    public int warriorWidth;
    public int farmerWidth;
    public int archerWidth;
    public int blacksmithWidth;

    int percent;

    public GUI(Text title) {
        super(title);
    }

    @Override
    public  void render(DrawContext context, int mouseX, int mouseY, float delta) {
          //привет это джемини
        // СПАСИ МЕНЯ ПОЖАЛУЙСТА!!!!!

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

        setClassIcon("miner_icon", 8, context);
        setClassIcon("warrior_icon", 48, context);
        setClassIcon("farmer_icon", 88, context);
        setClassIcon("archer_icon", 128, context);
        setClassIcon("blacksmith_icon", 168, context);

        setProgressBar(21, minerWidth, 18, context);
        setProgressBar(61, warriorWidth, 58, context);
        setProgressBar(101, farmerWidth, 98, context);
        setProgressBar(141, archerWidth, 138, context);
        setProgressBar(181, blacksmithWidth, 178, context);

    }
      public int getWidth (float experience){
        if (experience <= 100){
            return (int) (1.5f * experience);
        }
        else {
            float onePercent = (getLevelUpExp(experience) - lastExp) / 100;
            percent = 1;
            while (true) {
                percent++;
                if (onePercent * percent >= experience - lastExp) {
                    float width = 1.5f * percent;
                    return (int) width;
                }
            }
        }
      }

    public float getLevelUpExp(float experience) {
        int level = 1;
        float LevelUpExp;
        while (true) {
            LevelUpExp = (float) Math.pow(1.1f, level) * 100 + lastExp;
            if (experience >= LevelUpExp) {
                level++;
                lastExp = LevelUpExp;
            } else {
                LevelUpExp = (float) Math.pow(1.1f, level) * 100 + lastExp;
                break;
            }
        }
        return LevelUpExp;
    }

    private void setProgressBar(int y, int width, int frameY, DrawContext context){

        context.drawTexture(new Identifier(MODID, "textures/progress_bar/progress_bar_frame.png"),
                258, frameY, 0, 0, 156, 22, 156, 22);

        context.drawTexture(new Identifier(MODID, "textures/progress_bar/progress_bar.png"),
                261, y, 0, 0, width, 16, 150, 16);

    }

    private void setClassIcon(String textureName, int y, DrawContext context) {
        context.drawTexture(new Identifier(MODID, "textures/" + textureName + ".png"),
                25, y, 0, 0, 34, 34, 34, 34
        );
    }
}
