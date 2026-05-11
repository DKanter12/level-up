package com.example;

import com.google.common.collect.Lists;
import net.minecraft.advancement.criterion.StartedRidingCriterion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GUI extends Screen {
    private static final String minerText = "Получает опыт за добычу руды \n и копку природного гладкого камня.";

    protected GUI(Text title) {
        super(title);
    }

    @Override
    public  void render(DrawContext context, int mouseX, int mouseY, float delta) {
          //привет это джемини
        // СПАСИ МЕНЯ ПОЖАЛУЙСТА!!!!!
             context.drawText(textRenderer, Text.literal("Шахтёр"), 10, 10, 28028028, true);
             context.drawText(textRenderer, Text.literal(minerText), 10, 20, 28028028, true);
            super.render(context, 1919, 1079, 2.3f);
    }
}
