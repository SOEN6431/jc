package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is using to send request for user details *
 */
public class GetUserDetails extends Message implements Serializable {
    /**
     * If UserName is null, server returns details of the user which sent request *
     */
    public String UserName;

    public GetUserDetails(String aUserName) {
        UserName = aUserName;
    }
}
