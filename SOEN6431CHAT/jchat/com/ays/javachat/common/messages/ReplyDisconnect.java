package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyDisconnect extends Disconnect implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;
}
