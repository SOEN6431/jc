package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.UserDetails;

import java.io.Serializable;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyGetUserDetails extends GetUserDetails implements Serializable {
    /**
     * Yser details returned by server *
     */
    public UserDetails details = new UserDetails();
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyGetUserDetails(String aUserName, UserDetails aDetails, int aStatus) {
        super(aUserName);

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

        Status = aStatus;
    }

}
