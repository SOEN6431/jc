package com.ays.javachat.common.datatypes;

import java.io.Serializable;

/**
 * Abstract class needed for all data types structures.
 * By using this class as a parent, you can take some advantages :<br>
 * 1) implement data consistency checking ( by using abstact function isDataValid() )<br>
 * 2) describe why some data members has invalid data ( by using func getLastErrorMessage()<br>
 */
public abstract class Data implements Serializable {
    String lastErrorMessage = ""; // if data was invalid, lastErrorMessage contains short description about it

    /**
     * Checks for data consistency.*
     */
    public abstract boolean isDataValid();

    /**
     * Returns last description about data-validation *
     */
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
