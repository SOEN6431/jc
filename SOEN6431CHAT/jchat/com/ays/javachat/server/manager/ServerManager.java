package com.ays.javachat.server.manager;

import com.ays.javachat.common.globalconsts.Net;
import com.ays.javachat.common.messages.*;
import com.ays.javachat.server.database.ServerDatabase;
import com.ays.javachat.server.interfaces.ConnectionsListenerCallback;
import com.ays.javachat.server.interfaces.TransmitterCallback;
import com.ays.javachat.server.transmitter.ConnectionsListener;
import com.ays.javachat.server.transmitter.ServerTransmitter;

import java.net.Socket;
import java.util.Vector;


/**
 * Manages of all server's logic : creates neccessay classes, proccessings events, sends/receives/stores data<br>
 * See ConnectionsListenerCallback, TransmitterCallback  for more information *
 */
public class ServerManager implements ConnectionsListenerCallback, TransmitterCallback {
    public Vector clients = new Vector();
    private ConnectionsListener connectionsListener;
    public ServerDatabase serverDatabase;

    public ServerManager() {
        report("=================================================================");
        report("Chat server by Yevgeny Sergeyev");
        report("Ver 1.1");
        report("Contacts : tel(Israel). : +974-547945462. ICQ : 123845810. e-mail : yevgeny.sergeyev@gmail.com");
        report("=================================================================");

        serverDatabase = new ServerDatabase();
        String Params[] = {"user.ini", "ignores.dat"}; // ini file name
        serverDatabase.setDatabaseParams(Params);
        connectionsListener = new ConnectionsListener(this);
        if (connectionsListener.startListen(Net.DEFAULT_PORT))
            report("Server is started...");
        else
            report("Can't start server. Possible server is already started...");
    }

    public void report(String aString) {
        System.out.println(aString);
    }

    public int fillOnlineUserList(ReplyGetOnlineUsersList reply) {
		int iSize = 0;
		for (int i = 0; i < clients.size(); i++) {
			if (((ServerTransmitter) clients.elementAt(i)).getUserName() != null) {
				iSize++;
			}
		}
		reply.array = new String[iSize];
		int k = 0;
		for (int i = 0; i < clients.size(); i++) {
			if (((ServerTransmitter) clients.elementAt(i)).getUserName() != null) {
				reply.array[k] = ((ServerTransmitter) clients.elementAt(i))
						.getUserName();
				k++;
			}
		}
		return k;
	}

    public void someoneConnected(Object aConnectionBridgeObject) {
        // checking for object : must be a socket object
        if (!(aConnectionBridgeObject instanceof Socket))
            return;

        // now we have socket object
        // creating new transmitter
        ServerTransmitter serverTransmitter = new ServerTransmitter(this);
        clients.add(serverTransmitter);
        serverTransmitter.setupConnectionBridgeObject(aConnectionBridgeObject);

        report("Connection accepted from IP " + serverTransmitter.getIP() + ":" + serverTransmitter.getPort());

        serverTransmitter.startDataExchange();
    }

    public void receiveObject(Object aTransmitter, Object aData) {
        if (!(aTransmitter instanceof ServerTransmitter))
            return;

        if (!(aData instanceof Message))
            return;

        serverDatabase.processReceivedObject(this, (ServerTransmitter) aTransmitter, (Message) aData);
    }

    public void connectionDown(Object aTransmitter) {
        report("Connection was terminated");

        // searching & removing from client list
        for (int i = 0; i < clients.size(); i++)
            if (aTransmitter == clients.elementAt(i)) {
                ServerTransmitter t = (ServerTransmitter) clients.elementAt(i);
                // removing from list
                clients.remove(i);
                // notifiying all clients
                UpdateUsersList updateUsersList = new UpdateUsersList(t.getUserName(), Net.USER_LEFT);
                for (int j = 0; j < clients.size(); j++)
                    ((ServerTransmitter) clients.elementAt(j)).sendObject(updateUsersList);

                return;
            }
    }

}
