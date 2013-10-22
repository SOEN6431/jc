/**
 * <p> ScreenCapables interface </p>
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

import com.ays.javachat.common.messages.Message;


/**
 * This interface shows what can sreen object do  *
 */
public interface ScreenCapables {
    /**
     * Calls when server sent message object *
     */
    public void replyReceived(Message message);

    /**
     * Calls when connection is down or connection problem *
     */
    public void connectionDown();

    /**
     * Shows and inits main frame *
     */
    public void start();
}
