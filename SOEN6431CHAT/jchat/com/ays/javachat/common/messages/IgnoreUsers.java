package com.ays.javachat.common.messages;

import java.io.Serializable;
import java.util.Vector;

/**
 * Is using to send to the server users list which I want to ignore for private chat*
 */
public class IgnoreUsers extends Message implements Serializable {
    /**
     * Ingnored users list *
     */
    public Vector<String> ignoredUsersList = new Vector<String>();
    /**
     * If this variable is true, server overwrites existing list. If false - adds to the existing list *
     */
    public boolean OverwriteExistingList = true; // if true - ignored uers list overwrites existing list.
    // if false - adding to the exiting list

    public IgnoreUsers(Vector<String> aIgnoredUsersList, boolean aOverwriteExistingList) {
        OverwriteExistingList = aOverwriteExistingList;

        if (aIgnoredUsersList != null) {
            ignoredUsersList.clear();
            for (int i = 0; i < aIgnoredUsersList.size(); i++)
                ignoredUsersList.add(aIgnoredUsersList.get(i));
        }
    }
}
