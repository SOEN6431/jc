package com.ays.javachat.client.screen;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;

/**
 * This class is using as a container for some swing components which used for private room building.<br>
 * This class inherites from JPanel *
 */
public class PrivateRoomContainer extends JPanel {
    public JScrollPane scroll;
    //public JTextArea ta ;
    public JButton closeButton;
    public JButton ignoreButton;
    public JButton userDetailsButton;
    public JPanel buttonsPanel;

    public JTextPane tp;
    public StyledDocument doc;

    public PrivateRoomContainer(LayoutManager lm) {
        super(lm);
    }
}
