package com.ays.javachat.common.messages;

import java.io.Serializable;

/**
 * Is using by server to notify client about something *
 */
public class ServerNotification extends Message implements Serializable {
    public int Notification;
}
