package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.LoginData;
import com.ays.javachat.common.datatypes.UserDetails;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyRegister extends Register implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyRegister(LoginData aLogin, UserDetails aDetails, int aStatus) {
        super(aLogin, aDetails);
        Status = aStatus;
    }
}
