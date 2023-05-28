import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminComenzi extends JFrame {
    private JPanel panel1;
    private JButton mergiInapoiLaMeniulButton;
    private JButton delogheazaTeButton;
    private JTable table1;
    private JTextField statusText;
    private JButton schimbaStatusulComenziiButton;
    private JButton vizualizeazaOComandaButton;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");

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

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JTextField getTextField1() {
        return statusText;
    }

    public void setTextField1(JTextField textField1) {
        this.statusText = textField1;
    }

    public JButton getSchimbaStatusulComenziiButton() {
        return schimbaStatusulComenziiButton;
    }

    public void setSchimbaStatusulComenziiButton(JButton schimbaStatusulComenziiButton) {
        this.schimbaStatusulComenziiButton = schimbaStatusulComenziiButton;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public AdminComenzi() throws SQLException {
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
        schimbaStatusulComenziiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (statusText.getText().equalsIgnoreCase(""))
                        JOptionPane.showMessageDialog(null, "Scrie un status!");
                    else {
                        int selectedRow = table1.getSelectedRow();
                        int column = 0;
                        Object value = table1.getValueAt(selectedRow, column);
                        PreparedStatement ps = null;
                        ps = con.prepareStatement("UPDATE COMENZIPLASATEMOBILA SET STATUS=? WHERE ID=?");
                        ps.setString(1, statusText.getText());
                        ps.setInt(2, (Integer) value);
                        if (ps.executeUpdate() != 0)
                            JOptionPane.showMessageDialog(null, "Status actualizat!");
                        statusText.setText("");
                        showTable(table1);
                    }
                } catch (IndexOutOfBoundsException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Selecteaza o comanda!");
                }
            }
        });
        vizualizeazaOComandaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminVizualizareComanda avc = null;
                try {
                    int selectedRow = table1.getSelectedRow();
                    int column = 0;
                    Object value = table1.getValueAt(selectedRow, column);
                    int ID = (Integer) value;
                    avc = new AdminVizualizareComanda();
                    avc.frameSetup(avc, ID);
                    dispose();
                } catch (IndexOutOfBoundsException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Selecteaza o comanda!");
                }
            }
        });
    }

    protected void frameSetup(AdminComenzi ac) throws SQLException {
        ac.setContentPane(getPanel1());
        ac.setTitle("MobART");
        ac.setSize(1920, 1080);
        ac.setVisible(true);
        ac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM COMENZIPLASATEMOBILA ORDER BY ID DESC");
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
