package com.ays.javachat.client.transmitter;

import com.ays.javachat.client.interfaces.TransmitterCallback;
import com.ays.javachat.client.interfaces.TransmitterCapables;
import com.ays.javachat.common.datatypes.IPPort;
import com.ays.javachat.common.globalconsts.Net;

import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is a communication object ( transmitter ). See TransmitterCapables interface for more information *
 */
public class ClientTransmitter implements TransmitterCapables {
    // callback interface
    private TransmitterCallback transmitterCallback;

    // socket object
    Socket socket = null;
    ObjectOutputStream outputStream = null;

    // thread-listener
    ThreadListener threadListener = null;


    ////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    /**
     * Creator of this class need to pass a pointr to the object which implemented interface TransmitterCallback *
     */
    public ClientTransmitter(TransmitterCallback aTransmitterCallback) {
        transmitterCallback = aTransmitterCallback;
    }


    ////////////////////////////////////////////////////////////////////////////
    // connect
    public int connect(IPPort ipport) {
        if (threadListener != null) {
            threadListener.stop();
            threadListener = null;
        }


        if (socket != null) {
            disconnect();
            socket = null;
            outputStream = null;
        }

        try {
            socket = new Socket(ipport.IP, ipport.Port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception e) {
            return Net.CONNECTION_PROBLEM; // save_log()
        }

        // starting listener...
        try {
            threadListener = new ThreadListener(transmitterCallback, socket.getInputStream());
            threadListener.start();
        }
        catch (Exception e) {
            // save_log() ;
            transmitterCallback.connectionDown();
            return Net.CONNECTION_PROBLEM;
        }

        return Net.OK;
    }


    ////////////////////////////////////////////////////////////////////////////
    // disconnect
    public void disconnect() {
        if (socket != null)
            try {
                outputStream.close();
                socket.close();
            }
            catch (Exception e) {
                // save_log() ;
            }
    }


    ////////////////////////////////////////////////////////////////////////////
    // sendObject
    public synchronized int sendObject(Object o) {
        if (socket == null)
            return Net.NO_TCP_CONNECTION;

        try {
            outputStream.writeObject(o);
        }
        catch (Exception e) {
            // save_log() ;
            return Net.CONNECTION_PROBLEM;
        }

        return Net.OK;
    }

}
