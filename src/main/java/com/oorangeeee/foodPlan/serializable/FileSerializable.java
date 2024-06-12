package com.oorangeeee.foodPlan.serializable;

import java.io.Serializable;

/**
 * 文件序列化接口，定义了对象的序列化和反序列化方法。
 * 继承了 Serializable 接口，以便实现序列化功能。
 *
 * @author  晋晨曦
 */
public interface FileSerializable extends Serializable {

    /**
     * 序列化对象到输出流
     *
     * @param out 序列化输出流
     */
    void writeObject(java.io.ObjectOutputStream out);

    /**
     * 从输入流反序列化对象
     *
     * @param in 反序列化输入流
     */
    void readObject(java.io.ObjectInputStream in);
}
