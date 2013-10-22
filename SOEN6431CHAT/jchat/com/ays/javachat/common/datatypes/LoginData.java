package com.ays.javachat.common.datatypes;

import java.io.Serializable;

/**
 * Contains UserName and Password *
 */
public class LoginData extends Data implements Serializable {
    public String UserName;
    public String Password;

    public LoginData() {
    }

    public LoginData(String aUserName) {
        UserName = aUserName;
        Password = "";
    }

    private boolean isUserNameValid() {
        if (UserName == null) {
            lastErrorMessage = "UserName is null";
            return false;
        }

        if (UserName.length() < 5) {
            lastErrorMessage = "UserName length is less than 5";
            return false;
        }

        if ((UserName.lastIndexOf(',') >= 0) || (UserName.lastIndexOf(';') >= 0)) {
            lastErrorMessage = "UserName can't contain symbols ',;'";
            return false;
        }

        // here can be placed UserName checking...

        return true;
    }

    private boolean isPasswordValid() {
        if (Password == null) {
            lastErrorMessage = "Password is null";
            return false;
        }

        if (Password.length() < 5) {
            lastErrorMessage = "Password length is less than 5";
            return false;
        }

        return true;
    }

    /**
     * Checks for UserName & Password.<br>
     * UserName must not null with length > 4. Also UserName can't contain symbols : ",", ";"<br>
     * Password must be not null with legnth > 4.<br>
     * Also UserName and Password must be different*
     */
    public boolean isDataValid() {
        boolean bResult = (isUserNameValid() && isPasswordValid());

        if (UserName.equals(Password)) {
            bResult = false;
            lastErrorMessage = "Login and Password are identical";
        }

        if (bResult)
            lastErrorMessage = "";

        return bResult;
    }
}
