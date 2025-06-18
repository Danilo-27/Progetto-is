package boundary.BoundaryCliente;

import DTO.DTOEvento;
import boundary.BoundaryUtenteRegistrato.HomeUtenteRegistrato;
import boundary.BoundaryUtente.HomePage;
import boundary.Sessione;
import control.Controller;
import exceptions.*;
import external.StubSistemaGestioneAcquisti;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.time.LocalDate;
import java.util.List;

public class HomeCliente extends HomeUtenteRegistrato {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String SEGOE = "Segoe UI";
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);

    private JList<DTOEvento> listaEventiOdierni;
    private DefaultListModel<DTOEvento> eventiModel;
    private JButton partecipaButton;

    public HomeCliente(String nome, String cognome, String email, String immagineProfilo) {
        super();
        impostaIconaApplicazione();
        configuraFinestra();

        titleLabel.setText("Profilo Personale");

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        contentPanel.add(creaPannelloSinistro(nome, cognome, email, immagineProfilo), BorderLayout.WEST);
        contentPanel.add(creaPannelloDestro(), BorderLayout.CENTER);

        caricaEventiOdierni();

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void impostaIconaApplicazione() {
        BufferedImage icon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = icon.createGraphics();
        g.setColor(PRIMARY_COLOR);
        g.fillRect(0, 0, 64, 64);
        g.setColor(Color.WHITE);
        g.setFont(new Font(SEGOE, Font.BOLD, 32));
        String text = "T2";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, (64 - fm.stringWidth(text)) / 2, ((64 - fm.getHeight()) / 2) + fm.getAscent());
        g.dispose();
        setIconImage(icon);
    }

    private void configuraFinestra() {
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setMinimumSize(new Dimension(1100, 700));
    }


    private JPanel creaPannelloSinistro(String nome, String cognome, String email, String immagineProfilo) {
        JPanel panel = creaPannelloBase(400);
        panel.add(creaEtichettaTitolo("Profilo Cliente"));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(creaPannelloImmagineProfilo(immagineProfilo));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(creaEtichettaTesto("Nome: " + nome));
        panel.add(creaEtichettaTesto("Cognome: " + cognome));
        panel.add(creaEtichettaTesto("Email: " + email));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(creaBottone("Acquista Biglietto", new Color(39, 174, 96), () -> {
            try {
                new FormAcquistoBiglietto(email, new StubSistemaGestioneAcquisti()).setVisible(true);
            } catch (EventoNotFoundException ex) {
                mostraErrore("ERRORE", ex.getMessage());
            }
        }));

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(creaBottone("Consulta Storico Biglietti", PRIMARY_COLOR, () -> {
            try {
                new FormStoricoBiglietti(email, this).setVisible(true);
                this.setVisible(false);
            } catch (BigliettoNotFoundException ex) {
                mostraInfo("Storico Vuoto", "Nessun biglietto trovato.");
            }
        }));

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createParentButtonsPanel());
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(creaBottone("Torna alla Home", new Color(231, 76, 60), () -> {
            new HomePage().setVisible(true);
            dispose();
        }));

        return panel;
    }

    private JPanel creaPannelloDestro() {
        JPanel panel = creaPannelloBase(0);
        panel.add(creaEtichettaTitolo("Eventi di Oggi"));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(creaScrollPaneListaEventi());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        partecipaButton = creaBottone("Partecipa all'Evento", new Color(155, 89, 182), this::gestisciPartecipazione);
        partecipaButton.setEnabled(false);
        panel.add(partecipaButton);

        return panel;
    }

    private JPanel creaPannelloBase(int width) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        if (width > 0) panel.setPreferredSize(new Dimension(width, 0));
        return panel;
    }

    private JLabel creaEtichettaTitolo(String testo) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font(SEGOE, Font.BOLD, 20));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel creaEtichettaTesto(String testo) {
        JLabel label = new JLabel(testo);
        label.setFont(new Font(SEGOE, Font.PLAIN, 15));
        label.setForeground(new Color(44, 62, 80));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(new EmptyBorder(5, 0, 0, 0));
        return label;
    }

    private JButton creaBottone(String testo, Color colore, Runnable azione) {
        JButton button = new JButton(testo);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 35));
        styleButton(button, colore);
        button.addActionListener(e -> azione.run());
        return button;
    }

    private JScrollPane creaScrollPaneListaEventi() {
        eventiModel = new DefaultListModel<>();
        listaEventiOdierni = new JList<>(eventiModel);
        listaEventiOdierni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventiOdierni.setFont(new Font(SEGOE, Font.PLAIN, 15));
        listaEventiOdierni.setFixedCellHeight(70);

        listaEventiOdierni.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            DTOEvento evento = (DTOEvento) value;
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(true);
            panel.setBackground(isSelected ? new Color(52, 152, 219) : (index % 2 == 0 ? Color.WHITE : new Color(248, 249, 250)));
            panel.setBorder(new EmptyBorder(8, 12, 8, 12));

            JLabel titolo = new JLabel(evento.getTitolo());
            titolo.setFont(new Font(SEGOE, Font.BOLD, 16));
            titolo.setForeground(isSelected ? Color.WHITE : new Color(44, 62, 80));

            JLabel dettagli = new JLabel(evento.getLuogo() + " • " + evento.getOra());
            dettagli.setFont(new Font(SEGOE, Font.PLAIN, 13));
            dettagli.setForeground(isSelected ? Color.LIGHT_GRAY : new Color(127, 140, 141));

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setOpaque(false);
            textPanel.add(titolo);
            textPanel.add(Box.createRigidArea(new Dimension(0, 4)));
            textPanel.add(dettagli);

            panel.add(textPanel, BorderLayout.CENTER);
            return panel;
        });

        listaEventiOdierni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                partecipaButton.setEnabled(listaEventiOdierni.getSelectedValue() != null);
            }
        });

        JScrollPane scroll = new JScrollPane(listaEventiOdierni);
        scroll.setPreferredSize(new Dimension(480, 350));
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel creaPannelloImmagineProfilo(String fileName) {
        return new JPanel() {
            private BufferedImage originalImage;

            {
                setBackground(Color.WHITE);
                try {
                    if (fileName != null) {
                        originalImage = javax.imageio.ImageIO.read(new java.io.File("SistemaGesioneEventi/images/" + fileName));
                    }
                } catch (Exception e) {
                    originalImage = null;
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                if (originalImage != null) {
                    double imgRatio = (double) originalImage.getWidth() / originalImage.getHeight();
                    double panelRatio = (double) panelWidth / panelHeight;

                    int drawWidth, drawHeight;
                    if (panelRatio > imgRatio) {
                        drawHeight = panelHeight;
                        drawWidth = (int) (panelHeight * imgRatio);
                    } else {
                        drawWidth = panelWidth;
                        drawHeight = (int) (panelWidth / imgRatio);
                    }

                    int x = (panelWidth - drawWidth) / 2;
                    int y = (panelHeight - drawHeight) / 2;

                    g2.drawImage(originalImage, x, y, drawWidth, drawHeight, null);
                } else {
                    g2.setFont(new Font(SEGOE, Font.ITALIC, 14));
                    g2.setColor(new Color(127, 140, 141));
                    g2.drawString("Nessuna immagine", 10, 20);
                }

                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(350, 300); // dimensione iniziale
            }
        };
    }



    private void caricaEventiOdierni() {
        try {
            List<DTOEvento> eventi = Controller.ricercaEvento(null, LocalDate.now(), null);
            eventiModel.clear();
            eventi.forEach(eventiModel::addElement);
        } catch (EventoNotFoundException ignored) {
            eventiModel.clear();
        }
    }

    private void gestisciPartecipazione() {
        DTOEvento evento = listaEventiOdierni.getSelectedValue();
        if (evento == null) return;

        String codice = JOptionPane.showInputDialog(this,
                String.format("Partecipazione all'evento:\n\nTitolo: %s\nLuogo: %s\nOrario: %s\nDescrizione: %s\n\nInserisci il codice univoco del biglietto:",
                        evento.getTitolo(),
                        evento.getLuogo() != null ? evento.getLuogo() : "Da definire",
                        evento.getOra(),
                        evento.getDescrizione()
                ),
                "Partecipazione Evento", JOptionPane.PLAIN_MESSAGE);

        if (codice == null || codice.trim().isEmpty()) {
            mostraInfo("Attenzione", "Codice biglietto non inserito.");
            return;
        }

        if (!codice.matches("[a-zA-Z0-9\\-]+")) {
            mostraInfo("Warning", "Caratteri non consentiti nel codice.");
            return;
        }

        try {
            Sessione s = Sessione.getInstance();
            Controller.partecipazioneEvento(codice.trim(), evento,s.getEmail());
        } catch (BigliettoNotFoundException e) {
            mostraErrore("Errore", e.getMessage());
        } catch (BigliettoConsumatoException e) {
            mostraErrore("Errore", "Biglietto già utilizzato.");
        }
    }

    private void mostraErrore(String titolo, String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, titolo, JOptionPane.ERROR_MESSAGE);
    }

    private void mostraInfo(String titolo, String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, titolo, JOptionPane.INFORMATION_MESSAGE);
    }
}