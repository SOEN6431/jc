package com.ays.javachat.client.screen;


import javax.swing.JFrame;
import com.ays.javachat.common.datatypes.LoginData;
import com.ays.javachat.common.messages.Login;
import com.ays.javachat.common.messages.ReplyLogin;
import com.ays.javachat.common.globalconsts.Net;
import com.ays.javachat.common.datatypes.UserDetails;
import com.ays.javachat.common.messages.Register;
import com.ays.javachat.common.messages.ReplyRegister;
import com.ays.javachat.common.messages.Logout;
import com.ays.javachat.common.messages.ReplyLogout;
import com.ays.javachat.common.messages.GetUserDetails;
import com.ays.javachat.common.messages.ReplyGetUserDetails;
import com.ays.javachat.common.messages.GetOnlineUsersList;
import com.ays.javachat.common.messages.ReplyGetOnlineUsersList;
import com.ays.javachat.common.messages.ClientText;
import com.ays.javachat.common.messages.ReplyClientText;
import com.ays.javachat.common.messages.GetUsersIgnoredByMe;
import com.ays.javachat.common.messages.ReplyGetUsersIgnoredByMe;
import java.util.Vector;
import com.ays.javachat.common.messages.IgnoreUsers;
import com.ays.javachat.common.messages.ReplyIgnoreUsers;
import com.ays.javachat.common.messages.SetUserDetails;
import com.ays.javachat.common.messages.ReplySetUserDetails;
import com.ays.javachat.common.datatypes.IPPort;
import com.ays.javachat.client.interfaces.ScreenCallback;

public class ClientScreenFunctions {
	private ClientScreenBuildInit clientScreenBuildInit = new ClientScreenBuildInit();
	private JFrame f = new JFrame();
	private final String PROGRAM_TITLE = "Chat client 1.1";
	private String sLastLoggedOnUserName = null;

	public ClientScreenBuildInit getClientScreenBuildInit() {
		return clientScreenBuildInit;
	}

	public void setClientScreenBuildInit(
			ClientScreenBuildInit clientScreenBuildInit) {
		this.clientScreenBuildInit = clientScreenBuildInit;
	}

	public JFrame getF() {
		return f;
	}

	public void setF(JFrame f) {
		this.f = f;
	}

	public String getPROGRAM_TITLE() {
		return PROGRAM_TITLE;
	}

	public String getSLastLoggedOnUserName() {
		return sLastLoggedOnUserName;
	}

	public void setSLastLoggedOnUserName(String sLastLoggedOnUserName) {
		this.sLastLoggedOnUserName = sLastLoggedOnUserName;
	}

	public void doLogin(ScreenCallback screenCallback, ClientScreen clientScreen) {
		LoginData login = new LoginData("");
		if (Dialogs.loginDialog(login)) {
			clientScreenBuildInit.setStatus("Trying to log in as a "
					+ login.UserName, 0, f);
			if (sLastLoggedOnUserName != null)
				doLogout(screenCallback, clientScreen);
			sLastLoggedOnUserName = login.UserName;
			Login req = new Login(login);
			int iStatus = screenCallback.sendRequest(req);
			ReplyLogin reply;
			if (iStatus != Net.OK) {
				reply = new ReplyLogin(login, iStatus);
				clientScreen.replyReceived(reply);
			}
		}
	}

	public void doRegister(ScreenCallback screenCallback,
			ClientScreen clientScreen) {
		LoginData login = new LoginData("");
		UserDetails details = new UserDetails();
		if (Dialogs.registerDialog(login, details, false)) {
			clientScreenBuildInit.setStatus("Registering new user - "
					+ login.UserName, 0, f);
			Register req = new Register(login, details);
			int iStatus = screenCallback.sendRequest(req);
			ReplyRegister reply;
			if (iStatus != Net.OK) {
				reply = new ReplyRegister(login, details, iStatus);
				clientScreen.replyReceived(reply);
			}
		}
	}

	public void doLogout(ScreenCallback screenCallback,
			ClientScreen clientScreen) {
		sLastLoggedOnUserName = null;
		f.setTitle(PROGRAM_TITLE);
		Logout req = new Logout();
		int iStatus = screenCallback.sendRequest(req);
		ReplyLogout reply;
		if (iStatus != Net.OK) {
			reply = new ReplyLogout(iStatus);
			clientScreen.replyReceived(reply);
		}
	}

	public void doGetUserDetails(String aUserName, int aFlag,
			ScreenCallback screenCallback, ClientScreen clientScreen) {
		GetUserDetails req = new GetUserDetails(aUserName);
		req.InternalFlag = aFlag;
		int iStatus = screenCallback.sendRequest(req);
		ReplyGetUserDetails reply;
		if (iStatus != Net.OK) {
			reply = new ReplyGetUserDetails(aUserName, null, iStatus);
			clientScreen.replyReceived(reply);
		}
	}

	public void doGetOnlineUsersList(ScreenCallback screenCallback,
			ClientScreen clientScreen) {
		GetOnlineUsersList req = new GetOnlineUsersList();
		int iStatus = screenCallback.sendRequest(req);
		ReplyGetOnlineUsersList reply;
		if (iStatus != Net.OK) {
			reply = new ReplyGetOnlineUsersList(iStatus);
			clientScreen.replyReceived(reply);
		}
	}

	public void doSendText(String aUserName, String aText,
			ScreenCallback screenCallback, ClientScreen clientScreen) {
		ClientText req = new ClientText(aUserName, aText);
		int iStatus = screenCallback.sendRequest(req);
		ReplyClientText reply;
		if (iStatus != Net.OK) {
			reply = new ReplyClientText(aUserName, aText, iStatus);
			clientScreen.replyReceived(reply);
		}
	}

	public void doManageIgnoreList(ScreenCallback screenCallback,
			ClientScreen clientScreen) {
		GetUsersIgnoredByMe req = new GetUsersIgnoredByMe();
		int iStatus = screenCallback.sendRequest(req);
		ReplyGetUsersIgnoredByMe reply;
		if (iStatus != Net.OK) {
			reply = new ReplyGetUsersIgnoredByMe(null, iStatus);
			clientScreen.replyReceived(reply);
		}
	}

	public void doIgnoreUsers(Vector aIgnoredUsersList,
			boolean aOverwriteExistingList, ScreenCallback screenCallback,
			ClientScreen clientScreen) {
		IgnoreUsers req = new IgnoreUsers(aIgnoredUsersList,
				aOverwriteExistingList);
		int iStatus = screenCallback.sendRequest(req);
		ReplyIgnoreUsers reply;
		if (iStatus != Net.OK) {
			reply = new ReplyIgnoreUsers(aIgnoredUsersList,
					aOverwriteExistingList, iStatus);
			clientScreen.replyReceived(reply);
		}
	}

	public void doSetUserDetails(UserDetails details, String aPassword,
			ScreenCallback screenCallback, ClientScreen clientScreen) {
		SetUserDetails req = new SetUserDetails(details);
		req.Password = aPassword;
		int iStatus = screenCallback.sendRequest(req);
		ReplySetUserDetails reply;
		if (iStatus != Net.OK) {
			reply = new ReplySetUserDetails(details, iStatus);
			clientScreen.replyReceived(reply);
		}
	}

	public void doConnect(ScreenCallback screenCallback) {
		IPPort ipport = new IPPort();
		if (Dialogs.connectDialog(ipport)) {
			ipport.save();
			clientScreenBuildInit.setStatus("Connecting to the " + ipport.IP
					+ ":" + ipport.Port, 0, f);
			screenCallback.connect(ipport);
		}
	}
}