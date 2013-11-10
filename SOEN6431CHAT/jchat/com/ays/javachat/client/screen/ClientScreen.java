package com.ays.javachat.client.screen;

import com.ays.javachat.client.interfaces.ScreenCallback;
import com.ays.javachat.client.interfaces.ScreenCapables;

import com.ays.javachat.common.datatypes.LoginData;
import com.ays.javachat.common.datatypes.UserDetails;
import com.ays.javachat.common.globalconsts.Net;
import com.ays.javachat.common.messages.*;

import static com.ays.javachat.common.utils.StringUtils.isEmpty;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


/**
 * This class creates user interface ( gui ),  implements logic of sends/receives user requests ( connect, login,
 * register, ... ), permits to open/close private chat rooms, update user details, manage ignore user list.<br>
 * This class creates classes : Room, OnlineUsersList ( + implements their interfaces : RoomsActions, OnlineListActions ) *
 */
public class ClientScreen implements ScreenCapables,
        MouseListener,
        ActionListener,
        KeyListener,
        RoomActions,
        OnlineListActions {

    // data exchange flags
    private final int flagShowUserDetails = 100; // Mesasge.InternalFlag, if == 100, means that client needs to show user details
    private final int flagUpdateUserDetails = 101; // Message.InternalFlag. if == 101,  means that someone updates his details
    private final int flagShowMyDetails = 102; // Mesasge.InternalFlag. if == 102, means that i need to get my details and view it


    ClientScreenData data = new ClientScreenData(new ClientScreenFunctions(),
			new Dimension(800, 600), new Point(120, 80),  
			new JLabel("     Rooms"), new JLabel("     Online users"), new JLabel("     Type your message here"), new Rooms(this),
			new OnlineList(this), new JTextField(), new JButton("Send"), new JPopupMenu()) ;


	///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////
    public ClientScreen(ScreenCallback aScreenCallback) {
        data.setScreenCallback(aScreenCallback);

        if (data.getScreenCallback() == null)
            return;

    }

    /**
     * Shows and inits main frame *
     */
    public void start() {
        data.getClientScreenFunctions().getClientScreenBuildInit().show(data.getClientScreenFunctions().getF(), data.getClientScreenFunctions().getPROGRAM_TITLE(), data.getWINDOW_POS(), data.getWINDOW_SIZE(), this);
        init_tabs();

        data.getClientScreenFunctions().doConnect(data.getScreenCallback());
    }

    private void init_tabs() {
        data.getTabs().openRoom(data.getGENERAL_ROOM(), false);
    }


    ///////////////////////////////////////////////////////////////////////////
    // screen building functions
    ///////////////////////////////////////////////////////////////////////////
    private JPanel getBorderPanel() {
        JPanel p = new JPanel();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(5);
        bl.setHgap(5);
        p.setLayout(bl);
        p.add(new JPanel(), BorderLayout.SOUTH);
        p.add(new JPanel(), BorderLayout.WEST);
        p.add(new JPanel(), BorderLayout.NORTH);
        p.add(new JPanel(), BorderLayout.EAST);
        return p;
    }

    public void buildScreen() {
        data.getClientScreenFunctions().getClientScreenBuildInit().getStatusBarLabel().setOpaque(true);

        GridBagConstraints c = new GridBagConstraints();

        JPanel p;

        c.fill = GridBagConstraints.BOTH;

        c.weightx = 0.8;
        c.weighty = 0.8;
        c.gridwidth = GridBagConstraints.RELATIVE;
        p = getBorderPanel();
        p.add(data.getTabs(), BorderLayout.CENTER);
        p.add(data.getRooms(), BorderLayout.NORTH);
        data.getClientScreenFunctions().getClientScreenBuildInit().getMainPanel().add(p, c);

        c.weightx = 0.2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        p = getBorderPanel();
        p.add(data.getList());
        p.add(data.getPeoples(), BorderLayout.NORTH);
        data.getClientScreenFunctions().getClientScreenBuildInit().getMainPanel().add(p, c);

        c.weightx = 0.8;
        c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.RELATIVE;
        p = getBorderPanel();
        p.add(data.getTf());
        p.add(data.getTypeyourmessage(), BorderLayout.NORTH);
        data.getClientScreenFunctions().getClientScreenBuildInit().getMainPanel().add(p, c);

        c.weightx = 0.2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        p = getBorderPanel();
        p.add(data.getSend());
        data.getClientScreenFunctions().getClientScreenBuildInit().getMainPanel().add(p, c);

        data.getSend().setToolTipText("Send message");
    }

    public void buildMenu() {
        // menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem menuItem;

        data.getClientScreenFunctions().getF().setJMenuBar(menuBar);

        // File
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem = new JMenuItem(data.getMenuCONNECT());
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem(data.getMenuLOGIN());
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem(data.getMenuREGISTER());
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu.addSeparator();
        menuItem = new JMenuItem(data.getMenuEXIT());
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // View
        menu = new JMenu("View");
        menuBar.add(menu);
     
        menuItem = new JMenuItem(data.getMenuMyDetails());
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem(data.getMenuManageIgnoreList());
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // Help
        menu = new JMenu("Help");
        menuBar.add(menu);
        menuItem = new JMenuItem(data.getMenuABOUT());
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }


    // this func adds listeners to the compomnents, setted up names, blablabla...
    public void initComponents() {
        data.getSend().setName(data.getSEND_BUTTON());
        data.getSend().addMouseListener(this);
        data.getSend().setFocusable(false);

        data.getTf().setName(data.getTEXT_LINE());
        data.getTf().addKeyListener(this);
    }


    private void doShowAbout() {
        Dialogs.aboutDialog();
    }


    private void doUpdateUsersList(String aUserName, int iStatus) {
        // is user name empty ?
        if (isEmpty(aUserName)) {
            return;
        }

        switch (iStatus) {
            // user joined
            case Net.USER_JOINED: {
                UserDetails details = new UserDetails();
                data.getList().addUser(aUserName, details);
                data.getTabs().addTextToRoom(data.getGENERAL_ROOM(), "JOINED - " + aUserName, true);
                break;
            }

            // user left
            case Net.USER_LEFT: {
                data.getList().removeUser(aUserName);
                data.getTabs().addTextToRoom(data.getGENERAL_ROOM(), "LEFT - " + aUserName, true);
                if (data.getTabs().isRoomOpened(aUserName))
                    data.getTabs().addTextToRoom(aUserName, "USER LEFT", true);
                break;
            }

            // user updated his info
            case Net.USER_HASUPDATED_HISINFO: {
                data.getClientScreenFunctions().doGetUserDetails(aUserName, flagUpdateUserDetails, data.getScreenCallback(), this);
                break;
            }

        }
    }


    private void doReceiveServerText(String aFromUserName, boolean bIsPrivate, String aText) {
        if (!bIsPrivate)
            data.getTabs().addTextToRoom(data.getGENERAL_ROOM(), aFromUserName + " : " + aText, false);
        else
            data.getTabs().addTextToRoom(aFromUserName, aFromUserName + " : " + aText, false);
    }


    private void sendClicked() { // is a result of clicking on the SEND button
        String s = data.getTf().getText();

        if (s == null)
            return;

        if (s.length() < 1)
            return;

        String text = data.getClientScreenFunctions().getSLastLoggedOnUserName();
        if (isEmpty(text)) {
            text = "noname";
        }
        text += " : " + data.getTf().getText();
        data.getTabs().addTextToCurrentRoom(text, false);

        String sUser = data.getTabs().getCurrentRoomName();
        if (sUser.equals(data.getGENERAL_ROOM()))
            sUser = null;

        data.getClientScreenFunctions().doSendText(sUser, s, data.getScreenCallback(), this);
    }

    private void clearTextLine() {
        data.getTf().setText("");
    }

    private void showMyDetails(UserDetails aUserDetails) {
        if (data.getClientScreenFunctions().getSLastLoggedOnUserName() == null)
            return;

        LoginData login = new LoginData(data.getClientScreenFunctions().getSLastLoggedOnUserName());
        if (Dialogs.registerDialog(login, aUserDetails, true))
            data.getClientScreenFunctions().doSetUserDetails(aUserDetails, login.Password, data.getScreenCallback(), this);
    }

    private void displayUserDetails(String aUserName, UserDetails aDetails) {
        Dialogs.showUserDetailsDialog(aUserName, aDetails);
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface implementations
    ///////////////////////////////////////////////////////////////////////////

    // interface MouseListener

    public void mouseClicked(MouseEvent e) {
        if (!(e.getSource() instanceof Component))
            return;

        String s = ((Component) e.getSource()).getName();

        if (s == null)
            return;

        // send button clicked
        if (s.equals(data.getSEND_BUTTON())) {
            sendClicked();
            clearTextLine();
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

    // interface KeyListener
    public void keyPressed(KeyEvent e) {
    }

    // interface KeyListener
    public void keyReleased(KeyEvent e) {
    }

    // interface KeyListener
    public void keyTyped(KeyEvent e) {
        Component c = (Component) e.getSource();

        if (c.getName().equals(data.getTEXT_LINE()))
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                sendClicked();
                clearTextLine();
            }

    }

    // interface ActionListener
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(data.getMenuEXIT()))
            data.getScreenCallback().exit();

        if (e.getActionCommand().equals(data.getMenuCONNECT()))
            data.getClientScreenFunctions().doConnect(data.getScreenCallback());

        if (e.getActionCommand().equals(data.getMenuLOGIN()))
            data.getClientScreenFunctions().doLogin(data.getScreenCallback(), this);

        if (e.getActionCommand().equals(data.getMenuREGISTER()))
            data.getClientScreenFunctions().doRegister(data.getScreenCallback(), this);

        if (e.getActionCommand().equals(data.getMenuMyDetails()))
            data.getClientScreenFunctions().doGetUserDetails("", flagShowMyDetails, data.getScreenCallback(), this);

        if (e.getActionCommand().equals(data.getMenuManageIgnoreList()))
            data.getClientScreenFunctions().doManageIgnoreList(data.getScreenCallback(), this);

        if (e.getActionCommand().equals(data.getMenuABOUT()))
            doShowAbout();
    }

    // interface ScreenCapables
    public void replyReceived(Message message) {

        // ReplyConnect
        if (message instanceof ReplyConnect) {
            ReplyConnect reply = (ReplyConnect) message;
            if (reply.Status == Net.OK) {
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Connection established with " + reply.ipport.IP + ":" + reply.ipport.Port, 0, data.getClientScreenFunctions().getF());
                data.getClientScreenFunctions().doLogin(data.getScreenCallback(), this);
            } else
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Failed to connect to the " + reply.ipport.IP + ":" + reply.ipport.Port, reply.Status, data.getClientScreenFunctions().getF());
        }

        // replyLogin
        if (message instanceof ReplyLogin) {
            ReplyLogin reply = (ReplyLogin) message;
            if (reply.Status == Net.OK) {
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Logged in as a " + reply.login.UserName, 0, data.getClientScreenFunctions().getF());
                data.getClientScreenFunctions().getF().setTitle(data.getClientScreenFunctions().getPROGRAM_TITLE() + " - " + data.getClientScreenFunctions().getSLastLoggedOnUserName());
                data.getClientScreenFunctions().doGetOnlineUsersList(data.getScreenCallback(), this);
            } else {
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Failed to login as a " + reply.login.UserName, reply.Status, data.getClientScreenFunctions().getF());
                data.getClientScreenFunctions().getF().setTitle(data.getClientScreenFunctions().getPROGRAM_TITLE());
            }
        }

        // replyRegister
        if (message instanceof ReplyRegister) {
            ReplyRegister reply = (ReplyRegister) message;
            if (reply.Status == Net.OK)
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Registered new user - " + reply.login.UserName, 0, data.getClientScreenFunctions().getF());
            else
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Failed to register new user " + reply.login.UserName, reply.Status, data.getClientScreenFunctions().getF());
        }

        // ReplyClientText
        if (message instanceof ReplyClientText) {
            ReplyClientText reply = (ReplyClientText) message;
            if (reply.Status == Net.OK)
                ;
            else
                data.getTabs().addTextToCurrentRoom("Failed to send text. " + Net.describeMessage(reply.Status), true);//setStatus(  ) ;
        }

        // replyGetOnlineUsersList
        if (message instanceof ReplyGetOnlineUsersList) {
            ReplyGetOnlineUsersList reply = (ReplyGetOnlineUsersList) message;
            if (reply.Status == Net.OK) {
                data.getList().removeAllUsers();
                for (int i = 0; i < reply.array.length; i++)
                    data.getList().addUser(reply.array[i], new UserDetails());
            }
        }

        // replyGetUserDetals
        if (message instanceof ReplyGetUserDetails) {
            ReplyGetUserDetails reply = (ReplyGetUserDetails) message;
            if (reply.Status == Net.OK) {
                switch (reply.InternalFlag) {
                    case flagShowMyDetails: {
                        showMyDetails(reply.details);
                        break; 
                    }
                    case flagShowUserDetails: {
                        displayUserDetails(reply.UserName, reply.details);
                        break; 
                    }
                }
            } else
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Failed to obtain my details from server", reply.Status, data.getClientScreenFunctions().getF());
        }

        // replySetUserDetails
        if (message instanceof ReplySetUserDetails) {
            ReplySetUserDetails reply = (ReplySetUserDetails) message;
            if (reply.Status == Net.OK)
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Details updates successfully", 0, data.getClientScreenFunctions().getF());
            else
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Failed to update details", reply.Status, data.getClientScreenFunctions().getF());
        }

        // replyGetUsersIgnoredByMe
        if (message instanceof ReplyGetUsersIgnoredByMe) {
            ReplyGetUsersIgnoredByMe reply = (ReplyGetUsersIgnoredByMe) message;
            if (reply.Status == Net.OK) {
                if (Dialogs.manageIgnoreUsersialog(reply.ignoredUsersList))
                    data.getClientScreenFunctions().doIgnoreUsers(reply.ignoredUsersList, true, data.getScreenCallback(), this);
            } else
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Failed to get users list ignored by me", reply.Status, data.getClientScreenFunctions().getF());
        }

        // replyIgnoreUsers
        if (message instanceof ReplyIgnoreUsers) {
            ReplyIgnoreUsers reply = (ReplyIgnoreUsers) message;
            if (reply.Status == Net.OK)
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Updated successfully ignore list", 0, data.getClientScreenFunctions().getF());
            else
                data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Failed to setup users list ignored by me", reply.Status, data.getClientScreenFunctions().getF());
        }

        // UPDATE USERS LIST ( Server command )
        if (message instanceof UpdateUsersList && !isEmpty(data.getClientScreenFunctions().getSLastLoggedOnUserName())) {
            UpdateUsersList serverCmd = (UpdateUsersList) message;
            doUpdateUsersList(serverCmd.UserName, serverCmd.What);
        }

   

        // SERVER TEXT
        if (message instanceof ServerText && !isEmpty(data.getClientScreenFunctions().getSLastLoggedOnUserName())) {
            ServerText serverCmd = (ServerText) message;
            doReceiveServerText(serverCmd.FromUser, serverCmd.IsPrivate, serverCmd.Text);
        }


    }

    // interface ScreenCapables
    public void connectionDown() {
        data.getClientScreenFunctions().setSLastLoggedOnUserName(null);
        data.getClientScreenFunctions().getF().setTitle(data.getClientScreenFunctions().getPROGRAM_TITLE());
        data.getClientScreenFunctions().getClientScreenBuildInit().setStatus("Disconnected from server", 0, data.getClientScreenFunctions().getF());
        data.getList().removeAllUsers();
    }

    // interface RoomActions
    public void closeRoomPressed(String aRoomName) {
    }

    // interface RoomActions
    public void ignoreUserPressed(String aRoomName) {
    }

    // interface RoomActions
    public boolean canCloseRoom(String aRoomName) {
        if (aRoomName.equals(data.getGENERAL_ROOM())) {
            if (JOptionPane.showConfirmDialog(null, "By clicking on the CLOSE button in the GENERAL ROOM you will exit from chat.\nExit from chat ?", "Exit chat dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == 0)
                data.getScreenCallback().exit();
            return false;
        }
        return true;
    }

    public void showUserDetails(String aUserName) {
        data.getClientScreenFunctions().doGetUserDetails(aUserName, flagShowUserDetails, data.getScreenCallback(), this);
    }

    public void userSelected(String aUserName) {
    }

    public void userDoubleSelected(String aUserName) {
        if (aUserName != null)
            if (!aUserName.equals(data.getClientScreenFunctions().getSLastLoggedOnUserName()))
                data.getTabs().openRoom(aUserName, false);
    }

    public void ignoreUser(String aUserName) {
        if (aUserName == null)
            return;

        Vector v = new Vector();
        v.addElement(aUserName);
        data.getClientScreenFunctions().doIgnoreUsers(v, false, data.getScreenCallback(), this);
    }
}
