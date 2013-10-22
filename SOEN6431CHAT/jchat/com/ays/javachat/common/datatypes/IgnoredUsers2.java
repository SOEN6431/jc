package com.ays.javachat.common.datatypes;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Contains ignored users list *
 */
public class IgnoredUsers2 extends Data implements Serializable {
    /**
     * Contains ignored users list.
     * Every element of this hash table is : [UserName:String],[IgnoredList:Vector].<br>
     * IgnoredList is a vector which consists from users ignored by UserName  *
     */
    public Hashtable users = new Hashtable(); // every element of this hast-table contains Vector - list of the ignored user for each element

    public boolean isDataValid() {
        return true;
    }
}
