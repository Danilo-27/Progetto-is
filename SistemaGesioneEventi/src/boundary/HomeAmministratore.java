package boundary;

import java.awt.*;
import javax.swing.*;

public class HomeAmministratore extends HomeUtenteRegistrato {

    private static final long serialVersionUID = 1L;

    public HomeAmministratore() {
        super(); // inizializza finestra e pannello

        contentPanel.removeAll(); // rimuove i componenti ereditati

        // Titolo
        JLabel titleLabel = new JLabel("Profilo Amministratore");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Pulsanti funzionalità
        JButton ricercaEventoButton = new JButton("Ricerca Evento");
        JButton catalogoEventiButton = new JButton("Consulta Catalogo Eventi");
        JButton creaEventoButton = new JButton("Crea Evento");

        styleButton(ricercaEventoButton, new Color(52, 152, 219));
        styleButton(catalogoEventiButton, new Color(46, 204, 113));
        styleButton(creaEventoButton, new Color(231, 76, 60));

        //aggiungere le funzionalità

        ricercaEventoButton.addActionListener(e -> {

            // TODO: collegamento controller ricerca evento

        });

        catalogoEventiButton.addActionListener(e -> {
            // TODO: collegamento controller catalogo eventi
        });

        creaEventoButton.addActionListener(e -> {
            //FormEvento form = new FormEvento(this);
            //form.setVisible(true);
            //this.setVisible(false);
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(680, 40));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ricercaEventoButton.setMaximumSize(new Dimension(200, 40));
        catalogoEventiButton.setMaximumSize(new Dimension(200, 40));
        creaEventoButton.setMaximumSize(new Dimension(200, 40));

        buttonsPanel.add(ricercaEventoButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(catalogoEventiButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(creaEventoButton);

        contentPanel.add(buttonsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Bottone Home in basso a destra
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 35));
        homeButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        homeButton.setBackground(new Color(189, 195, 199));
        homeButton.setForeground(Color.BLACK);
        homeButton.setFocusPainted(false);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        homeButton.addActionListener(e -> {
            new HomePage().setVisible(true);
            dispose();
        });

        bottomPanel.add(homeButton);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(bottomPanel);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}

