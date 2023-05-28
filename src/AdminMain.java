import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminMain extends JFrame {
    private JButton mergiLaProduseButton;
    private JButton mergiLaTicheteButton;
    private JButton mergiLaUseriButton;
    private JButton delogheazaTeButton;
    private JPanel panel1;
    private JButton mergiLaComenziButton;
    private Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "db");

    public JButton getMergiLaProduseButton() {
        return mergiLaProduseButton;
    }

    public void setMergiLaProduseButton(JButton mergiLaProduseButton) {
        this.mergiLaProduseButton = mergiLaProduseButton;
    }

    public JButton getMergiLaTicheteButton() {
        return mergiLaTicheteButton;
    }

    public void setMergiLaTicheteButton(JButton mergiLaTicheteButton) {
        this.mergiLaTicheteButton = mergiLaTicheteButton;
    }

    public JButton getMergiLaUseriButton() {
        return mergiLaUseriButton;
    }

    public void setMergiLaUseriButton(JButton mergiLaUseriButton) {
        this.mergiLaUseriButton = mergiLaUseriButton;
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

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public AdminMain() throws SQLException {
        mergiLaProduseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminManageProducts amp = null;
                try {
                    amp = new AdminManageProducts();
                    amp.frameSetup(amp);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        mergiLaTicheteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminManageTickets amt = null;
                try {
                    amt = new AdminManageTickets();
                    amt.frameSetup(amt);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        mergiLaUseriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminManageUsers amu = null;
                try {
                    amu = new AdminManageUsers();
                    amu.frameSetup(amu);
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
        mergiLaComenziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AdminComenzi ac = new AdminComenzi();
                    ac.frameSetup(ac);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    protected void frameSetup(AdminMain am) throws SQLException {
        am.setContentPane(getPanel1());
        am.setTitle("MobART");
        am.setSize(1920, 1080);
        am.setVisible(true);
        am.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
