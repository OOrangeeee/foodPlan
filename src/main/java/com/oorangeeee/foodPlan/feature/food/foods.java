package com.oorangeeee.foodPlan.feature.food;

import com.oorangeeee.foodPlan.serializable.FileSerializable;

import java.io.File;

/**
 * 这个接口定义了食物的基本属性和操作方法，并继承了FileSerializable接口。
 * 提供了获取和设置食物名称、时间类型、食物类型、备注和食物图片的方法。
 * 同时定义了一些食物时间类型和食物类型的常量数组。
 *
 * @author  晋晨曦
 */
public interface foods extends FileSerializable {

    // 可选的食物时间类型
    String[] TIME_TYPES = {"早餐", "午餐", "晚餐", "零食"};

    // 可选的食物类型
    String[] FOOD_TYPES = {"面食", "蒸点", "炒菜", "汤", "饮料", "水果", "火锅", "烧烤", "快餐", "轻食", "甜品", "蔬菜", "油炸食品", "烘焙", "米饭", "零食"};

    /**
     * 获取食物名称
     *
     * @return 食物名称
     */
    String getFoodName();

    /**
     * 设置食物名称
     *
     * @param foodName 食物名称
     */
    void setFoodName(String foodName);

    /**
     * 获取食物时间类型
     *
     * @return 时间类型数组
     */
    String[] getTimeType();

    /**
     * 设置食物时间类型
     *
     * @param timeType 时间类型字符串（多个类型用逗号分隔）
     */
    void setTimeType(String timeType);

    /**
     * 获取食物类型
     *
     * @return 食物类型数组
     */
    String[] getFoodType();

    /**
     * 设置食物类型
     *
     * @param foodType 食物类型字符串（多个类型用逗号分隔）
     */
    void setFoodType(String foodType);

    /**
     * 获取备注
     *
     * @return 备注字符串
     */
    String getRemark();

    /**
     * 设置备注
     *
     * @param remark 备注字符串
     */
    void setRemark(String remark);

    /**
     * 获取食物图片的路径或URL
     *
     * @return 食物图片路径或URL
     */
    String getFoodImage();

    /**
     * 设置食物图片的路径或URL
     *
     * @param foodImage 食物图片路径或URL
     */
    void setFoodImage(String foodImage);

    /**
     * 保存食物数据到文件
     *
     * @param file 文件对象
     */
    void save(File file);

    /**
     * 从文件加载食物数据
     *
     * @param file 文件对象
     */
    void load(File file);
}
