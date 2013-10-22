/**
 * <p> TransmitterCapables interface </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: AYS company</p>
 *
 * @Yevgeny S.
 * @version 1.0
 */


package com.ays.javachat.client.interfaces;

import com.ays.javachat.common.datatypes.IPPort;


/**
 * Shows what communication object ( transmitter ) can do *
 */
public interface TransmitterCapables {
    /**
     * Connects to the ipport. If case of success returns 0 *
     */
    public int connect(IPPort ipport);

    /**
     * Disconnects from server *
     */
    public void disconnect();

    /**
     * Sends onject to the server. In case of success returns 0 *
     */
    public int sendObject(Object o); // must be a synchronized
}
