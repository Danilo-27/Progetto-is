package boundary;

import DTO.DTOBiglietto;
import control.Controller;
import exceptions.BigliettoNotFoundException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FormStoricoBiglietti extends JFrame {

    private final DefaultListModel<DTOBiglietto> bigliettiModel;
    private final JList<DTOBiglietto> listaBiglietti;

    public FormStoricoBiglietti(String emailUtente, JFrame parentFrame) throws BigliettoNotFoundException {
        setTitle("Storico Biglietti");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setLayout(new BorderLayout(0, 15));
        add(mainPanel, BorderLayout.CENTER);

        JLabel titoloLabel = new JLabel("Storico Biglietti di " + emailUtente);
        titoloLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titoloLabel, BorderLayout.NORTH);

        bigliettiModel = new DefaultListModel<>();
        listaBiglietti = new JList<>(bigliettiModel);
        listaBiglietti.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaBiglietti.setFixedCellHeight(100);
        listaBiglietti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Renderer personalizzato per mostrare le info del biglietto su più righe
        listaBiglietti.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(new EmptyBorder(8, 12, 8, 12));
            panel.setOpaque(true);
            panel.setBackground(isSelected ? new Color(52, 152, 219) : (index % 2 == 0 ? Color.WHITE : new Color(248, 249, 250)));

            if (value != null) {
                String statoStr = switch (value.getStato()) {
                    case 0 -> "VALIDO";
                    case 1 -> "OBLITERATO";
                    default -> "Sconosciuto";
                };

                JLabel codiceLabel = new JLabel("Codice: " + value.getCodiceUnivoco());
                codiceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                codiceLabel.setForeground(isSelected ? Color.WHITE : new Color(44, 62, 80));

                JLabel titoloEventoLabel = new JLabel("Titolo evento: " + value.getTitoloEvento());
                titoloEventoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                titoloEventoLabel.setForeground(isSelected ? Color.WHITE : new Color(44, 62, 80));

                JLabel dataLabel = new JLabel("Data: " + value.getData().toString());
                dataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                dataLabel.setForeground(isSelected ? Color.WHITE : new Color(44, 62, 80));

                JLabel statoLabel = new JLabel("Stato: " + statoStr);
                statoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                statoLabel.setForeground(isSelected ? Color.WHITE : new Color(44, 62, 80));

                panel.add(codiceLabel);
                panel.add(titoloEventoLabel);
                panel.add(dataLabel);
                panel.add(statoLabel);
            }

            return panel;
        });

        JScrollPane scrollPane = new JScrollPane(listaBiglietti);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Pulsante per tornare alla Home o all'interfaccia precedente
        JButton tornaIndietroButton = new JButton("Torna Indietro");
        tornaIndietroButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tornaIndietroButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            dispose();
        });
        mainPanel.add(tornaIndietroButton, BorderLayout.SOUTH);


        caricaStorico(emailUtente);

        listaBiglietti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DTOBiglietto selected = listaBiglietti.getSelectedValue();
                    if (selected != null) {
                        String statoStr = switch (selected.getStato()) {
                            case 0 -> "VALIDO";
                            case 1 -> "OBLITERATO";
                            default -> "Sconosciuto";
                        };
                        String testo = String.format("""
                            Codice: %s
                            Titolo evento: %s
                            Data: %s
                            Stato: %s
                            """,
                                selected.getCodiceUnivoco(),
                                selected.getTitoloEvento(),
                                selected.getData(),
                                statoStr);

                        JTextArea textArea = new JTextArea(testo);
                        textArea.setEditable(false);
                        textArea.setLineWrap(true);
                        textArea.setWrapStyleWord(true);
                        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        textArea.setCaretPosition(0);

                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(400, 150));

                        JOptionPane.showMessageDialog(
                                FormStoricoBiglietti.this,
                                scrollPane,
                                "Dettagli Biglietto (Seleziona e copia)",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        });
    }

    private void caricaStorico(String emailUtente) throws BigliettoNotFoundException {
        ArrayList<DTOBiglietto> biglietti = Controller.consultaStoricoBiglietti(emailUtente);
        if (biglietti.isEmpty()) {
            throw new BigliettoNotFoundException("Nessun biglietto trovato per l'utente: " + emailUtente);
        }

        bigliettiModel.clear();
        for (DTOBiglietto biglietto : biglietti) {
            bigliettiModel.addElement(biglietto);
        }
    }
}
