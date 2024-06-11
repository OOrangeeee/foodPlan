package com.oorangeeee.foodPlan.log.impl;

import com.oorangeeee.foodPlan.log.log;

import java.io.File;

/**
 * @author 晋晨曦
 */
public class configLog implements log {
    private final String logPath = "./log/configLog.txt";
    private int infoCounts;
    private int errorCounts;
    private int panicCounts;

    public configLog() {
        File logFile = new File(logPath);
        if (!logFile.exists()) {
            try {
                boolean ifCreateLogFileParent = logFile.getParentFile().mkdirs();
                boolean ifCreateLogFile = logFile.createNewFile();
                if (!ifCreateLogFile || !ifCreateLogFileParent) {
                    System.out.println("日志文件创建失败");
                }
            } catch (Exception e) {
                System.out.println("日志文件创建失败");
                System.exit(1);
            }
        }
        infoCounts = 0;
        errorCounts = 0;
        panicCounts = 0;
    }

    @Override
    public void writeLog(String logContent, int logLevel) {
        // 写入日志
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
            // 统计日志等级，如果是panic则退出程序
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

    // getter

    @Override
    public int getInfoCounts() {
        return infoCounts;
    }

    @Override
    public int getErrorCounts() {
        return errorCounts;
    }

    @Override
    public int getPanicCounts() {
        return panicCounts;
    }

    @Override
    public String getLogPath() {
        return logPath;
    }

    @Override
    public String toString() {
        return "现有Config日志：\n" +
                "info日志数量：" + infoCounts + "\n" +
                "error日志数量：" + errorCounts + "\n" +
                "panic日志数量：" + panicCounts + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        // java14后引入的语法糖，instanceof后面可以直接跟变量名
        if (obj instanceof configLog configLogObj) {
            return this.infoCounts == configLogObj.infoCounts && this.errorCounts == configLogObj.errorCounts && this.panicCounts == configLogObj.panicCounts;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
