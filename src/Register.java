import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register extends JFrame {
    private JTextField usernameText;
    private JPasswordField passowrdText;
    private JTextField telefonText;
    private JTextField adresaText;
    private JButton inregistreazaTeButton;
    private JButton logheazaTeButton;
    private JPanel panel1;
    private JTextField prenumeText;
    private JTextField numeText;
    private JTextField orasText;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");

    public JTextField getUsernameText() {
        return usernameText;
    }

    public void setUsernameText(JTextField usernameText) {
        this.usernameText = usernameText;
    }

    public JPasswordField getPassowrdText() {
        return passowrdText;
    }

    public void setPassowrdText(JPasswordField passowrdText) {
        this.passowrdText = passowrdText;
    }

    public JTextField getTelefonText() {
        return telefonText;
    }

    public void setTelefonText(JTextField telefonText) {
        this.telefonText = telefonText;
    }

    public JTextField getAdresaText() {
        return adresaText;
    }

    public void setAdresaText(JTextField adresaText) {
        this.adresaText = adresaText;
    }

    public JButton getInregistreazaTeButton() {
        return inregistreazaTeButton;
    }

    public void setInregistreazaTeButton(JButton inregistreazaTeButton) {
        this.inregistreazaTeButton = inregistreazaTeButton;
    }

    public JButton getLogheazaTeButton() {
        return logheazaTeButton;
    }

    public void setLogheazaTeButton(JButton logheazaTeButton) {
        this.logheazaTeButton = logheazaTeButton;
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

    public JTextField getPrenumeText() {
        return prenumeText;
    }

    public void setPrenumeText(JTextField prenumeText) {
        this.prenumeText = prenumeText;
    }

    public JTextField getNumeText() {
        return numeText;
    }

    public void setNumeText(JTextField numeText) {
        this.numeText = numeText;
    }

    public Register() throws SQLException {
        inregistreazaTeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM USERMOBILA WHERE USERNAME=? or TELEFON=?");
                    ps.setString(1, usernameText.getText());
                    ps.setString(2, telefonText.getText());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Numele de utilizator sau numarul de telefon sunt folosite!");
                    } else {
                        if (usernameText.getText().equalsIgnoreCase("") || passowrdText.getText().equalsIgnoreCase("") || telefonText.getText().equalsIgnoreCase("") || adresaText.getText().equalsIgnoreCase("") || orasText.getText().equalsIgnoreCase("") || numeText.getText().equalsIgnoreCase("") || prenumeText.getText().equalsIgnoreCase("")) {
                            JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate!");
                        } else {
                            PreparedStatement ps1 = con.prepareStatement("INSERT INTO USERMOBILA VALUES (?,?,?,?,?,?,?,?)");
                            ps1.setString(1, usernameText.getText());
                            ps1.setString(2, passowrdText.getText());
                            ps1.setString(3, "user");
                            ps1.setString(4, telefonText.getText());
                            ps1.setString(5, orasText.getText());
                            ps1.setString(6, adresaText.getText());
                            ps1.setString(7, prenumeText.getText());
                            ps1.setString(8, numeText.getText());
                            if (ps1.executeUpdate() != 0)
                                JOptionPane.showMessageDialog(null, "Te-ai inregistrat cu succes!");
                            ps.close();
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        logheazaTeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login lg = null;
                try {
                    lg = new Login();
                    lg.frameSetup(lg);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    protected void frameSetup(Register rg) throws SQLException {
        rg.setContentPane(getPanel1());
        rg.setTitle("MobART");
        rg.setSize(1920, 1080);
        rg.setVisible(true);
        rg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //showTable(table1);
    }
}
