package com.ays.javachat.common.datatypes;

import com.ays.javachat.common.globalconsts.Net;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Contains IP address ( string ) and Port ( int ).
 */
public class IPPort extends Data implements Serializable {
    private transient final String FileName = "ipport.ini";
    private transient final String defFileName = "defipport.ini";

    public String IP;
    public int Port;

    public IPPort() {
        loadFromFile(FileName);
        if (!saveToFile(FileName))
            setDefaults();
    }

    public IPPort(String aIP, int aPort) {
        IP = aIP;
        Port = aPort;

        saveToFile(FileName);
    }

    /**
     * Checks for IP address & Port.<br>
     * Checks for IP address. Must be not null with length > 0<br>
     * Checks for Port. Must be in the port range 1025..65535 *
     */
    public boolean isDataValid() {
        if (IP == null) {
            lastErrorMessage = "IP is null";
            return false;
        }
        if ((IP.length() < 1) || (Port < 1025) || (Port > 0xFFFF)) {
            lastErrorMessage = "Port is out of range or IP string is zero-length";
            return false;
        }

        lastErrorMessage = "";
        return true;
    }

    private boolean loadFromFile(String aFileName) {
        Properties p = new Properties();

        try {
            p.load(new FileInputStream(aFileName));
            IP = p.getProperty("IP");
            Port = Integer.valueOf(p.getProperty("Port"));
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean saveToFile(String aFileName) {
        Properties p = new Properties();

        try {
            p.setProperty("IP", IP);
            p.setProperty("Port", String.valueOf(Port));
            p.store(new FileOutputStream(aFileName), null);
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    public void setDefaults() {
        if (!loadFromFile(defFileName)) {
            IP = Net.DEFAULT_HOST;
            Port = Net.DEFAULT_PORT;
        }

        saveToFile(defFileName);
    }

    public void save() {
        if (!saveToFile(FileName))
            setDefaults();
    }
}
