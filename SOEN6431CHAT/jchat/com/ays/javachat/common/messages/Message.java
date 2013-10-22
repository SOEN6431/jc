package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Base class for all messages-objects. messages-objects are data-objects which passes between server and client. *
 */
public abstract class Message implements Serializable {
    public int InternalFlag;
}
