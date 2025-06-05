package boundary;

import DTO.DTOEvento;
import control.Controller;
import exceptions.AcquistoException;
import exceptions.BigliettoNotFoundException;
import exceptions.BigliettoConsumatoException;
import exceptions.EventoNotFoundException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class HomeCliente extends HomeUtenteRegistrato {

    private static final long serialVersionUID = 1L;
    private JList<DTOEvento> listaEventiOdierni;
    private JButton partecipaButton;
    private DefaultListModel<DTOEvento> eventiModel;

    public HomeCliente(String nome, String cognome, String email, String immagineProfilo) {
        super();

        // Configura la finestra
        setPreferredSize(new Dimension(1000, 600));
        setSize(new Dimension(1000, 600));
        setMinimumSize(new Dimension(1000, 600));

        // Modifica il titolo ereditato
        titleLabel.setText("Profilo Personale");

        // Rimuove il layout predefinito del padre per creare un layout personalizzato
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        // Crea il pannello sinistro per il profilo utente
        JPanel sinistraPanel = createLeftPanel(nome, cognome, email, immagineProfilo);

        // Crea il pannello destro per gli eventi
        JPanel destraPanel = createRightPanel();

        // Aggiungi i pannelli al contenuto principale
        contentPanel.add(sinistraPanel, BorderLayout.WEST);
        contentPanel.add(destraPanel, BorderLayout.CENTER);

        // Carica gli eventi odierni
        loadEventiOdierni();

        // Refresh della UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createLeftPanel(String nome, String cognome, String email, String immagineProfilo) {
        JPanel sinistraPanel = new JPanel();
        sinistraPanel.setLayout(new BoxLayout(sinistraPanel, BoxLayout.Y_AXIS));
        sinistraPanel.setBackground(Color.WHITE);
        sinistraPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));
        sinistraPanel.setPreferredSize(new Dimension(400, 0));

        // Titolo del profilo
        JLabel titolo = new JLabel("Profilo Cliente");
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sinistraPanel.add(titolo);
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Immagine profilo
        JPanel imagePanel = createProfileImagePanel(immagineProfilo);
        sinistraPanel.add(imagePanel);
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Informazioni utente
        sinistraPanel.add(createStyledLabel("Nome: " + nome));
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sinistraPanel.add(createStyledLabel("Cognome: " + cognome));
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sinistraPanel.add(createStyledLabel("Email: " + email));
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Pulsante Acquista Biglietto (specifico del cliente)
        JButton acquistaBigliettoButton = createAcquistaBigliettoButton(email);
        sinistraPanel.add(acquistaBigliettoButton);
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ✅ Aggiungi i pulsanti ereditati dal padre usando il metodo helper
        JPanel pulsantiPadre = createParentButtonsPanel();
        sinistraPanel.add(pulsantiPadre);
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Pulsante Torna alla Home
        JButton tornaHomeButton = createTornaHomeButton();
        sinistraPanel.add(tornaHomeButton);

        return sinistraPanel;
    }

    private JPanel createProfileImagePanel(String immagineProfilo) {
        JPanel imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(120, 120));
        imagePanel.setMaximumSize(new Dimension(120, 120));
        imagePanel.setBorder(new LineBorder(new Color(52, 152, 219), 2, true));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePanel.setLayout(new BorderLayout());

        JLabel profilePic = new JLabel();
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        profilePic.setVerticalAlignment(SwingConstants.CENTER);

        if (immagineProfilo != null) {
            profilePic.setIcon(new ImageIcon(immagineProfilo));
        } else {
            profilePic.setText("Nessuna immagine");
            profilePic.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            profilePic.setForeground(new Color(127, 140, 141));
        }

        imagePanel.add(profilePic, BorderLayout.CENTER);
        return imagePanel;
    }

    private JButton createAcquistaBigliettoButton(String email) {
        JButton acquistaBigliettoButton = new JButton("Acquista Biglietto");
        styleButton(acquistaBigliettoButton, new Color(39, 174, 96));
        acquistaBigliettoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        acquistaBigliettoButton.setMaximumSize(new Dimension(220, 35));
        acquistaBigliettoButton.addActionListener(e -> {
            new FormAcquistoBiglietto(this, email).setVisible(true);
        });
        return acquistaBigliettoButton;
    }

    private JButton createTornaHomeButton() {
        JButton tornaHomeButton = new JButton("Torna alla Home");
        styleButton(tornaHomeButton, new Color(231, 76, 60));
        tornaHomeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tornaHomeButton.setMaximumSize(new Dimension(220, 35));
        tornaHomeButton.addActionListener(e -> {
            new HomePage().setVisible(true);
            dispose();
        });
        return tornaHomeButton;
    }

    private JPanel createRightPanel() {
        JPanel destraPanel = new JPanel();
        destraPanel.setLayout(new BoxLayout(destraPanel, BoxLayout.Y_AXIS));
        destraPanel.setBackground(Color.WHITE);
        destraPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));

        // Titolo sezione eventi
        JLabel eventiTitleLabel = new JLabel("Eventi di Oggi");
        eventiTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        eventiTitleLabel.setForeground(new Color(44, 62, 80));
        eventiTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        destraPanel.add(eventiTitleLabel);
        destraPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Lista eventi
        JScrollPane scrollPane = createEventiList();
        destraPanel.add(scrollPane);

        // Pulsante partecipa
        partecipaButton = createPartecipaButton();
        destraPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        destraPanel.add(partecipaButton);

        return destraPanel;
    }

    private JScrollPane createEventiList() {
        eventiModel = new DefaultListModel<>();
        listaEventiOdierni = new JList<>(eventiModel);
        listaEventiOdierni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventiOdierni.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        listaEventiOdierni.setFixedCellHeight(70);

        // Custom renderer per la lista eventi
        listaEventiOdierni.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setOpaque(true);
                panel.setBorder(new EmptyBorder(8, 12, 8, 12));
                panel.setBackground(isSelected ? new Color(52, 152, 219) :
                        (index % 2 == 0 ? Color.WHITE : new Color(248, 249, 250)));

                if (value instanceof DTOEvento evento) {
                    JLabel titoloLabel = new JLabel(evento.getTitolo());
                    titoloLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    titoloLabel.setForeground(isSelected ? Color.WHITE : new Color(44, 62, 80));

                    JLabel dettagliLabel = new JLabel(evento.getLuogo() + "  •  " + evento.getOra());
                    dettagliLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    dettagliLabel.setForeground(isSelected ? new Color(230, 230, 230) : new Color(127, 140, 141));

                    JPanel textPanel = new JPanel();
                    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                    textPanel.setOpaque(false);
                    textPanel.add(titoloLabel);
                    textPanel.add(Box.createRigidArea(new Dimension(0, 4)));
                    textPanel.add(dettagliLabel);

                    panel.add(textPanel, BorderLayout.CENTER);
                }

                return panel;
            }
        });

        // Mouse listener per abilitare il pulsante partecipa
        listaEventiOdierni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    DTOEvento eventoSelezionato = listaEventiOdierni.getSelectedValue();
                    partecipaButton.setEnabled(eventoSelezionato != null);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(listaEventiOdierni);
        scrollPane.setPreferredSize(new Dimension(480, 350));
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private JButton createPartecipaButton() {
        JButton button = new JButton("Partecipa all'Evento");
        styleButton(button, new Color(155, 89, 182));
        button.setPreferredSize(new Dimension(0, 40));
        button.setEnabled(false);
        button.addActionListener(e -> {
            DTOEvento eventoSelezionato = listaEventiOdierni.getSelectedValue();
            if (eventoSelezionato != null) {
                String codiceUnivoco = apriFormPartecipazione(eventoSelezionato);
                try {
                    Controller.partecipaEvento(codiceUnivoco,eventoSelezionato);
                } catch (BigliettoConsumatoException | BigliettoNotFoundException ex) {
                    System.out.println(ex.getMessage());//cambiare
                }

            }
        });
        return button;
    }

    private void loadEventiOdierni() {
        try {
            List<DTOEvento> eventi = Controller.RicercaEvento(null, LocalDate.now(), null);
            updateEventiOdierni(eventi);
        } catch (EventoNotFoundException e) {
            eventiModel.clear();
        }
    }

    public void updateEventiOdierni(List<DTOEvento> eventiOdierni) {
        eventiModel.clear();
        for (DTOEvento evento : eventiOdierni) {
            eventiModel.addElement(evento);
        }
    }

    private String apriFormPartecipazione(DTOEvento evento) {
        String messaggio = String.format(
                "Partecipazione all'evento:\n\nTitolo: %s\nLuogo: %s\nOrario: %s\nDescrizione: %s\n\nInserisci il codice univoco del biglietto:",
                evento.getTitolo(),
                evento.getLuogo() != null ? evento.getLuogo() : "Da definire",
                evento.getOra(),
                evento.getDescrizione()
        );

        String codiceBiglietto = JOptionPane.showInputDialog(this, messaggio, "Partecipazione Evento", JOptionPane.PLAIN_MESSAGE);

        // Puoi gestire il caso in cui l'utente annulla o lascia vuoto
        if (codiceBiglietto == null || codiceBiglietto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Codice biglietto non inserito.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return codiceBiglietto.trim(); // Ritorna il codice inserito, rimuovendo eventuali spazi
    }
}