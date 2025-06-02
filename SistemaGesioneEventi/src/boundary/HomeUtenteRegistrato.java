package boundary;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HomeUtenteRegistrato extends JFrame {

    private static final long serialVersionUID = 1L;
    protected JPanel contentPanel;

    // Pulsanti resi accessibili alle sottoclassi
    protected JButton ricercaEventoButton;
    protected JButton catalogoEventiButton;

    public HomeUtenteRegistrato() {
        setTitle("Home Utente Registrato");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 245));
        setContentPane(contentPanel);

        // Inizializziamo ma NON li aggiungiamo ancora
        ricercaEventoButton = new JButton("Ricerca Evento");
        catalogoEventiButton = new JButton("Consulta Catalogo Eventi");

        ricercaEventoButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        catalogoEventiButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ricercaEventoButton.setBackground(new Color(52, 152, 219));
        ricercaEventoButton.setForeground(Color.WHITE);
        catalogoEventiButton.setBackground(new Color(46, 204, 113));
        catalogoEventiButton.setForeground(Color.WHITE);
        ricercaEventoButton.setFocusPainted(false);
        catalogoEventiButton.setFocusPainted(false);
        ricercaEventoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        catalogoEventiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Spazio per collegamento controller
        ricercaEventoButton.addActionListener(e -> {
            // TODO: richiamo controller Ricerca Evento
        });
        catalogoEventiButton.addActionListener(e -> {
            // TODO: richiamo controller Consulta Catalogo
        });
    }
}
