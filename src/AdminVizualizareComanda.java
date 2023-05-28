import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminVizualizareComanda extends JFrame{
    private JButton mergiInapoiLaComenziButton;
    private JButton delogheazaTeButton;
    private JTable table1;
    private JPanel panel1;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");
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

    public AdminVizualizareComanda() throws SQLException {
        mergiInapoiLaComenziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminComenzi ac = null;
                try {
                    ac = new AdminComenzi();
                    ac.frameSetup(ac);
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
    protected void frameSetup(AdminVizualizareComanda avc, int ID) throws SQLException {
        id = ID;
        avc.setContentPane(getPanel1());
        avc.setTitle("MobART");
        avc.setSize(1920, 1080);
        avc.setVisible(true);
        avc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
