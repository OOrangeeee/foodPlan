package com.oorangeeee.foodPlan.log;

import com.oorangeeee.foodPlan.serializable.FileSerializable;
import java.util.Map;

/**
 * 日志接口，定义了基本的日志操作方法和日志级别。
 * 继承了 FileSerializable 接口，增加了日志文件的序列化/反序列化功能。
 *
 * @author  晋晨曦
 */
public interface log extends FileSerializable {

    // 日志级别常量
    int INFO = 0;
    int ERROR = 1;
    int PANIC = 2;

    // 日志级别映射
    Map<Integer, String> LOG_LEVEL_MAP = Map.of(
            INFO, "INFO",
            ERROR, "ERROR",
            PANIC, "PANIC"
    );

    /**
     * 写入日志内容
     *
     * @param logContent 日志内容
     * @param logLevel 日志级别
     */
    void writeLog(String logContent, int logLevel);

    /**
     * 获取 info 级别日志的数量
     *
     * @return info 级别日志数量
     */
    int getInfoCounts();

    /**
     * 获取 error 级别日志的数量
     *
     * @return error 级别日志数量
     */
    int getErrorCounts();

    /**
     * 获取 panic 级别日志的数量
     *
     * @return panic 级别日志数量
     */
    int getPanicCounts();

    /**
     * 获取日志文件路径
     *
     * @return 日志文件路径
     */
    String getLogPath();
}
