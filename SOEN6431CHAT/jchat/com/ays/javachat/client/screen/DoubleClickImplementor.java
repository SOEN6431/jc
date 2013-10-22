package com.ays.javachat.client.screen;

import java.awt.event.MouseEvent;

/**
 * Is uses for implementing of double click *
 */
public class DoubleClickImplementor extends Thread {
    private DoubleClick doubleClick = null;
    private int Delay;
    private MouseEvent mouseEvent;

    /**
     * aDoubleClick - is a pointer to object which implements interface DoubleClick<br>
     * aDelay - is a time given for double click<br>
     * aEvent - is an event which will passed to the doubleClickTimeout func *
     */
    public DoubleClickImplementor(DoubleClick aDoubleClick, int aDelay, MouseEvent aEvent) {
        doubleClick = aDoubleClick;
        Delay = aDelay;
        mouseEvent = aEvent;

        if ((Delay < 1) || (doubleClick == null))
            return;

        start();
    }

    public void run() {
        try {
            Thread.sleep(Delay);
        }
        catch (Exception e) {
        }

        doubleClick.doubleClickTimeout(mouseEvent);
    }
}
