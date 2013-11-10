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

       
		try {
			 dataListner();
        }
        catch (Exception e) {
            // save_log() ;
            transmitterCallback.connectionDown();
            return;
        }
    }


	private void dataListner() throws java.io.IOException,
			java.lang.ClassNotFoundException {
		Object o;
		ObjectInputStream objectInputStream;
		objectInputStream = new ObjectInputStream(inputStream);
		while (true) {
			o = objectInputStream.readObject();
			transmitterCallback.receiveObject(o);
		}
	}
}
