package boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import DTO.DTOEvento;

public class CatalogoEventi extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    public CatalogoEventi(HomeUtenteRegistrato homeutenteregistrato) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 720);

        // Layout principale
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // ScrollPane centrale
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Pannello contenitore per gli eventi (verticale)
        JPanel eventiPanel = new JPanel();
        eventiPanel.setLayout(new BoxLayout(eventiPanel, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(eventiPanel);

        // --- Chiamata al controller per ottenere gli eventi ---
        // La verifica degli eventi è già stata fatta nel padre HomeUtenteRegistrato
        List<DTOEvento> eventi = control.Controller.ConsultaCatalogo();

        for (DTOEvento evento : eventi) {
            JPanel eventoPanel = new JPanel();
            eventoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            eventoPanel.setLayout(new BoxLayout(eventoPanel, BoxLayout.Y_AXIS));

            JLabel titoloLabel = new JLabel("Titolo: " + evento.getTitolo());
            JLabel dataLabel = new JLabel("Data: " + evento.getData());
            JLabel oraLabel = new JLabel("Ora: " + evento.getOra());
            JLabel luogoLabel = new JLabel("Luogo: " + evento.getLuogo());
            JLabel descrizioneLabel = new JLabel("Descrizione: " + evento.getDescrizione());

            eventoPanel.add(titoloLabel);
            eventoPanel.add(dataLabel);
            eventoPanel.add(oraLabel);
            eventoPanel.add(luogoLabel);
            eventoPanel.add(descrizioneLabel);

            eventiPanel.add(eventoPanel);
        }

        // Pannello inferiore con bottone allineato a destra
        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backHome = new JButton("Back");
        backHome.setFont(new Font("Arial Black", Font.PLAIN, 18));
        backHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                homeutenteregistrato.setVisible(true);
                dispose();
            }
        });

        panelBottoni.add(backHome);
        contentPane.add(panelBottoni, BorderLayout.SOUTH);
    }
}