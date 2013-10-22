package com.ays.javachat.client.transmitter;

import com.ays.javachat.client.interfaces.TransmitterCallback;

import java.io.InputStream;
import java.io.ObjectInputStream;


/**
 * This class is using for listening for incoming data in new thread.<br>
 * When readed something, object calls for transmitterCallback-functions from TransmitterCallback interface*
 */
public class ThreadListener extends Thread {
    TransmitterCallback transmitterCallback;
    InputStream inputStream;

    /**
     * Creator of this class must pass pointer to the object which implemented interface TransmitterCallback *
     */
    public ThreadListener(TransmitterCallback aTransmitterCallback, InputStream aInputStream) {
        transmitterCallback = aTransmitterCallback;
        inputStream = aInputStream;
    }


    public void run() {
        if (transmitterCallback == null)
            ; // save_log() ;
        if (inputStream == null)
            ; // save_log() ;

        Object o;
        ObjectInputStream objectInputStream;

        try {
            objectInputStream = new ObjectInputStream(inputStream);
        }
        catch (Exception e) {
            // save_log() ;
            transmitterCallback.connectionDown();
            return;
        }

        while (true) {
            try {
                o = objectInputStream.readObject();
            }
            catch (Exception e) {
                // save_log() ;
                transmitterCallback.connectionDown();
                return;
            }

            transmitterCallback.receiveObject(o);
        }
    }
}
