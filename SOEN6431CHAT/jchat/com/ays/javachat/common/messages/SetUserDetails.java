package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.UserDetails;

import java.io.Serializable;

/**
 * Is using by client to update current user details ( current means user which connected and logged in to the server ) *
 */
public class SetUserDetails extends Message implements Serializable {
    public UserDetails details = new UserDetails();
    public String Password = null;

    public SetUserDetails(UserDetails aDetails) {
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
