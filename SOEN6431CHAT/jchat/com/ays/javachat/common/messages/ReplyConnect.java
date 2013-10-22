package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.IPPort;

import java.io.Serializable;

/**
 * Is ising to notify client about request results ( see parent class ) *
 */
public class ReplyConnect extends Connect implements Serializable {
    /**
     * If Status == 0, operation completed successfully *
     */
    public int Status;

    public ReplyConnect(IPPort aIPPort, int aStatus) {
        ipport.IP = aIPPort.IP;
        ipport.Port = aIPPort.Port;
        Status = aStatus;
    }
}
