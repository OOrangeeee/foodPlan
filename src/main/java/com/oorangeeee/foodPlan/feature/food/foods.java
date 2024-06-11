package com.oorangeeee.foodPlan.feature.food;

import com.oorangeeee.foodPlan.serializable.FileSerializable;

import java.io.File;

/**
 * @author 晋晨曦
 */
public interface foods extends FileSerializable {
    // 一个接口，定义了食物的属性
    // 1. 食物的名字
    // 2. 早午晚餐，下午茶，零食，加餐（多选）
    // 3. 食物的类型: 面食，蒸点，炒菜，汤，饮料，水果，火锅，烧烤，快餐，轻食，甜品，蔬菜，油炸食品，烘焙，米饭，零食（多选）
    // 4. 备注
    // 5. 食物图片
    // food中储存食物的时间类型和类型只存一个由,分隔的字符串，如"早餐,午餐"，"面食,蒸点"
    // 返回的时候再分割成数组
    // 同理在set的时候也是接受一个字符串
    // 此类必须实现序列化接口，以便于存储
    String[] TIME_TYPES = {"早餐", "午餐", "晚餐", "零食"};
    String[] FOOD_TYPES = {"面食", "蒸点", "炒菜", "汤", "饮料", "水果", "火锅", "烧烤", "快餐", "轻食", "甜品", "蔬菜", "油炸食品", "烘焙", "米饭", "零食"};

    // getter and setter

    String getFoodName();

    void setFoodName(String foodName);

    String[] getTimeType();

    void setTimeType(String timeType);

    String[] getFoodType();

    void setFoodType(String foodType);

    String getRemark();

    void setRemark(String remark);

    String getFoodImage();

    void setFoodImage(String foodImage);

    void save(File file);

    void load(File file);

}
