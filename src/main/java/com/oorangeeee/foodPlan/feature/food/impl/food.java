package com.oorangeeee.foodPlan.feature.food.impl;

import com.oorangeeee.foodPlan.configDriver.configDriver;
import com.oorangeeee.foodPlan.configDriver.impl.config;
import com.oorangeeee.foodPlan.feature.food.foods;
import com.oorangeeee.foodPlan.log.impl.foodLog;
import com.oorangeeee.foodPlan.log.log;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

/**
 * @author 晋晨曦
 */
public class food implements foods {
    private final log foodLog = new foodLog();
    private String foodName;
    private String timeType;
    private String foodType;
    private String remark;
    private String foodImage;
    private final configDriver config = new config();

    public food() {
        foodName = "未知食物";
        timeType = "未知餐点类型";
        foodType = "未知食物类型";
        remark = "";
        foodImage = config.getConfig("defaultImage");
    }

    public food(String foodName, String timeType, String foodType, String remark) {
        this.foodName = foodName;
        this.timeType = timeType;
        this.foodType = foodType;
        this.remark = remark;
        this.foodImage = config.getConfig("defaultImage");
    }

    public food(String foodName, String timeType, String foodType, String remark, String foodImage) {
        this.foodName = foodName;
        this.timeType = timeType;
        this.foodType = foodType;
        this.remark = remark;
        this.foodImage = foodImage;
    }

    @Override
    public String getFoodName() {
        return foodName;
    }

    @Override
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    @Override
    public String[] getTimeType() {
        return timeType.split(",");
    }

    @Override
    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    @Override
    public String[] getFoodType() {
        return foodType.split(",");
    }

    @Override
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getFoodImage() {
        return foodImage;
    }

    @Override
    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    @Override
    public void save(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            writeObject(out);
        } catch (Exception e) {
            foodLog.writeLog("保存食物失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    @Override
    public void load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            readObject(in);
        } catch (Exception e) {
            foodLog.writeLog("加载食物失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(foodName);
            out.writeObject(timeType);
            out.writeObject(foodType);
            out.writeObject(remark);
            out.writeObject(foodImage);
        } catch (Exception e) {
            foodLog.writeLog("序列化食物失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            foodName = (String) in.readObject();
            timeType = (String) in.readObject();
            foodType = (String) in.readObject();
            remark = (String) in.readObject();
            foodImage = (String) in.readObject();
        } catch (Exception e) {
            foodLog.writeLog("反序列化食物失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    @Override
    public String toString() {
        return "食物名称：" + foodName + "\n" +
                "餐点类型：" + timeType + "\n" +
                "食物类型：" + foodType + "\n" +
                "备注：" + remark + "\n" +
                "食物图片：" + foodImage + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof food f) {
            return f.foodName.equals(foodName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        String markString = foodName + timeType + foodType + remark + foodImage;
        return markString.hashCode();
    }
}
