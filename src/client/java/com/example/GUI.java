package com.example;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GUI extends Screen {
    protected GUI(Text title) {
        super(title);
    }

    @Override
    public  void render(DrawContext context, int mouseX, int mouseY, float delta) {

             context.drawText(textRenderer, Text.literal("dwwd"), 5, 5, 1213131, true);
            super.render(context, 5, 5, 2.3f);
    }
}
