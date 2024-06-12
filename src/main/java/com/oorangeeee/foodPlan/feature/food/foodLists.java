package com.oorangeeee.foodPlan.feature.food;

import com.oorangeeee.foodPlan.serializable.FileSerializable;

import java.io.File;

/**
 * 这个接口定义了食物列表的操作方法，继承了FileSerializable接口。
 * 提供了添加食物、获取所有食物、按餐点类型获取食物、随机获取食物以及保存和加载食物列表的方法。
 *
 * @author  晋晨曦
 */
public interface foodLists extends FileSerializable {

    /**
     * 向食物列表中添加一个食物对象。
     *
     * @param food 食物对象
     */
    void addFood(foods food);

    /**
     * 获取所有食物对象。
     *
     * @return 包含所有食物对象的数组
     */
    foods[] getFoods();

    /**
     * 获取所有早餐食物对象。
     *
     * @return 包含所有早餐食物对象的数组
     */
    foods[] getBreakfast();

    /**
     * 获取所有午餐食物对象。
     *
     * @return 包含所有午餐食物对象的数组
     */
    foods[] getLunch();

    /**
     * 获取所有晚餐食物对象。
     *
     * @return 包含所有晚餐食物对象的数组
     */
    foods[] getDinner();

    /**
     * 获取所有零食食物对象。
     *
     * @return 包含所有零食食物对象的数组
     */
    foods[] getSnack();

    /**
     * 根据指定的食物类型获取所有匹配的食物对象。
     *
     * @param type 食物类型
     * @return 包含所有匹配类型的食物对象的数组
     */
    foods[] getFoodByType(String type);

    /**
     * 随机获取一个早餐食物对象。
     *
     * @return 一个随机的早餐食物对象
     */
    foods getBreakfastByRandom();

    /**
     * 随机获取一个午餐食物对象。
     *
     * @return 一个随机的午餐食物对象
     */
    foods getLunchByRandom();

    /**
     * 随机获取一个晚餐食物对象。
     *
     * @return 一个随机的晚餐食物对象
     */
    foods getDinnerByRandom();

    /**
     * 随机获取一个零食食物对象。
     *
     * @return 一个随机的零食食物对象
     */
    foods getSnackByRandom();

    /**
     * 将食物列表保存到文件。
     *
     * @param file 文件对象
     */
    void save(File file);

    /**
     * 从文件加载食物列表。
     *
     * @param file 文件对象
     */
    void load(File file);
}

