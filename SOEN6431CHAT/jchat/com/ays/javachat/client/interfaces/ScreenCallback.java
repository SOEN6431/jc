/**
 * <p>  ScreenCallback interface </p>
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
import com.ays.javachat.common.messages.Message;


/**
 * Sceen component can call functions declared in this interface *
 */
public interface ScreenCallback {
    /**
     * Sends connect request to the server *
     */
    public void connect(IPPort ipport);

    /**
     * Send disconnect request to the server *
     */
    public void disconnect();

    /**
     * Sends request to exit from program *
     */
    public void exit(); // exits from application

    /**
     * Sends special request to the server.<br> See Messages-object*
     */
    public int sendRequest(Message aMesasge);
}
