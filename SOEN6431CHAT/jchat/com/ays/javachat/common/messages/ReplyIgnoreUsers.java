package com.ays.javachat.common.messages;

import java.io.Serializable;
import java.util.Vector;

/**
 * Is ising by server to notify client about request results ( see parent class ) *
 */
public class ReplyIgnoreUsers extends IgnoreUsers implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyIgnoreUsers(Vector aIgnoredUsersList, boolean aOverwriteExistingList, int aStatus) {
        super(aIgnoredUsersList, aOverwriteExistingList);
        Status = aStatus;
    }
}
