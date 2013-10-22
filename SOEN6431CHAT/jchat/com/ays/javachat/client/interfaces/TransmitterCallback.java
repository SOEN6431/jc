/**
 * <p> TransmitterCallback interface </p>
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

/**
 * Is using to pass received objects and notify about connection problem *
 */
public interface TransmitterCallback {
    /**
     * Receives object *
     */
    public void receiveObject(Object o);

    /**
     * Notifiyes about connection problem *
     */
    public void connectionDown();
}
