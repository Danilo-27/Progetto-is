package boundary;

import java.awt.*;
import javax.swing.*;

public class HomeAmministratore extends HomeUtenteRegistrato {

    private static final long serialVersionUID = 1L;
    private JButton creaEventoButton;

    public HomeAmministratore(String nome, String cognome, String email) {
        super();

        // Configura la finestra
        setPreferredSize(new Dimension(800, 600));
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));

        // Modifica il titolo ereditato
        titleLabel.setText("Profilo Amministratore");

        // Rimuove il layout predefinito del padre per creare un layout personalizzato
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        // Crea il pannello principale dell'amministratore
        JPanel mainPanel = createMainAdminPanel(nome, cognome, email);

        // Aggiungi il pannello principale al centro
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        // Refresh della UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createMainAdminPanel(String nome, String cognome, String email) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Sezione titolo
        JPanel titleSection = createTitleSection();
        mainPanel.add(titleSection);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Sezione informazioni amministratore
        JPanel infoSection = createAdminInfoSection(nome, cognome, email);
        mainPanel.add(infoSection);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Sezione pulsanti amministratore
        JPanel adminButtonsSection = createAdminButtonsSection();
        mainPanel.add(adminButtonsSection);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // ✅ Sezione pulsanti ereditati dal padre
        JPanel parentButtonsSection = createParentButtonsSection();
        mainPanel.add(parentButtonsSection);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Sezione pulsanti di navigazione
        JPanel navigationSection = createNavigationSection();
        mainPanel.add(navigationSection);

        // Spazio flessibile
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private JPanel createTitleSection() {
        JPanel titleSection = new JPanel();
        titleSection.setLayout(new BoxLayout(titleSection, BoxLayout.Y_AXIS));
        titleSection.setOpaque(false);
        titleSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo principale
        JLabel mainTitle = new JLabel("Dashboard Amministratore");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mainTitle.setForeground(new Color(44, 62, 80));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sottotitolo
        JLabel subtitle = new JLabel("Gestione Sistema Eventi");
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitle.setForeground(new Color(127, 140, 141));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleSection.add(mainTitle);
        titleSection.add(Box.createRigidArea(new Dimension(0, 5)));
        titleSection.add(subtitle);

        return titleSection;
    }

    private JPanel createAdminInfoSection(String nome, String cognome, String email) {
        JPanel infoSection = new JPanel();
        infoSection.setLayout(new BoxLayout(infoSection, BoxLayout.Y_AXIS));
        infoSection.setBackground(Color.WHITE);
        infoSection.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        infoSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoSection.setMaximumSize(new Dimension(500, 150));

        // Titolo sezione
        JLabel sectionTitle = new JLabel("Informazioni Amministratore");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoSection.add(sectionTitle);
        infoSection.add(Box.createRigidArea(new Dimension(0, 15)));

        // Informazioni amministratore
        infoSection.add(createStyledLabel("Nome: " + nome));
        infoSection.add(Box.createRigidArea(new Dimension(0, 8)));
        infoSection.add(createStyledLabel("Cognome: " + cognome));
        infoSection.add(Box.createRigidArea(new Dimension(0, 8)));
        infoSection.add(createStyledLabel("Email: " + email));

        return infoSection;
    }

    private JPanel createAdminButtonsSection() {
        JPanel adminSection = new JPanel();
        adminSection.setLayout(new BoxLayout(adminSection, BoxLayout.Y_AXIS));
        adminSection.setOpaque(false);
        adminSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo sezione
        JLabel sectionTitle = new JLabel("Funzioni Amministrative");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminSection.add(sectionTitle);
        adminSection.add(Box.createRigidArea(new Dimension(0, 15)));

        // Pulsante Crea Evento (specifico dell'amministratore)
        creaEventoButton = createCreaEventoButton();
        adminSection.add(creaEventoButton);

        return adminSection;
    }

    private JPanel createParentButtonsSection() {
        JPanel parentSection = new JPanel();
        parentSection.setLayout(new BoxLayout(parentSection, BoxLayout.Y_AXIS));
        parentSection.setOpaque(false);
        parentSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo sezione
        JLabel sectionTitle = new JLabel("Gestione Eventi");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        parentSection.add(sectionTitle);
        parentSection.add(Box.createRigidArea(new Dimension(0, 15)));

        // ✅ Ottieni i pulsanti dal padre e personalizzali per l'amministratore
        JPanel parentButtons = createParentButtonsPanel();

        // Personalizza le azioni per l'amministratore
        setupAdminActions();

        parentSection.add(parentButtons);

        return parentSection;
    }

    private JPanel createNavigationSection() {
        JPanel navigationSection = new JPanel();
        navigationSection.setLayout(new BoxLayout(navigationSection, BoxLayout.Y_AXIS));
        navigationSection.setOpaque(false);
        navigationSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Titolo sezione
        JLabel sectionTitle = new JLabel("Navigazione");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        navigationSection.add(sectionTitle);
        navigationSection.add(Box.createRigidArea(new Dimension(0, 15)));

        // Pulsanti di navigazione
        JPanel navigationButtons = new JPanel();
        navigationButtons.setLayout(new BoxLayout(navigationButtons, BoxLayout.X_AXIS));
        navigationButtons.setOpaque(false);
        navigationButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton logoutButton = createLogoutButton();

        navigationButtons.add(logoutButton);
        navigationButtons.add(Box.createRigidArea(new Dimension(15, 0)));

        navigationSection.add(navigationButtons);

        return navigationSection;
    }

    private JButton createCreaEventoButton() {
        JButton button = new JButton("Crea Nuovo Evento");
        styleButton(button, new Color(231, 76, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Aggiunge un'icona se disponibile
        button.addActionListener(e -> openFormEvento());

        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        styleButton(button, new Color(149, 165, 166));
        button.setMaximumSize(new Dimension(120, 35));
        button.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Sei sicuro di voler effettuare il logout?",
                    "Conferma Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                // TODO: Implementare logout
                new HomePage().setVisible(true);
                dispose();
            }
        });
        return button;
    }


    private void setupAdminActions() {
        // Rimuovi i listener esistenti dai pulsanti del padre per comportamento specifico admin
        for (java.awt.event.ActionListener listener : ricercaEventoButton.getActionListeners()) {
            ricercaEventoButton.removeActionListener(listener);
        }

        // Il catalogoEventiButton mantiene l'implementazione del padre (non modificato)

        // Comportamento specifico per ricerca evento amministratore
        ricercaEventoButton.addActionListener(e -> {
            // TODO: collegamento controller ricerca evento - comportamento specifico admin
            JOptionPane.showMessageDialog(
                    this,
                    "Ricerca Eventi - Modalità Amministratore\n(Funzionalità da implementare)",
                    "Ricerca Amministratore",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    // Metodo specifico dell'amministratore
    private void openFormEvento() {
        try {
            FormEvento form = new FormEvento(this);
            form.setVisible(true);
            this.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Errore nell'apertura del form di creazione evento:\n" + e.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Override del metodo createStyledLabel per un font più grande
    @Override
    protected JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}