package boundary;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import control.Controller;
import exceptions.LoadingException;
import exceptions.RedundancyException;
import org.jdesktop.swingx.JXDatePicker;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.awt.Font;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
public class FormEvento extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JTextField textTitolo;
    private final JTextField textLuogo;
    private static final String ARIAL_BLACK="Arial Black";


    public FormEvento(HomeAmministratore homeamministratore,String nomeAmministratore,String cognomeAmministratore,String emailAmministratore) {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 1051, 697);
        JPanel formEvento = new JPanel();
        formEvento.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(formEvento);
        formEvento.setLayout(null);

        // Campo Titolo
        textTitolo = new JTextField();
        textTitolo.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        textTitolo.setBounds(68, 88, 217, 35);
        formEvento.add(textTitolo);
        textTitolo.setColumns(10);

        JLabel LabelTitolo = new JLabel("TITOLO");
        LabelTitolo.setForeground(new Color(0, 128, 255));
        LabelTitolo.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        LabelTitolo.setBounds(68, 55, 77, 23);
        formEvento.add(LabelTitolo);

        // Campo Luogo
        textLuogo = new JTextField();
        textLuogo.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        textLuogo.setBounds(68, 183, 217, 35);
        formEvento.add(textLuogo);
        textLuogo.setColumns(10);

        JLabel labelLuogo = new JLabel("LUOGO");
        labelLuogo.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        labelLuogo.setForeground(new Color(0, 128, 255));
        labelLuogo.setBounds(68, 150, 77, 23);
        formEvento.add(labelLuogo);

        // Campo Capienza
        JLabel CapienzaLabel = new JLabel("CAPIENZA");
        CapienzaLabel.setForeground(new Color(0, 128, 255));
        CapienzaLabel.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        CapienzaLabel.setBounds(366, 52, 125, 35);
        formEvento.add(CapienzaLabel);

        JSpinner spinnerCapienza = new JSpinner();
        spinnerCapienza.setModel(new SpinnerNumberModel(0, 0, null, 1));
        spinnerCapienza.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        spinnerCapienza.setBounds(366, 88, 217, 34);
        formEvento.add(spinnerCapienza);

        // Campo Descrizione
        TextArea textArea = new TextArea();
        textArea.setBounds(68, 408, 603, 195);
        formEvento.add(textArea);

        JLabel lblNewLabel = new JLabel("DESCRIZIONE");
        lblNewLabel.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        lblNewLabel.setForeground(new Color(0, 128, 255));
        lblNewLabel.setBounds(68, 379, 142, 23);
        formEvento.add(lblNewLabel);

        // Campo Costo
        JSpinner spinnerCosto = new JSpinner();
        spinnerCosto.setModel(new SpinnerNumberModel(0, 0, null, 1));
        spinnerCosto.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        spinnerCosto.setBounds(366, 180, 217, 34);
        formEvento.add(spinnerCosto);

        JLabel labelCosto = new JLabel("COSTO");
        labelCosto.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        labelCosto.setForeground(new Color(0, 128, 255));
        labelCosto.setBounds(366, 150, 125, 21);
        formEvento.add(labelCosto);

        // Campo Data (JXDatePicker)
        JXDatePicker dataDatePicker = new JXDatePicker();
        dataDatePicker.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        dataDatePicker.getEditor().setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        dataDatePicker.getEditor().setForeground(new Color(0, 0, 0));
        dataDatePicker.getEditor().setText("DATA");
        dataDatePicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        dataDatePicker.setBounds(68, 290, 217, 35);
        formEvento.add(dataDatePicker);

        JLabel labelData = new JLabel("DATA");
        labelData.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        labelData.setForeground(new Color(0, 128, 255));
        labelData.setBounds(68, 245, 77, 35);
        formEvento.add(labelData);

        // Campo Ora e Minuti
        JLabel labelOra = new JLabel("ORA");
        labelOra.setForeground(new Color(0, 128, 255));
        labelOra.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        labelOra.setBounds(366, 245, 83, 35);
        formEvento.add(labelOra);

        JComboBox<String> comboOra = new JComboBox<>();
        comboOra.setBackground(new Color(255, 255, 255));
        JComboBox<String> comboMinuti = new JComboBox<>();
        comboMinuti.setBackground(new Color(255, 255, 255));

        comboOra.addItem("");
        for (int i = 0; i < 24; i++) {
            comboOra.addItem(String.format("%02d", i));
        }

        comboMinuti.addItem("");
        for (int i = 0; i < 60; i += 5) {
            comboMinuti.addItem(String.format("%02d", i));
        }

        comboOra.setBounds(366, 295, 107, 30);
        comboMinuti.setBounds(476, 295, 107, 30);
        formEvento.add(comboOra);
        formEvento.add(comboMinuti);

        JButton BackButton = new JButton("Back");
        BackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (homeamministratore != null) {
                    homeamministratore.setVisible(true);
                }
                dispose();
            }
        });
        BackButton.setBackground(new Color(192, 192, 192));
        BackButton.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        BackButton.setBounds(866, 568, 142, 35);
        formEvento.add(BackButton);

        // Pulsante Crea
        JButton creaButton = new JButton("Crea");
        creaButton.setBackground(new Color(0, 204, 102));
        creaButton.setFont(new Font(ARIAL_BLACK, Font.PLAIN, 18));
        creaButton.setBounds(710, 568, 142, 35);
        creaButton.addActionListener(e -> {
            // Raccogliere i dati dal form
            String titolo = textTitolo.getText();
            String luogo = textLuogo.getText();
            int capienza = (int) spinnerCapienza.getValue();
            int costo = (int) spinnerCosto.getValue();
            String descrizione = textArea.getText();

            // Ottengo la java.util.Date dal date picker
            java.util.Date dateUtil = dataDatePicker.getDate();
            LocalDate data = null;
            if (dateUtil != null) {
                data = dateUtil.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }

            String oraStr = (String) comboOra.getSelectedItem();
            String minutiStr = (String) comboMinuti.getSelectedItem();
            LocalTime ora = null;
            if (oraStr != null && !oraStr.isEmpty() && minutiStr != null && !minutiStr.isEmpty()) {
                int hour = Integer.parseInt(oraStr);
                int minute = Integer.parseInt(minutiStr);
                ora = LocalTime.of(hour, minute);
            }

            // ESEMPIO: stampa a console con LocalDate


            Sessione sess = Sessione.getInstance();
            try{
                Controller.creaEvento(titolo,descrizione,data,ora,luogo,costo,capienza,sess.getEmail());
            }catch(RedundancyException _){
                JOptionPane.showMessageDialog(this, "Campo/i non validi.", "Errore", JOptionPane.ERROR_MESSAGE);
            }catch(LoadingException _){
                JOptionPane.showMessageDialog(this, "Errore di caricamento.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
            new HomeAmministratore(nomeAmministratore,cognomeAmministratore,emailAmministratore).setVisible(true);
        });
        formEvento.add(creaButton);
    }
}
