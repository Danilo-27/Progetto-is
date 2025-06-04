package boundary;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HomeUtenteRegistrato extends JFrame {

    private static final long serialVersionUID = 1L;
    protected JPanel contentPanel;
    protected JPanel titlePanel;
    protected JPanel infoPanel;
    protected JPanel buttonsPanel;
    protected JPanel bottomPanel;

    // Componenti condivisi
    protected JLabel titleLabel;
    protected JButton ricercaEventoButton;
    protected JButton catalogoEventiButton;
    protected JButton homeButton;

    public HomeUtenteRegistrato() {
        setTitle("Home Utente Registrato");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Pannello principale
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 245));
        setContentPane(contentPanel);

        // Inizializza i pannelli
        initializePanels();
        initializeComponents();
        layoutComponents();
    }

    protected void initializePanels() {
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
    }

    protected void initializeComponents() {
        // Titolo di default
        titleLabel = new JLabel("Home Utente Registrato");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pulsanti principali
        ricercaEventoButton = new JButton("Ricerca Evento");
        catalogoEventiButton = new JButton("Consulta Catalogo Eventi");

        styleButton(ricercaEventoButton, new Color(52, 152, 219));
        styleButton(catalogoEventiButton, new Color(46, 204, 113));

        ricercaEventoButton.setMaximumSize(new Dimension(220, 40));
        catalogoEventiButton.setMaximumSize(new Dimension(220, 40));

        // Pulsante Home
        homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 35));
        homeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        homeButton.setBackground(new Color(189, 195, 199));
        homeButton.setForeground(Color.BLACK);
        homeButton.setFocusPainted(false);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Action listeners di default
        setupDefaultActions();
    }

    protected void setupDefaultActions() {
        ricercaEventoButton.addActionListener(e -> {
            // TODO: richiamo controller Ricerca Evento
        });

        catalogoEventiButton.addActionListener(e -> {
            openCatalogoEventi();
        });

        homeButton.addActionListener(e -> {
            openHomePage();
        });
    }

    // Metodi comuni per aprire le interfacce
    protected void openCatalogoEventi() {
        CatalogoEventi catalogo = new CatalogoEventi(this);
        catalogo.setVisible(true);
        this.setVisible(false);
    }

    protected void openHomePage() {
        new HomePage().setVisible(true);
        dispose();
    }

    protected void layoutComponents() {
        // Aggiungi titolo
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Aggiungi pannello info (vuoto di default, le sottoclassi lo riempiranno)

        // Aggiungi pulsanti (layout di base per 2 pulsanti)
        buttonsPanel.setMaximumSize(new Dimension(460, 40));
        buttonsPanel.add(ricercaEventoButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(catalogoEventiButton);

        // Aggiungi pulsante home
        bottomPanel.add(homeButton);

        // Layout finale
        contentPanel.add(titlePanel);
        contentPanel.add(infoPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(buttonsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(bottomPanel);
    }

    protected void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    protected JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}