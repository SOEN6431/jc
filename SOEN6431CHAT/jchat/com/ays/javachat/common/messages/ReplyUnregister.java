package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyUnregister extends UnRegister implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyUnregister(int aStatus) {
        Status = aStatus;
    }
}
