package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.LoginData;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyLogin extends Login implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyLogin(LoginData aLoginData, int aStatus) {
        super(aLoginData);
        Status = aStatus;
    }
}
