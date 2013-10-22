package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is using by server to send text to the client *
 */
public class ServerText extends Message implements Serializable {
    /**
     * From which user message was sent *
     */
    public String FromUser;
    /**
     * Is this message private *
     */
    public boolean IsPrivate;
    public String Text;
}
