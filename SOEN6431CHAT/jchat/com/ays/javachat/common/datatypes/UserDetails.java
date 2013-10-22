package com.ays.javachat.common.datatypes;

import java.io.Serializable;


/**
 * Contains user's information - UserDetails.<br>
 */
public class UserDetails extends Data implements Serializable {
    public String RealName;
    public String Age;
    public String Sex; // must be int that represents : 0 - male, 1 - female, 2 - nodefined
    public String Country;
    public String City;
    public String Email;
    public String HomePage;
    public String ICQ;
    public String About;
    /**
     * Didn't used yet *
     */
    public byte Picture[];

    public void copy(UserDetails aCopy) {
        RealName = aCopy.RealName;
        Age = aCopy.Age;
        Sex = aCopy.Sex;
        Country = aCopy.Country;
        City = aCopy.City;
        Email = aCopy.Email;
        HomePage = aCopy.HomePage;
        ICQ = aCopy.ICQ;
        About = aCopy.About;
        // here must be implemented copying of Picture[] ( if you will use pictures )
    }

    /**
     * Checks for age & sex values.<br>
     * Age must be an integer within 0..120<br>
     * Sex must be an integer within 0..2*
     */
    public boolean isDataValid() {
        try {
            lastErrorMessage = "Invalid sex value";
            if ((Integer.valueOf(Sex) < 0) || (Integer.valueOf(Sex) > 2))
                return false;

            lastErrorMessage = "Invalid age value";
            if (!Age.equals(""))
                if ((Integer.valueOf(Age) < 1) || (Integer.valueOf(Age) > 120))
                    return false;
        }
        catch (Exception e) {
            return false;
        }


        lastErrorMessage = "";
        return true;
    }
}
