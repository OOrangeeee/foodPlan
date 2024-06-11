package com.oorangeeee.foodPlan.configDriver;

import java.io.File;

/**
 * @author 晋晨曦
 */
public interface configDriver {
    String PROJECT_HOME_DIR= System.getProperty("user.dir");
    String CONFIG_PATH =PROJECT_HOME_DIR+"/config/config.dat";

    String getConfig(String key);
    boolean addConfig(String key, String value);
    boolean updateConfig(String key, String value);
    boolean deleteConfig(String key);
    void save(File file);
    void load(File file);
}
