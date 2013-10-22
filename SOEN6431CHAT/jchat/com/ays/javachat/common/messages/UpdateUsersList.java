package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is using by server to notify clients about online users list *
 */
public class UpdateUsersList extends Message implements Serializable {
    /**
     * Is a user which does something *
     */
    public String UserName;
    /**
     * What user does : left, joined, updated his info... *
     */
    public int What; // what happend with user. for example : user left, joined, updated his info

    public UpdateUsersList() {
    }

    public UpdateUsersList(String aUserName, int aWhat) {
        UserName = aUserName;
        What = aWhat;
    }
}
