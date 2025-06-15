package boundary.BoundaryCliente;

import DTO.DTOEvento;
import control.Controller;
import exceptions.AcquistoException;
import exceptions.BigliettoNotFoundException;
import exceptions.RedundancyException;
import external.PagamentoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class FormAcquistoBiglietto extends JFrame {
    private final String emailUtente;
    private final PagamentoService pagamentoService;
    private JList<DTOEvento> listaEventi;
    private DefaultListModel<DTOEvento> eventiModel;
    private JButton acquistaButton;
    private static final String ERRORE = "Errore";

    public FormAcquistoBiglietto(String emailUtente, PagamentoService pagamentoService) {
        this.emailUtente = emailUtente;
        this.pagamentoService = pagamentoService;
        setTitle("Acquisto Biglietto");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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
                    label.setText(String.format(
                            "<html><b>%s</b> - %s<br>Data: %s, Ora: %s<br>Costo: €%d</html>",
                            evento.getTitolo(),
                            evento.getLuogo(),
                            evento.getData(),
                            evento.getOra(),
                            evento.getCosto()
                    ));
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
        List<DTOEvento> eventi = Controller.ConsultaCatalogo();
        if (!eventi.isEmpty()) {
            eventiModel.clear();
            eventi.forEach(eventiModel::addElement);
        } else {
            JOptionPane.showMessageDialog(this, "Nessun evento disponibile al momento.", ERRORE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acquistaBiglietto() {
        DTOEvento dto = listaEventi.getSelectedValue();
        if (dto == null) {
            JOptionPane.showMessageDialog(this, "Seleziona un evento prima di procedere.", ERRORE, JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<String> datiCarta = apriFormDatiCarta();
        if (datiCarta.size() < 4 ||
                datiCarta.get(0).isEmpty() || datiCarta.get(1).isEmpty() ||
                datiCarta.get(2).isEmpty() || datiCarta.get(3).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dati carta non validi o annullati.", ERRORE, JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!scadenzaValida(datiCarta.get(3))) {
            JOptionPane.showMessageDialog(this, "Formato scadenza non valido. Usa MM/yy.", ERRORE, JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Controller.AcquistoBiglietto(pagamentoService, dto, emailUtente,
                    datiCarta.get(0), datiCarta.get(1), datiCarta.get(2), datiCarta.get(3));
            JOptionPane.showMessageDialog(this, "Biglietto acquistato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (AcquistoException | BigliettoNotFoundException | RedundancyException e) {
            JOptionPane.showMessageDialog(this, "Errore durante l'acquisto: " + e.getMessage(), ERRORE, JOptionPane.ERROR_MESSAGE);
        }
    }

    private ArrayList<String> apriFormDatiCarta() {
        JTextField numeroCartaField = new JTextField();
        JTextField nomeTitolareField = new JTextField();
        JTextField cognomeTitolareField = new JTextField();
        JTextField scadenzaField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Numero della carta:"));
        panel.add(numeroCartaField);
        panel.add(new JLabel("Nome Titolare:"));
        panel.add(nomeTitolareField);
        panel.add(new JLabel("Cognome Titolare:"));
        panel.add(cognomeTitolareField);
        panel.add(new JLabel("Scadenza (MM/yy):"));
        panel.add(scadenzaField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Inserisci dati carta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        ArrayList<String> datiCarta = new ArrayList<>();
        if (result == JOptionPane.OK_OPTION) {
            datiCarta.add(numeroCartaField.getText().trim());
            datiCarta.add(nomeTitolareField.getText().trim());
            datiCarta.add(cognomeTitolareField.getText().trim());
            datiCarta.add(scadenzaField.getText().trim());
        }

        return datiCarta;
    }

    private boolean scadenzaValida(String scadenza) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth.parse(scadenza, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}