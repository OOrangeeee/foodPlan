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
 * @author 晋晨曦
 */
public class foodList implements foodLists{

    private ArrayList<foods> foodList;
    private ArrayList<foods> breakfast;
    private ArrayList<foods> lunch;
    private ArrayList<foods> dinner;
    private ArrayList<foods> snack;
    private final log foodListLog = new foodLog();
    private final configDriver config = new config();

    public foodList() {
        foodList = new ArrayList<>();
        breakfast = new ArrayList<>();
        lunch = new ArrayList<>();
        dinner = new ArrayList<>();
        snack = new ArrayList<>();
        initFoodList();
    }

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

    @Override
    public foods[] getFoods() {
        this.load(new File(config.getConfig("savePath")));
        return foodList.toArray(new foods[0]);
    }

    @Override
    public foods[] getBreakfast() {
        this.load(new File(config.getConfig("savePath")));
        return breakfast.toArray(new foods[0]);
    }

    @Override
    public foods[] getLunch() {
        this.load(new File(config.getConfig("savePath")));
        return lunch.toArray(new foods[0]);
    }

    @Override
    public foods[] getDinner() {
        this.load(new File(config.getConfig("savePath")));
        return dinner.toArray(new foods[0]);
    }

    @Override
    public foods[] getSnack() {
        this.load(new File(config.getConfig("savePath")));
        return snack.toArray(new foods[0]);
    }

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

    private foods getRandomFood(ArrayList<foods> list) {
        if (list.isEmpty()) {
            return null;
        }
        int index = (int) (Math.random() * list.size());
        return list.get(index);
    }

    @Override
    public foods getBreakfastByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(breakfast);
    }

    @Override
    public foods getLunchByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(lunch);
    }

    @Override
    public foods getDinnerByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(dinner);
    }

    @Override
    public foods getSnackByRandom() {
        this.load(new File(config.getConfig("savePath")));
        return getRandomFood(snack);
    }

    @Override
    public void save(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            this.writeObject(out);
        } catch (Exception e) {
            foodListLog.writeLog("保存食物列表失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    @Override
    public void load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            this.readObject(in);
        } catch (Exception e) {
            foodListLog.writeLog("加载食物列表失败:\n" + e.getMessage(), log.PANIC);
        }
    }

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
