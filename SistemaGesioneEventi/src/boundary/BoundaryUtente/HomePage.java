package boundary.BoundaryUtente;

import boundary.BoundaryUtenteRegistrato.BoundaryAutenticazione;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HomePage extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    public HomePage() {
        setTitle("TicketTwo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1129, 522);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("TicketTwo");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(new Color(44, 62, 80));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> {
            BoundaryAutenticazione interfacciaLogin = new BoundaryAutenticazione();
            interfacciaLogin.setVisible(true);
            dispose();
        });

        JButton signInButton = new JButton("Sign in");
        styleButton(signInButton);
        signInButton.addActionListener(e -> {
            BoundaryRegistrazione signInInterface = new BoundaryRegistrazione();
            signInInterface.setVisible(true);
            dispose();
        });

        buttonsPanel.add(loginButton);
        buttonsPanel.add(signInButton);

        topPanel.add(buttonsPanel, BorderLayout.EAST);

        JPanel centerPanel = getJPanel();
        add(centerPanel, BorderLayout.CENTER);
    }

    private static JPanel getJPanel() {
        JPanel centerPanel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color colorStart = new Color(173, 216, 230);
                Color colorEnd = new Color(70, 130, 180);
                GradientPaint gp = new GradientPaint(0, 0, colorStart, 0, height, colorEnd);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        centerPanel.setOpaque(false);
        return centerPanel;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41, 128, 185));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(52, 152, 219));
            }
        });
    }
}
