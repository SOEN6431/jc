package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyGetOnlineUsersList extends GetOnlineUsersList implements Serializable {
    /**
     * Online users list returned by server*
     */
    public String array[] = new String[0];
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyGetOnlineUsersList(int aStatus) {
        Status = aStatus;
    }
}
