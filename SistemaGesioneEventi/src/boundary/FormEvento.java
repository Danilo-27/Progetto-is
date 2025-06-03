package boundary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;
import java.text.SimpleDateFormat;


import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSpinner;
import java.awt.TextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormEvento extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel FormEvento;
    private JTextField textTitolo;
    private JTextField textLuogo;


    /**
     * Create the frame.
     */

    public FormEvento() {
        this(null);
    }

    public FormEvento(HomeAmministratore homeamministratore) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1051, 697);
        FormEvento = new JPanel();
        FormEvento.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(FormEvento);
        FormEvento.setLayout(null);

        textTitolo = new JTextField();
        textTitolo.setFont(new Font("Arial Black", Font.PLAIN, 18));
        textTitolo.setBounds(68, 88, 217, 35);
        FormEvento.add(textTitolo);
        textTitolo.setColumns(10);

        JLabel LabelTitolo = new JLabel("TITOLO");
        LabelTitolo.setForeground(new Color(0, 128, 255));
        LabelTitolo.setFont(new Font("Arial Black", Font.PLAIN, 18));
        LabelTitolo.setBounds(68, 55, 77, 23);
        FormEvento.add(LabelTitolo);

        textLuogo = new JTextField();
        textLuogo.setBounds(68, 183, 217, 35);
        FormEvento.add(textLuogo);
        textLuogo.setColumns(10);

        JLabel labelLuogo = new JLabel("LUOGO");
        labelLuogo.setFont(new Font("Arial Black", Font.PLAIN, 18));
        labelLuogo.setForeground(new Color(0, 128, 255));
        labelLuogo.setBounds(68, 150, 77, 23);
        FormEvento.add(labelLuogo);

        JLabel CapienzaLabel = new JLabel("CAPIENZA");
        CapienzaLabel.setForeground(new Color(0, 128, 255));
        CapienzaLabel.setFont(new Font("Arial Black", Font.PLAIN, 18));
        CapienzaLabel.setBounds(366, 52, 125, 35);
        FormEvento.add(CapienzaLabel);

        JSpinner spinnerCapienza = new JSpinner();
        spinnerCapienza.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        spinnerCapienza.setFont(new Font("Arial Black", Font.PLAIN, 18));
        spinnerCapienza.setBounds(366, 88, 217, 34);
        FormEvento.add(spinnerCapienza);

        TextArea textArea = new TextArea();
        textArea.setBounds(68, 408, 603, 195);
        FormEvento.add(textArea);

        JLabel lblNewLabel = new JLabel("DESCRIZIONE");
        lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 18));
        lblNewLabel.setForeground(new Color(0, 128, 255));
        lblNewLabel.setBounds(68, 379, 142, 23);
        FormEvento.add(lblNewLabel);

        JSpinner spinnerCosto = new JSpinner();
        spinnerCosto.setModel(new SpinnerNumberModel(0.0, 0.0, null, 0.01));
        spinnerCosto.setFont(new Font("Arial Black", Font.PLAIN, 18));
        spinnerCosto.setBounds(366, 180, 217, 34);
        FormEvento.add(spinnerCosto);

        JLabel labelCosto = new JLabel("COSTO");
        labelCosto.setFont(new Font("Arial Black", Font.PLAIN, 18));
        labelCosto.setForeground(new Color(0, 128, 255));
        labelCosto.setBounds(366, 150, 125, 21);
        FormEvento.add(labelCosto);

        JXDatePicker dataDatePicker = new JXDatePicker();
        dataDatePicker.setFont(new Font("Arial Black", Font.PLAIN, 18));
        dataDatePicker.getEditor().setFont(new Font("Arial Black", Font.PLAIN, 18));
        dataDatePicker.getEditor().setForeground(new Color(0, 0, 0));
        dataDatePicker.getEditor().setText("DATA");
        dataDatePicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        dataDatePicker.setBounds(68, 290, 217, 35);
        FormEvento.add(dataDatePicker);

        JLabel labelData = new JLabel("DATA");
        labelData.setFont(new Font("Arial Black", Font.PLAIN, 18));
        labelData.setForeground(new Color(0, 128, 255));
        labelData.setBounds(68, 245, 77, 35);
        FormEvento.add(labelData);


        JLabel labelOra = new JLabel("ORA");
        labelOra.setForeground(new Color(0, 128, 255));
        labelOra.setFont(new Font("Arial Black", Font.PLAIN, 18));
        labelOra.setBounds(366, 245, 83, 35);
        FormEvento.add(labelOra);

        // ComboBox per ora e minuti
        JComboBox<String> comboOra = new JComboBox<>();
        comboOra.setBackground(new Color(255, 255, 255));
        JComboBox<String> comboMinuti = new JComboBox<>();
        comboMinuti.setBackground(new Color(255, 255, 255));

        comboOra.addItem(""); // valore vuoto iniziale
        for (int i = 0; i < 24; i++) {
            comboOra.addItem(String.format("%02d", i));
        }

        comboMinuti.addItem(""); // valore vuoto iniziale
        for (int i = 0; i < 60; i += 5) {
            comboMinuti.addItem(String.format("%02d", i));
        }

        // Posizionamento
        comboOra.setBounds(366, 295, 107, 30);
        comboMinuti.setBounds(476, 295, 107, 30);

        // Aggiunta al pannello
        FormEvento.add(comboOra);
        FormEvento.add(comboMinuti);

        JButton BackButton = new JButton("Back");
        BackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                homeamministratore.setVisible(true); // riporta visibilità
                dispose();
            }
        });
        BackButton.setBackground(new Color(192, 192, 192));
        BackButton.setFont(new Font("Arial Black", Font.PLAIN, 18));
        BackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        BackButton.setBounds(866, 568, 142, 35);
        FormEvento.add(BackButton);




    }
}
