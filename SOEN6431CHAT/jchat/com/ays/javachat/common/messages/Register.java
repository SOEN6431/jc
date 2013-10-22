package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.LoginData;
import com.ays.javachat.common.datatypes.UserDetails;

import java.io.Serializable;

/**
 * Is using to pass to the server request for register of a new user *
 */
public class Register extends Message implements Serializable {
    public LoginData login = new LoginData();
    public UserDetails details = new UserDetails();

    public Register(LoginData aLogin, UserDetails aDetails) {
        login.UserName = aLogin.UserName;
        login.Password = aLogin.Password;

        if (aDetails != null) {
            details.RealName = aDetails.RealName;
            details.Age = aDetails.Age;
            details.Sex = aDetails.Sex;
            details.Country = aDetails.Country;
            details.City = aDetails.City;
            details.Email = aDetails.Email;
            details.HomePage = aDetails.HomePage;
            details.ICQ = aDetails.ICQ;
            details.About = aDetails.About;
            details.Picture = aDetails.Picture;
        }
    }
}
