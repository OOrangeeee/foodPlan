package com.oorangeeee.foodPlan.configDriver.impl;

import com.oorangeeee.foodPlan.configDriver.configDriver;
import com.oorangeeee.foodPlan.log.log;
import com.oorangeeee.foodPlan.log.impl.configLog;
import com.oorangeeee.foodPlan.serializable.FileSerializable;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置实现类，用于管理应用程序的配置项。
 * 提供了配置的读取、保存、添加、更新和删除等功能。
 *
 * @author 晋晨曦
 */
public class config implements configDriver, FileSerializable {
    private final log configLog = new configLog(); // 用于记录日志的对象
    private Map<String, String> configMap; // 存储配置项的键值对

    /**
     * 构造函数，初始化配置项。
     * 如果配置文件存在则加载配置，否则创建新配置文件并保存默认配置。
     */
    public config() {
        configMap = new HashMap<>();
        initConfigs();
    }

    /**
     * 设置默认配置项。
     * 默认配置包括保存路径和图标路径。
     */
    @Override
    public void defaultConfig() {
        this.addConfig("savePath", config.PROJECT_HOME_DIR + "/data/data.dat");
        this.addConfig("iconPath", config.PROJECT_HOME_DIR + "/icon.png");
    }

    /**
     * 初始化配置项。
     * 如果配置文件存在则加载配置文件，否则创建新文件并保存默认配置。
     */
    private void initConfigs() {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            load(configFile);
        } else {
            try {
                boolean ifCreateConfigFileParent = configFile.getParentFile().mkdirs();
                boolean ifCreateConfigFile = configFile.createNewFile();
                if (!ifCreateConfigFile || !ifCreateConfigFileParent) {
                    configLog.writeLog("配置文件已存在，无需初始化", log.ERROR);
                }
                save(configFile);
            } catch (Exception e) {
                configLog.writeLog("初始化配置文件失败:\n" + e.getMessage() + "\n", log.PANIC);
            }
        }
    }

    /**
     * 获取指定键的配置值。
     *
     * @param key 配置项的键
     * @return 配置项的值，如果不存在则返回null
     */
    @Override
    public String getConfig(String key) {
        load(new File(CONFIG_PATH));
        if (!configMap.containsKey(key)) {
            configLog.writeLog("获取配置失败:配置不存在", log.ERROR);
            return null;
        }
        return configMap.get(key);
    }

    /**
     * 添加新的配置项。
     *
     * @param key 配置项的键
     * @param value 配置项的值
     * @return 添加成功返回true，失败返回false
     */
    @Override
    public boolean addConfig(String key, String value) {
        if (configMap.containsKey(key)) {
            configLog.writeLog("添加配置失败:配置已存在", log.ERROR);
            return false;
        }
        configMap.put(key, value);
        save(new File(CONFIG_PATH));
        return true;
    }

    /**
     * 更新指定配置项的值。
     *
     * @param key 配置项的键
     * @param value 新的配置值
     * @return 更新成功返回true，失败返回false
     */
    @Override
    public boolean updateConfig(String key, String value) {
        if (!configMap.containsKey(key)) {
            configLog.writeLog("更新配置失败:配置不存在", log.ERROR);
            return false;
        }
        configMap.put(key, value);
        save(new File(CONFIG_PATH));
        return true;
    }

    /**
     * 删除指定的配置项。
     *
     * @param key 配置项的键
     * @return 删除成功返回true，失败返回false
     */
    @Override
    public boolean deleteConfig(String key) {
        if (!configMap.containsKey(key)) {
            configLog.writeLog("删除配置失败:配置不存在", log.ERROR);
            return false;
        }
        configMap.remove(key);
        save(new File(CONFIG_PATH));
        return true;
    }

    /**
     * 保存配置到指定文件。
     *
     * @param file 要保存的文件
     */
    @Override
    public void save(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            this.writeObject(out);
        } catch (Exception e) {
            configLog.writeLog("保存配置文件失败:\n" + e.getMessage() + "\n", log.PANIC);
        }
    }

    /**
     * 从指定文件加载配置。
     *
     * @param file 要加载的文件
     */
    @Override
    public void load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            this.readObject(in);
        } catch (Exception e) {
            configLog.writeLog("加载配置文件失败:\n" + e.getMessage() + "\n", log.PANIC);
        }
    }

    /**
     * 序列化配置对象，将其写入输出流。
     *
     * @param out 输出流
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(configMap);
        } catch (Exception e) {
            configLog.writeLog("序列化配置文件失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    /**
     * 反序列化配置对象，从输入流读取数据。
     *
     * @param in 输入流
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            configMap = (Map<String, String>) in.readObject();
        } catch (Exception e) {
            configLog.writeLog("反序列化配置文件失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    /**
     * 返回配置对象的字符串表示形式。
     *
     * @return 配置对象的字符串表示
     */
    @Override
    public String toString() {
        return "config{\n" +
                "configMap=\n" +
                configMap +
                "}";
    }

    /**
     * 判断两个配置对象是否相等。
     *
     * @param obj 要比较的对象
     * @return 如果相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof config configObj) {
            return this.configMap.equals(configObj.configMap);
        }
        return false;
    }

    /**
     * 返回配置对象的哈希码。
     *
     * @return 配置对象的哈希码
     */
    @Override
    public int hashCode() {
        return configMap.hashCode();
    }
}
