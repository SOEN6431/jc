package com.ays.javachat.common.globalconsts;


/**
 * Contains all global constants which uses client and server applications *
 */
public class Net {
    // error messages
    public static final int OK = 0;
    public static final int CONNECTION_PROBLEM = -1;
    public static final int INVALID_USERNAME_PASS = -2; // when someone tryes to log in
    public static final int BANNED = -3;
    public static final int NOT_LOGGED_IN = -4;
    public static final int BAD_USERNAME = -5; // for example, username is zero-length
    public static final int BAD_PASSWORD = -6; // for example, password is zero-length
    public static final int BAD_USER_DETAILS = -7; // for example, age < 0
    public static final int USER_ALREADY_EXISTS = -8;
    public static final int USER_DOESNT_EXISTS = -9;
    public static final int USER_DONTWANT_TOTALK = -10;
    public static final int USER_DONT_ACCEPT_MESSAGES = -12;
    public static final int USER_ISOFFLINE = -13;
    public static final int KICKED = -14;
    public static final int NO_TCP_CONNECTION = -15; // i.e. there no TCP connection established with someone
    public static final int NO_SUCH_USER = -16;
    public static final int INTERNAL_ERROR = -17; // server internal error
    public static final int NOT_LOGGEDOUT = -18; // user trying to log in as a new UserName, but didn't do logout
    public static final int SOMEONE_LOGGEDIN_ASIT = -19; // someone logged in with this UserName on another compoter

    // information messages
    public static final int USER_JOINED = 1;
    public static final int USER_LEFT = 2;
    public static final int USER_HASUPDATED_HISINFO = 3;

    // default Port
    public static final int DEFAULT_PORT = 57277;
    // default IP
    public static final String DEFAULT_HOST = "localhost";

    /**
     * Returns error description *
     */
    public static String describeMessage(int aMessage) {
        switch (aMessage) {
            case OK:
                return "OK";
            case CONNECTION_PROBLEM:
                return "Connection problem";
            case INVALID_USERNAME_PASS:
                return "User name and/or password not entered properly";
            case BANNED:
                return "You have beed banned from this server";
            case NOT_LOGGED_IN:
                return "You are not logged in";
            case BAD_USERNAME:
                return "Bad user name";
            case BAD_PASSWORD:
                return "Bad password";
            case BAD_USER_DETAILS:
                return "Bad user details";
            case USER_ALREADY_EXISTS:
                return "User is already exists";
            case USER_DOESNT_EXISTS:
                return "User doesn't exists";
            case USER_DONTWANT_TOTALK:
                return "User don't want to talk with you";
            case USER_DONT_ACCEPT_MESSAGES:
                return "User don't accep messages from you";
            case USER_ISOFFLINE:
                return "User is offline";
            case KICKED:
                return "You have beed kicked from";
            case NO_TCP_CONNECTION:
                return "No connection with server";
            case NO_SUCH_USER:
                return "No such user";
            case INTERNAL_ERROR:
                return "Server internal error";
            case NOT_LOGGEDOUT:
                return "You are not logged out";
            case SOMEONE_LOGGEDIN_ASIT:
                return "User name is used on another client";

            default:
                return "( no description )";
        }
    }
}
