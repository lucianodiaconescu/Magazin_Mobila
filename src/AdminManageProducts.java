import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminManageProducts extends JFrame {
    private JButton mergiInapoiLaMeniulButton;
    private JTable table1;
    private JButton cautaUnProdusDupaButton;
    private JTextField searchNameText;
    private JButton delogheazaTeButton;
    private JButton stergeUnProdusDupaButton;
    private JTextField deleteText;
    private JButton arataToateProduseleButton;
    private JTextField numeText;
    private JTextField descriereText;
    private JTextField stocText;
    private JButton adaugaProdusButton;
    private JPanel panel1;
    private JButton modificaUnProdusButton;
    private JTextField produsModificare;
    private JTextField numeModif;
    private JTextField descriereModif;
    private JTextField stocModif;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");

    public JButton getMergiInapoiLaMeniulButton() {
        return mergiInapoiLaMeniulButton;
    }

    public void setMergiInapoiLaMeniulButton(JButton mergiInapoiLaMeniulButton) {
        this.mergiInapoiLaMeniulButton = mergiInapoiLaMeniulButton;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JButton getCautaUnProdusDupaButton() {
        return cautaUnProdusDupaButton;
    }

    public void setCautaUnProdusDupaButton(JButton cautaUnProdusDupaButton) {
        this.cautaUnProdusDupaButton = cautaUnProdusDupaButton;
    }

    public JTextField getSerachNameText() {
        return searchNameText;
    }

    public void setSerachNameText(JTextField serachNameText) {
        this.searchNameText = serachNameText;
    }

    public JButton getDelogheazaTeButton() {
        return delogheazaTeButton;
    }

    public void setDelogheazaTeButton(JButton delogheazaTeButton) {
        this.delogheazaTeButton = delogheazaTeButton;
    }

    public JButton getStergeUnProdusDupaButton() {
        return stergeUnProdusDupaButton;
    }

    public void setStergeUnProdusDupaButton(JButton stergeUnProdusDupaButton) {
        this.stergeUnProdusDupaButton = stergeUnProdusDupaButton;
    }

    public JTextField getDeleteText() {
        return deleteText;
    }

    public void setDeleteText(JTextField deleteText) {
        this.deleteText = deleteText;
    }

    public JButton getArataToateProduseleButton() {
        return arataToateProduseleButton;
    }

    public void setArataToateProduseleButton(JButton arataToateProduseleButton) {
        this.arataToateProduseleButton = arataToateProduseleButton;
    }

    public JTextField getNumeText() {
        return numeText;
    }

    public void setNumeText(JTextField numeText) {
        this.numeText = numeText;
    }

    public JTextField getDescriereText() {
        return descriereText;
    }

    public void setDescriereText(JTextField descriereText) {
        this.descriereText = descriereText;
    }

    public JTextField getStocText() {
        return stocText;
    }

    public void setStocText(JTextField stocText) {
        this.stocText = stocText;
    }

    public JButton getAdaugaProdusButton() {
        return adaugaProdusButton;
    }

    public void setAdaugaProdusButton(JButton adaugaProdusButton) {
        this.adaugaProdusButton = adaugaProdusButton;
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

    public AdminManageProducts() throws SQLException {
        mergiInapoiLaMeniulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AdminMain am = new AdminMain();
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
                try {
                    Login lg = new Login();
                    lg.frameSetup(lg);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        cautaUnProdusDupaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    searchForName(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        stergeUnProdusDupaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement ps = null;
                try {
                    ps = con.prepareStatement("DELETE FROM PRODUSEMOBILA WHERE NUME=?");
                    ps.setString(1, deleteText.getText());
                    if (ps.executeUpdate() != 0)
                        JOptionPane.showMessageDialog(null, "Produsul " + deleteText.getText() + " a fost sters!");
                    else
                        JOptionPane.showMessageDialog(null, "Produsul " + deleteText.getText() + " nu se afla in stoc!");
                    deleteText.setText("");
                    showTable(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        arataToateProduseleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTable(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        adaugaProdusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numeText.getText().equalsIgnoreCase("") || descriereText.getText().equalsIgnoreCase("") || stocText.getText().equalsIgnoreCase(""))
                    JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate!");
                else {
                    PreparedStatement ps = null;
                    try {
                        try {
                            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUSEMOBILA WHERE NUME=?");
                            ps1.setString(1, numeText.getText());
                            ResultSet rs = ps1.executeQuery();
                            if (rs.next()) {
                                JOptionPane.showMessageDialog(null, "Produsul se afla in stoc!");
                            } else {
                                ps = con.prepareStatement("INSERT INTO PRODUSEMOBILA VALUES (?,?,?)");
                                ps.setString(1, numeText.getText());
                                ps.setString(2, descriereText.getText());
                                ps.setString(3, stocText.getText());
                                Float.parseFloat(stocText.getText());
                                if (ps.executeUpdate() != 0)
                                    JOptionPane.showMessageDialog(null, "Produsul " + numeText.getText() + " a fost adaugat in stoc!");
                                numeText.setText("Nume");
                                descriereText.setText("Descriere");
                                stocText.setText("Stoc");
                                showTable(table1);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Stocul trebuie sa fie un numar!");
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        modificaUnProdusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement ps = null;
                try {
                    try {
                        if (produsModificare.getText().equalsIgnoreCase("") || numeModif.getText().equalsIgnoreCase("") || descriereText.getText().equalsIgnoreCase("") || stocModif.getText().equalsIgnoreCase(""))
                            JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate!");
                        else {
                            ps = con.prepareStatement("UPDATE PRODUSEMOBILA SET NUME=?, DESCRIERE=?, STOC=? WHERE NUME=?");
                            ps.setString(1, numeModif.getText());
                            ps.setString(2, descriereModif.getText());
                            ps.setFloat(3, Float.parseFloat(stocModif.getText()));
                            ps.setString(4, produsModificare.getText());
                            if (ps.executeUpdate() != 0)
                                JOptionPane.showMessageDialog(null, "Produsul a fost modificat!");
                            else
                                JOptionPane.showMessageDialog(null, "Produsul nu se afla in stoc!");
                            numeModif.setText("Nume nou");
                            descriereModif.setText("Descriere noua");
                            stocModif.setText("Stoc nou");
                            produsModificare.setText("Nume produs");
                            showTable(table1);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Stocul trebuie sa fie un numar!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    protected void frameSetup(AdminManageProducts amp) throws SQLException {
        amp.setContentPane(getPanel1());
        amp.setTitle("MobART");
        amp.setSize(1920, 1080);
        amp.setVisible(true);
        amp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUSEMOBILA");
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[3];
            row[0] = rs.getString("NUME");
            row[1] = rs.getString("DESCRIERE");
            row[2] = rs.getString("STOC");
            model.addRow(row);
        }
        table.setModel(model);
    }

    protected void searchForName(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUSEMOBILA WHERE NUME=?");
        ps.setString(1, searchNameText.getText());
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[3];
            row[0] = rs.getString("NUME");
            row[1] = rs.getString("DESCRIERE");
            row[2] = rs.getString("STOC");
            model.addRow(row);
        }
        table.setModel(model);
        searchNameText.setText("");
    }
}
