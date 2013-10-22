package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.LoginData;

import java.io.Serializable;

/**
 * Is using to pass to the server login information *
 */
public class Login extends Message implements Serializable {
    public LoginData login = new LoginData();

    public Login(LoginData aLoginData) {
        login.UserName = aLoginData.UserName;
        login.Password = aLoginData.Password;
    }
}
