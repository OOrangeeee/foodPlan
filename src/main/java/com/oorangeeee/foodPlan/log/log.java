package com.oorangeeee.foodPlan.log;

import com.oorangeeee.foodPlan.serializable.FileSerializable;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface log extends FileSerializable {
    int INFO = 0;
    int ERROR = 1;
    int PANIC = 2;
    Map<Integer, String> LOG_LEVEL_MAP = Map.of(0, "INFO", 1, "ERROR", 2, "PANIC");

    void writeLog(String logContent, int logLevel);

    int getInfoCounts();

    int getErrorCounts();

    int getPanicCounts();

    String getLogPath();
}
