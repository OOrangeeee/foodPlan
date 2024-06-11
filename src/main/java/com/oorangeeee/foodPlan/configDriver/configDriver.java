package com.oorangeeee.foodPlan.configDriver;

import java.io.File;

/**
 * 配置驱动接口，定义了配置管理的基本操作。
 * 提供了默认配置设置、配置项的增删改查以及配置文件的加载和保存等功能。
 *
 * @author 晋晨曦
 */
public interface configDriver {
    /**
     * 项目根目录路径
     */
    String PROJECT_HOME_DIR = System.getProperty("user.dir");

    /**
     * 配置文件路径
     */
    String CONFIG_PATH = PROJECT_HOME_DIR + "/config/config.dat";

    /**
     * 设置默认配置。
     */
    void defaultConfig();

    /**
     * 获取指定键的配置值。
     *
     * @param key 配置项的键
     * @return 配置项的值
     */
    String getConfig(String key);

    /**
     * 添加新的配置项。
     *
     * @param key 配置项的键
     * @param value 配置项的值
     * @return 添加成功返回true，失败返回false
     */
    boolean addConfig(String key, String value);

    /**
     * 更新指定配置项的值。
     *
     * @param key 配置项的键
     * @param value 新的配置值
     * @return 更新成功返回true，失败返回false
     */
    boolean updateConfig(String key, String value);

    /**
     * 删除指定的配置项。
     *
     * @param key 配置项的键
     * @return 删除成功返回true，失败返回false
     */
    boolean deleteConfig(String key);

    /**
     * 保存配置到指定文件。
     *
     * @param file 要保存的文件
     */
    void save(File file);

    /**
     * 从指定文件加载配置。
     *
     * @param file 要加载的文件
     */
    void load(File file);
}
