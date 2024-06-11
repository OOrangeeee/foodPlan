package com.oorangeeee.foodPlan.serializable;

import java.io.Serializable;

/**
 * @author 晋晨曦
 */
public interface FileSerializable extends Serializable {
    void writeObject(java.io.ObjectOutputStream out);
    void readObject(java.io.ObjectInputStream in);
}
