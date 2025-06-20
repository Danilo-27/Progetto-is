package boundary.BoundaryUtente;

import control.Controller;
import exceptions.RegistrationFailedException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BoundaryRegistrazione extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private static final String ERRORE ="Errore";
    

    public BoundaryRegistrazione() {
        //icona
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

        setTitle("Registrazione Utente");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 30, 20, 30));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBackground(new Color(245, 245, 245));
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Crea un Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(titleLabel);

        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextField nomeField = createTextField("Nome");
        JTextField cognomeField = createTextField("Cognome");
        JTextField emailField = createTextField("Email");
        JPasswordField passwordField = createPasswordField();

        contentPane.add(nomeField);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(cognomeField);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(emailField);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(passwordField);
        contentPane.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton homeButton = new JButton("Home");
        homeButton.setBackground(new Color(189, 195, 199));
        homeButton.setForeground(Color.BLACK);
        homeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setFocusPainted(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        homeButton.addActionListener(e -> {
            new HomePage().setVisible(true);
            dispose();
        });

        JButton registerButton = new JButton("Registrati");
        registerButton.setBackground(new Color(52, 152, 219));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        registerButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Nome non può essere vuoto.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nome.length() > 20) {
                JOptionPane.showMessageDialog(contentPane, "Nome non valido. Deve essere lungo ≤ 20 caratteri.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cognome.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Cognome non può essere vuoto.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cognome.length() > 30) {
                JOptionPane.showMessageDialog(contentPane, "Cognome non valido. Deve essere lungo ≤ 30 caratteri.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Email non può essere vuota.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (email.length() > 50) {
                JOptionPane.showMessageDialog(contentPane, "Email non valida. Deve essere lunga ≤ 50 caratteri.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Controllo formato email
            String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
            if (!email.matches(emailRegex)) {
                JOptionPane.showMessageDialog(contentPane, "Formato email non valido. Deve essere del tipo esempio@dominio.estensione", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Password non può essere vuota.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() > 40) {
                JOptionPane.showMessageDialog(contentPane, "Password non valida. Deve essere lunga ≤ 40 caratteri.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>§°?].*")) {
                JOptionPane.showMessageDialog(contentPane, "La PASSWORD deve contenere almeno un carattere speciale.", ERRORE, JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Controller.registrazione(password, nome, cognome, email);
                JOptionPane.showMessageDialog(contentPane,
                        "Registrazione completata:\nNome: " + nome +
                                "\nCognome: " + cognome +
                                "\nEmail: " + email);
                new HomePage().setVisible(true);
                dispose();

            } catch (RegistrationFailedException ex) {
                JOptionPane.showMessageDialog(contentPane, "Email già registrata", ERRORE, JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(homeButton);
        buttonPanel.add(registerButton);
        contentPane.add(buttonPanel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createTitledBorder(placeholder));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createTitledBorder("Password"));
        return field;
    }
}
