import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminManageTickets extends JFrame {
    private JButton mergiInapoiLaMeniulButton;
    private JButton delogheazaTeButton;
    private JPanel panel1;
    private JButton arataToateTicheteleButton;
    private JButton cautaTichetButton;
    private JButton stergeTichetButton;
    private JTextField cautaTichetText;
    private JTextField stergeTichetText;
    private JTable table1;
    private JButton raspundeLaTichetButton;
    private JButton arataTicheteNerezolvateButton;
    private JTextField cautaIdText;
    private JButton cautaTichetDupaIDButton;
    private JTextField idRaspundere;
    private JTextField mesajRaspundere;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");

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

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JButton getArataToateTicheteleButton() {
        return arataToateTicheteleButton;
    }

    public void setArataToateTicheteleButton(JButton arataToateTicheteleButton) {
        this.arataToateTicheteleButton = arataToateTicheteleButton;
    }

    public JButton getCautaTichetButton() {
        return cautaTichetButton;
    }

    public void setCautaTichetButton(JButton cautaTichetButton) {
        this.cautaTichetButton = cautaTichetButton;
    }

    public JButton getStergeTichetButton() {
        return stergeTichetButton;
    }

    public void setStergeTichetButton(JButton stergeTichetButton) {
        this.stergeTichetButton = stergeTichetButton;
    }

    public JTextField getCautaTichetText() {
        return cautaTichetText;
    }

    public void setCautaTichetText(JTextField cautaTichetText) {
        this.cautaTichetText = cautaTichetText;
    }

    public JTextField getStergeTichetText() {
        return stergeTichetText;
    }

    public void setStergeTichetText(JTextField stergeTichetText) {
        this.stergeTichetText = stergeTichetText;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JButton getRaspundeLaTichetButton() {
        return raspundeLaTichetButton;
    }

    public void setRaspundeLaTichetButton(JButton raspundeLaTichetButton) {
        this.raspundeLaTichetButton = raspundeLaTichetButton;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public AdminManageTickets() throws SQLException {
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
                    JOptionPane.showMessageDialog(null, "Te-ai delogat cu succes!");
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cautaTichetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchForName(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        stergeTichetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stergeTichetText.getText().equalsIgnoreCase(""))
                    JOptionPane.showMessageDialog(null, "Campul trebuie completat!");
                else {
                    PreparedStatement ps = null;
                    try {
                        ps = con.prepareStatement("DELETE FROM TICHETEMOBILA WHERE ID=?");
                        ps.setString(1, stergeTichetText.getText());
                        if (ps.executeUpdate() != 0) {
                            JOptionPane.showMessageDialog(null, "Tichetul " + stergeTichetText.getText() + " a fost sters!");
                            showTable(table1);
                        } else
                            JOptionPane.showMessageDialog(null, "Tichetul nu exista!");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        arataToateTicheteleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTable(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        raspundeLaTichetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    raspundereTichet(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        arataTicheteNerezolvateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchNerezolvate(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cautaTichetDupaIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchByID(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    protected void frameSetup(AdminManageTickets amt) throws SQLException {
        amt.setContentPane(getPanel1());
        amt.setTitle("MobART");
        amt.setSize(1920, 1080);
        amt.setVisible(true);
        amt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM TICHETEMOBILA ORDER BY ID");
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getString("ID");
            row[1] = rs.getString("NUME");
            row[2] = rs.getString("SUBIECT");
            row[3] = rs.getString("MESAJ");
            row[4] = rs.getString("RASPUNS");
            model.addRow(row);
        }
        table.setModel(model);
    }

    protected void searchForName(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM TICHETEMOBILA WHERE NUME=? ORDER BY ID");
        ps.setString(1, cautaTichetText.getText());
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getString("ID");
            row[1] = rs.getString("NUME");
            row[2] = rs.getString("SUBIECT");
            row[3] = rs.getString("MESAJ");
            row[4] = rs.getString("RASPUNS");
            model.addRow(row);
        }
        table.setModel(model);
        cautaTichetText.setText("");
    }

    protected void searchNerezolvate(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM TICHETEMOBILA WHERE RASPUNS=? ORDER BY ID");
        ps.setString(1, "Raspuns in asteptare!");
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getString("ID");
            row[1] = rs.getString("NUME");
            row[2] = rs.getString("SUBIECT");
            row[3] = rs.getString("MESAJ");
            row[4] = rs.getString("RASPUNS");
            model.addRow(row);
        }
        table.setModel(model);
    }

    protected void searchByID(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3", "C4", "C5"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM TICHETEMOBILA WHERE ID=?");
        ps.setString(1, cautaIdText.getText());
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getString("ID");
            row[1] = rs.getString("NUME");
            row[2] = rs.getString("SUBIECT");
            row[3] = rs.getString("MESAJ");
            row[4] = rs.getString("RASPUNS");
            model.addRow(row);
        }
        table.setModel(model);
        cautaIdText.setText("");
    }

    protected void raspundereTichet(JTable table) throws SQLException {
        if (idRaspundere.getText().equalsIgnoreCase("") || mesajRaspundere.getText().equalsIgnoreCase(""))
            JOptionPane.showMessageDialog(null, "Ambele campuri trebuie completate!");
        else {
            PreparedStatement ps = con.prepareStatement("UPDATE TICHETEMOBILA SET RASPUNS=? WHERE ID=?");
            ps.setString(1, mesajRaspundere.getText());
            ps.setString(2, idRaspundere.getText());
            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Raspunsul a fost trimis!");
                searchNerezolvate(table1);
                idRaspundere.setText("ID");
                mesajRaspundere.setText("Mesaj");
            }
        }
    }
}
