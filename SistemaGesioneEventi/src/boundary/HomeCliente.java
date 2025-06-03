package boundary;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class HomeCliente extends HomeUtenteRegistrato {

    private static final long serialVersionUID = 1L;

    public HomeCliente(String nome, String cognome, String email, String immagineProfilo) {
        super(); // inizializza finestra e pannello

        contentPanel.removeAll(); //puliamo i componenti già presenti da HomeUtenteRegistrato

        // Titolo
        JLabel titleLabel = new JLabel("Profilo Cliente");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Immagine profilo con cornice
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(120, 120));
        imagePanel.setMaximumSize(new Dimension(120, 120));
        imagePanel.setBorder(new LineBorder(new Color(52, 152, 219), 2, true));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel profilePic = new JLabel();
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        profilePic.setVerticalAlignment(SwingConstants.CENTER);

        if (immagineProfilo != null) {
            //Image img = immagineProfilo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            //profilePic.setIcon(new ImageIcon(img));
            System.out.println(immagineProfilo);
        } else {
            profilePic.setText("Nessuna immagine");
            profilePic.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            profilePic.setForeground(new Color(127, 140, 141));
            profilePic.setHorizontalAlignment(SwingConstants.CENTER);
            profilePic.setVerticalAlignment(SwingConstants.CENTER);
        }
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(profilePic, BorderLayout.CENTER);
        contentPanel.add(imagePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Info Cliente
        contentPanel.add(createStyledLabel("Nome: " + nome));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(createStyledLabel("Cognome: " + cognome));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(createStyledLabel("Email: " + email));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Pulsanti affiancati
        JButton ricercaEventoButton = new JButton("Ricerca Evento");
        JButton catalogoEventiButton = new JButton("Consulta Catalogo Eventi");

        ricercaEventoButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        catalogoEventiButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        ricercaEventoButton.setFocusPainted(false);
        catalogoEventiButton.setFocusPainted(false);

        ricercaEventoButton.setBackground(new Color(52, 152, 219));
        ricercaEventoButton.setForeground(Color.WHITE);
        catalogoEventiButton.setBackground(new Color(46, 204, 113));
        catalogoEventiButton.setForeground(Color.WHITE);

        ricercaEventoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        catalogoEventiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Spazio per collegamento al controller
        ricercaEventoButton.addActionListener(e -> {
            // TODO: richiamo controller per Ricerca Evento
        });

        catalogoEventiButton.addActionListener(e -> {
            CatalogoEventi catalogo = new CatalogoEventi(this);
            catalogo.setVisible(true);
            this.setVisible(false);
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(460, 40));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ricercaEventoButton.setMaximumSize(new Dimension(220, 40));
        catalogoEventiButton.setMaximumSize(new Dimension(220, 40));

        buttonsPanel.add(ricercaEventoButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(catalogoEventiButton);

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


}
