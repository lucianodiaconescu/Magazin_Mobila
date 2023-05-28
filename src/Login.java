import oracle.jdbc.proxy.annotation.Pre;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton logheazaTeButton;
    private JButton inregistreazaTeButton;
    private JPanel panel1;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");

    public JTextField getUsernameText() {
        return usernameText;
    }

    public void setUsernameText(JTextField usernameText) {
        this.usernameText = usernameText;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(JPasswordField passwordText) {
        this.passwordText = passwordText;
    }

    public JButton getLogheazaTeButton() {
        return logheazaTeButton;
    }

    public void setLogheazaTeButton(JButton logheazaTeButton) {
        this.logheazaTeButton = logheazaTeButton;
    }

    public JButton getInregistreazaTeButton() {
        return inregistreazaTeButton;
    }

    public void setInregistreazaTeButton(JButton inregistreazaTeButton) {
        this.inregistreazaTeButton = inregistreazaTeButton;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Login() throws SQLException {
        logheazaTeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM USERMOBILA WHERE USERNAME=? AND PAROLA=?");
                    ps.setString(1, usernameText.getText());
                    ps.setString(2, passwordText.getText());
                    ResultSet rs = ps.executeQuery();
                    if (usernameText.getText().equalsIgnoreCase("") || passwordText.getText().equalsIgnoreCase(""))
                        JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate!");
                    else {
                        if (rs.next()) {
                            String functie = rs.getString(3);
                            JOptionPane.showMessageDialog(null, "Te-ai logat cu succes!");
                            if (functie.equalsIgnoreCase("user")) {
                                UserMain um = new UserMain();
                                String user = rs.getString(1);
                                um.frameSetup(um, user);
                                dispose();
                            } else if (functie.equalsIgnoreCase("admin")) {
                                AdminMain am = new AdminMain();
                                am.frameSetup(am);
                                dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Nume de utilizator sau parola incorecte!");
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        inregistreazaTeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register rg = null;
                try {
                    rg = new Register();
                    rg.frameSetup(rg);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    protected void frameSetup(Login lg) throws SQLException {
        lg.setContentPane(getPanel1());
        lg.setTitle("MobART");
        lg.setSize(1920, 1080);
        lg.setVisible(true);
        lg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
