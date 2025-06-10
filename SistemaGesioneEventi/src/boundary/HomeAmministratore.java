package boundary;

import java.awt.*;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DTO.DTOEvento;
import DTO.DTOUtente;
import exceptions.BigliettoNotFoundException;

public class HomeAmministratore extends HomeUtenteRegistrato {

    @Serial
    private static final long serialVersionUID = 1L;
    private final DefaultListModel<String> eventiModel;
    private final JList<String> eventiList;
    private final String emailAmministratore;
    private final Map<Integer, Map<String, Object>> eventiInfoMap;
    private final String nomeAmministratore;
    private final String cognomeAmministratore;

    public HomeAmministratore(String nome, String cognome, String email) {
        super();
        this.emailAmministratore = email;
        this.nomeAmministratore = nome;
        this.cognomeAmministratore = cognome;

        eventiInfoMap = new HashMap<>();

        setPreferredSize(new Dimension(800, 600));
        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));

        titleLabel.setText("Dashboard Amministratore");

        eventiModel = new DefaultListModel<>();
        eventiList = new JList<>(eventiModel);
        eventiList.setVisibleRowCount(8);
        eventiList.setFixedCellHeight(-1);
        eventiList.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        eventiList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventiList.setCellRenderer(new EventListCellRenderer());

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JPanel mainPanel = createMainAdminPanel();
        contentPanel.add(mainPanel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();

        caricaEventiPubblicati(emailAmministratore);

        eventiList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = eventiList.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        mostraDettagliPartecipanti(index);
                    }
                }
            }
        });
    }

    private void  caricaEventiPubblicati(String email) {
        try {
            Map<DTOEvento, Object> eventiPubblicati = control.Controller.ConsultaEventiPubblicati(email);

            eventiModel.clear();
            eventiInfoMap.clear();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            int idCounter = 0;
            for (Map.Entry<DTOEvento, Object> entry : eventiPubblicati.entrySet()) {
                DTOEvento evento = entry.getKey();
                Map<String, Object> info = (Map<String,Object>) entry.getValue();

                System.out.println(evento.getTitolo()+" "+evento.getBigliettiVenduti());
                StringBuilder sb = new StringBuilder();
                sb.append("Titolo: ").append(evento.getTitolo()).append("\n")
                        .append("Data: ").append(evento.getData().format(dateFormatter))
                        .append("  |  Ora: ").append(evento.getOra().format(timeFormatter)).append("\n")
                        .append("Luogo: ").append(evento.getLuogo()).append("\n")
                        .append("Costo: ").append(evento.getCosto()).append("€")
                        .append("  |  Capienza: ").append(evento.getCapienza()).append("\n")
                        .append("Biglietti venduti: ").append(evento.getBigliettiVenduti()).append("\n");

                if (info.containsKey("numeroPartecipanti")) {
                    sb.append("  |  Partecipanti: ").append(info.get("numeroPartecipanti"));
                }else{
                    sb.append("  |  Partecipanti: ").append(0);
                }

                eventiModel.addElement(sb.toString());
                eventiInfoMap.put(idCounter, info);
                idCounter++;
            }

            if (eventiModel.isEmpty()) {
                eventiModel.addElement("Nessun evento pubblicato al momento.");
            }

        }catch(BigliettoNotFoundException _){
            JOptionPane.showMessageDialog(this, "Non sono stati venduti biglietti per l'evento.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostraDettagliPartecipanti(int index) {
        if (index < 0 || index >= eventiModel.size()) return;

        Map<String, Object> info = eventiInfoMap.get(index);
        if (info == null || !info.containsKey("listaPartecipanti")) {
            JOptionPane.showMessageDialog(this, "Lista partecipanti non disponibile.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<DTOUtente> partecipanti = (List<DTOUtente>) info.get("listaPartecipanti");
        if (partecipanti == null || partecipanti.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun partecipante disponibile per questo evento.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder partecipantiInfo = new StringBuilder();
        for (DTOUtente p : partecipanti) {
            String nome = p.getNome() != null ? p.getNome() : "";
            String cognome = p.getCognome() != null ? p.getCognome() : "";
            if (!nome.isEmpty() || !cognome.isEmpty()) {
                partecipantiInfo.append(nome).append(" ").append(cognome).append("\n");
            }
        }

        if (partecipantiInfo.isEmpty()) {
            partecipantiInfo.append("Nessun dato disponibile sui partecipanti.");
        }

        JTextArea textArea = new JTextArea(partecipantiInfo.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Lista Partecipanti", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel createMainAdminPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(300, 0));

        leftPanel.add(createTitleSection());
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        leftPanel.add(createAdminButtonsSection());
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        leftPanel.add(createInheritedButtonsSection());
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(createNavigationSection());

        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(0, 30, 0, 0));
        rightPanel.add(createEventiSection(), BorderLayout.CENTER);

        mainPanel.add(rightPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createEventiSection() {
        JPanel eventiPanel = new JPanel(new BorderLayout());
        eventiPanel.setOpaque(false);

        JLabel sectionTitle = new JLabel("Eventi Pubblicati");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        eventiPanel.add(sectionTitle, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(eventiList);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        eventiPanel.add(scrollPane, BorderLayout.CENTER);

        return eventiPanel;
    }

    private JPanel createTitleSection() {
        JPanel titleSection = new JPanel();
        titleSection.setLayout(new BoxLayout(titleSection, BoxLayout.Y_AXIS));
        titleSection.setOpaque(false);

        JLabel mainTitle = new JLabel("Dashboard Amministratore");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        mainTitle.setForeground(new Color(44, 62, 80));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleSection.add(mainTitle);
        return titleSection;
    }

    private JPanel createAdminButtonsSection() {
        JPanel adminSection = new JPanel();
        adminSection.setLayout(new BoxLayout(adminSection, BoxLayout.Y_AXIS));
        adminSection.setOpaque(false);

        JLabel sectionTitle = new JLabel("Funzioni Amministrative");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        adminSection.add(sectionTitle);
        adminSection.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton creaEventoButton = createCreaEventoButton();
        adminSection.add(creaEventoButton);

        return adminSection;
    }

    private JPanel createInheritedButtonsSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel sectionTitle = new JLabel("Gestione Eventi");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(sectionTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        ricercaEventoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ricercaEventoButton.setMaximumSize(new Dimension(250, 45));
        ricercaEventoButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        catalogoEventiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        catalogoEventiButton.setMaximumSize(new Dimension(250, 45));
        catalogoEventiButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        panel.add(ricercaEventoButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(catalogoEventiButton);

        return panel;
    }

    private JPanel createNavigationSection() {
        JPanel navigationSection = new JPanel();
        navigationSection.setLayout(new BoxLayout(navigationSection, BoxLayout.Y_AXIS));
        navigationSection.setOpaque(false);

        JLabel sectionTitle = new JLabel("Navigazione");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(44, 62, 80));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        navigationSection.add(sectionTitle);
        navigationSection.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton logoutButton = createLogoutButton();
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        navigationSection.add(logoutButton);

        return navigationSection;
    }

    private JButton createCreaEventoButton() {
        JButton button = new JButton("Crea Nuovo Evento");
        styleButton(button, new Color(231, 76, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.addActionListener(e -> openFormEvento());
        return button;
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("Logout");
        styleButton(button, new Color(149, 165, 166));
        button.setMaximumSize(new Dimension(120, 35));
        button.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Sei sicuro di voler effettuare il logout?",
                    "Conferma Logout",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(() -> {
                    JFrame homePage = new HomePage();
                    homePage.setVisible(true);
                    dispose();
                });
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) window.dispose();
            }
        });

        return button;
    }

    private void openFormEvento() {
        FormEvento form = new FormEvento(this,this.nomeAmministratore,this.cognomeAmministratore,this.emailAmministratore);
        form.setVisible(true);
        dispose();
    }

    private static class EventListCellRenderer extends JPanel implements ListCellRenderer<String> {
        private final JTextArea textArea;

        public EventListCellRenderer() {
            setLayout(new BorderLayout());
            textArea = new JTextArea();
            textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(false);
            textArea.setEditable(false);
            textArea.setBorder(new EmptyBorder(8, 10, 8, 10));
            add(textArea, BorderLayout.CENTER);
            setBorder(new EmptyBorder(5, 5, 5, 5)); // spazio tra elementi
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            textArea.setText(value);
            if (isSelected) {
                setBackground(new Color(173, 216, 230));
                textArea.setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                textArea.setForeground(Color.BLACK);
            }
            return this;
        }
    }


}
