package boundary.BoundaryUtenteRegistrato;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import DTO.DTOEvento;
import exceptions.EventoNotFoundException;

public class CatalogoEventi extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    public CatalogoEventi(HomeUtenteRegistrato homeutenteregistrato) {
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
        List<DTOEvento> eventi = null;
        try {
            eventi = control.Controller.consultaCatalogo();
        } catch (EventoNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERRORE", JOptionPane.ERROR_MESSAGE);

        }

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