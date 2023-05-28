import oracle.jdbc.proxy.annotation.Pre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Result;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserCosCumparaturi extends JFrame {
    private JButton mergiInapoiLaMeniulButton;
    private JButton delogheazaTeButton;
    private JTable table1;
    private JButton stergeDinCosButton;
    private JButton modificaCantitateaButton;
    private JTextField modificareCantitateText;
    private JButton plaseazaComandaButton;
    private JPanel panel1;
    private String userConectat;
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

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JButton getStergeDinCosButton() {
        return stergeDinCosButton;
    }

    public void setStergeDinCosButton(JButton stergeDinCosButton) {
        this.stergeDinCosButton = stergeDinCosButton;
    }

    public JButton getModificaCantitateaButton() {
        return modificaCantitateaButton;
    }

    public void setModificaCantitateaButton(JButton modificaCantitateaButton) {
        this.modificaCantitateaButton = modificaCantitateaButton;
    }

    public JTextField getModificareCantitateText() {
        return modificareCantitateText;
    }

    public void setModificareCantitateText(JTextField modificareCantitateText) {
        this.modificareCantitateText = modificareCantitateText;
    }

    public JButton getPlaseazaComandaButton() {
        return plaseazaComandaButton;
    }

    public void setPlaseazaComandaButton(JButton plaseazaComandaButton) {
        this.plaseazaComandaButton = plaseazaComandaButton;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public String getUserConectat() {
        return userConectat;
    }

    public void setUserConectat(String userConectat) {
        this.userConectat = userConectat;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public UserCosCumparaturi() throws SQLException {
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
        stergeDinCosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table1.getSelectedRow();
                    int column = 0;
                    PreparedStatement ps = con.prepareStatement("DELETE FROM COMENZIMOBILA WHERE NUMEPRODUS=?");
                    ps.setString(1, table1.getValueAt(selectedRow, column).toString());
                    if (ps.executeUpdate() != 0)
                        JOptionPane.showMessageDialog(null, "Produs sters din cosul de cumparaturi!");
                    showTable(table1);
                } catch (IndexOutOfBoundsException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Selecteaza un produs!");
                }
            }
        });
        modificaCantitateaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Integer.parseInt(modificareCantitateText.getText()) <= 0)
                        JOptionPane.showMessageDialog(null, "Cantitatea trebuie sa fie un numar pozitiv nenul.");
                    else {
                        int selectedRow1 = table1.getSelectedRow();
                        int column1 = 0;
                        String numeVerif = table1.getValueAt(selectedRow1, column1).toString();
                        PreparedStatement ps1 = con.prepareStatement("SELECT * FROM PRODUSEMOBILA WHERE NUME=?");
                        ps1.setString(1, numeVerif);
                        ResultSet rs = ps1.executeQuery();
                        int stocDisponibil = -1;
                        while (rs.next()) {
                            stocDisponibil = rs.getInt(3);
                        }
                        if (stocDisponibil == -1) {
                            JOptionPane.showMessageDialog(null, "Produsul nu mai e pe stoc!");
                            int selectedRow2 = table1.getSelectedRow();
                            int column2 = 0;
                            PreparedStatement ps = con.prepareStatement("DELETE FROM COMENZIMOBILA WHERE NUMEPRODUS=?");
                            ps.setString(1, table1.getValueAt(selectedRow2, column2).toString());
                            if (ps.executeUpdate() != 0)
                                JOptionPane.showMessageDialog(null, "Produs sters din cosul de cumparaturi!");
                            showTable(table1);
                        }
                        if (stocDisponibil < Integer.parseInt(modificareCantitateText.getText()))
                            JOptionPane.showMessageDialog(null, "Stoc indisponibil!");
                        else {
                            int selectedRow = table1.getSelectedRow();
                            int column = 0;
                            PreparedStatement ps = con.prepareStatement("UPDATE COMENZIMOBILA SET CANTITATE=? WHERE NUMEPRODUS=?");
                            ps.setInt(1, Integer.parseInt(modificareCantitateText.getText()));
                            ps.setString(2, table1.getValueAt(selectedRow, column).toString());
                            if (ps.executeUpdate() != 0)
                                JOptionPane.showMessageDialog(null, "Cantitate modificata!");
                            showTable(table1);
                        }
                    }
                } catch (IndexOutOfBoundsException | SQLException | NumberFormatException Ex) {
                    JOptionPane.showMessageDialog(null, "Selecteaza un produs, iar cantitatea trebuie sa fie un numar pozitiv nenul!");
                }
                modificareCantitateText.setText("");
            }
        });
        plaseazaComandaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement ps = null;
                    ps = con.prepareStatement("SELECT * FROM COMENZIMOBILA WHERE NUME=?");
                    ps.setString(1, userConectat);
                    ResultSet rs = ps.executeQuery();
                    boolean ok = false;
                    while (rs.next()) {
                        ok = true;
                    }
                    if (ok == true) {
                        int option = JOptionPane.showOptionDialog(null, "Doriti sa plasati comanda?", "Confirmare comanda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (option == JOptionPane.YES_OPTION) {
                            //verificare stoc
                            boolean disponibilitate = true;
                            PreparedStatement psStoc = con.prepareStatement("SELECT * FROM COMENZIMOBILA WHERE NUME=?");
                            psStoc.setString(1, userConectat);
                            ResultSet rsStoc = psStoc.executeQuery();
                            while (rsStoc.next()) {
                                String produs = rsStoc.getString(2);
                                int stoc = rsStoc.getInt(3);
                                PreparedStatement psStocuri = con.prepareStatement("SELECT * FROM PRODUSEMOBILA WHERE NUME=?");
                                psStocuri.setString(1, produs);
                                ResultSet rsStocuri = psStocuri.executeQuery();
                                rsStocuri.next();
                                int stocBaza = rsStocuri.getInt(3);
                                if (stocBaza < stoc) {
                                    disponibilitate = false;
                                    JOptionPane.showMessageDialog(null, "Stocul produsului " + produs + " este prea mic. (A fost plasata o alta comanda inainte.)");
                                    break;
                                }
                            }
                            if (disponibilitate == true) {
                                //actualizare stoc
                                PreparedStatement psStoc1 = con.prepareStatement("SELECT * FROM COMENZIMOBILA WHERE NUME=?");
                                psStoc1.setString(1, userConectat);
                                ResultSet rsStoc1 = psStoc1.executeQuery();
                                while (rsStoc1.next()) {
                                    String produs = rsStoc1.getString(2);
                                    int stoc = rsStoc1.getInt(3);
                                    PreparedStatement psStocuri1 = con.prepareStatement("SELECT * FROM PRODUSEMOBILA WHERE NUME=?");
                                    psStocuri1.setString(1, produs);
                                    ResultSet rsStocuri1 = psStocuri1.executeQuery();
                                    rsStocuri1.next();
                                    int stocBaza = rsStocuri1.getInt(3);
                                    int stocNou = stocBaza - stoc;
                                    PreparedStatement psActualizare = con.prepareStatement("UPDATE PRODUSEMOBILA SET STOC=? WHERE NUME=?");
                                    psActualizare.setInt(1, stocNou);
                                    psActualizare.setString(2, produs);
                                    psActualizare.executeUpdate();
                                }
                                //datele user-ului
                                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM USERMOBILA WHERE USERNAME=?");
                                ps2.setString(1, userConectat);
                                ResultSet rsUser = ps2.executeQuery();
                                rsUser.next();

                                //verificare prima comanda
                                PreparedStatement ps3 = con.prepareStatement("SELECT * FROM COMENZIPLASATEMOBILA ORDER BY ID DESC FETCH FIRST 1 ROW ONLY");
                                ResultSet rsVerif = ps3.executeQuery();
                                rsVerif.next();


                                int ID = 0;
                                PreparedStatement ps1 = con.prepareStatement("INSERT INTO COMENZIPLASATEMOBILA VALUES (?,?,?,?,?,?,?,?)");

                                try {
                                    ps1.setInt(1, rsVerif.getInt(1) + 1);
                                    ID = rsVerif.getInt(1) + 1;
                                } catch (SQLException ex) {
                                    ps1.setInt(1, 1);
                                    ID = 1;
                                }
                                ps1.setString(2, rsUser.getString(1));
                                ps1.setString(3, rsUser.getString(7));
                                ps1.setString(4, rsUser.getString(8));
                                ps1.setString(5, rsUser.getString(6));
                                ps1.setString(6, rsUser.getString(4));
                                ps1.setString(7, "In verificare!");
                                ps1.setString(8, rsUser.getString(5));
                                if (ps1.executeUpdate() != 0) {
                                    JOptionPane.showMessageDialog(null, "Comanda plasata cu succes!");
                                    //plasare produse in verif admin

                                    PreparedStatement psAdminComenzi = con.prepareStatement("SELECT * FROM COMENZIMOBILA WHERE NUME=?");
                                    psAdminComenzi.setString(1, userConectat);

                                    ResultSet rsAdmin = psAdminComenzi.executeQuery();
                                    while (rsAdmin.next()) {
                                        PreparedStatement psAdmin = con.prepareStatement("INSERT INTO ADMINCOMENZIMOBILA VALUES (?,?,?)");
                                        psAdmin.setInt(1, ID);
                                        psAdmin.setString(2, rsAdmin.getString(2));
                                        psAdmin.setString(3, rsAdmin.getString(3));
                                        psAdmin.executeUpdate();
                                    }

                                    //golire cos
                                    PreparedStatement psGolire = con.prepareStatement("DELETE FROM COMENZIMOBILA WHERE NUME=?");
                                    psGolire.setString(1, userConectat);
                                    if (psGolire.executeUpdate() != 0)
                                        JOptionPane.showMessageDialog(null, "Cos golit!");
                                    showTable(table1);
                                }
                            } else if (option == JOptionPane.NO_OPTION) {
                                //anulare
                            }
                        }
                    } else
                        JOptionPane.showMessageDialog(null, "Cosul de cumparaturi este gol!");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    protected void frameSetup(UserCosCumparaturi ucc, String user) throws SQLException {
        userConectat = user;
        ucc.setContentPane(getPanel1());
        ucc.setTitle("MobART");
        ucc.setSize(1920, 1080);
        ucc.setVisible(true);
        ucc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showTable(table1);
    }

    protected void showTable(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM COMENZIMOBILA WHERE NUME=?");
        ps.setString(1, userConectat);
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[2];
            row[0] = rs.getString("NUMEPRODUS");
            row[1] = rs.getInt("CANTITATE");
            model.addRow(row);
        }
        table.setModel(model);
    }
}
