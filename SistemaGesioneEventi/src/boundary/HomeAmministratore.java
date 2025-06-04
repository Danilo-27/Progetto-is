package boundary;

import java.awt.*;
import javax.swing.*;

public class HomeAmministratore extends HomeUtenteRegistrato {

    private static final long serialVersionUID = 1L;
    private JButton creaEventoButton;

    public HomeAmministratore(String nome, String cognome, String email) {
        super(); // inizializza tutti i componenti del padre

        // Modifica il titolo ereditato
        titleLabel.setText("Profilo Amministratore");

        // Aggiungi contenuto specifico dell'amministratore al pannello info
        setupAdminInfo(nome, cognome, email);

        // Aggiungi il pulsante specifico dell'amministratore
        addAdminButton();

        // Sovrascrivi le azioni dei pulsanti ereditati
        setupAdminActions();

        // Ridimensiona il pannello dei pulsanti per 3 pulsanti
        buttonsPanel.setMaximumSize(new Dimension(680, 40));

        // Refresh della UI
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void setupAdminInfo(String nome, String cognome, String email) {
        // Informazioni amministratore
        infoPanel.add(createStyledLabel("Nome: " + nome));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(createStyledLabel("Cognome: " + cognome));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(createStyledLabel("Email: " + email));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void addAdminButton() {
        // Crea il pulsante "Crea Evento" specifico per l'amministratore
        creaEventoButton = new JButton("Crea Evento");
        styleButton(creaEventoButton, new Color(231, 76, 60));
        creaEventoButton.setMaximumSize(new Dimension(200, 40));

        // Ridimensiona i pulsanti esistenti per fare spazio al terzo
        ricercaEventoButton.setMaximumSize(new Dimension(200, 40));
        catalogoEventiButton.setMaximumSize(new Dimension(200, 40));

        // Aggiungi il nuovo pulsante al pannello
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(creaEventoButton);
    }

    private void setupAdminActions() {
        // Rimuovi solo il listener del pulsante ricerca per comportamento specifico
        for (java.awt.event.ActionListener listener : ricercaEventoButton.getActionListeners()) {
            ricercaEventoButton.removeActionListener(listener);
        }

        // Il catalogoEventiButton mantiene l'implementazione del padre
        ricercaEventoButton.addActionListener(e -> {
            // TODO: collegamento controller ricerca evento - comportamento specifico admin
        });

        // Azione specifica per il pulsante Crea Evento
        creaEventoButton.addActionListener(e -> {
            openFormEvento();
        });
    }

    // Metodo specifico dell'amministratore
    private void openFormEvento() {
        FormEvento form = new FormEvento(this);
        form.setVisible(true);
        this.setVisible(false);
    }
}