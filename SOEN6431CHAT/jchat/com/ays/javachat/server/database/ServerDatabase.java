package com.ays.javachat.server.database;

import com.ays.javachat.common.datatypes.IgnoredUsers2;
import com.ays.javachat.common.datatypes.LoginData;
import com.ays.javachat.common.datatypes.UserDetails;
import com.ays.javachat.common.globalconsts.Net;
import com.ays.javachat.common.messages.ClientText;
import com.ays.javachat.common.messages.GetOnlineUsersList;
import com.ays.javachat.common.messages.GetUserDetails;
import com.ays.javachat.common.messages.GetUsersIgnoredByMe;
import com.ays.javachat.common.messages.IgnoreUsers;
import com.ays.javachat.common.messages.Login;
import com.ays.javachat.common.messages.Logout;
import com.ays.javachat.common.messages.Message;
import com.ays.javachat.common.messages.Register;
import com.ays.javachat.common.messages.ReplyClientText;
import com.ays.javachat.common.messages.ReplyGetOnlineUsersList;
import com.ays.javachat.common.messages.ReplyGetUserDetails;
import com.ays.javachat.common.messages.ReplyGetUsersIgnoredByMe;
import com.ays.javachat.common.messages.ReplyIgnoreUsers;
import com.ays.javachat.common.messages.ReplyLogin;
import com.ays.javachat.common.messages.ReplyLogout;
import com.ays.javachat.common.messages.ReplyRegister;
import com.ays.javachat.common.messages.ReplySetUserDetails;
import com.ays.javachat.common.messages.ServerText;
import com.ays.javachat.common.messages.SetUserDetails;
import com.ays.javachat.common.messages.UpdateUsersList;
import com.ays.javachat.server.interfaces.ServerDatabaseCapables;
import com.ays.javachat.server.manager.ServerManager;
import com.ays.javachat.server.transmitter.ServerTransmitter;

import java.io.*;
import java.util.Properties;
import java.util.Vector;


/**
 * Stores all user data. See ServerDatabaseCapables interface for more information *
 */
public class ServerDatabase implements ServerDatabaseCapables {
    private String UsersFileName = null;
    private String IgnoreFileName = null;
    private Properties p = new Properties();

    private final String DATA_DIR = "./DATA/";

    public ServerDatabase() {
    }

    private String getUsersFileName() {
        // is DATA_DIR exists ?
        File dataDir = new File(DATA_DIR);
        if (!dataDir.isDirectory()) {
            // trying to create it...
            try {
                if (!dataDir.mkdirs()) {
                    return UsersFileName;
                }
            } catch (Exception e) {
                return UsersFileName;
            }
        }

        return DATA_DIR + UsersFileName;
    }

    private String getIgnoreFileName() {
        return DATA_DIR + IgnoreFileName;
    }

    private void createFileIfNotExists(String aFileName) {
        File f = new File(aFileName);
        if (!f.exists()) {
            try {
                FileOutputStream stream = new FileOutputStream(aFileName);
                stream.close();
            }
            catch (Exception e) {

            }
        }
    }


    public void setDatabaseParams(String aParams[]) {
        if (aParams != null) {
            if (aParams.length >= 1)
                UsersFileName = aParams[0];
            if (aParams.length >= 2)
                IgnoreFileName = aParams[1];
        }
    }

    public int checkUserNamePassword(LoginData aLogin) {
        try {
            if (!aLogin.isDataValid())
                return Net.INVALID_USERNAME_PASS;

            createFileIfNotExists(getUsersFileName());

            p.load(new FileInputStream(getUsersFileName()));
            String s = p.getProperty(aLogin.UserName);
            if (s == null)
                return Net.NO_SUCH_USER;
            if (s.equals(aLogin.Password))
                return Net.OK;
            else
                return Net.BAD_PASSWORD;
        }
        catch (Exception e) {
            System.out.print(e);
            return Net.INTERNAL_ERROR;
        }
    }

    public int changePassword(LoginData aLogin) {
        if (!aLogin.isDataValid())
            return Net.INVALID_USERNAME_PASS;

        createFileIfNotExists(getUsersFileName());

        try {
            p.load(new FileInputStream(getUsersFileName()));
            String s = p.getProperty(aLogin.UserName);
            if (s == null)
                return Net.USER_DOESNT_EXISTS;

            p.setProperty(aLogin.UserName, aLogin.Password);
            p.store(new FileOutputStream(getUsersFileName()), null);

            return Net.OK;
        }
        catch (Exception e) {
            return Net.INTERNAL_ERROR;
        }
    }

    public String getPassword(String aUserName) {
        try {
            createFileIfNotExists(getUsersFileName());

            p.load(new FileInputStream(getUsersFileName()));
            return p.getProperty(aUserName);
        }
        catch (Exception e) {
            System.out.print(e);
            return "";
        }
    }

    public int addUser(LoginData aLogin, UserDetails aDetails) {
        try {
            if (!aLogin.isDataValid())
                return Net.INVALID_USERNAME_PASS;
            if (!aDetails.isDataValid())
                return Net.BAD_USER_DETAILS;

            createFileIfNotExists(getUsersFileName());

            int iResult = updateUserDetails(aLogin.UserName, aDetails);
            if (iResult != Net.OK)
                return iResult;

            p.load(new FileInputStream(getUsersFileName()));
            String s = p.getProperty(aLogin.UserName);
            if (s != null)
                return Net.USER_ALREADY_EXISTS;

            p.setProperty(aLogin.UserName, aLogin.Password);
            p.store(new FileOutputStream(getUsersFileName()), null);

            return Net.OK;
        }
        catch (Exception e) {
            return Net.INTERNAL_ERROR;
        }
    }

    public int deleteUser(String aUserName) {
        return Net.OK;
    }

    public int updateUserDetails(String aUserName, UserDetails aDetails) {
        try {
            FileOutputStream fileStream = new FileOutputStream(DATA_DIR + aUserName);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(aDetails);
            objectStream.flush();
            objectStream.close();
        }
        catch (Exception e) {
            return Net.INTERNAL_ERROR;
        }
        return Net.OK;
    }

    public int getUserDetails(String aUserName, UserDetails aUserDetails) {
        try {
            FileInputStream fileStream = new FileInputStream(DATA_DIR + aUserName);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            UserDetails d = (UserDetails) objectStream.readObject();
            aUserDetails.copy(d);
            objectStream.close();
        }
        catch (Exception e) {
            return Net.INTERNAL_ERROR;
        }
        return Net.OK;
    }

    // must be synch !!!!
    public synchronized int setIgnoreUsersList(String aUserName, Vector aUsersList, boolean aOverwriteExistingList) {

        if (aUsersList == null)
            return Net.OK;

        IgnoredUsers2 ignoredUsers;

        createFileIfNotExists(getIgnoreFileName());

        // trying to load existing data
        try {
            FileInputStream fileStream = new FileInputStream(getIgnoreFileName());
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            ignoredUsers = (IgnoredUsers2) objectStream.readObject();
            objectStream.close();
        }
        catch (Exception e) {
            // no data. creating new data object ( kotoriy doljen bil zagruzitsa if file )
            ignoredUsers = new IgnoredUsers2();
        }

        // getting users which places to the ignore list
        Vector v = (Vector) ignoredUsers.users.get(aUserName);

        if (v == null) { // ignore list is empty : creating
            v = new Vector();
            ignoredUsers.users.put(aUserName, v); // adding
        } else if (aOverwriteExistingList)
            v.clear();

        // copying ignore list from the source message
        boolean b;
        int j;
        for (int i = 0; i < aUsersList.size(); i++) {
            b = false;
            for (j = 0; j < v.size(); j++)
                if (v.get(j).equals(aUsersList.get(i))) { // checking for dublicate values
                    b = true;
                    break; ////
                }

            if (!b) // if there are no dublicates - adding
                v.add(aUsersList.get(i));
        }

        // now saving...
        try {
            FileOutputStream fileStream = new FileOutputStream(getIgnoreFileName());
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(ignoredUsers);
            objectStream.flush();
            objectStream.close();
        }
        catch (Exception e) {
            return Net.INTERNAL_ERROR;
        }

        return Net.OK;
    }

    // must be synch !!!!
    // vector object must created before
    public int getIgnoreUsersList(String aUserName, Vector aUsersList) {

        if (aUsersList == null)
            return Net.OK;

        aUsersList.clear();

        IgnoredUsers2 ignoredUsers;

        try {
            ignoredUsers = null;
            FileInputStream fileStream = new FileInputStream(getIgnoreFileName());
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            ignoredUsers = (IgnoredUsers2) objectStream.readObject();
            objectStream.close();
        }
        catch (Exception e) {
            ignoredUsers = new IgnoredUsers2();
        }

        Vector v = (Vector) ignoredUsers.users.get(aUserName);

        if (v == null)
            return Net.OK;

        for (int i = 0; i < v.size(); i++)
            aUsersList.add(v.get(i));

        return Net.OK;
    }

	public void processReceivedObject(ServerManager serverManager, ServerTransmitter t, Message aMsg) {
	    // LOGIN
	    if (aMsg instanceof Login) {
	        Login msg = (Login) aMsg;
	
	        serverManager.report("Someone from IP " + t.getIP() + ":" + t.getPort() + " trying to login as " + msg.login.UserName);
	
	        ReplyLogin reply = new ReplyLogin(msg.login, Net.OK);
	
	        // checking if this user was logged in...
	        String s = t.getUserName();
	        if (t.getUserName() != null) {
	            reply.Status = Net.NOT_LOGGEDOUT;
	            t.sendObject(reply);
	            return;
	        }
	
	        // checking if user another user logged in with this user name
	        ServerTransmitter transmitter;
	        for (int i = 0; i < serverManager.clients.size(); i++) {
	            transmitter = (ServerTransmitter) serverManager.clients.elementAt(i);
	            if (transmitter.getUserName() != null)
	                if (transmitter.getUserName().equals(msg.login.UserName)) {
	                    reply.Status = Net.SOMEONE_LOGGEDIN_ASIT;
	                    t.sendObject(reply);
	                    serverManager.report("Someone from IP " + transmitter.getIP() + ":" + transmitter.getPort() + " already logged in as " + transmitter.getUserName());
	                    return;
	                }
	        }
	
	        t.setUserName(null);
	
	        reply.Status = checkUserNamePassword(msg.login);
	        if (reply.Status != Net.OK) {
	            t.sendObject(reply);
	            serverManager.report("Not passed login/password validation");
	            return;
	        }
	
	        // user successfully logged in
	        t.setUserName(msg.login.UserName);
	        t.sendObject(reply);
	
	        serverManager.report("Logged in successfully");
	
	        // notifying all clients about new login
	        UpdateUsersList updateUsersList = new UpdateUsersList(t.getUserName(), Net.USER_JOINED);
	        for (int i = 0; i < serverManager.clients.size(); i++) {
	            transmitter = (ServerTransmitter) serverManager.clients.elementAt(i);
	            if (transmitter.getUserName() != null)
	                if (!transmitter.getUserName().equals(t.getUserName()))
	                    transmitter.sendObject(updateUsersList);
	        }
	
	
	    }
	
	    // LOGOUT
	    if (aMsg instanceof Logout) {
	    
	
	        ReplyLogout reply = new ReplyLogout(Net.OK);
	
	        if (t.getUserName() == null) {
	            reply.Status = Net.NOT_LOGGED_IN;
	            t.sendObject(reply);
	            return;
	        }
	
	        serverManager.report("User " + t.getUserName() + " from IP " + t.getIP() + ":" + t.getPort() + " logging out");
	
	        // notifying all clients about user logout
	        ServerTransmitter transmitter;
	        UpdateUsersList updateUsersList = new UpdateUsersList(t.getUserName(), Net.USER_LEFT);
	        for (int i = 0; i < serverManager.clients.size(); i++) {
	            transmitter = (ServerTransmitter) serverManager.clients.elementAt(i);
	            if (transmitter.getUserName() != null)
	                transmitter.sendObject(updateUsersList);
	        }
	
	        t.setUserName(null);
	        t.sendObject(reply);
	    }
	
	    // REGISTER
	    if (aMsg instanceof Register) {
	        Register msg = (Register) aMsg;
	        int iStatus;
	
	        serverManager.report("Someone from IP " + t.getIP() + ":" + t.getPort() + " trying to register user " + msg.login.UserName);
	
	        // checking for data
	        iStatus = addUser(msg.login, msg.details);
	
	        serverManager.report("Register status : " + Net.describeMessage(iStatus));
	
	        ReplyRegister reply = new ReplyRegister(msg.login, msg.details, iStatus);
	        t.sendObject(reply);
	    }
	
	    // GETUSERDETAILS
	    if (aMsg instanceof GetUserDetails) {
	        GetUserDetails msg = (GetUserDetails) aMsg;
	
	        int iStatus;
	        UserDetails details = new UserDetails();
	
	        // if msg.UserName is null -> returnin details of the current user
	        String sUserName = t.getUserName();
	        if (msg.UserName != null)
	            if (!msg.UserName.equals(""))
	                sUserName = msg.UserName;
	
	
	        if (t.getUserName() == null)
	            iStatus = Net.NOT_LOGGED_IN;
	        else
	            iStatus = getUserDetails(sUserName, details);
	
	        serverManager.report("User " + t.getUserName() + " from IP " + t.getIP() + ":" + t.getPort() + " with user name " + t.getUserName() + " trying to get details of user " + msg.UserName);
	
	        serverManager.report("GetUserDetails status : " + Net.describeMessage(iStatus));
	
	        ReplyGetUserDetails reply = new ReplyGetUserDetails(sUserName, details, iStatus);
	        reply.InternalFlag = msg.InternalFlag;
	        t.sendObject(reply);
	    }
	
	    // SETUSERDETAILS
	    if (aMsg instanceof SetUserDetails) {
	        SetUserDetails msg = (SetUserDetails) aMsg;
	
	        ReplySetUserDetails reply = new ReplySetUserDetails(msg.details, Net.OK);
	
	        if (t.getUserName() == null) {
	            reply.Status = Net.NOT_LOGGED_IN;
	            t.sendObject(reply);
	            return;
	        }
	
	        serverManager.report("User " + t.getUserName() + " from IP " + t.getIP() + ":" + t.getPort() + " with user name " + t.getUserName() + " trying to update his details");
	
	        if (!getPassword(t.getUserName()).equals(msg.Password)) {
	            reply.Status = Net.BAD_PASSWORD;
	            t.sendObject(reply);
	            return;
	        }
	
	        reply.Status = updateUserDetails(t.getUserName(), msg.details);
	
	        serverManager.report("Update status : " + Net.describeMessage(reply.Status));
	
	        t.sendObject(reply);
	    }
	
	    // GET ONLINE USERS LIST
	    if (aMsg instanceof GetOnlineUsersList) {
	        GetOnlineUsersList msg = (GetOnlineUsersList) aMsg;
	
	        ReplyGetOnlineUsersList reply = new ReplyGetOnlineUsersList(Net.OK);
	
	        if (t.getUserName() == null) {
	            reply.Status = Net.NOT_LOGGED_IN;
	            t.sendObject(reply);
	            return;
	        }
	
	        serverManager.report("User " + t.getUserName() + " from IP " + t.getIP() + ":" + t.getPort() + " with user name " + t.getUserName() + " trying to get OnlineUsersList");
	
	        int k = serverManager.fillOnlineUserList(reply);
			t.sendObject(reply);
	        serverManager.report("GetOnlieUsersList status : " + Net.describeMessage(reply.Status));
	    }
	
	    // ClientText
	    if (aMsg instanceof ClientText) {
	        ClientText msg = (ClientText) aMsg;
	
	        ReplyClientText reply = new ReplyClientText(t.getUserName(), msg.Text, 0);
	
	        if (t.getUserName() == null) {
	            reply.Status = Net.NOT_LOGGED_IN;
	            t.sendObject(reply);
	            serverManager.report("Unknown user from IP " + t.getIP() + ":" + t.getPort() + " sent text : " + msg.Text);
	            return;
	        }
	
	        String s = " to user " + msg.UserName;
	        if (msg.UserName == null)
	            s = " to the general room";
	        serverManager.report("User " + t.getUserName() + " from IP " + t.getIP() + ":" + t.getPort() + " sent text : " + msg.Text + s);
	
	        ServerTransmitter transmitter;
	        ServerText st = new ServerText();
	        st.FromUser = t.getUserName();
	        st.Text = msg.Text;
	        if (msg.UserName == null) { // sending text to general room for all clients
	            st.IsPrivate = false;
	            for (int i = 0; i < serverManager.clients.size(); i++) {
	                transmitter = (ServerTransmitter) (serverManager.clients.elementAt(i));
	                if (transmitter.getUserName() != null)
	                    if ((!transmitter.getUserName().equals(t.getUserName())) &&
	                            (t.getUserName() != null))
	                        transmitter.sendObject(st);
	            }
	            reply.Status = Net.OK;
	            t.sendObject(reply);
	        } else { // sending message to the one user ( private )
	            st.IsPrivate = true;
	            // searching for user...
	            boolean bUserFound = false;
	            for (int i = 0; i < serverManager.clients.size(); i++) {
	                transmitter = (ServerTransmitter) serverManager.clients.elementAt(i);
	                if (transmitter.getUserName().equals(msg.UserName)) {
	                    // checking if your wants to speak with you
	                    if (serverManager.serverDatabase.isUserIgnored(transmitter.getUserName(), t.getUserName())) {
	                        reply.Status = Net.USER_DONTWANT_TOTALK;
	                        t.sendObject(reply);
	                        serverManager.report("Text wasn't sent to the user. Reason : inogred");
	                        return;
	                    }
	                    // sending text...
	                    bUserFound = true;
	                    reply.Status = transmitter.sendObject(st);
	                    t.sendObject(reply);
	                    break;
	                }
	            }
	            if (!bUserFound) {
	                reply.Status = Net.NO_SUCH_USER;
	                serverManager.report("Text wasn't sent to the user. Reason : tarhet user is offline or doesn't exists");
	                t.sendObject(reply);
	            }
	
	        }
	
	    }
	
	    // GetUsersIgnoredByMe
	    if (aMsg instanceof GetUsersIgnoredByMe) {
	        GetUsersIgnoredByMe msg = (GetUsersIgnoredByMe) aMsg;
	
	        ReplyGetUsersIgnoredByMe reply;
	        if (t.getUserName() == null) {
	            reply = new ReplyGetUsersIgnoredByMe(null, Net.NOT_LOGGED_IN);
	            t.sendObject(reply);
	            return;
	        }
	
	        serverManager.report("User " + t.getUserName() + " from IP " + t.getIP() + ":" + t.getPort() + " trying to ignore list");
	
	        Vector ignoredUsersList = new Vector();
	        int iStatus = getIgnoreUsersList(t.getUserName(), ignoredUsersList);
	
	        reply = new ReplyGetUsersIgnoredByMe(ignoredUsersList, iStatus);
	        t.sendObject(reply);
	    }
	
	    // IgnoreUsers
	    if (aMsg instanceof IgnoreUsers) {
	        IgnoreUsers msg = (IgnoreUsers) aMsg;
	
	        ReplyIgnoreUsers reply;
	
	        if (t.getUserName() == null) {
	            reply = new ReplyIgnoreUsers(msg.ignoredUsersList, msg.OverwriteExistingList, Net.NOT_LOGGED_IN);
	            t.sendObject(reply);
	            return;
	        }
	
	        serverManager.report("User " + t.getUserName() + " from IP " + t.getIP() + ":" + t.getPort() + " trying to ignore some another user(s)");
	
	        int iStatus = setIgnoreUsersList(t.getUserName(), msg.ignoredUsersList, msg.OverwriteExistingList);
	
	        reply = new ReplyIgnoreUsers(msg.ignoredUsersList, msg.OverwriteExistingList, iStatus);
	        t.sendObject(reply);
	    }
	}

	public boolean isUserIgnored(String aReceiver, String aSender) {
	    Vector v = new Vector();
	    getIgnoreUsersList(aReceiver, v);
	    for (int i = 0; i < v.size(); i++)
	        if (v.get(i).equals(aSender))
	            return true;
	
	    return false;
	}
}
