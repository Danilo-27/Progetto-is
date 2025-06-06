package boundary;

import DTO.DTOUtente;
import control.Controller;
import entity.EntityPiattaforma;
import exceptions.RegistrationFailedException;
import exceptions.LoginFailedException;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BoundaryAutenticazione extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPasswordField passwordField;
    private JTextField emailField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BoundaryAutenticazione frame = new BoundaryAutenticazione();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public BoundaryAutenticazione() {
        setTitle("Accesso Utente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(20, 40, 20, 40));
        contentPane.setBackground(new Color(245, 245, 245));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
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
        homeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
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
                DTOUtente utente = Controller.Autenticazione(email, password);
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
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createTitledBorder(placeholder));
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
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
        if (password == null || password.length() == 0) {
            return "Errore: Password vuota.";
        }
        if (password.length() > 40) {
            return "Errore: Lunghezza password maggiore di 40 caratteri.";
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

//    // 🔐 Simulazione autenticazione (da sostituire con Controller BCED reale)
//    private boolean verificaCredenziali(String email, String password) {
//        return email.equals("f@g.com") && password.equals("@123");
//    }

}
