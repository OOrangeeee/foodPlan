// -*- coding: utf-8 -*-
package com.oorangeeee.foodPlan.gui;

import com.oorangeeee.foodPlan.configDriver.configDriver;
import com.oorangeeee.foodPlan.configDriver.impl.config;
import com.oorangeeee.foodPlan.feature.food.foodLists;
import com.oorangeeee.foodPlan.feature.food.foods;
import com.oorangeeee.foodPlan.feature.food.impl.food;
import com.oorangeeee.foodPlan.feature.food.impl.foodList;
import com.oorangeeee.foodPlan.log.impl.guiLog;
import com.oorangeeee.foodPlan.log.log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.function.Consumer;


/**
 * @author 晋晨曦
 */
public class run {

    private static final log guiLog = new guiLog();
    private static final configDriver config = new config();
    private static final guiConfig configGui = new guiConfig();


    // 重写JTextField的paintComponent()方法：重绘缓存。解决Java Swing 使用idea输入中文，出现白板。
    public static class JTextField2 extends JTextField {
        @Serial
        private static final long serialVersionUID = 1L;
        private BufferedImage buffer = null;

        @Override
        public void paintComponent(Graphics g) {
            Component window = this.getTopLevelAncestor();
            if (window instanceof Window && !((Window) window).isOpaque()) {
                // This is a translucent window, so we need to draw to a buffer
                // first to work around a bug in the DirectDraw rendering in Swing.
                int w = this.getWidth();
                int h = this.getHeight();
                if (buffer == null || buffer.getWidth() != w || buffer.getHeight() != h) {
                    // Create a new buffer based on the current size.
                    GraphicsConfiguration gc = this.getGraphicsConfiguration();
                    buffer = gc.createCompatibleImage(w, h, BufferedImage.TRANSLUCENT);
                }

                // Use the super class's paintComponent implementation to draw to
                // the buffer, then write that buffer to the original Graphics object.
                Graphics bufferGraphics = buffer.createGraphics();
                try {
                    super.paintComponent(bufferGraphics);
                } finally {
                    bufferGraphics.dispose();
                }
                g.drawImage(buffer, 0, 0, w, h, 0, 0, w, h, null);
            } else {
                // This is not a translucent window, so we can call the super class
                // implementation directly.
                super.paintComponent(g);
            }
        }
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.noddraw", "true");
        config.defaultConfig();
        foodLists foodList = new foodList();
        String iconPath = config.getConfig("iconPath");
        // 读取图标文件
        Image icon = null;
        try {
            icon = ImageIO.read(new File(iconPath));
        } catch (IOException e) {
            guiLog.writeLog("读取图标文件失败", guiLog.ERROR);
        }
        // 创建主窗口
        JFrame frame = new JFrame("好吃果园");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 800);
        frame.setLocationRelativeTo(null);

        // 设置图标
        if (icon != null) {
            frame.setIconImage(icon);
        }

        // 创建CardLayout布局管理器
        CardLayout cardLayout = new CardLayout();
        frame.setLayout(cardLayout);

        // 创建欢迎页面
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("欢迎使用好吃果园!", JLabel.CENTER);
        String cangErFont = configGui.addFont(configDriver.PROJECT_HOME_DIR + "/仓耳与墨.TTF");
        welcomeLabel.setFont(configGui.getFont(cangErFont, 75, Font.BOLD));
        JButton startButton = new JButton("开始使用");
        startButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        startButton.setPreferredSize(new Dimension(20, 50));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(startButton, BorderLayout.SOUTH);

        // 创建菜单页面
        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        JButton button1 = new JButton("录入菜品");
        button1.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        button1.setPreferredSize(new Dimension(150, 50));
        JButton button2 = new JButton("浏览菜品");
        button2.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        button2.setPreferredSize(new Dimension(150, 50));
        JButton button3 = new JButton("今天吃什么！");
        button3.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        button3.setPreferredSize(new Dimension(150, 50));
        JButton searchButton = new JButton("检索菜品");
        searchButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        searchButton.setPreferredSize(new Dimension(150, 50));
        JLabel menuLabel = new JLabel("好吃果园");
        menuLabel.setFont(configGui.getFont(cangErFont, 55, Font.BOLD));
        menuPanel.add(menuLabel, gbc);
        menuPanel.add(button1, gbc);
        menuPanel.add(button2, gbc);
        menuPanel.add(button3, gbc);
        menuPanel.add(searchButton, gbc);

        // 创建录入菜品页面
        JPanel inputPanel = new JPanel(new GridBagLayout());
        JLabel inputLabel = new JLabel("请输入菜品信息", JLabel.CENTER);
        inputLabel.setFont(configGui.getFont(cangErFont, 20, Font.BOLD));
        JTextField dishNameField = new JTextField(20);
        dishNameField.setFont(new Font("宋体", Font.PLAIN, 15));
        JLabel dishNameLabel = new JLabel("菜品名称", JLabel.CENTER);
        dishNameLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
        JPanel mealTypePanel = new JPanel(new FlowLayout());
        String[] mealTypes = {"早餐", "午餐", "晚餐", "零食"};
        JToggleButton[] mealButtons = new JToggleButton[mealTypes.length];
        for (int i = 0; i < mealTypes.length; i++) {
            mealButtons[i] = new JToggleButton(mealTypes[i]);
            mealButtons[i].setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
            mealTypePanel.add(mealButtons[i]);
        }
        JPanel dishTypePanel = new JPanel(new FlowLayout());
        String[] dishTypes = {"面食", "蒸点", "炒菜", "汤", "饮料", "水果", "火锅", "烧烤", "快餐", "轻食", "甜品", "蔬菜", "油炸食品", "烘焙", "米饭", "零食"};
        JToggleButton[] dishButtons = new JToggleButton[dishTypes.length];
        for (int i = 0; i < dishTypes.length; i++) {
            dishButtons[i] = new JToggleButton(dishTypes[i]);
            dishButtons[i].setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
            dishTypePanel.add(dishButtons[i]);
        }
        JTextField notesField = new JTextField(20);
        notesField.setFont(new Font("宋体", Font.PLAIN, 15));
        JLabel notesLabel = new JLabel("备注", JLabel.CENTER);
        notesLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
        JButton confirmButton = new JButton("确认");
        confirmButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        confirmButton.setPreferredSize(new Dimension(200, 50));

        // 布局组件
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(inputLabel, gbc);
        gbc.insets = new Insets(5, 5, 0, 5);
        inputPanel.add(dishNameLabel, gbc);
        gbc.insets = new Insets(0, 5, 5, 5);
        inputPanel.add(dishNameField, gbc);
        gbc.insets = new Insets(5, 5, 0, 5);
        inputPanel.add(mealTypePanel, gbc);
        gbc.insets = new Insets(0, 5, 5, 5);
        inputPanel.add(dishTypePanel, gbc);
        gbc.insets = new Insets(5, 5, 0, 5);
        inputPanel.add(notesLabel, gbc);
        gbc.insets = new Insets(0, 5, 5, 5);
        inputPanel.add(notesField, gbc);
        gbc.insets = new Insets(10, 5, 10, 5);
        inputPanel.add(confirmButton, gbc);

        // 创建浏览菜品页面
        JPanel viewPanel = new JPanel(new BorderLayout());
        JLabel viewLabel = new JLabel("所有菜品", JLabel.CENTER);
        viewLabel.setFont(configGui.getFont(cangErFont, 20, Font.BOLD));
        viewPanel.add(viewLabel, BorderLayout.NORTH);

        // 添加返回菜单页面的按钮
        JButton backButton = new JButton("返回菜单");
        backButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        backButton.setPreferredSize(new Dimension(200, 50));
        viewPanel.add(backButton, BorderLayout.SOUTH);

        JPanel foodListPanel = new JPanel();
        foodListPanel.setLayout(new BoxLayout(foodListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(foodListPanel);
        viewPanel.add(scrollPane, BorderLayout.CENTER);

        // 更新浏览菜品页面的方法
        Runnable updateFoodListView = () -> {
            foodListPanel.removeAll();
            for (foods f : foodList.getFoods()) {
                JPanel foodPanel = new JPanel();
                foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));

                JLabel foodNameLabel = new JLabel("菜品名称: " + f.getFoodName(), JLabel.CENTER);
                foodNameLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));

                JPanel timeTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                for (String timeType : f.getTimeType()) {
                    JLabel timeTypeLabel = new JLabel(timeType);
                    timeTypeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    timeTypeLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
                    timeTypePanel.add(timeTypeLabel);
                }

                JPanel foodTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                for (String foodType : f.getFoodType()) {
                    JLabel foodTypeLabel = new JLabel(foodType);
                    foodTypeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    foodTypeLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
                    foodTypePanel.add(foodTypeLabel);
                }

                JLabel remarkLabel = new JLabel("评论: " + f.getRemark(), JLabel.CENTER);
                remarkLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));

                // 添加组件到 foodPanel，并居中对齐
                foodNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                timeTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                foodTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                remarkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                foodPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 添加较小的空白区域
                foodPanel.add(foodNameLabel);
                foodPanel.add(Box.createRigidArea(new Dimension(0, 2))); // 添加较小的空白区域
                foodPanel.add(timeTypePanel);
                foodPanel.add(Box.createRigidArea(new Dimension(0, 2))); // 添加较小的空白区域
                foodPanel.add(foodTypePanel);
                foodPanel.add(Box.createRigidArea(new Dimension(0, 2))); // 添加较小的空白区域
                foodPanel.add(remarkLabel);
                foodPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 添加较小的空白区域

                JLabel separator = new JLabel("----------------------------------------------------------", JLabel.CENTER);
                separator.setAlignmentX(Component.CENTER_ALIGNMENT);
                foodPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 添加较小的空白区域
                foodPanel.add(separator);
                foodPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 添加较小的空白区域

                foodListPanel.add(foodPanel);
            }
            foodListPanel.revalidate();
            foodListPanel.repaint();
        };

        // 创建吃什么页面
        JPanel whatToEatPanel = new JPanel();
        whatToEatPanel.setLayout(new BoxLayout(whatToEatPanel, BoxLayout.Y_AXIS));
        whatToEatPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐

        JLabel whatToEatLabel = new JLabel("吃什么吃什么！！", JLabel.CENTER);
        whatToEatLabel.setFont(configGui.getFont(cangErFont, 30, Font.BOLD));
        whatToEatLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        whatToEatPanel.add(Box.createVerticalGlue()); // 添加垂直空间以居中
        whatToEatPanel.add(whatToEatLabel);

        whatToEatPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 添加空白区域

        JButton breakfastButton = new JButton("吃什么早餐！");
        breakfastButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        breakfastButton.setPreferredSize(new Dimension(200, 50));
        breakfastButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        whatToEatPanel.add(breakfastButton);

        whatToEatPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JButton lunchButton = new JButton("吃什么午餐！");
        lunchButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        lunchButton.setPreferredSize(new Dimension(200, 50));
        lunchButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        whatToEatPanel.add(lunchButton);

        whatToEatPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JButton dinnerButton = new JButton("吃什么晚餐！");
        dinnerButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        dinnerButton.setPreferredSize(new Dimension(200, 50));
        dinnerButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        whatToEatPanel.add(dinnerButton);

        whatToEatPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JButton snackButton = new JButton("吃什么零食！");
        snackButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        snackButton.setPreferredSize(new Dimension(200, 50));
        snackButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        whatToEatPanel.add(snackButton);

        whatToEatPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JButton returnButton = new JButton("返回菜单");
        returnButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        returnButton.setPreferredSize(new Dimension(200, 50));
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        whatToEatPanel.add(returnButton);

        whatToEatPanel.add(Box.createVerticalGlue()); // 添加垂直空间以居中

        // 创建检索页面
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐

        JButton viewBreakfastButton = new JButton("查看所有早餐");
        viewBreakfastButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        viewBreakfastButton.setPreferredSize(new Dimension(200, 50));
        viewBreakfastButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        searchPanel.add(Box.createVerticalGlue()); // 添加垂直空间以居中
        searchPanel.add(viewBreakfastButton);

        searchPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JButton viewLunchButton = new JButton("查看所有午餐");
        viewLunchButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        viewLunchButton.setPreferredSize(new Dimension(200, 50));
        viewLunchButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        searchPanel.add(viewLunchButton);

        searchPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JButton viewDinnerButton = new JButton("查看所有晚餐");
        viewDinnerButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        viewDinnerButton.setPreferredSize(new Dimension(200, 50));
        viewDinnerButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        searchPanel.add(viewDinnerButton);

        searchPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JButton viewSnackButton = new JButton("查看所有零食");
        viewSnackButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        viewSnackButton.setPreferredSize(new Dimension(200, 50));
        viewSnackButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        searchPanel.add(viewSnackButton);

        searchPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 添加空白区域

        JLabel searchLabel = new JLabel("按照类型检索", JLabel.CENTER);
        searchLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
        searchLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        searchPanel.add(searchLabel);

        searchPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 添加空白区域

        JPanel searchBoxPanel = new JPanel(new FlowLayout());
        searchBoxPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField searchTextField = new JTextField(10);
        searchTextField.setFont(new Font("宋体", Font.PLAIN, 15));
        searchBoxPanel.add(searchTextField);
        JButton searchButtonAction = new JButton("查询");
        searchButtonAction.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        searchButtonAction.setPreferredSize(new Dimension(200, 50));
        searchButtonAction.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        searchBoxPanel.add(searchButtonAction);

        searchPanel.add(searchBoxPanel);

        searchPanel.add(Box.createVerticalGlue()); // 添加垂直空间以居中

        JButton searchReturnButton = new JButton("返回菜单");
        searchReturnButton.setFont(configGui.getFont(cangErFont, 15, Font.BOLD));
        searchReturnButton.setPreferredSize(new Dimension(200, 50));
        searchReturnButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        searchPanel.add(searchReturnButton);

        searchPanel.add(Box.createVerticalGlue()); // 添加垂直空间以居中

        // 设置检索按钮的点击事件
        searchButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "searchPanel"));

        // 设置返回菜单按钮的点击事件
        searchReturnButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "menuPanel"));

        // 创建显示检索结果的小窗口
        Image finalIcon = icon;
        Consumer<foods[]> showFoodListInfo = (foods[] foodArray) -> {
            JDialog infoDialog = new JDialog();
            infoDialog.setTitle("菜品信息");
            infoDialog.setSize(400, 400);
            if (finalIcon != null) {
                infoDialog.setIconImage(finalIcon);
            }
            infoDialog.setLocationRelativeTo(null);

            JPanel foodPanel = new JPanel();
            foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPaneInfo = new JScrollPane(foodPanel);

            if (foodArray == null || foodArray.length == 0) {
                JLabel noFoodLabel = new JLabel("没有对应菜品！", JLabel.CENTER);
                noFoodLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
                noFoodLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
                foodPanel.add(Box.createVerticalGlue()); // 添加垂直空间以居中
                foodPanel.add(noFoodLabel);
                foodPanel.add(Box.createVerticalGlue()); // 添加垂直空间以居中
            } else {
                for (foods f : foodArray) {
                    JLabel foodNameLabel = new JLabel("菜品名称: " + f.getFoodName(), JLabel.CENTER);
                    foodNameLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
                    foodNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
                    foodPanel.add(foodNameLabel);

                    JPanel timeTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    for (String timeType : f.getTimeType()) {
                        JLabel timeTypeLabel = new JLabel(timeType);
                        timeTypeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        timeTypeLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
                        timeTypePanel.add(timeTypeLabel);
                    }
                    foodPanel.add(timeTypePanel);

                    JPanel foodTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    for (String foodType : f.getFoodType()) {
                        JLabel foodTypeLabel = new JLabel(foodType);
                        foodTypeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        foodTypeLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
                        foodTypePanel.add(foodTypeLabel);
                    }
                    foodPanel.add(foodTypePanel);

                    JLabel remarkLabel = new JLabel("评论: " + f.getRemark(), JLabel.CENTER);
                    remarkLabel.setFont(configGui.getFont(cangErFont, 15, Font.PLAIN));
                    remarkLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
                    foodPanel.add(remarkLabel);

                    JLabel separator = new JLabel("----------------------------------------------------------", JLabel.CENTER);
                    separator.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
                    foodPanel.add(separator);
                }
            }

            infoDialog.add(scrollPaneInfo);
            infoDialog.setVisible(true);
        };

        // 设置检索页面按钮的点击事件
        viewBreakfastButton.addActionListener(e -> showFoodListInfo.accept(foodList.getBreakfast()));
        viewLunchButton.addActionListener(e -> showFoodListInfo.accept(foodList.getLunch()));
        viewDinnerButton.addActionListener(e -> showFoodListInfo.accept(foodList.getDinner()));
        viewSnackButton.addActionListener(e -> showFoodListInfo.accept(foodList.getSnack()));
        searchButtonAction.addActionListener(e -> showFoodListInfo.accept(foodList.getFoodByType(searchTextField.getText())));

        // 将六个页面添加到主窗口中
        frame.add(welcomePanel, "welcomePanel");
        frame.add(menuPanel, "menuPanel");
        frame.add(inputPanel, "inputPanel");
        frame.add(viewPanel, "viewPanel");
        frame.add(whatToEatPanel, "whatToEatPanel");
        frame.add(searchPanel, "searchPanel");

        // 设置开始使用按钮的点击事件
        startButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "menuPanel"));

        // 设置录入菜品按钮的点击事件
        button1.addActionListener(e -> cardLayout.show(frame.getContentPane(), "inputPanel"));

        // 设置浏览菜品按钮的点击事件
        button2.addActionListener(e -> {
            updateFoodListView.run(); // 切换到浏览菜品页面前，更新页面内容
            cardLayout.show(frame.getContentPane(), "viewPanel");
        });

        // 设置今天吃什么按钮的点击事件
        button3.addActionListener(e -> cardLayout.show(frame.getContentPane(), "whatToEatPanel"));

        // 设置吃什么页面按钮的点击事件
        breakfastButton.addActionListener(e -> {
            foods randomFood = foodList.getBreakfastByRandom();
            if (randomFood == null) {
                showFoodListInfo.accept(null);
            } else {
                showFoodListInfo.accept(new foods[]{randomFood});
            }
        });

        lunchButton.addActionListener(e -> {
            foods randomFood = foodList.getLunchByRandom();
            if (randomFood == null) {
                showFoodListInfo.accept(null);
            } else {
                showFoodListInfo.accept(new foods[]{randomFood});
            }
        });

        dinnerButton.addActionListener(e -> {
            foods randomFood = foodList.getDinnerByRandom();
            if (randomFood == null) {
                showFoodListInfo.accept(null);
            } else {
                showFoodListInfo.accept(new foods[]{randomFood});
            }
        });

        snackButton.addActionListener(e -> {
            foods randomFood = foodList.getSnackByRandom();
            if (randomFood == null) {
                showFoodListInfo.accept(null);
            } else {
                showFoodListInfo.accept(new foods[]{randomFood});
            }
        });

        // 设置返回菜单按钮的点击事件
        backButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "menuPanel"));
        returnButton.addActionListener(e -> cardLayout.show(frame.getContentPane(), "menuPanel"));

        // 设置确认按钮的点击事件
        confirmButton.addActionListener(e -> {
            String dishName = dishNameField.getText();
            String notes = notesField.getText();
            boolean[] mealSelected = new boolean[mealTypes.length];
            boolean[] dishSelected = new boolean[dishTypes.length];

            for (int i = 0; i < mealTypes.length; i++) {
                mealSelected[i] = mealButtons[i].isSelected();
            }
            for (int i = 0; i < dishTypes.length; i++) {
                dishSelected[i] = dishButtons[i].isSelected();
            }

            // 根据mealTypes和dishTypes的选中情况，生成mealTypesStr和dishTypesStr
            StringBuilder mealTypesStr = new StringBuilder();
            StringBuilder dishTypesStr = new StringBuilder();
            for (int i = 0; i < mealTypes.length; i++) {
                if (mealSelected[i]) {
                    mealTypesStr.append(mealTypes[i]).append(",");
                }
            }
            for (int i = 0; i < dishSelected.length; i++) {
                if (dishSelected[i]) {
                    dishTypesStr.append(dishTypes[i]).append(",");
                }
            }
            foodList.addFood(new food(dishName, mealTypesStr.toString(), dishTypesStr.toString(), notes));

            // 清空所有的输入框，还原所有的按钮
            dishNameField.setText("");
            notesField.setText("");
            for (JToggleButton button : mealButtons) {
                button.setSelected(false);
            }
            for (JToggleButton button : dishButtons) {
                button.setSelected(false);
            }

            // 更新所有页面
            updateFoodListView.run();

            // 切换回菜单页面
            cardLayout.show(frame.getContentPane(), "menuPanel");
        });

        // 显示欢迎页面
        cardLayout.show(frame.getContentPane(), "welcomePanel");

        // 设置窗口可见
        frame.setVisible(true);
    }


}

class guiConfig {

    private final log guiLog = new guiLog();

    private final String defaultFont = "微软雅黑";

    private final ArrayList<String> fontList = new ArrayList<String>();

    public guiConfig() {
    }

    public String addFont(String fontPath) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            fontList.add(customFont.getName());
            return customFont.getName();
        } catch (Exception e) {
            guiLog.writeLog("添加字体 " + fontPath + " 失败", guiLog.ERROR);
        }
        return null;
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
