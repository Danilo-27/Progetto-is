package boundary.BoundaryUtenteRegistrato;

import java.awt.*;
import java.io.Serial;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import DTO.DTOEvento;
import exceptions.EventoNotFoundException;

public class HomeUtenteRegistrato extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    protected JPanel contentPanel;
    protected JPanel titlePanel;
    protected JPanel infoPanel;
    protected JPanel buttonsPanel;

    // Componenti condivisi
    protected JLabel titleLabel;
    protected JButton ricercaEventoButton;
    protected JButton catalogoEventiButton;

    public HomeUtenteRegistrato() {
        setTitle("Home Utente Registrato");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 245));
        setContentPane(contentPanel);

        initializePanels();
        initializeComponents();

        // ✅ Solo per il padre, non per le classi figlie
        if (this.getClass() == HomeUtenteRegistrato.class) {
            layoutComponents();
        }
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
    }

    protected void initializeComponents() {
        titleLabel = new JLabel("Home Utente Registrato");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ricercaEventoButton = new JButton("Ricerca Evento");
        catalogoEventiButton = new JButton("Consulta Catalogo Eventi");

        styleButton(ricercaEventoButton, new Color(52, 152, 219));
        styleButton(catalogoEventiButton, new Color(46, 204, 113));

        ricercaEventoButton.setMaximumSize(new Dimension(220, 40));
        catalogoEventiButton.setMaximumSize(new Dimension(220, 40));

        setupDefaultActions();
    }

    protected void setupDefaultActions() {
        ricercaEventoButton.addActionListener(e -> {
            this.setVisible(false);
            new FormRicercaEvento(this);
        });

        catalogoEventiButton.addActionListener(e -> openCatalogoEventi());
    }

    protected void openCatalogoEventi() {
        try {
            // Verifica se ci sono eventi disponibili usando il Controller
            List<DTOEvento> eventi = control.Controller.consultaCatalogo();

            if (eventi.isEmpty()) {
                throw new EventoNotFoundException("Nessun evento disponibile nel catalogo");
            }

            // Se ci sono eventi, apri il catalogo
            CatalogoEventi catalogo = new CatalogoEventi(this);
            catalogo.setVisible(true);
            this.setVisible(false);

        } catch (EventoNotFoundException ex) {
            // Mostra popup quando non ci sono eventi
            JOptionPane.showMessageDialog(
                    this,
                    "Nessun evento disponibile",
                    "Catalogo Vuoto",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    protected void layoutComponents() {
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        buttonsPanel.setMaximumSize(new Dimension(460, 40));
        buttonsPanel.add(ricercaEventoButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(catalogoEventiButton);

        contentPanel.add(titlePanel);
        contentPanel.add(infoPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(buttonsPanel);
        contentPanel.add(Box.createVerticalGlue());
    }

    // ✅ Metodo helper per le classi figlie che vogliono i pulsanti del padre
    protected JPanel createParentButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ricercaEventoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        catalogoEventiButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(ricercaEventoButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(catalogoEventiButton);

        return panel;
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