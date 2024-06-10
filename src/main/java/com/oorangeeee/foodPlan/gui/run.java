package com.oorangeeee.foodPlan.gui;

import com.oorangeeee.foodPlan.log.impl.guiLog;
import com.oorangeeee.foodPlan.log.log;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * @author 晋晨曦
 */
public class run {

    private final log guiLog = new guiLog();
    private final guiConfig guiConfig = new guiConfig();

    public static void main(String[] args) {
        // 配置字体
        // 利用一个button
        // 创建欢迎窗口
        JFrame frame = new JFrame("水果乐园饮食管理系统");
        JLabel startLabel = new JLabel();
        startLabel.setText("欢迎使用水果乐园饮食管理系统");
        startLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
    }
}

class guiConfig {

    private final log guiLog = new guiLog();

    private final String defaultFont = "微软雅黑";

    private final ArrayList<String> fontList = new ArrayList<String>();

    public guiConfig() {
    }

    public void addFont(String fontPath) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            fontList.add(customFont.getName());
        } catch (Exception e) {
            guiLog.writeLog("添加字体 " + fontPath + " 失败", guiLog.ERROR);
        }
    }

    public Font getFont(String fontName, int fontSize, int fontStyle) {
        if (fontList.contains(fontName)) {
            return new Font(fontName, fontStyle, fontSize);
        } else {
            guiLog.writeLog("字体 " + fontName + " 不存在", guiLog.ERROR);
            return new Font(defaultFont, fontStyle, fontSize);
        }
    }

    // getter

    public ArrayList<String> getFontList() {
        return fontList;
    }

    public String getDefaultFont() {
        return defaultFont;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("guiConfig{\n");
        stringBuilder.append("defaultFont: " + defaultFont + "\n");
        int i = 0;
        for (String font : fontList) {
            stringBuilder.append("font").append(i).append(": ").append(font).append("\n");
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof guiConfig guiConfig) {
            return this.fontList.equals(guiConfig.fontList);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
