package entity;

import database.UtenteDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EntityAmministratore extends EntityUtenteRegistrato {
    private ArrayList<EntityEvento> eventi;

    public EntityAmministratore() {}

    public EntityAmministratore(UtenteDAO utente){
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.eventi=new ArrayList<>();
    }

    /**
     * Carica gli eventi pubblicati dall'amministratore corrente e li salva nella lista interna degli eventi.
     * Questa operazione recupera i dati utilizzando il metodo {@code get_EventiPubblicati}
     * della classe {@code EntityCatalogo}.
     */
    public void caricaEventiPubblicati() {
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        this.eventi = catalogo.get_EventiPubblicati(this);
    }

    /**
     * Crea un nuovo evento con i dettagli specificati e lo associa all'amministratore corrente.
     *
     * @param Titolo      il titolo dell'evento
     * @param Descrizione la descrizione dell'evento
     * @param Data        la data dell'evento
     * @param Ora         l'ora dell'evento
     * @param Luogo       il luogo dell'evento
     * @param Costo       il costo dell'evento
     * @param Capienza    la capienza massima dell'evento
     * @return l'oggetto {@code EntityEvento} creato che rappresenta l'evento
     */
    public EntityEvento creazioneEvento(String Titolo, String Descrizione, LocalDate Data, LocalTime Ora, String Luogo, int Costo, int Capienza) {
        return new EntityEvento(Titolo, Descrizione, Data, Ora, Luogo, Costo, Capienza, this);
    }
    public ArrayList<EntityEvento> getEventiPubblicati() {
        return eventi;
    }

    public void setEventi(ArrayList<EntityEvento> eventi) {
        this.eventi = eventi;
    }
}
