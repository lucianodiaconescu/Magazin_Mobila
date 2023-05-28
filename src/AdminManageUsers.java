import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminManageUsers extends JFrame {
    private JTable table1;
    private JPanel panel1;
    private JButton mergiInapoiLaMeniulButton;
    private JButton delogheazaTeButton;
    private JTextField stergeUserText;
    private JButton stergeUserButton;
    private JTextField cautaUserText;
    private JButton cautaUserButton;
    private JButton arataTotiUseriiButton;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JButton getMergiInapoiLaMeniulButton() {
        return mergiInapoiLaMeniulButton;
    }

    public void setMergiInapoiLaMeniulButton(JButton mergiInapoiLaMeniulButton) {
        this.mergiInapoiLaMeniulButton = mergiInapoiLaMeniulButton;
    }

    public JButton getDelogheazaTeButton() {
        return delogheazaTeButton;
    }

    public void setDelogheazaTeButton(JButton delogheazaTeButton) {
        this.delogheazaTeButton = delogheazaTeButton;
    }

    public JTextField getStergeUserText() {
        return stergeUserText;
    }

    public void setStergeUserText(JTextField stergeUserText) {
        this.stergeUserText = stergeUserText;
    }

    public JButton getStergeUserButton() {
        return stergeUserButton;
    }

    public void setStergeUserButton(JButton stergeUserButton) {
        this.stergeUserButton = stergeUserButton;
    }

    public JTextField getCautUserText() {
        return cautaUserText;
    }

    public void setCautUserText(JTextField cautUserText) {
        this.cautaUserText = cautUserText;
    }

    public JButton getCautaUserButton() {
        return cautaUserButton;
    }

    public void setCautaUserButton(JButton cautaUserButton) {
        this.cautaUserButton = cautaUserButton;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public AdminManageUsers() throws SQLException {
        mergiInapoiLaMeniulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminMain am = null;
                try {
                    am = new AdminMain();
                    am.frameSetup(am);
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
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        stergeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stergeUserText.getText().equalsIgnoreCase(""))
                    JOptionPane.showMessageDialog(null, "Campul trebuie completat!");
                else {
                    PreparedStatement ps = null;
                    try {
                        ps = con.prepareStatement("DELETE FROM USERMOBILA WHERE USERNAME=?");
                        ps.setString(1, stergeUserText.getText());
                        if (ps.executeUpdate() != 0) {
                            JOptionPane.showMessageDialog(null, "User-ul " + stergeUserText.getText() + " a fost sters din baza de date!");
                            showTable(table1);
                        } else
                            JOptionPane.showMessageDialog(null, "User-ul " + stergeUserButton.getText() + " nu se afla in baza de date!");
                        stergeUserText.setText("");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        cautaUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchForName(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        arataTotiUseriiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTable(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    protected void frameSetup(AdminManageUsers amu) throws SQLException {
        amu.setContentPane(getPanel1());
        amu.setTitle("MobART");
        amu.setSize(1920, 1080);
        amu.setVisible(true);
        amu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5", "C6", "C7"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM USERMOBILA");
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[7];
            row[0] = rs.getString("USERNAME");
            row[1] = rs.getString("FUNCTIE");
            row[2] = rs.getString("TELEFON");
            row[3] = rs.getString("ORAS");
            row[4] = rs.getString("ADRESA");
            row[5] = rs.getString("PRENUME");
            row[6] = rs.getString("NUME");
            model.addRow(row);
        }
        table.setModel(model);
    }

    protected void searchForName(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM USERMOBILA WHERE USERNAME=?");
        ps.setString(1, cautaUserText.getText());
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[7];
            row[0] = rs.getString("USERNAME");
            row[1] = rs.getString("FUNCTIE");
            row[2] = rs.getString("TELEFON");
            row[3] = rs.getString("ORAS");
            row[4] = rs.getString("ADRESA");
            row[5] = rs.getString("PRENUME");
            row[6] = rs.getString("NUME");
            model.addRow(row);
        }
        table.setModel(model);
        cautaUserText.setText("");
    }
}
