package boundary.BoundaryCliente;

import DTO.DTOEvento;
import DTO.DTODatiPagamento;
import control.Controller;
import exceptions.*;
import external.PagamentoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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

    public FormAcquistoBiglietto(String emailUtente, PagamentoService pagamentoService) throws EventoNotFoundException {
        this.emailUtente = emailUtente;
        this.pagamentoService = pagamentoService;

        //icona
        BufferedImage iconImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = iconImage.createGraphics();

        g2d.setColor(new Color(41, 128, 185)); // Sfondo blu
        g2d.fillRect(0, 0, 64, 64);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 32));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "T2";
        int x = (64 - fm.stringWidth(text)) / 2;
        int y = ((64 - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, x, y);
        g2d.dispose();
        setIconImage(iconImage);

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

    private void caricaEventiDisponibili() throws EventoNotFoundException {
        List<DTOEvento> eventi = Controller.consultaCatalogo();
        eventiModel.clear();
        eventi.forEach(eventiModel::addElement);
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
            JOptionPane.showMessageDialog(this, "Dati pagamento incompleti","Errore di pagamento", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!scadenzaValida(datiCarta.get(3))) {
            JOptionPane.showMessageDialog(this, "Formato scadenza non valido. Usa MM/yy.", ERRORE, JOptionPane.WARNING_MESSAGE);
            return;
        }

        DTODatiPagamento dtoDatiPagamento = new DTODatiPagamento (datiCarta.get(0),datiCarta.get(1),datiCarta.get(2),datiCarta.get(3));

        try {
            Controller.acquistoBiglietto(pagamentoService, dto, emailUtente,dtoDatiPagamento);
            JOptionPane.showMessageDialog(this, "Biglietto acquistato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (AcquistoException | BigliettoNotFoundException | UpdateException | LoadingException e) {
            JOptionPane.showMessageDialog(this, "Errore : " + e.getMessage(), ERRORE, JOptionPane.ERROR_MESSAGE);
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