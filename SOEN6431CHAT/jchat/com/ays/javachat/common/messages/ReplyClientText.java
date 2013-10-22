package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyClientText extends ClientText implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyClientText(String aUserName, String aText, int aStatus) {
        super(aUserName, aText);
        Status = aStatus;
    }
}
