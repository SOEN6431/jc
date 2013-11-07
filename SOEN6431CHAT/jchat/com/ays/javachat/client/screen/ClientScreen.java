package com.ays.javachat.client.screen;

import com.ays.javachat.client.interfaces.ScreenCallback;
import com.ays.javachat.client.interfaces.ScreenCapables;
import com.ays.javachat.common.datatypes.IPPort;
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
    // program title
    private final String PROGRAM_TITLE = "Chat client 1.1";

    private final String GENERAL_ROOM = "General room";

    // window pos & size
    private final Dimension WINDOW_SIZE = new Dimension(800, 600);
    private final Point WINDOW_POS = new Point(120, 80);

    // Component names
    private final String SEND_BUTTON = "Send Text Button";
    private final String TEXT_LINE = "Send Text Line";


    // menu constants
    private final String menuCONNECT = "Connect";
    private final String menuLOGIN = "Login";
    private final String menuEXIT = "Exit";
    private final String menuOPTIONS = "Options";
    private final String menuABOUT = "About";
    private final String menuREGISTER = "Register";
    private final String menuUNREGISTER = "UnRegister";
    private final String menuMyDetails = "View/Change my Details";
    private final String menuManageIgnoreList = "Manage Ignore List";
    private final String menuAbout = "About";


    // data exchange flags
    private final int flagShowUserDetails = 100; // Mesasge.InternalFlag, if == 100, means that client needs to show user details
    private final int flagUpdateUserDetails = 101; // Message.InternalFlag. if == 101,  means that someone updates his details
    private final int flagShowMyDetails = 102; // Mesasge.InternalFlag. if == 102, means that i need to get my details and view it


    // object from ScreenCapables interface
    private ScreenCallback screenCallback;


    //
    String sLastLoggedOnUserName = null;


    // GUI controls
    JLabel rooms = new JLabel("     Rooms");
    JLabel peoples = new JLabel("     Online users");
    JLabel typeyourmessage = new JLabel("     Type your message here");
    Rooms tabs = new Rooms(this);
    OnlineList list = new OnlineList(this);
    JTextField tf = new JTextField();
    JButton send = new JButton("Send");
    JLabel statusBarLabel = new JLabel("Ready");
    JFrame f = new JFrame();
    JPanel MainPanel = new JPanel(new GridBagLayout());
    JPopupMenu popupMenu = new JPopupMenu();


    ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR FUNCTIONS
    ///////////////////////////////////////////////////////////////////////////
    public ClientScreen(ScreenCallback aScreenCallback) {
        screenCallback = aScreenCallback;

        if (screenCallback == null)
            return;

    }

    /**
     * Shows and inits main frame *
     */
    public void start() {
        show();
        init_tabs();
      //  init_list();

        doConnect();
    }

    private void init_tabs() {
        tabs.openRoom(GENERAL_ROOM, false);
    }

   // private void init_list() {
        //list.addUser( "a", new UserDetails() ) ;
        //list.removeAll() ;

        //UsersList users = new UsersList() ;
    //}


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

    private JPanel getFlowPanel() {
        JPanel p = new JPanel();
        FlowLayout fl = new FlowLayout();
        fl.setVgap(5);
        fl.setHgap(5);
        p.setLayout(fl);
        return p;
    }

    private void buildScreen() {
        statusBarLabel.setOpaque(true);

        GridBagConstraints c = new GridBagConstraints();

        JPanel p;

        c.fill = GridBagConstraints.BOTH;

        c.weightx = 0.8;
        c.weighty = 0.8;
        c.gridwidth = GridBagConstraints.RELATIVE;
        p = getBorderPanel();
        p.add(tabs, BorderLayout.CENTER);
        p.add(rooms, BorderLayout.NORTH);
        MainPanel.add(p, c);

        c.weightx = 0.2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        p = getBorderPanel();
        p.add(list);
        p.add(peoples, BorderLayout.NORTH);
        MainPanel.add(p, c);

        c.weightx = 0.8;
        c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.RELATIVE;
        p = getBorderPanel();
        p.add(tf);
        p.add(typeyourmessage, BorderLayout.NORTH);
        MainPanel.add(p, c);

        c.weightx = 0.2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        p = getBorderPanel();
        p.add(send);
        MainPanel.add(p, c);

        send.setToolTipText("Send message");
    }

    private void buildMenu() {
        // menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem menuItem;

        f.setJMenuBar(menuBar);

        // File
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem = new JMenuItem(menuCONNECT);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem(menuLOGIN);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem(menuREGISTER);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /*
        menuItem = new JMenuItem( menuUNREGISTER ) ;
        menuItem.addActionListener( this ) ;
        menu.add( menuItem ) ;
        */
        menu.addSeparator();
        menuItem = new JMenuItem(menuEXIT);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // View
        menu = new JMenu("View");
        menuBar.add(menu);
        /*
        menuItem = new JMenuItem( menuOPTIONS ) ;
        menuItem.addActionListener( this ) ;
        menu.add( menuItem ) ;
        */
        menuItem = new JMenuItem(menuMyDetails);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem(menuManageIgnoreList);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // Help
        menu = new JMenu("Help");
        menuBar.add(menu);
        menuItem = new JMenuItem(menuABOUT);
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }


    private void show() {
        initComponents();
        buildScreen();
        buildMenu();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(new JPanel(), BorderLayout.NORTH);
        f.getContentPane().add(new JPanel(), BorderLayout.WEST);
        f.getContentPane().add(statusBarLabel, BorderLayout.SOUTH);
        f.getContentPane().add(new JPanel(), BorderLayout.EAST);
        f.getContentPane().add(MainPanel, BorderLayout.CENTER);
        f.setTitle(PROGRAM_TITLE);
        f.setLocation(WINDOW_POS);
        f.setSize(WINDOW_SIZE);
        f.setVisible(true);
    }


    // this func adds listeners to the compomnents, setted up names, blablabla...
    private void initComponents() {
        send.setName(SEND_BUTTON);
        send.addMouseListener(this);
        send.setFocusable(false);

        tf.setName(TEXT_LINE);
        tf.addKeyListener(this);
    }


    ///////////////////////////////////////////////////////////////////////////
    // misc functions
    ///////////////////////////////////////////////////////////////////////////
    private void doConnect() {
        IPPort ipport = new IPPort();
        if (Dialogs.connectDialog(ipport)) {
            ipport.save();
            setStatus("Connecting to the " + ipport.IP + ":" + ipport.Port, 0);
            screenCallback.connect(ipport);
        }
    }

    private void doLogin() {
        LoginData login = new LoginData("");
        if (Dialogs.loginDialog(login)) {
            setStatus("Trying to log in as a " + login.UserName, 0);
            if (sLastLoggedOnUserName != null)
                doLogout();
            sLastLoggedOnUserName = login.UserName;

            Login req = new Login(login);
            int iStatus = screenCallback.sendRequest(req);

            ReplyLogin reply;
            if (iStatus != Net.OK) {
                reply = new ReplyLogin(login, iStatus);
                replyReceived(reply);
            }
        }
    }

    private void doLogout() {
        sLastLoggedOnUserName = null;
        f.setTitle(PROGRAM_TITLE);
        Logout req = new Logout();
        int iStatus = screenCallback.sendRequest(req);

        ReplyLogout reply;
        if (iStatus != Net.OK) {
            reply = new ReplyLogout(iStatus);
            replyReceived(reply);
        }
    }

    private void doRegister() {
        LoginData login = new LoginData("");
        UserDetails details = new UserDetails();
        if (Dialogs.registerDialog(login, details, false)) {
            setStatus("Registering new user - " + login.UserName, 0);

            Register req = new Register(login, details);
            int iStatus = screenCallback.sendRequest(req);

            ReplyRegister reply;
            if (iStatus != Net.OK) {
                reply = new ReplyRegister(login, details, iStatus);
                replyReceived(reply);
            }
        }
    }

    private void doUnregister() {
        UnRegister req = new UnRegister();
        int iStatus = screenCallback.sendRequest(req);

        ReplyUnregister reply;
        if (iStatus != Net.OK) {
            reply = new ReplyUnregister(iStatus);
            replyReceived(reply);
        }
    }

    private void doGetUserDetails(String aUserName, int aFlag) {
        GetUserDetails req = new GetUserDetails(aUserName);
        req.InternalFlag = aFlag;
        int iStatus = screenCallback.sendRequest(req);

        ReplyGetUserDetails reply;
        if (iStatus != Net.OK) {
            reply = new ReplyGetUserDetails(aUserName, null, iStatus);
            replyReceived(reply);
        }
    }

    private void doSetUserDetails(UserDetails details, String aPassword) {
        SetUserDetails req = new SetUserDetails(details);
        req.Password = aPassword;
        int iStatus = screenCallback.sendRequest(req);

        ReplySetUserDetails reply;
        if (iStatus != Net.OK) {
            reply = new ReplySetUserDetails(details, iStatus);
            replyReceived(reply);
        }
    }

    private void doGetOnlineUsersList() {
        GetOnlineUsersList req = new GetOnlineUsersList();
        int iStatus = screenCallback.sendRequest(req);

        ReplyGetOnlineUsersList reply;
        if (iStatus != Net.OK) {
            reply = new ReplyGetOnlineUsersList(iStatus);
            replyReceived(reply);
        }
    }

    private void doShowAbout() {
        Dialogs.aboutDialog();
    }


    private void doSendText(String aUserName, String aText) {
        ClientText req = new ClientText(aUserName, aText);
        int iStatus = screenCallback.sendRequest(req);

        ReplyClientText reply;
        if (iStatus != Net.OK) {
            reply = new ReplyClientText(aUserName, aText, iStatus);
            replyReceived(reply);
        }
    }

    private void doManageIgnoreList() {
        GetUsersIgnoredByMe req = new GetUsersIgnoredByMe();
        int iStatus = screenCallback.sendRequest(req);

        ReplyGetUsersIgnoredByMe reply;
        if (iStatus != Net.OK) {
            reply = new ReplyGetUsersIgnoredByMe(null, iStatus);
            replyReceived(reply);
        }
    }

    private void doIgnoreUsers(Vector aIgnoredUsersList, boolean aOverwriteExistingList) {
        IgnoreUsers req = new IgnoreUsers(aIgnoredUsersList, aOverwriteExistingList);
        int iStatus = screenCallback.sendRequest(req);

        ReplyIgnoreUsers reply;
        if (iStatus != Net.OK) {
            reply = new ReplyIgnoreUsers(aIgnoredUsersList, aOverwriteExistingList, iStatus);
            replyReceived(reply);
        }
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
                list.addUser(aUserName, details);
                tabs.addTextToRoom(GENERAL_ROOM, "JOINED - " + aUserName, true);
                break;
            }

            // user left
            case Net.USER_LEFT: {
                list.removeUser(aUserName);
                tabs.addTextToRoom(GENERAL_ROOM, "LEFT - " + aUserName, true);
                if (tabs.isRoomOpened(aUserName))
                    tabs.addTextToRoom(aUserName, "USER LEFT", true);
                break;
            }

            // user updated his info
            case Net.USER_HASUPDATED_HISINFO: {
                doGetUserDetails(aUserName, flagUpdateUserDetails);
                break;
            }

        }
    }


    private void doReceiveServerText(String aFromUserName, boolean bIsPrivate, String aText) {
        if (!bIsPrivate)
            tabs.addTextToRoom(GENERAL_ROOM, aFromUserName + " : " + aText, false);
        else
            tabs.addTextToRoom(aFromUserName, aFromUserName + " : " + aText, false);
    }


    private void sendClicked() { // is a result of clicking on the SEND button
        String s = tf.getText();

        if (s == null)
            return;

        if (s.length() < 1)
            return;

        String text = sLastLoggedOnUserName;
        if (isEmpty(text)) {
            text = "noname";
        }
        text += " : " + tf.getText();
        tabs.addTextToCurrentRoom(text, false);

        String sUser = tabs.getCurrentRoomName();
        if (sUser.equals(GENERAL_ROOM))
            sUser = null;

        doSendText(sUser, s);
    }

    private void clearTextLine() {
        tf.setText("");
    }

    // this function report action described in the aStatus in the status bar
    private void setStatus(String aStatus, int aErrorCode) {

        String s = "";
        if (aErrorCode != Net.OK)
            s = aStatus + ". Reason : " + Net.describeMessage(aErrorCode);
        else
            s = aStatus + "...";

        if (aErrorCode != Net.OK) {
            statusBarLabel.setForeground(Color.green);
            statusBarLabel.setBackground(Color.red);
        } else {
            statusBarLabel.setForeground(Color.black);
            statusBarLabel.setBackground(f.getBackground());
        }

        statusBarLabel.setText(s);
    }


    private void showMyDetails(UserDetails aUserDetails) {
        if (sLastLoggedOnUserName == null)
            return;

        LoginData login = new LoginData(sLastLoggedOnUserName);
        if (Dialogs.registerDialog(login, aUserDetails, true))
            doSetUserDetails(aUserDetails, login.Password);
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
        if (s.equals(SEND_BUTTON)) {
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

        if (c.getName().equals(TEXT_LINE))
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                sendClicked();
                clearTextLine();
            }

    }


    // interface ActionListener
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(menuEXIT))
            screenCallback.exit();

        if (e.getActionCommand().equals(menuOPTIONS))
            ;

        if (e.getActionCommand().equals(menuCONNECT))
            doConnect();

        if (e.getActionCommand().equals(menuLOGIN))
            doLogin();

        if (e.getActionCommand().equals(menuREGISTER))
            doRegister();

        if (e.getActionCommand().equals(menuMyDetails))
            doGetUserDetails("", flagShowMyDetails);

        if (e.getActionCommand().equals(menuManageIgnoreList))
            doManageIgnoreList();

        if (e.getActionCommand().equals(menuABOUT))
            doShowAbout();
    }


    // interface ScreenCapables
    public void replyReceived(Message message) {

        // ReplyConnect
        if (message instanceof ReplyConnect) {
            ReplyConnect reply = (ReplyConnect) message;
            if (reply.Status == Net.OK) {
                setStatus("Connection established with " + reply.ipport.IP + ":" + reply.ipport.Port, 0);
                doLogin();
            } else
                setStatus("Failed to connect to the " + reply.ipport.IP + ":" + reply.ipport.Port, reply.Status);
        }

        // replyLogin
        if (message instanceof ReplyLogin) {
            ReplyLogin reply = (ReplyLogin) message;
            if (reply.Status == Net.OK) {
                setStatus("Logged in as a " + reply.login.UserName, 0);
                f.setTitle(PROGRAM_TITLE + " - " + sLastLoggedOnUserName);
                doGetOnlineUsersList();
            } else {
                setStatus("Failed to login as a " + reply.login.UserName, reply.Status);
                f.setTitle(PROGRAM_TITLE);
            }
        }

        // replyRegister
        if (message instanceof ReplyRegister) {
            ReplyRegister reply = (ReplyRegister) message;
            if (reply.Status == Net.OK)
                setStatus("Registered new user - " + reply.login.UserName, 0);
            else
                setStatus("Failed to register new user " + reply.login.UserName, reply.Status);
        }

        // ReplyClientText
        if (message instanceof ReplyClientText) {
            ReplyClientText reply = (ReplyClientText) message;
            if (reply.Status == Net.OK)
                ;
            else
                tabs.addTextToCurrentRoom("Failed to send text. " + Net.describeMessage(reply.Status), true);//setStatus(  ) ;
        }

        // replyGetOnlineUsersList
        if (message instanceof ReplyGetOnlineUsersList) {
            ReplyGetOnlineUsersList reply = (ReplyGetOnlineUsersList) message;
            if (reply.Status == Net.OK) {
                list.removeAllUsers();
                for (int i = 0; i < reply.array.length; i++)
                    list.addUser(reply.array[i], new UserDetails());
            }
        }

        // replyGetUserDetals
        if (message instanceof ReplyGetUserDetails) {
            ReplyGetUserDetails reply = (ReplyGetUserDetails) message;
            if (reply.Status == Net.OK) {
                switch (reply.InternalFlag) {
                    case flagShowMyDetails: {
                        showMyDetails(reply.details);
                        break; //bbbbbbbbbbbbbbbbbbbbbbbbbbb
                    }
                    case flagShowUserDetails: {
                        displayUserDetails(reply.UserName, reply.details);
                        break; //bbbbbbbbbbbbbbbbbbbbbbbbbbbb
                    }
                }
            } else
                setStatus("Failed to obtain my details from server", reply.Status);
        }

        // replySetUserDetails
        if (message instanceof ReplySetUserDetails) {
            ReplySetUserDetails reply = (ReplySetUserDetails) message;
            if (reply.Status == Net.OK)
                setStatus("Details updates successfully", 0);
            else
                setStatus("Failed to update details", reply.Status);
        }

        // replyGetUsersIgnoredByMe
        if (message instanceof ReplyGetUsersIgnoredByMe) {
            ReplyGetUsersIgnoredByMe reply = (ReplyGetUsersIgnoredByMe) message;
            if (reply.Status == Net.OK) {
                if (Dialogs.manageIgnoreUsersialog(reply.ignoredUsersList))
                    doIgnoreUsers(reply.ignoredUsersList, true);
            } else
                setStatus("Failed to get users list ignored by me", reply.Status);
        }

        // replyIgnoreUsers
        if (message instanceof ReplyIgnoreUsers) {
            ReplyIgnoreUsers reply = (ReplyIgnoreUsers) message;
            if (reply.Status == Net.OK)
                setStatus("Updated successfully ignore list", 0);
            else
                setStatus("Failed to setup users list ignored by me", reply.Status);
        }

        // UPDATE USERS LIST ( Server command )
        if (message instanceof UpdateUsersList && !isEmpty(sLastLoggedOnUserName)) {
            UpdateUsersList serverCmd = (UpdateUsersList) message;
            doUpdateUsersList(serverCmd.UserName, serverCmd.What);
        }

        // SERVER NOTIFICATION
        if (message instanceof ServerNotification && !isEmpty(sLastLoggedOnUserName)) {
            ServerNotification serverCmd = (ServerNotification) message;
        }

        // SERVER TEXT
        if (message instanceof ServerText && !isEmpty(sLastLoggedOnUserName)) {
            ServerText serverCmd = (ServerText) message;
            doReceiveServerText(serverCmd.FromUser, serverCmd.IsPrivate, serverCmd.Text);
        }


    }

    // interface ScreenCapables
    public void connectionDown() {
        sLastLoggedOnUserName = null;
        f.setTitle(PROGRAM_TITLE);
        setStatus("Disconnected from server", 0);
        list.removeAllUsers();
    }

    // interface RoomActions
    public void closeRoomPressed(String aRoomName) {
    }

    // interface RoomActions
    public void ignoreUserPressed(String aRoomName) {
    }

    // interface RoomActions
    public boolean canCloseRoom(String aRoomName) {
        if (aRoomName.equals(GENERAL_ROOM)) {
            if (JOptionPane.showConfirmDialog(null, "By clicking on the CLOSE button in the GENERAL ROOM you will exit from chat.\nExit from chat ?", "Exit chat dialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == 0)
                screenCallback.exit();
            return false;
        }
        return true;
    }

    public void showUserDetails(String aUserName) {
        doGetUserDetails(aUserName, flagShowUserDetails);
    }

    public void userSelected(String aUserName) {
    }

    public void userDoubleSelected(String aUserName) {
        if (aUserName != null)
            if (!aUserName.equals(sLastLoggedOnUserName))
                tabs.openRoom(aUserName, false);
    }

    public void ignoreUser(String aUserName) {
        if (aUserName == null)
            return;

        Vector v = new Vector();
        v.addElement(aUserName);
        doIgnoreUsers(v, false);
    }
}
