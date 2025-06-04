package boundary;

import DTO.DTOEvento;
import control.Controller;
import exceptions.EventoNotFoundException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HomeCliente extends HomeUtenteRegistrato {

    private static final long serialVersionUID = 1L;
    private JList<DTOEvento> listaEventiOdierni;
    private JButton partecipaButton;
    private DefaultListModel<DTOEvento> eventiModel;

    public HomeCliente(String nome, String cognome, String email, String immagineProfilo) {
        super();

        setPreferredSize(new Dimension(1000, 600));
        setSize(new Dimension(1000, 600));
        setMinimumSize(new Dimension(1000, 600));

        titleLabel.setText("Profilo Cliente");

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JPanel sinistraPanel = new JPanel();
        sinistraPanel.setLayout(new BoxLayout(sinistraPanel, BoxLayout.Y_AXIS));
        sinistraPanel.setBackground(Color.WHITE);
        sinistraPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));
        sinistraPanel.setPreferredSize(new Dimension(400, 0));

        JPanel destraPanel = new JPanel();
        destraPanel.setLayout(new BoxLayout(destraPanel, BoxLayout.Y_AXIS));
        destraPanel.setBackground(Color.WHITE);
        destraPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));

        JLabel titolo = new JLabel("Profilo Cliente");
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sinistraPanel.add(titolo);
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 20)));

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
            System.out.println(immagineProfilo);
        } else {
            profilePic.setText("Nessuna immagine");
            profilePic.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            profilePic.setForeground(new Color(127, 140, 141));
        }

        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(profilePic, BorderLayout.CENTER);

        sinistraPanel.add(imagePanel);
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        sinistraPanel.add(createStyledLabel("Nome: " + nome));
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sinistraPanel.add(createStyledLabel("Cognome: " + cognome));
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sinistraPanel.add(createStyledLabel("Email: " + email));
        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        buttonsPanel.setMaximumSize(new Dimension(280, 120));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sinistraPanel.add(buttonsPanel);

        JButton tornaHomeButton = new JButton("Torna alla Home");
        tornaHomeButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tornaHomeButton.setBackground(new Color(231, 76, 60));
        tornaHomeButton.setForeground(Color.WHITE);
        tornaHomeButton.setFocusPainted(false);
        tornaHomeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tornaHomeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tornaHomeButton.setMaximumSize(new Dimension(220, 35));

        tornaHomeButton.addActionListener(e -> {
            new HomePage().setVisible(true);
            dispose();
        });

        sinistraPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sinistraPanel.add(tornaHomeButton);

        JLabel eventiTitleLabel = new JLabel("Eventi di Oggi");
        eventiTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        eventiTitleLabel.setForeground(new Color(44, 62, 80));
        eventiTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        destraPanel.add(eventiTitleLabel);
        destraPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        eventiModel = new DefaultListModel<>();
        listaEventiOdierni = new JList<>(eventiModel);
        listaEventiOdierni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventiOdierni.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        listaEventiOdierni.setFixedCellHeight(70);
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
        destraPanel.add(scrollPane);

        partecipaButton = new JButton("Partecipa all'Evento");
        partecipaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        partecipaButton.setBackground(new Color(155, 89, 182));
        partecipaButton.setForeground(Color.WHITE);
        partecipaButton.setFocusPainted(false);
        partecipaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        partecipaButton.setPreferredSize(new Dimension(0, 40));
        partecipaButton.setEnabled(false);
        partecipaButton.addActionListener(e -> {
            DTOEvento eventoSelezionato = listaEventiOdierni.getSelectedValue();
            if (eventoSelezionato != null) {
                apriFormPartecipazione(eventoSelezionato);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Seleziona un evento dalla lista prima di partecipare.",
                        "Nessun evento selezionato",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        destraPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        destraPanel.add(partecipaButton);

        contentPanel.add(sinistraPanel, BorderLayout.WEST);
        contentPanel.add(destraPanel, BorderLayout.CENTER);

        loadEventiOdierni();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void loadEventiOdierni() {
        LocalDate oggi = LocalDate.now();
        try {
            List<DTOEvento> eventi = Controller.RicercaEvento(null, oggi, null);
            updateEventiOdierni(eventi);
        } catch (EventoNotFoundException e) {
            eventiModel.clear();
            JOptionPane.showMessageDialog(this, "Nessun evento trovato per oggi.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void updateEventiOdierni(List<DTOEvento> eventiOdierni) {
        eventiModel.clear();
        for (DTOEvento evento : eventiOdierni) {
            eventiModel.addElement(evento);
        }
    }

    private void apriFormPartecipazione(DTOEvento evento) {
        String messaggio = String.format(
                "Partecipazione all'evento:\n\nTitolo: %s\nLuogo: %s\nOrario: %s\nDescrizione: %s",
                evento.getTitolo(),
                evento.getLuogo() != null ? evento.getLuogo() : "Da definire",
                evento.getOra(),
                evento.getDescrizione()
        );
        JOptionPane.showMessageDialog(this, messaggio, "Partecipazione Evento", JOptionPane.INFORMATION_MESSAGE);
    }
}
