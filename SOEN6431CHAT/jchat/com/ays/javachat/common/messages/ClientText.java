package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is using to pass text from client to the server *
 */
public class ClientText extends Message implements Serializable {
    /**
     * Is the user which will receive this text. If UserName is null, messages sends to the private room *
     */
    public String UserName;
    public String Text;

    public ClientText(String aUserName, String aText) {
        UserName = aUserName;
        Text = aText;
    }
}
