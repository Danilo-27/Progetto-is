package boundary;

import DTO.DTOEvento;
import control.Controller;
import exceptions.AcquistoException;
import exceptions.DBException;
import exceptions.EventoNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FormAcquistoBiglietto extends JFrame {

    private final HomeCliente homeCliente;
    private final String emailUtente;
    private JList<DTOEvento> listaEventi;
    private DefaultListModel<DTOEvento> eventiModel;
    private JButton acquistaButton;

    public FormAcquistoBiglietto(HomeCliente homeCliente, String emailUtente) {
        this.homeCliente = homeCliente;
        this.emailUtente = emailUtente; // Assicurati che questo metodo esista
        setTitle("Acquisto Biglietto");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        caricaEventiDisponibili();
    }

    private void initComponents() {
        eventiModel = new DefaultListModel<>();
        listaEventi = new JList<>(eventiModel);
        listaEventi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaEventi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaEventi.setFixedCellHeight(60);

        listaEventi.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof DTOEvento evento) {
                    label.setText(String.format("%s - %s (%s)", evento.getTitolo(), evento.getLuogo(), evento.getOra()));
                }
                return label;
            }
        });

        listaEventi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                acquistaButton.setEnabled(listaEventi.getSelectedValue() != null);
            }
        });

        JScrollPane scrollPane = new JScrollPane(listaEventi);

        acquistaButton = new JButton("Acquista Biglietto");
        acquistaButton.setEnabled(false);
        acquistaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        acquistaButton.setBackground(new Color(52, 152, 219));
        acquistaButton.setForeground(Color.WHITE);
        acquistaButton.setFocusPainted(false);
        acquistaButton.addActionListener(e -> acquistaBiglietto());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.add(new JLabel("Seleziona un evento disponibile:"), BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(acquistaButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void caricaEventiDisponibili() {
        try {
            List<DTOEvento> eventi = Controller.RicercaEvento(null, null, null);
            eventiModel.clear();
            for (DTOEvento evento : eventi) {
                eventiModel.addElement(evento);
            }
        } catch (EventoNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Nessun evento disponibile al momento.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acquistaBiglietto() {
        DTOEvento dto = listaEventi.getSelectedValue();
        if (dto == null) {
            JOptionPane.showMessageDialog(this, "Seleziona un evento prima di procedere.", "Errore", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Controller.AcquistoBiglietto(dto, emailUtente);
            JOptionPane.showMessageDialog(this, "Biglietto acquistato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (DBException | AcquistoException e) {
            JOptionPane.showMessageDialog(this, "Errore durante l'acquisto: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
