import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserMain extends JFrame {
    private JTable table1;
    private JPanel panel1;
    private JButton cosDeCumparaturiButton;
    private JButton faUnTichetButton;
    private JButton delogheazaTeButton;
    private JTextField cautaProdusText;
    private JButton cautaDupaNumeButton;
    private JButton arataToateProduseleButton;
    private JButton adaugaInCosButton;
    private JTextField cantitateCumparareText;
    private JButton comenziPlasateButton;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");
    private String userConectat;

    public String getUserConectat() {
        return userConectat;
    }

    public void setUserConectat(String userConectat) {
        this.userConectat = userConectat;
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

    public JButton getCosDeCumparaturiButton() {
        return cosDeCumparaturiButton;
    }

    public void setCosDeCumparaturiButton(JButton cosDeCumparaturiButton) {
        this.cosDeCumparaturiButton = cosDeCumparaturiButton;
    }

    public JButton getFaUnTichetButton() {
        return faUnTichetButton;
    }

    public void setFaUnTichetButton(JButton faUnTichetButton) {
        this.faUnTichetButton = faUnTichetButton;
    }

    public JButton getDelogheazaTeButton() {
        return delogheazaTeButton;
    }

    public void setDelogheazaTeButton(JButton delogheazaTeButton) {
        this.delogheazaTeButton = delogheazaTeButton;
    }

    public JTextField getCautaProdusText() {
        return cautaProdusText;
    }

    public void setCautaProdusText(JTextField cautaProdusText) {
        this.cautaProdusText = cautaProdusText;
    }

    public JButton getCautaDupaNumeButton() {
        return cautaDupaNumeButton;
    }

    public void setCautaDupaNumeButton(JButton cautaDupaNumeButton) {
        this.cautaDupaNumeButton = cautaDupaNumeButton;
    }

    public JButton getArataToateProduseleButton() {
        return arataToateProduseleButton;
    }

    public void setArataToateProduseleButton(JButton arataToateProduseleButton) {
        this.arataToateProduseleButton = arataToateProduseleButton;
    }

    public JButton getAdaugaInCosButton() {
        return adaugaInCosButton;
    }

    public void setAdaugaInCosButton(JButton adaugaInCosButton) {
        this.adaugaInCosButton = adaugaInCosButton;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public UserMain() throws SQLException {
        cosDeCumparaturiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserCosCumparaturi ucc = new UserCosCumparaturi();
                    ucc.frameSetup(ucc, userConectat);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        faUnTichetButton.addActionListener(new ActionListener() {
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
                    JOptionPane.showMessageDialog(null, "Te-ai delogat cu succes!");
                    dispose();
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
        cautaDupaNumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showName(table1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        adaugaInCosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table1.getSelectedRow();
                    int column = 0;
                    boolean disponibilitate = false;
                    Object value = table1.getValueAt(selectedRow, column);
                    PreparedStatement psVerif = con.prepareStatement("SELECT * FROM COMENZIMOBILA WHERE NUME=? AND NUMEPRODUS=?");
                    psVerif.setString(1, userConectat);
                    psVerif.setString(2, value.toString());
                    ResultSet rsVerif = psVerif.executeQuery();
                    while (rsVerif.next()) {
                        disponibilitate = true;
                        break;
                    }
                    if (disponibilitate == true)
                        JOptionPane.showMessageDialog(null, "Produsul se afla deja in cos!");
                    else {
                        if (cantitateCumparareText.getText().equalsIgnoreCase(""))
                            JOptionPane.showMessageDialog(null, "Alege cantitatea!");
                        else {
                            try {
                                Object valueStoc = table1.getValueAt(selectedRow, 2);
                                if (Integer.parseInt(cantitateCumparareText.getText()) > Integer.parseInt(valueStoc.toString()))
                                    JOptionPane.showMessageDialog(null, "Nu exista atatea produse in stoc, alegeti o cantitate mai mica! ( Verifica stocul disponibil! )");
                                else {
                                    PreparedStatement ps = con.prepareStatement("INSERT INTO COMENZIMOBILA VALUES (?,?,?)");
                                    ps.setString(1, userConectat);
                                    ps.setString(2, value.toString());
                                    ps.setInt(3, Integer.parseInt(cantitateCumparareText.getText()));
                                    if (ps.executeUpdate() != 0)
                                        JOptionPane.showMessageDialog(null, "Produsul a fost adaugat in cos!");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Stocul trebuie sa fie un numar!");
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Selecteaza un produs!");
                }
                cantitateCumparareText.setText("Cantitate (numar)!");
            }
        });
        comenziPlasateButton.addActionListener(new ActionListener() {
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
    }

    protected void frameSetup(UserMain um, String user) throws SQLException {
        userConectat = user;
        um.setContentPane(getPanel1());
        um.setTitle("MobART");
        um.setSize(1920, 1080);
        um.setVisible(true);
        um.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            row[2] = rs.getInt("STOC");
            model.addRow(row);
        }
        table.setModel(model);
    }

    protected void showName(JTable table) throws SQLException {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"C1", "C2", "C3"});
        PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUSEMOBILA WHERE NUME=?");
        ps.setString(1, cautaProdusText.getText());
        ResultSet rs = ps.executeQuery();
        model.setRowCount(0);
        while (rs.next()) {
            Object[] row = new Object[3];
            row[0] = rs.getString("NUME");
            row[1] = rs.getString("DESCRIERE");
            row[2] = rs.getInt("STOC");
            model.addRow(row);
        }
        table.setModel(model);
        cautaProdusText.setText("");
    }
}