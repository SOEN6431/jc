package com.ays.javachat.client.screen;


import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import com.ays.javachat.common.globalconsts.Net;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Dimension;

public class ClientScreenBuildInit {
	private JLabel statusBarLabel = new JLabel("Ready");
	private JPanel MainPanel = new JPanel(new GridBagLayout());

	public JLabel getStatusBarLabel() {
		return statusBarLabel;
	}

	public JPanel getMainPanel() {
		return MainPanel;
	}
	
	public void setStatus(String aStatus, int aErrorCode, JFrame f) {
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

	public void show(JFrame f, String PROGRAM_TITLE, Point WINDOW_POS,
			Dimension WINDOW_SIZE, ClientScreen clientScreen) {
		clientScreen.initComponents();
		clientScreen.buildScreen();
		clientScreen.buildMenu();
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
}