package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is using by current user ( current means user which connected and logged in to the server ) *
 */
public class UnRegister extends Message implements Serializable {
    public String password = "";
}
