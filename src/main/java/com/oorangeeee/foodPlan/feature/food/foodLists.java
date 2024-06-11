package com.oorangeeee.foodPlan.feature.food;

import com.oorangeeee.foodPlan.serializable.FileSerializable;

import java.io.File;

/**
 * @author 晋晨曦
 */
public interface foodLists extends FileSerializable {
    void addFood(foods food);
    foods[] getFoods();
    foods[] getBreakfast();
    foods[] getLunch();
    foods[] getDinner();
    foods[] getSnack();
    foods[] getFoodByType(String type);
    foods getBreakfastByRandom();
    foods getLunchByRandom();
    foods getDinnerByRandom();
    foods getSnackByRandom();
    void save(File file);
    void load(File file);
}
