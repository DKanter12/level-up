package com.example;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.example.LevelUpMod.MODID;

public class GUI extends Screen {
    private static final int TEXT_X = 24;
    private static final int ICON_X = 90;

    // Шаг смещения для каждого следующего класса по горизонтали (вправо)
    private static final int X_OFFSET_STEP = 240;

    public  float lastExp = 100;
    public int minerWidth;
    public int warriorWidth;
    public int farmerWidth;
    public int archerWidth;
    public int blacksmithWidth;

    int percent;

    // --- ПЕРЕМЕННЫЕ ДЛЯ ПЛАВАЮЩЕГО GUI ---
    private double scrollX = 0;
    private double scrollY = 0;
    private boolean isDragging = false;

    public GUI(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Рендерим стандартный задний фон (затемнение мира)
        this.renderBackground(context);

        // Сохраняем состояние матрицы и сдвигаем весь последующий рендер
        context.getMatrices().push();
        context.getMatrices().translate(scrollX, scrollY, 0);

        int minerX = 0;
        renderClassLines("miner", TEXT_X + minerX, 50, context);
        setClassIcon("miner_icon", minerX, 8, context);
        setProgressBar(minerX, minerWidth, context);

        int warriorX = X_OFFSET_STEP;
        renderClassLines("warrior", TEXT_X + 5 + warriorX, 50, context);
        setClassIcon("warrior_icon", warriorX, 8, context);
        setProgressBar(warriorX, warriorWidth, context);

        int farmerX = X_OFFSET_STEP * 2;
        renderClassLines("farmer", TEXT_X + farmerX, 50, context);
        setClassIcon("farmer_icon", farmerX, 8, context);
        setProgressBar(farmerX, farmerWidth, context);

        int archerX = X_OFFSET_STEP * 3; // Сдвиг вправо на 720
        renderClassLines("archer", TEXT_X  + archerX, 50, context);
        setClassIcon("archer_icon", archerX, 8, context);
        setProgressBar(archerX, archerWidth, context);

        int blacksmithX = X_OFFSET_STEP * 4;
        renderClassLines("blacksmith", TEXT_X + blacksmithX, 50, context);
        setClassIcon("blacksmith_icon", blacksmithX, 8, context);
        setProgressBar(blacksmithX, blacksmithWidth, context);

        context.getMatrices().pop();

        super.render(context, mouseX, mouseY, delta);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.isDragging = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.isDragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.isDragging) {
            this.scrollX += deltaX;
            this.scrollY += deltaY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }private void renderClassLines(String classKey, int x, int yTitle, DrawContext context) {
        context.drawText(textRenderer, Text.translatable("class." + classKey),
                x + 66, yTitle, 0xFFFF00, true);

        context.drawText(textRenderer, Text.translatable("class." + classKey + ".line1"),
                x, yTitle + 10, 0xFFFFFF, true);

        context.drawText(textRenderer, Text.translatable("class." + classKey + ".line2"),
                x, yTitle + 20, 0xFFFFFF, true);
    }

    public int getWidth (float experience){
        if (experience <= 100){
            return (int) (2f * experience);
        }
        else {
            float onePercent = (getLevelUpExp(experience) - lastExp) / 100;
            percent = 1;
            while (true) {
                percent++;
                if (onePercent * percent >= experience - lastExp) {
                    float width = 2f * percent;
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

    private void setProgressBar(int offsetX, int width, DrawContext context){
        context.drawTexture(new Identifier(MODID, "textures/progress_bar/progress_bar_frame.png"),
                15 + offsetX, 90, 0, 0, 206, 26, 206, 26);

        context.drawTexture(new Identifier(MODID, "textures/progress_bar/progress_bar.png"),
                18 + offsetX, 93, 0, 0, width, 20, 200, 20);
    }

    private void setClassIcon(String textureName, int offsetX, int y, DrawContext context) {
        context.drawTexture(new Identifier(MODID, "textures/" + textureName + ".png"),
                ICON_X + offsetX, y, 0, 0, 34, 34, 34, 34);
    }
}

