// manages all logic between screen ( user input ) and trnamitter ( data sending & receiving )
package com.ays.javachat.client.manager;

import com.ays.javachat.client.interfaces.ScreenCallback;
import com.ays.javachat.client.interfaces.TransmitterCallback;
import com.ays.javachat.client.screen.ClientScreen;
import com.ays.javachat.client.transmitter.ClientTransmitter;
import com.ays.javachat.common.datatypes.IPPort;
import com.ays.javachat.common.messages.*;


/**
 * Manages of all client's logic : creates neccessay classes, implements neccessary functions, psses data between modules.<br>
 * See ScreenCallback, TransmitterCallback interfaces for more infromation *
 */
public class ClientManager implements ScreenCallback, TransmitterCallback {
    ClientTransmitter clientTransmitter = new ClientTransmitter(this);
    ClientScreen clientScreen = new ClientScreen(this);


    public ClientManager() {
        clientScreen.start();
    }


    public void connect(IPPort ipport) {
        int iStatus = clientTransmitter.connect(ipport);
        ReplyConnect reply = new ReplyConnect(ipport, iStatus);
        clientScreen.replyReceived(reply);
    }

    public void disconnect() {
    }


    public synchronized void receiveObject(Object o) {
        ObjectReceived(o);
    }


    // here is proccessing all logic of received messages
    private void ObjectReceived(Object o) {
        // received object not from type Message
        // func exists
        if (!(o instanceof Message))
            return;

        // REPLY FROM SERVER
        if ((o instanceof ReplyLogin) ||
                (o instanceof ReplyLogout) ||
                (o instanceof ReplyRegister) ||
                (o instanceof ReplyUnregister) ||
                (o instanceof ReplyConnect) ||
                (o instanceof ReplyDisconnect) ||
                (o instanceof ReplyGetUserDetails) ||
                (o instanceof ReplySetUserDetails) ||
                (o instanceof ReplyGetOnlineUsersList) ||
                (o instanceof ReplyIgnoreUsers) ||
                (o instanceof ReplyGetUsersIgnoredByMe) ||
                (o instanceof ReplyClientText) ||
                (o instanceof UpdateUsersList) ||
                (o instanceof ServerNotification) ||
                (o instanceof ServerText))
            clientScreen.replyReceived((Message) o);
    }

    public void exit() {
        System.exit(0);
    }


    public int sendRequest(Message aMessage) {
        return clientTransmitter.sendObject(aMessage);
    }

    public void connectionDown() {
        clientScreen.connectionDown();
    }

}
