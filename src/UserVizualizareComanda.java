import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserVizualizareComanda extends JFrame {
    private JButton mergiInapoiLaComenziButton;
    private JButton delogheazaTeButton;
    private JTable table1;
    private JPanel panel1;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");
    private String userConectat;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JButton getMergiInapoiLaComenziButton() {
        return mergiInapoiLaComenziButton;
    }

    public void setMergiInapoiLaComenziButton(JButton mergiInapoiLaComenziButton) {
        this.mergiInapoiLaComenziButton = mergiInapoiLaComenziButton;
    }

    public JButton getDelogheazaTeButton() {
        return delogheazaTeButton;
    }

    public void setDelogheazaTeButton(JButton delogheazaTeButton) {
        this.delogheazaTeButton = delogheazaTeButton;
    }

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

    public UserVizualizareComanda() throws SQLException {
        mergiInapoiLaComenziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserVerificareComenzi uvc = null;
                try {
                    uvc = new UserVerificareComenzi();
                    uvc.frameSetup(uvc, userConectat);
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
    }

    protected void frameSetup(UserVizualizareComanda uvc, String user, int ID) throws SQLException {
        userConectat = user;
        id = ID;
        uvc.setContentPane(getPanel1());
        uvc.setTitle("MobART");
        uvc.setSize(1920, 1080);
        uvc.setVisible(true);
        uvc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM ADMINCOMENZIMOBILA WHERE ID=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[2];
            row[0] = rs.getString("PRODUS");
            row[1] = rs.getString("STOC");
            model.addRow(row);
        }
        table.setModel(model);
    }
}
