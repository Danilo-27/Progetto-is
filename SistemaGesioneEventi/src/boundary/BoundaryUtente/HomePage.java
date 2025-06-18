package boundary.BoundaryUtente;

import boundary.BoundaryUtenteRegistrato.BoundaryAutenticazione;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;

public class HomePage extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    private BufferedImage backgroundImage;

    public HomePage() {
        // Carica immagine da file esterno
        try {
            backgroundImage = ImageIO.read(new File("SistemaGesioneEventi/images/sfondo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //icona dell'applicazione
        BufferedImage iconImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = iconImage.createGraphics();

        g2d.setColor(new Color(41, 128, 185)); // Sfondo blu
        g2d.fillRect(0, 0, 64, 64);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 32));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "T2";
        int x = (64 - fm.stringWidth(text)) / 2;
        int y = ((64 - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, x, y);
        g2d.dispose();
        setIconImage(iconImage);


        setTitle("TickeTwo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1129, 522);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

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

        // Pannello con sfondo immagine
        JPanel centerPanel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        centerPanel.setOpaque(false);
        centerPanel.setLayout(null); // Per posizionamento assoluto

        centerPanel.setLayout(null);

        add(centerPanel, BorderLayout.CENTER);
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
