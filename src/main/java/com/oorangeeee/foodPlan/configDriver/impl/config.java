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
 * @author 晋晨曦
 */
public class config implements configDriver, FileSerializable {

    private final log configLog = new configLog();

    private Map<String, String> configMap;

    public config() {
        configMap = new HashMap<>();
        initConfigs();
    }

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

    @Override
    public String getConfig(String key) {
        load(new File(CONFIG_PATH));
        if (!configMap.containsKey(key)) {
            configLog.writeLog("获取配置失败:配置不存在", log.ERROR);
        }
        return configMap.get(key);
    }

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

    @Override
    public void save(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file.toPath()))) {
            this.writeObject(out);
        } catch (Exception e) {
            configLog.writeLog("保存配置文件失败:\n" + e.getMessage() + "\n", log.PANIC);
        }
    }

    @Override
    public void load(File file) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file.toPath()))) {
            this.readObject(in);
        } catch (Exception e) {
            configLog.writeLog("加载配置文件失败:\n" + e.getMessage() + "\n", log.PANIC);
        }
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(configMap);
        } catch (Exception e) {
            configLog.writeLog("序列化配置文件失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            configMap = (Map<String, String>) in.readObject();
        } catch (Exception e) {
            configLog.writeLog("反序列化配置文件失败:\n" + e.getMessage(), log.PANIC);
        }
    }

    @Override
    public String toString() {
        return "config{\n" +
                "configMap=\n" +
                configMap +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof config configObj) {
            return this.configMap.equals(configObj.configMap);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return configMap.hashCode();
    }
}
