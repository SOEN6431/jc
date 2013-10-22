// this class is represents chat rooms

package com.ays.javachat.client.screen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * This class manages and displays chat-rooms *
 */
public class Rooms extends JTabbedPane implements MouseListener, ChangeListener {
    private final String CLOSE_BUTTON = "Close Button";
    private final String IGNORE_BUTTON = "Ignore Button";
    private final String USER_DETAILS_BUTTON = "User Details Button";

    RoomActions roomActions;

    // CONSTRUCTOR
    /**
     * Creator of this class must pass pointer to object which implements interface RoomActins *
     */
    public Rooms(RoomActions aRoomActions) {
        roomActions = aRoomActions;

        setFocusable(false);

        addChangeListener(this);
        fireStateChanged();
    }

    public void stateChanged(ChangeEvent e) {
        int i = ((JTabbedPane) e.getSource()).getSelectedIndex();
        if (i >= 0)
            markRoomAsUnread(((PrivateRoomContainer) getComponentAt(i)).getName());
    }

    public boolean isRoomOpened(String aRoomName) {
        for (int i = 0; i < getTabCount(); i++)
            if (getComponentAt(i).getName().equals(aRoomName))
                return true;

        return false;
    }

    /**
     * if aSilent == true, romms activates, esle room opens and not activetes *
     */
    public void openRoom(String aRoomName, boolean aSilent) {
        // checking if room already exists
        for (int i = 0; i < getTabCount(); i++)
            if (getComponentAt(i).getName().equals(aRoomName)) {
                if (!aSilent)
                    setSelectedComponent(getComponentAt(i));
                return;
            }

        PrivateRoomContainer prc = new PrivateRoomContainer(new BorderLayout());

        prc.tp = new JTextPane();
        prc.tp.setFocusable(false);
        prc.tp.setEditable(false);
        prc.doc = prc.tp.getStyledDocument();

        /*
        Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE ) ;

        Style color = prc.doc.addStyle( "def", def ) ;
        StyleConstants.setForeground( color, Color.red ) ;

        Style bw = prc.doc.addStyle( "color", color ) ;
        StyleConstants.setForeground( bw, Color.black );

        prc.doc.addStyle( "bw", bw ) ;

        StyleConstants.setFontSize( color, 10 ) ;
        StyleConstants.setFontSize( bw, 20 ) ;
        */
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = prc.doc.addStyle("regular", def);
        StyleConstants.setForeground(regular, Color.BLUE);

        Style s = prc.doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setForeground(s, Color.red);

        //prc.ta = new JTextArea() ;
        //prc.ta.setName( aRoomName ) ;
        //prc.ta.setEditable( false ) ;
        //prc.ta.setFocusable( false ) ;

        //prc.scroll = new JScrollPane( prc.ta ) ;
        prc.scroll = new JScrollPane(prc.tp);
        prc.scroll.setName(aRoomName);

        prc.closeButton = new JButton("Close ");
        prc.closeButton.setFocusable(false);
        prc.closeButton.setToolTipText("Close private dialog");
        prc.closeButton.setName(CLOSE_BUTTON);
        prc.closeButton.addMouseListener(this);

        prc.ignoreButton = new JButton("Ignore");
        prc.ignoreButton.setFocusable(false);
        prc.ignoreButton.setToolTipText("Ignore user");
        prc.ignoreButton.setName(IGNORE_BUTTON);
        prc.ignoreButton.addMouseListener(this);

        //prc.userDetailsButton = new JButton( "User details" ) ;
        //prc.ignoreButton.setToolTipText( "Show user details" ) ;
        //prc.ignoreButton.setName( USER_DETAILS_BUTTON ) ;
        //prc.ignoreButton.addMouseListener( this ) ;


        JPanel p1, p2;
        p1 = new JPanel();
        p2 = new JPanel();
        p1.add(prc.closeButton);
        //p2.add( prc.ignoreButton ) ;
        prc.buttonsPanel = new JPanel(new GridLayout(10, 1));
        //prc.buttonsPanel.add( prc.closeButton ) ;
        prc.buttonsPanel.add(p1);
        prc.buttonsPanel.add(p2);
        //prc.buttonsPanel.add( prc.ignoreButton ) ;

        prc.setName(aRoomName);
        prc.add(prc.buttonsPanel, BorderLayout.EAST);
        prc.add(prc.scroll, BorderLayout.CENTER);

        add(prc, aRoomName);
        if (!aSilent)
            setSelectedComponent(prc);
    }

    public void closeRoom(String aRoomName) {
    }

    public void closeAllRooms() {
    }

    private void markRoomAsUnread(String aRoomName) {
        for (int i = 0; i < getTabCount(); i++)
            if (getComponentAt(i).getName().equals(aRoomName)) {
                PrivateRoomContainer prc = (PrivateRoomContainer) getComponentAt(i);

                if (getSelectedIndex() == i)
                    setTitleAt(i, prc.getName());
                else
                    setTitleAt(i, "* " + prc.getName());
            }
    }


    // new room opens automatically if not opened yet
    /**
     * Adds aText to the room aRoomName. If aSystemText is true, text displays as a system text ( by another color ) *
     */
    public void addTextToRoom(String aRoomName, String aText, boolean aSystemText) {
        if ((aRoomName == null) || (aText == null))
            return;

        // opening room
        openRoom(aRoomName, true);

        // if room is not active, it marks a room as a unreded
        markRoomAsUnread(aRoomName);

        // searching for room
        for (int i = 0; i < getTabCount(); i++)
            if (getComponentAt(i).getName().equals(aRoomName)) {
                // adding text & exit func
                //JTextArea ta = ( ( PrivateRoomContainer )getComponentAt( i ) ).ta ;
                JTextPane tp = ((PrivateRoomContainer) getComponentAt(i)).tp;
                StyledDocument doc = ((PrivateRoomContainer) getComponentAt(i)).doc;

                try {
                    if (aSystemText)
                        doc.insertString(doc.getLength(), aText + "\n", doc.getStyle("bold"));
                    else
                        doc.insertString(doc.getLength(), aText + "\n", doc.getStyle("regular"));

                    // scrolling to the end
                    ((PrivateRoomContainer) getComponentAt(i)).scroll.getVerticalScrollBar().setValue(Integer.MAX_VALUE);
                }
                catch (Exception e) {
                }
                return; //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            }
    }

    /**
     * Analog of addTextToRoom. Text will be added to the selected room *
     */
    public void addTextToCurrentRoom(String aText, boolean aSystem) {
        Component c = getSelectedComponent();
        if (c == null)
            return;

        addTextToRoom(c.getName(), aText, aSystem);
    }


    /**
     * Returns selected room name *
     */
    public String getCurrentRoomName() {
        return getSelectedComponent().getName();
    }

    public void closeCurrentRoom() {
        remove(getSelectedComponent());
    }

    // interface MouseListener
    public void mouseClicked(MouseEvent e) {
        if (!(e.getSource() instanceof Component))
            return;

        String s = ((Component) e.getSource()).getName();

        if (s.equals(CLOSE_BUTTON)) {
            if (roomActions != null)
                if (!roomActions.canCloseRoom(getCurrentRoomName()))
                    return;
            if (roomActions != null)
                roomActions.closeRoomPressed(getCurrentRoomName());
            closeCurrentRoom();
        }

        if (s.equals(IGNORE_BUTTON)) {
            if (roomActions != null)
                roomActions.ignoreUserPressed(getCurrentRoomName());
        }
    }

    // interface MouseListener
    public void mouseExited(MouseEvent e) {
    }

    // interface MouseListener
    public void mouseEntered(MouseEvent e) {
    }

    // interface MouseListener
    public void mouseReleased(MouseEvent e) {
    }

    // interface MouseListener
    public void mousePressed(MouseEvent e) {
    }
}
