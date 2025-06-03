package boundary;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import DTO.DTOEvento;

public class CatalogoEventi extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public CatalogoEventi(HomeUtenteRegistrato homeutenteregistrato) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 720);

        // Layout principale
        contentPane = new JPanel();
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
