package com.ays.javachat.client.screen;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.ays.javachat.client.interfaces.ScreenCallback;

public class ClientScreenData {
	private ClientScreenFunctions clientScreenFunctions;
	private String GENERAL_ROOM;
	private Dimension WINDOW_SIZE;
	private Point WINDOW_POS;
	private String SEND_BUTTON;
	private String TEXT_LINE;
	private String menuCONNECT;
	private String menuLOGIN;
	private String menuEXIT;
	private String menuABOUT;
	private String menuREGISTER;
	private String menuMyDetails;
	private String menuManageIgnoreList;
	private ScreenCallback screenCallback;
	private JLabel rooms;
	private JLabel peoples;
	private JLabel typeyourmessage;
	private Rooms tabs;
	private OnlineList list;
	private JTextField tf;
	private JButton send;
	private JPopupMenu popupMenu;

	public ClientScreenData(ClientScreenFunctions clientScreenFunctions,
			Dimension wINDOW_SIZE, Point wINDOW_POS, JLabel rooms,
			JLabel peoples, JLabel typeyourmessage, Rooms tabs,
			OnlineList list, JTextField tf, JButton send, JPopupMenu popupMenu) {
		this.clientScreenFunctions = clientScreenFunctions;
		GENERAL_ROOM = "General room";
		WINDOW_SIZE = wINDOW_SIZE;
		WINDOW_POS = wINDOW_POS;
		SEND_BUTTON = "Send Text Button";
		TEXT_LINE = "Send Text Line";
		this.menuCONNECT = "Connect";
		this.menuLOGIN = "Login";
		this.menuEXIT = "Exit";
		this.menuABOUT = "About";
		this.menuREGISTER = "Register";
		this.menuMyDetails = "View/Change my Details";
		this.menuManageIgnoreList = "Manage Ignore List";
		this.rooms = rooms;
		this.peoples = peoples;
		this.typeyourmessage = typeyourmessage;
		this.tabs = tabs;
		this.list = list;
		this.tf = tf;
		this.send = send;
		this.popupMenu = popupMenu;
	}

	public ClientScreenFunctions getClientScreenFunctions() {
		return clientScreenFunctions;
	}

	public String getGENERAL_ROOM() {
		return GENERAL_ROOM;
	}

	public Dimension getWINDOW_SIZE() {
		return WINDOW_SIZE;
	}

	public Point getWINDOW_POS() {
		return WINDOW_POS;
	}

	public String getSEND_BUTTON() {
		return SEND_BUTTON;
	}

	public String getTEXT_LINE() {
		return TEXT_LINE;
	}

	public String getMenuCONNECT() {
		return menuCONNECT;
	}

	public String getMenuLOGIN() {
		return menuLOGIN;
	}

	public String getMenuEXIT() {
		return menuEXIT;
	}

	public String getMenuABOUT() {
		return menuABOUT;
	}

	public String getMenuREGISTER() {
		return menuREGISTER;
	}

	public String getMenuMyDetails() {
		return menuMyDetails;
	}

	public String getMenuManageIgnoreList() {
		return menuManageIgnoreList;
	}

	public ScreenCallback getScreenCallback() {
		return screenCallback;
	}

	public void setScreenCallback(ScreenCallback screenCallback) {
		this.screenCallback = screenCallback;
	}

	public JLabel getRooms() {
		return rooms;
	}

	public JLabel getPeoples() {
		return peoples;
	}

	public JLabel getTypeyourmessage() {
		return typeyourmessage;
	}

	public Rooms getTabs() {
		return tabs;
	}

	public OnlineList getList() {
		return list;
	}


	public JTextField getTf() {
		return tf;
	}

	

	public JButton getSend() {
		return send;
	}

	
}