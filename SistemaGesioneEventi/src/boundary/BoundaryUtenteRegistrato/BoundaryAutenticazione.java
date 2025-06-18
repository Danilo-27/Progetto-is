package boundary.BoundaryUtenteRegistrato;

import DTO.DTOUtente;
import boundary.BoundaryAmministratore.HomeAmministratore;
import boundary.BoundaryCliente.HomeCliente;
import boundary.BoundaryUtente.HomePage;
import boundary.Sessione;
import control.Controller;
import exceptions.LoginFailedException;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BoundaryAutenticazione extends JFrame {

    private final JPanel contentPane;
    private final JPasswordField passwordField;
    private final JTextField emailField;
    private static final String SEGOE="Segoe UI";

//    private final String = "Segoe UI";


    public BoundaryAutenticazione() {
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

        setTitle("Accesso Utente");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 40, 20, 40));
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font(SEGOE, Font.BOLD, 26));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(titleLabel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));

        emailField = createTextField("Email");
        passwordField = createPasswordField("Password");

        contentPane.add(emailField);
        contentPane.add(Box.createRigidArea(new Dimension(0, 15)));
        contentPane.add(passwordField);
        contentPane.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton homeButton = new JButton("Home");
        homeButton.setBackground(new Color(200, 200, 200));
        homeButton.setForeground(Color.BLACK);
        homeButton.setFont(new Font(SEGOE, Font.PLAIN, 14));
        homeButton.setFocusPainted(false);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        homeButton.addActionListener(e -> {
            new HomePage().setVisible(true);
            dispose();
        });

        JButton loginButton = new JButton("Accedi");
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font(SEGOE, Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.addActionListener(e -> {

            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            String emailValidation = validateEmail(email);
            String passwordValidation = validatePassword(password);

            if (!emailValidation.equals("OK")) {
                JOptionPane.showMessageDialog(this, emailValidation, "Errore Email", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Sessione section = Sessione.getInstance();
                DTOUtente utente = Controller.autenticazione(email, password);
                section.setUtenteAutenticato(email,utente.getTipoUtente());

                if (utente.getTipoUtente()==0){
                    new HomeCliente(utente.getNome(),utente.getCognome(),email, utente.getImmagine()).setVisible(true);
                    dispose();
                }else{
                    new HomeAmministratore(utente.getNome(),utente.getCognome(),email).setVisible(true);
                    dispose();
                }

            } catch (LoginFailedException Lfe) {
                JOptionPane.showMessageDialog(contentPane,
                        Lfe.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        buttonPanel.add(homeButton);
        buttonPanel.add(loginButton);

        contentPane.add(buttonPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font(SEGOE, Font.PLAIN, 16));
        field.setBorder(BorderFactory.createTitledBorder(placeholder));
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font(SEGOE, Font.PLAIN, 16));
        field.setBorder(BorderFactory.createTitledBorder(placeholder));
        return field;
    }

    private String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "Errore: Email non memorizzata o vuota.";
        }
        if (email.length() > 50) {
            return "Errore: Lunghezza email maggiore di 50 caratteri.";
        }
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            return "Errore: Formato email non valido. Deve essere del tipo esempio@dominio.estensione.";
        }
        return "OK";
    }

    private String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Errore: Password vuota.";
        }
        if (password.length() > 40) {
            return "Errore: Lunghezza PASSWORD maggiore di 40 caratteri.";
        }
        if (!containsSpecialCharacter(password)) {
            return "Errore: Password deve contenere almeno un carattere speciale.";
        }
        return "OK";
    }

    private boolean containsSpecialCharacter(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
