import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserVerificareComenzi extends JFrame {
    private JButton mergiInapoiLaMeniulButton;
    private JButton delogheazaTeButton;
    private JTable table1;
    private JButton mergiLaSectiuneaTicheteButton;
    private JPanel panel1;
    private JButton vizualizeazaOComandaButton;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");
    private String userConectat;

    public JButton getMergiInapoiLaMeniulButton() {
        return mergiInapoiLaMeniulButton;
    }

    public JButton getDelogheazaTeButton() {
        return delogheazaTeButton;
    }

    public JTable getTable1() {
        return table1;
    }

    public JButton getMergiLaSectiuneaTicheteButton() {
        return mergiLaSectiuneaTicheteButton;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public Connection getCon() {
        return con;
    }

    public String getUserConectat() {
        return userConectat;
    }

    public void setMergiInapoiLaMeniulButton(JButton mergiInapoiLaMeniulButton) {
        this.mergiInapoiLaMeniulButton = mergiInapoiLaMeniulButton;
    }

    public void setDelogheazaTeButton(JButton delogheazaTeButton) {
        this.delogheazaTeButton = delogheazaTeButton;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public void setMergiLaSectiuneaTicheteButton(JButton mergiLaSectiuneaTicheteButton) {
        this.mergiLaSectiuneaTicheteButton = mergiLaSectiuneaTicheteButton;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void setUserConectat(String userConectat) {
        this.userConectat = userConectat;
    }

    public UserVerificareComenzi() throws SQLException {
        mergiInapoiLaMeniulButton.addActionListener(new ActionListener() {
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
        mergiLaSectiuneaTicheteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTichet ut = null;
                try {
                    ut = new UserTichet();
                    ut.frameSetup(ut, userConectat);
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
        vizualizeazaOComandaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserVizualizareComanda uvc = null;
                try {
                    int selectedRow = table1.getSelectedRow();
                    int column = 0;
                    Object value = table1.getValueAt(selectedRow, column);
                    int ID = (Integer) value;
                    uvc = new UserVizualizareComanda();
                    uvc.frameSetup(uvc, userConectat, ID);
                    dispose();
                } catch (IndexOutOfBoundsException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Selecteaza o comanda!");
                }
            }
        });
    }

    protected void frameSetup(UserVerificareComenzi uvc, String user) throws SQLException {
        userConectat = user;
        uvc.setContentPane(getPanel1());
        uvc.setTitle("MobART");
        uvc.setSize(1920, 1080);
        uvc.setVisible(true);
        uvc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM COMENZIPLASATEMOBILA WHERE USERNAME=?");
        ps.setString(1, userConectat);
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[8];
            row[0] = rs.getInt("ID");
            row[1] = rs.getString("USERNAME");
            row[2] = rs.getString("PRENUME");
            row[3] = rs.getString("NUME");
            row[4] = rs.getString("ADRESA");
            row[5] = rs.getString("TELEFON");
            row[6] = rs.getString("STATUS");
            row[7] = rs.getString("ORAS");
            model.addRow(row);
        }
        table.setModel(model);
    }
}
