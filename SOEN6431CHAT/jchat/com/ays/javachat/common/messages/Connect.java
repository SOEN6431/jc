package com.ays.javachat.common.messages;

import com.ays.javachat.common.datatypes.IPPort;

import java.io.Serializable;

/**
 * Is using to pass net-info *
 */
public class Connect extends Message implements Serializable {
    public IPPort ipport = new IPPort();
}
