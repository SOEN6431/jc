package com.ays.javachat.client.screen;

/**
 * Is using by class OnlineList to notify screen about action : showUserDetails, userSelected, userDoubleSelected, ignoreUser *
 */
public interface OnlineListActions {
    /**
     * This functions calls when pressing "Show User Details" *
     */
    public void showUserDetails(String aUserName);

    /**
     * Calls when selecting user name in the list *
     */
    public void userSelected(String aUserName);

    /**
     * Calls when double click occured at user *
     */
    public void userDoubleSelected(String aUserName);

    /**
     * Calls when pressing Ignore User *
     */
    public void ignoreUser(String aUserName);
}
