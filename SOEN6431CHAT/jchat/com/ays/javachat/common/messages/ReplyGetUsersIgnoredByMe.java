package com.ays.javachat.common.messages;

import java.io.Serializable;
import java.util.Vector;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyGetUsersIgnoredByMe extends GetUsersIgnoredByMe implements Serializable {
    /**
     * Ignored users list returned by server *
     */
    public Vector ignoredUsersList = new Vector();
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyGetUsersIgnoredByMe(Vector aIgnoredUsersList, int aStatus) {
        Status = aStatus;
        if (aIgnoredUsersList != null) {
            ignoredUsersList.clear();
            for (int i = 0; i < aIgnoredUsersList.size(); i++)
                ignoredUsersList.add(aIgnoredUsersList.get(i));
        }
    }
}
