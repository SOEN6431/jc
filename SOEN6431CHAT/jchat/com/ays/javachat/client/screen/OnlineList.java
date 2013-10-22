// this class is represents online users list

package com.ays.javachat.client.screen;

import com.ays.javachat.common.datatypes.UserDetails;
import com.ays.javachat.common.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;


/**
 * Is using to display and manage online users list *
 */
public class OnlineList extends JScrollPane implements MouseListener, ActionListener, DoubleClick {
    private final int DOUBLE_CLICK_DELAY = 500;

    private final String popupShowUserDetails = "Show Details";
    private final String popupOpenPrivateRoom = "Private Chat";
    private final String popupIgnoreUser = "Ignore User";

    private Hashtable onlineList = new Hashtable();
    private JList guiList = new JList();
    private DefaultListModel listModel = new DefaultListModel();

    private JPopupMenu popupMenu = new JPopupMenu();

    private OnlineListActions onlineListActions;

    private DoubleClickImplementor doubleClickImp = null;

    // CONSTRUCTOR
    /**
     * Receives pointer to the object which implements interface OnlineListActions *
     */
    public OnlineList(OnlineListActions aOnlineListActions) {
        super();

        onlineListActions = aOnlineListActions;

        guiList.setModel(listModel);
        guiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setViewportView(guiList);
        setPreferredSize(new Dimension(60, 400));

        guiList.addMouseListener(this);
        buildPopupMenu();
        guiList.setFocusable(false);

        // new !!! list with pictures
        //guiList.setCellRenderer( new OnlineListRenderer( onlineListActions ) );
    }

    private void buildPopupMenu() {
        JMenuItem menuItem = new JMenuItem(popupShowUserDetails);
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

        menuItem = new JMenuItem(popupOpenPrivateRoom);
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);

        menuItem = new JMenuItem(popupIgnoreUser);
        menuItem.addActionListener(this);
        popupMenu.add(menuItem);
    }

    public void addUser(String aUserName, UserDetails Details) {
        if (onlineList.get(aUserName) != null)
            return; // user is already in the list

        // adding to the hash-table
        onlineList.put(aUserName, Details);
        // adding to the gui-control
        listModel.addElement(aUserName);
    }

    public void removeUser(String aUserName) {
        if (StringUtils.isEmpty(aUserName)) {
            return;
        }

        // removing from hash
        onlineList.remove(aUserName);

        // removing from gui-control
        listModel.removeElement(aUserName);
    }

    public void removeAllUsers() {
        onlineList.clear();
        listModel.clear();

        guiList.revalidate();
    }

    public void updateUser(String aUserName, UserDetails aDetails) {
        if (onlineList.get(aUserName) == null)
            return; // user is not in the list

        onlineList.put(aUserName, aDetails);
    }

    public void setName(String aName) {
        super.setName(aName);
        guiList.setName(aName);
    }

    private String getSelectedItem() {
        int iIndex = guiList.getSelectedIndex();
        if (iIndex < 0)
            return null;
        return (String) (listModel.getElementAt(iIndex));
    }

    // interface MouseListener
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }

        if (onlineListActions == null)
            return;

        if (e.getButton() == MouseEvent.BUTTON1)
            if (doubleClickImp == null)
                doubleClickImp = new DoubleClickImplementor(this, DOUBLE_CLICK_DELAY, e);
            else {
                doubleClickImp.stop();
                doubleClickImp = null;
                String s = getSelectedItem();
                if (s != null)
                    onlineListActions.userDoubleSelected(s);
            }

        if (guiList.getSelectedIndex() < 0)
            return;

        //onlineListActions.userDoubleClicked( ( String )listModel.getElementAt( guiList.getSelectedIndex() ) ) ;
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

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(popupShowUserDetails)) {
            String s = getSelectedItem();
            if (s != null)
                onlineListActions.showUserDetails(s);
        }

        if (e.getActionCommand().equals(popupOpenPrivateRoom)) {
            String s = getSelectedItem();
            if (s != null)
                onlineListActions.userDoubleSelected(s);
        }

        if (e.getActionCommand().equals(popupIgnoreUser)) {
            String s = getSelectedItem();
            if (s != null)
                onlineListActions.ignoreUser(s);
        }

    }

    public void doubleClickTimeout(MouseEvent e) {
        doubleClickImp = null;
    }
}
