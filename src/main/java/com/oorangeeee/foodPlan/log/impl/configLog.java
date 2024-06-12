package com.oorangeeee.foodPlan.log.impl;

import com.oorangeeee.foodPlan.log.log;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 这个类实现了日志记录接口 log，用于配置日志的记录和管理。
 * 它提供了写入日志、获取日志计数、获取日志文件路径等方法。
 * 还包括日志文件的创建和日志的序列化/反序列化功能。
 *
 * @author  晋晨曦
 */
public class configLog implements log {

    // 日志文件路径
    private final String logPath = "./log/configLog.txt";

    // 日志计数
    private int infoCounts;
    private int errorCounts;
    private int panicCounts;

    /**
     * 构造函数，初始化日志文件和日志计数
     */
    public configLog() {
        File logFile = new File(logPath);
        if (!logFile.exists()) {
            try {
                boolean ifCreateLogFileParent = logFile.getParentFile().mkdirs();
                boolean ifCreateLogFile = logFile.createNewFile();
            } catch (Exception e) {
                System.out.println("日志文件创建失败");
                System.exit(1);
            }
        }
        infoCounts = 0;
        errorCounts = 0;
        panicCounts = 0;
    }

    /**
     * 写入日志到文件
     *
     * @param logContent 日志内容
     * @param logLevel 日志级别
     */
    @Override
    public void writeLog(String logContent, int logLevel) {
        try {
            // 日志格式：时间 日志等级 日志内容
            logContent = "===============================\n" +
                    "Config\n" +
                    java.time.LocalDateTime.now() + "\n" +
                    LOG_LEVEL_MAP.get(logLevel) + "\n" +
                    logContent + "\n";
            java.io.FileWriter fileWriter = new java.io.FileWriter(logPath, true);
            fileWriter.write(logContent);
            fileWriter.close();
            // 统计日志等级，如果是 panic 则退出程序
            switch (logLevel) {
                case INFO:
                    infoCounts++;
                    break;
                case ERROR:
                    errorCounts++;
                    break;
                case PANIC:
                    panicCounts++;
                    System.out.println("程序发生错误，即将退出");
                    System.exit(1);
                    break;
                default:
                    System.out.println("未知日志等级");
                    break;
            }
        } catch (Exception e) {
            System.out.println("日志写入失败");
            System.exit(1);
        }
    }

    /**
     * 获取 info 级别日志的数量
     *
     * @return info 级别日志数量
     */
    @Override
    public int getInfoCounts() {
        return infoCounts;
    }

    /**
     * 获取 error 级别日志的数量
     *
     * @return error 级别日志数量
     */
    @Override
    public int getErrorCounts() {
        return errorCounts;
    }

    /**
     * 获取 panic 级别日志的数量
     *
     * @return panic 级别日志数量
     */
    @Override
    public int getPanicCounts() {
        return panicCounts;
    }

    /**
     * 获取日志文件路径
     *
     * @return 日志文件路径
     */
    @Override
    public String getLogPath() {
        return logPath;
    }

    /**
     * 返回日志的字符串表示
     *
     * @return 日志的字符串表示
     */
    @Override
    public String toString() {
        return "现有Config日志：\n" +
                "info日志数量：" + infoCounts + "\n" +
                "error日志数量：" + errorCounts + "\n" +
                "panic日志数量：" + panicCounts + "\n";
    }

    /**
     * 比较两个 configLog 对象是否相等
     *
     * @param obj 要比较的对象
     * @return 如果两个对象相等则返回 true，否则返回 false
     */
    @Override
    public boolean equals(Object obj) {
        // java14后引入的语法糖，instanceof后面可以直接跟变量名
        if (obj instanceof configLog configLogObj) {
            return this.infoCounts == configLogObj.infoCounts &&
                    this.errorCounts == configLogObj.errorCounts &&
                    this.panicCounts == configLogObj.panicCounts;
        }
        return false;
    }

    /**
     * 返回对象的哈希码
     *
     * @return 对象的哈希码
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * 序列化对象到输出流
     *
     * @param out 序列化输出流
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(infoCounts);
            out.writeObject(errorCounts);
            out.writeObject(panicCounts);
        } catch (Exception e) {
            System.out.println("日志写入失败");
            System.exit(1);
        }
    }

    /**
     * 从输入流反序列化对象
     *
     * @param in 反序列化输入流
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            infoCounts = (int) in.readObject();
            errorCounts = (int) in.readObject();
            panicCounts = (int) in.readObject();
        } catch (Exception e) {
            System.out.println("日志写入失败");
            System.exit(1);
        }
    }
}
