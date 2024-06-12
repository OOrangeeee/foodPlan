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
 * 这个类表示一个食物实体，实现了foods接口。
 * 它包含食物的名称、餐点类型、食物类型、备注和图片路径等属性。
 * 提供了相应的getter和setter方法，并支持序列化和反序列化操作。
 * 日志记录在操作失败时记录错误信息。
 *
 * @author 晋晨曦
 */
public class food implements foods {

    // 日志对象用于记录日志
    private final log foodLog = new foodLog();

    // 食物的名称
    private String foodName;

    // 餐点类型
    private String timeType;

    // 食物类型
    private String foodType;

    // 备注
    private String remark;

    // 食物图片路径
    private String foodImage;

    // 配置驱动对象
    private final configDriver config = new config();

    /**
     * 无参构造函数，初始化默认值
     */
    public food() {
        foodName = "未知食物";
        timeType = "未知餐点类型";
        foodType = "未知食物类型";
        remark = "";
        foodImage = config.getConfig("defaultImage");
    }

    /**
     * 带参数的构造函数
     *
     * @param foodName 食物名称
     * @param timeType 餐点类型
     * @param foodType 食物类型
     * @param remark 备注
     */
    public food(String foodName, String timeType, String foodType, String remark) {
        this.foodName = foodName;
        this.timeType = timeType;
        this.foodType = foodType;
        this.remark = remark;
        this.foodImage = config.getConfig("defaultImage");
    }

    /**
     * 带所有参数的构造函数
     *
     * @param foodName 食物名称
     * @param timeType 餐点类型
     * @param foodType 食物类型
     * @param remark 备注
     * @param foodImage 食物图片路径
     */
    public food(String foodName, String timeType, String foodType, String remark, String foodImage) {
        this.foodName = foodName;
        this.timeType = timeType;
        this.foodType = foodType;
        this.remark = remark;
        this.foodImage = foodImage;
    }

    /**
     * 获取食物名称
     *
     * @return 食物名称
     */
    @Override
    public String getFoodName() {
        return foodName;
    }

    /**
     * 设置食物名称
     *
     * @param foodName 食物名称
     */
    @Override
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     * 获取餐点类型
     *
     * @return 餐点类型数组
     */
    @Override
    public String[] getTimeType() {
        return timeType.split(",");
    }

    /**
     * 设置餐点类型
     *
     * @param timeType 餐点类型
     */
    @Override
    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    /**
     * 获取食物类型
     *
     * @return 食物类型数组
     */
    @Override
    public String[] getFoodType() {
        return foodType.split(",");
    }

    /**
     * 设置食物类型
     *
     * @param foodType 食物类型
     */
    @Override
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    @Override
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取食物图片路径
     *
     * @return 食物图片路径
     */
    @Override
    public String getFoodImage() {
        return foodImage;
    }

    /**
     * 设置食物图片路径
     *
     * @param foodImage 食物图片路径
     */
    @Override
    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    /**
     * 保存食物信息到文件
     *
     * @param file 文件对象
     */
    @Override
    public void save(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            writeObject(out);
        } catch (Exception e) {
            foodLog.writeLog("保存食物失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    /**
     * 从文件加载食物信息
     *
     * @param file 文件对象
     */
    @Override
    public void load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            readObject(in);
        } catch (Exception e) {
            foodLog.writeLog("加载食物失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    /**
     * 将食物信息写入输出流
     *
     * @param out 输出流对象
     */
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

    /**
     * 从输入流读取食物信息
     *
     * @param in 输入流对象
     */
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

    /**
     * 返回食物信息的字符串表示
     *
     * @return 食物信息字符串
     */
    @Override
    public String toString() {
        return "食物名称：" + foodName + "\n" +
                "餐点类型：" + timeType + "\n" +
                "食物类型：" + foodType + "\n" +
                "备注：" + remark + "\n" +
                "食物图片：" + foodImage + "\n";
    }

    /**
     * 比较两个食物对象是否相等
     *
     * @param obj 另一个食物对象
     * @return 如果两个对象相等则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof food f) {
            return f.foodName.equals(foodName);
        }
        return false;
    }

    /**
     * 返回食物对象的哈希码
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        String markString = foodName + timeType + foodType + remark + foodImage;
        return markString.hashCode();
    }
}

