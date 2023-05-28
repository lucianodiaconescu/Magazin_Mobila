import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserTichet extends JFrame {
    private JTextField subjectText;
    private JTextField messageText;
    private JButton inapoiLaMeniulPrincipalButton;
    private JButton delogheazaTeButton;
    private JButton trimiteTichetButton;
    private JPanel panel1;
    private JTable table1;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");
    private String userConectat;

    public JTextField getSubjectText() {
        return subjectText;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public void setSubjectText(JTextField subjectText) {
        this.subjectText = subjectText;
    }

    public JTextField getMessageText() {
        return messageText;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public void setMessageText(JTextField messageText) {
        this.messageText = messageText;
    }

    public JButton getInapoiLaMeniulPrincipalButton() {
        return inapoiLaMeniulPrincipalButton;
    }

    public void setInapoiLaMeniulPrincipalButton(JButton inapoiLaMeniulPrincipalButton) {
        this.inapoiLaMeniulPrincipalButton = inapoiLaMeniulPrincipalButton;
    }

    public JButton getDelogheazaTeButton() {
        return delogheazaTeButton;
    }

    public void setDelogheazaTeButton(JButton delogheazaTeButton) {
        this.delogheazaTeButton = delogheazaTeButton;
    }

    public JButton getTrimiteTichetButton() {
        return trimiteTichetButton;
    }

    public void setTrimiteTichetButton(JButton trimiteTichetButton) {
        this.trimiteTichetButton = trimiteTichetButton;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getUserConectat() {
        return userConectat;
    }

    public void setUserConectat(String userConectat) {
        this.userConectat = userConectat;
    }

    public UserTichet() throws SQLException {
        inapoiLaMeniulPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserMain um = null;
                try {
                    um = new UserMain();
                    um.frameSetup(um, userConectat);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        delogheazaTeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login lg = null;
                try {
                    lg = new Login();
                    lg.frameSetup(lg);
                    dispose();
                    JOptionPane.showMessageDialog(null, "Te-ai delogat cu succes!");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        trimiteTichetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (subjectText.getText().equalsIgnoreCase("") || messageText.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate!");
                } else {
                    PreparedStatement ps = null;
                    try {
                        ps = con.prepareStatement("INSERT INTO TICHETEMOBILA VALUES (?,?,?,?,?)");
                        PreparedStatement ps1 = con.prepareStatement("SELECT * FROM TICHETEMOBILA ORDER BY ID DESC FETCH FIRST 1 ROW ONLY");
                        ResultSet rs = ps1.executeQuery();
                        rs.next();
                        ps.setString(1, userConectat);
                        ps.setString(2, subjectText.getText());
                        ps.setString(3, messageText.getText());
                        ps.setString(4, "Raspuns in asteptare!");
                        try {
                            ps.setInt(5, rs.getInt("ID") + 1);
                        } catch (SQLException ex) {
                            ps.setInt(5, 1);
                        }
                        if (ps.executeUpdate() != 0)
                            JOptionPane.showMessageDialog(null, "Tichetul a fost trimis!");
                        showTable(table1);
                        ps.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    void frameSetup(UserTichet ut, String user) throws SQLException {
        userConectat = user;
        ut.setContentPane(getPanel1());
        ut.setTitle("MobART");
        ut.setSize(1920, 1080);
        ut.setVisible(true);
        ut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM TICHETEMOBILA WHERE NUME=? ORDER BY ID");
        ps.setString(1, userConectat);
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getInt(5);
            row[1] = rs.getString(1);
            row[2] = rs.getString(2);
            row[3] = rs.getString(3);
            row[4] = rs.getString(4);
            model.addRow(row);
        }
        table.setModel(model);
    }
}
