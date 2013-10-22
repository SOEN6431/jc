package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.UserDetails;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplySetUserDetails extends SetUserDetails implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplySetUserDetails(UserDetails aDetails, int aStatus) {
        super(aDetails);
        Status = aStatus;
    }
}
