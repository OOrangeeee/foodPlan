package com.oorangeeee.foodPlan.feature.food.impl;

import com.oorangeeee.foodPlan.configDriver.configDriver;
import com.oorangeeee.foodPlan.configDriver.impl.config;
import com.oorangeeee.foodPlan.feature.food.foodLists;
import com.oorangeeee.foodPlan.feature.food.foods;
import com.oorangeeee.foodPlan.log.impl.foodLog;
import com.oorangeeee.foodPlan.log.log;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * 这个类表示一个食物列表，实现了foodLists接口。
 * 它包含所有食物的集合，并提供了按餐点类型分类的方法。
 * 提供了相应的getter和setter方法，并支持序列化和反序列化操作。
 * 日志记录在操作失败时记录错误信息。
 *
 * @author 晋晨曦
 */
public class foodList implements foodLists {

    // 存储所有食物的列表
    private ArrayList<foods> foodList;

    // 分别存储早餐、午餐、晚餐和零食的列表
    private ArrayList<foods> breakfast;
    private ArrayList<foods> lunch;
    private ArrayList<foods> dinner;
    private ArrayList<foods> snack;

    // 日志对象用于记录日志
    private final log foodListLog = new foodLog();

    // 配置驱动对象
    private final configDriver config = new config();

    /**
     * 无参构造函数，初始化默认值
     */
    public foodList() {
        foodList = new ArrayList<>();
        breakfast = new ArrayList<>();
        lunch = new ArrayList<>();
        dinner = new ArrayList<>();
        snack = new ArrayList<>();
        initFoodList();
    }

    /**
     * 初始化食物列表
     */
    private void initFoodList() {
        File foodListFile = new File(config.getConfig("savePath"));
        if (foodListFile.exists()) {
            load(foodListFile);
        } else {
            try {
                boolean ifCreateFoodListFileParent = foodListFile.getParentFile().mkdirs();
                boolean ifCreateFoodListFile = foodListFile.createNewFile();
                if (!ifCreateFoodListFile || !ifCreateFoodListFileParent) {
                    foodListLog.writeLog("食物列表文件已存在，无需初始化", log.ERROR);
                }
                save(foodListFile);
            } catch (Exception e) {
                foodListLog.writeLog("初始化食物列表文件失败:\n" + e.getMessage(), log.PANIC);
            }
        }
    }

    /**
     * 添加食物到食物列表中
     *
     * @param food 食物对象
     */
    @Override
    public void addFood(foods food) {
        if (food == null) {
            return;
        }
        if (foodList.contains(food)) {
            return;
        }
        foodList.add(food);
        String[] timeType = food.getTimeType();
        for (String type : timeType) {
            switch (type) {
                case "早餐":
                    breakfast.add(food);
                    break;
                case "午餐":
                    lunch.add(food);
                    break;
                case "晚餐":
                    dinner.add(food);
                    break;
                case "零食":
                    snack.add(food);
                    break;
            }
        }
        this.save(new File(config.getConfig("savePath")));
    }

    /**
     * 获取所有食物
     *
     * @return 食物数组
     */
    @Override
    public foods[] getFoods() {
        this.load(new File(config.getConfig("savePath")));
        return foodList.toArray(new foods[0]);
    }

    /**
     * 获取早餐食物
     *
     * @return 早餐食物数组
     */
    @Override
    public foods[] getBreakfast() {
        this.load(new File(config.getConfig("savePath")));
        return breakfast.toArray(new foods[0]);
    }

    /**
     * 获取午餐食物
     *
     * @return 午餐食物数组
     */
    @Override
    public foods[] getLunch() {
        this.load(new File(config.getConfig("savePath")));
        return lunch.toArray(new foods[0]);
    }

    /**
     * 获取晚餐食物
     *
     * @return 晚餐食物数组
     */
    @Override
    public foods[] getDinner() {
        this.load(new File(config.getConfig("savePath")));
        return dinner.toArray(new foods[0]);
    }

    /**
     * 获取零食
     *
     * @return 零食数组
     */
    @Override
    public foods[] getSnack() {
        this.load(new File(config.getConfig("savePath")));
        return snack.toArray(new foods[0]);
    }

    /**
     * 按食物类型获取食物
     *
     * @param type 食物类型
     * @return 对应类型的食物数组
     */
    @Override
    public foods[] getFoodByType(String type) {
        this.load(new File(config.getConfig("savePath")));
        ArrayList<foods> result = new ArrayList<>();
        for (foods food : foodList) {
            String[] foodType = food.getFoodType();
            for (String t : foodType) {
                if (t.equals(type)) {
                    result.add(food);
                    break;
                }
            }
        }
        return result.toArray(new foods[0]);
    }

    /**
     * 从指定列表中随机获取一个食物
     *
     * @param list 食物列表
     * @return 随机食物
     */
    private foods getRandomFood(ArrayList<foods> list) {
        if (list.isEmpty()) {
            return null;
        }
        int index = (int) (Math.random() * list.size());
        return list.get(index);
    }

    /**
     * 随机获取早餐食物
     *
     * @return 随机早餐食物
     */
    @Override
    public foods getBreakfastByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(breakfast);
    }

    /**
     * 随机获取午餐食物
     *
     * @return 随机午餐食物
     */
    @Override
    public foods getLunchByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(lunch);
    }

    /**
     * 随机获取晚餐食物
     *
     * @return 随机晚餐食物
     */
    @Override
    public foods getDinnerByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(dinner);
    }

    /**
     * 随机获取零食
     *
     * @return 随机零食
     */
    @Override
    public foods getSnackByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(snack);
    }

    /**
     * 保存食物列表到文件
     *
     * @param file 文件对象
     */
    @Override
    public void save(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            this.writeObject(out);
        } catch (Exception e) {
            foodListLog.writeLog("保存食物列表失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    /**
     * 从文件加载食物列表
     *
     * @param file 文件对象
     */
    @Override
    public void load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            this.readObject(in);
        } catch (Exception e) {
            foodListLog.writeLog("加载食物列表失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    /**
     * 将食物列表写入输出流
     *
     * @param out 输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(foodList);
            out.writeObject(breakfast);
            out.writeObject(lunch);
            out.writeObject(dinner);
            out.writeObject(snack);
        } catch (Exception e) {
            foodListLog.writeLog("序列化食物列表失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    /**
     * 从输入流读取食物列表
     *
     * @param in 输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            foodList = (ArrayList<foods>) in.readObject();
            breakfast = (ArrayList<foods>) in.readObject();
            lunch = (ArrayList<foods>) in.readObject();
            dinner = (ArrayList<foods>) in.readObject();
            snack = (ArrayList<foods>) in.readObject();
        } catch (Exception e) {
            foodListLog.writeLog("反序列化食物列表失败:\n" + e.getMessage(), log.PANIC);
        }
    }
}

