package com.ays.javachat.client.screen;

import com.ays.javachat.common.datatypes.IPPort;
import com.ays.javachat.common.datatypes.LoginData;
import com.ays.javachat.common.datatypes.UserDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;


/**
 * This class is using to display some dialogs *
 */
public class Dialogs {
    public Dialogs() {
    }

    private static Dialogs createSelf() {
        return new Dialogs();
    }

    private static String chars2String(char chars[]) {
        String s = "";
        for (int i = 0; i < chars.length; i++)
            s = s + chars[i];

        return s;
    }

    private boolean showDialog(String aTitle, JPanel aPanel, String aButtons[]) {
        if (JOptionPane.showOptionDialog(null, aPanel, aTitle, 0, JOptionPane.PLAIN_MESSAGE, null, aButtons, null) == 0)
            return true;

        return false;
    }


    /**
     * Displays Login dialog. Returns LoginData *
     */
    public static boolean loginDialog(LoginData aLogin) {
        // building login and password screen
        JLabel l1 = new JLabel("User name :");
        JLabel l2 = new JLabel("Password :");
        JTextField tf1 = new JTextField(aLogin.UserName);
        JPasswordField tf2 = new JPasswordField("");

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(l1);
        panel.add(tf1);
        panel.add(l2);
        panel.add(tf2);

        String buttons[] = {"Login", "Cancel"};

        if (createSelf().showDialog("Login", panel, buttons)) {
            aLogin.UserName = tf1.getText();
            aLogin.Password = chars2String(tf2.getPassword());
            return true;
        }

        return false;
    }


    /**
     * Displays Connect Dialog. Returns IPPort *
     */
    public static boolean connectDialog(final IPPort aIPPort) {
        // building login and password screen
        JLabel l1 = new JLabel("IP address :");
        JLabel l2 = new JLabel("Port :");
        final JTextField tf1 = new JTextField(aIPPort.IP);
        final JTextField tf2 = new JTextField(String.valueOf(aIPPort.Port));

        JButton restoreDefaults = new JButton("Restore Defaults");
        restoreDefaults.addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1)
                            if (JOptionPane.showConfirmDialog(null, "Restore defaults ?", "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == 0) {
                                aIPPort.setDefaults();
                                tf1.setText(aIPPort.IP);
                                tf2.setText(String.valueOf(aIPPort.Port));
                            }
                    }
                });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(l1);
        panel.add(tf1);
        panel.add(l2);
        panel.add(tf2);
        panel.add(new JPanel());
        panel.add(restoreDefaults);

        String buttons[] = {"Connect", "Cancel"};

        if (createSelf().showDialog("Connect", panel, buttons)) {
            aIPPort.IP = tf1.getText();
            aIPPort.Port = Integer.valueOf(tf2.getText());
            return true;
        }

        return false;
    }


    /**
     * Displays Register new user dialog.<br> If aUpdate == true, UserName field is readonly ( user can't change his UserName )*
     */
    public static boolean registerDialog(LoginData aLogin, UserDetails aDetails,
                                         boolean aUpdate) {
        // building login and password screen
        JLabel l1 = new JLabel("User name :");
        JLabel l2 = new JLabel("Password :");
        JLabel l22 = new JLabel("Confirm password :");
        JTextField tf1 = new JTextField(aLogin.UserName);
        JPasswordField tf2 = new JPasswordField("");
        JPasswordField tf22 = new JPasswordField("");

        JLabel lrealname = new JLabel("Real name :");
        JTextField tfrealname = new JTextField("");

        JLabel lage = new JLabel("Age :");
        JTextField tfage = new JTextField("");

        JLabel lsex = new JLabel("Sex :");
        String s[] = {"Male", "Female", "No defined"};
        JComboBox cbsex = new JComboBox(s);

        JLabel lcountry = new JLabel("Country :");
        JTextField tfcountry = new JTextField("");

        JLabel lcity = new JLabel("City :");
        JTextField tfcity = new JTextField("");

        JLabel lemail = new JLabel("e-mail :");
        JTextField tfemail = new JTextField("");

        JLabel lhomepage = new JLabel("Homepage :");
        JTextField tfhomepage = new JTextField("");

        JLabel licq = new JLabel("ICQ :");
        JTextField tficq = new JTextField("");

        JLabel labout = new JLabel("About :");
        JTextField tfabout = new JTextField("");

        JPanel panel = new JPanel(new GridLayout(12, 2));
        panel.add(l1);
        panel.add(tf1);
        panel.add(l2);
        panel.add(tf2);
        panel.add(l22);
        panel.add(tf22);

        panel.add(lrealname);
        panel.add(tfrealname);
        panel.add(lage);
        panel.add(tfage);
        panel.add(lsex);
        panel.add(cbsex);
        panel.add(lcountry);
        panel.add(tfcountry);
        panel.add(lcity);
        panel.add(tfcity);
        panel.add(lemail);
        panel.add(tfemail);
        panel.add(lhomepage);
        panel.add(tfhomepage);
        panel.add(licq);
        panel.add(tficq);
        panel.add(labout);
        panel.add(tfabout);

        if (aUpdate) {
            l1.setEnabled(false);
            tf1.setEnabled(false);

            tfrealname.setText(aDetails.RealName);
            tfage.setText(aDetails.Age);
            int iSex = 0;
            try {
                iSex = Integer.valueOf(aDetails.Sex);
            }
            catch (Exception e) {
            }
            cbsex.setSelectedIndex(iSex);
            tfcountry.setText(aDetails.Country);
            tfcity.setText(aDetails.City);
            tfemail.setText(aDetails.Email);
            tfhomepage.setText(aDetails.HomePage);
            tficq.setText(aDetails.ICQ);
            tfabout.setText(aDetails.About);

        }

        String buttons[] = {"OK", "Cancel"};

        String sCaption;
        if (aUpdate)
            sCaption = "View/Change my details";
        else
            sCaption = "Register new user";

        while (true) {
            if (createSelf().showDialog(sCaption, panel, buttons)) {
                aLogin.UserName = tf1.getText();
                aLogin.Password = chars2String(tf2.getPassword());
                aDetails.RealName = tfrealname.getText();
                aDetails.Age = tfage.getText();
                aDetails.Sex = Integer.toString(cbsex.getSelectedIndex());
                aDetails.Country = tfcountry.getText();
                aDetails.City = tfcity.getText();
                aDetails.Email = tfemail.getText();
                aDetails.HomePage = tfhomepage.getText();
                aDetails.ICQ = tficq.getText();
                aDetails.About = tfabout.getText();

                boolean bRetype = false;

                String sInfo = "";
                if (!chars2String(tf2.getPassword()).equals(chars2String(tf22.getPassword()))) {
                    sInfo = "\n( wrong password confirmation )";
                    bRetype = true;
                }

                if (!aLogin.isDataValid()) {
                    sInfo = "\n( " + aLogin.getLastErrorMessage() + ")";
                    bRetype = true;
                }

                if (!aDetails.isDataValid()) {
                    sInfo = "\n( " + aDetails.getLastErrorMessage() + ")";
                    bRetype = true;
                }

                if (bRetype)
                    if (JOptionPane.showConfirmDialog(null, "Data you have entered is invalid\nClick Yes if you want to reEnter it" + sInfo, "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null) == 0)
                        ;
                    else
                        return false;
                else
                    return true;
            } else return false; // cancel pressed
        }
    }


    /**
     * Shows User Details *
     */
    public static void showUserDetailsDialog(String aUserName, UserDetails aDetails) {
        // building login and password screen
        JLabel l1 = new JLabel("User name :");
        JTextField tf1 = new JTextField(aUserName);

        JLabel lrealname = new JLabel("Real name :");
        JTextField tfrealname = new JTextField("");

        JLabel lage = new JLabel("Age :");
        JTextField tfage = new JTextField("");

        JLabel lsex = new JLabel("Sex :");
        String s[] = {"Male", "Female", "No defined"};
        JComboBox cbsex = new JComboBox(s);

        JLabel lcountry = new JLabel("Country :");
        JTextField tfcountry = new JTextField("");

        JLabel lcity = new JLabel("City :");
        JTextField tfcity = new JTextField("");

        JLabel lemail = new JLabel("e-mail :");
        JTextField tfemail = new JTextField("");

        JLabel lhomepage = new JLabel("Homepage :");
        JTextField tfhomepage = new JTextField("");

        JLabel licq = new JLabel("ICQ :");
        JTextField tficq = new JTextField("");

        JLabel labout = new JLabel("About :");
        JTextField tfabout = new JTextField("");

        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.add(l1);
        panel.add(tf1);

        panel.add(lrealname);
        panel.add(tfrealname);
        panel.add(lage);
        panel.add(tfage);
        panel.add(lsex);
        panel.add(cbsex);
        panel.add(lcountry);
        panel.add(tfcountry);
        panel.add(lcity);
        panel.add(tfcity);
        panel.add(lemail);
        panel.add(tfemail);
        panel.add(lhomepage);
        panel.add(tfhomepage);
        panel.add(licq);
        panel.add(tficq);
        panel.add(labout);
        panel.add(tfabout);

        l1.setEnabled(false);
        tf1.setEnabled(false);
        lrealname.setEnabled(false);
        tfrealname.setEnabled(false);
        lage.setEnabled(false);
        tfage.setEnabled(false);
        lsex.setEnabled(false);
        cbsex.setEnabled(false);
        lcountry.setEnabled(false);
        tfcountry.setEnabled(false);
        lcity.setEnabled(false);
        tfcity.setEnabled(false);
        lemail.setEnabled(false);
        tfemail.setEnabled(false);
        lhomepage.setEnabled(false);
        tfhomepage.setEnabled(false);
        licq.setEnabled(false);
        tficq.setEnabled(false);
        labout.setEnabled(false);
        tfabout.setEnabled(false);

        tfrealname.setText(aDetails.RealName);
        tfage.setText(aDetails.Age);
        int iSex = 0;
        try {
            iSex = Integer.valueOf(aDetails.Sex);
        }
        catch (Exception e) {
        }
        cbsex.setSelectedIndex(iSex);
        tfcountry.setText(aDetails.Country);
        tfcity.setText(aDetails.City);
        tfemail.setText(aDetails.Email);
        tfhomepage.setText(aDetails.HomePage);
        tficq.setText(aDetails.ICQ);
        tfabout.setText(aDetails.About);


        String buttons[] = {"OK"};

        createSelf().showDialog("User details", panel, buttons);
    }


    /**
     * Displays Ignored Users list dialog. Here you can add/remove users ignored by yourself *
     */
    public static boolean manageIgnoreUsersialog(Vector aIgnoredUsersList) {
        if (aIgnoredUsersList == null)
            return false;

        // building login and password screen
        final DefaultListModel listModel = new DefaultListModel();
        final JList list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        JButton addUser = new JButton("Add User");
        JButton removeUser = new JButton("Remove User");
        JPanel buttonsPanel = new JPanel(new GridLayout(8, 1));
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Add/Remove users ignored by myself");

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listModel.clear();

        buttonsPanel.add(addUser);
        buttonsPanel.add(removeUser);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.EAST);
        panel.add(label, BorderLayout.NORTH);

        addUser.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String s = JOptionPane.showInputDialog(null, "Enter user name", "Adding user to ignore", JOptionPane.DEFAULT_OPTION);
                if (s != null)
                    if (!s.equals(""))
                        listModel.addElement(s);
            }
        });

        removeUser.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (list.getSelectedIndex() < 0)
                    return;
                listModel.remove(list.getSelectedIndex());
            }
        });

        String buttons[] = {"Save", "Cancel"};

        for (int i = 0; i < aIgnoredUsersList.size(); i++)
            listModel.addElement(aIgnoredUsersList.get(i));

        if (createSelf().showDialog("Ignroed users", panel, buttons)) {
            aIgnoredUsersList.clear();
            for (int i = 0; i < listModel.size(); i++)
                aIgnoredUsersList.add((String) listModel.getElementAt(i));

            return true;
        }

        return false;
    }

    /**
     * Displays about dialog *
     */
    public static void aboutDialog() {
        // building login and password screen
        JLabel l1 = new JLabel("Product name : ");
        JLabel l2 = new JLabel("Author : ");
        JLabel l3 = new JLabel("Released : ");
        JLabel l4 = new JLabel("Author contacts : ");

        JLabel l12 = new JLabel("Chat client v1.1");
        JLabel l22 = new JLabel("Yevgeny Sergeyev");
        JLabel l32 = new JLabel("AUG-2005");
        JLabel l42 = new JLabel("tel(Israel). : +974-547945462. ICQ : 123845810. e-mail : yevgeny.sergeyev@gmail.com");

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(l1);
        panel.add(l12);
        panel.add(l3);
        panel.add(l32);
        panel.add(l2);
        panel.add(l22);
        panel.add(l4);
        panel.add(l42);

        String buttons[] = {"OK"};

        createSelf().showDialog("About", panel, buttons);
    }

}
