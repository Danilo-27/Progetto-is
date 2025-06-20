package boundary.BoundaryUtenteRegistrato;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import DTO.DTOEvento;
import control.Controller;
import exceptions.EventoNotFoundException;
import org.jdesktop.swingx.JXDatePicker;

public class FormRicercaEvento extends JFrame {

    private JTextField titoloField;
    private JXDatePicker dataPicker;
    private JTextField luogoField;
    private JTextArea risultatoArea;
    private final JFrame homeCaller; // riferimento alla HomeUtenteRegistrato

    public FormRicercaEvento(JFrame homeCaller) {
        this.homeCaller = homeCaller;

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

        setTitle("Ricerca Evento");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pannello principale: sinistra (form), destra (risultati)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(creaFormPanel());
        mainPanel.add(creaRisultatiPanel());

        add(mainPanel, BorderLayout.CENTER);
        add(creaBottomPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel creaFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Titolo
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Titolo:"), gbc);

        gbc.gridx = 1;
        titoloField = new JTextField(15);
        formPanel.add(titoloField, gbc);

        // Data
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Data:"), gbc);

        gbc.gridx = 1;
        dataPicker = new JXDatePicker();
        dataPicker.setFormats("yyyy-MM-dd");
        formPanel.add(dataPicker, gbc);

        // Luogo
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Luogo:"), gbc);

        gbc.gridx = 1;
        luogoField = new JTextField(15);
        formPanel.add(luogoField, gbc);

        // Pulsante Cerca
        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton cercaButton = new JButton("Cerca");
        cercaButton.addActionListener(e -> cercaEventi());
        formPanel.add(cercaButton, gbc);

        return formPanel;
    }


    private JScrollPane creaRisultatiPanel() {
        risultatoArea = new JTextArea();
        risultatoArea.setEditable(false);
        risultatoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(risultatoArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Risultati della ricerca"));
        return scrollPane;
    }

    private JPanel creaBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Indietro");

        backButton.addActionListener(e -> {
            this.dispose();             // chiude la finestra attuale
            homeCaller.setVisible(true); // mostra la finestra chiamante
        });

        bottomPanel.add(backButton);
        return bottomPanel;
    }

    private void cercaEventi() {
        String titolo = titoloField.getText().trim();

        if(titolo.isBlank()) {
            titolo=null;
        }else{
            if(titolo.length() > 50) {
                JOptionPane.showMessageDialog(this, "Titolo troppo lungo", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String luogo = luogoField.getText().trim();

        if (luogo.isBlank()) {
            luogo = null;
        } else {
            if (luogo.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(this, "Luogo non valido: contiene numeri", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!luogo.matches("[a-zA-ZàèéìòùÀÈÉÌÒÙ\\s]*")) {
                JOptionPane.showMessageDialog(this, "Luogo non valido: contiene caratteri speciali", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Date selectedDate = dataPicker.getDate();

        LocalDate data = null;

        if (selectedDate != null) {
            LocalDate selectedLocalDate = selectedDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate today = LocalDate.now();

            if (selectedLocalDate.isBefore(today)) {
                JOptionPane.showMessageDialog(this, "Data non valida", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            data = selectedLocalDate;
        }


        try {
            List<DTOEvento> eventi = Controller.ricercaEvento(titolo, data, luogo);
            mostraRisultati(eventi);
        } catch (EventoNotFoundException e) {
            risultatoArea.setText("Nessun evento trovato.");
        } catch (Exception ex) {
            risultatoArea.setText("Errore durante la ricerca: " + ex.getMessage());
        }
    }

    private void mostraRisultati(List<DTOEvento> eventi) {
        StringBuilder sb = new StringBuilder();
        for (DTOEvento evento : eventi) {
            sb.append("Titolo: ").append(evento.getTitolo()).append("\n");
            sb.append("Descrizione: ").append(evento.getDescrizione()).append("\n");
            sb.append("Data: ").append(evento.getData()).append(" ").append(evento.getOra()).append("\n");
            sb.append("Luogo: ").append(evento.getLuogo()).append("\n");
            sb.append("Costo: ").append(evento.getCosto()).append("€\n");
            sb.append("------------------------------------------------\n");
        }
        risultatoArea.setText(sb.toString());
    }


}
