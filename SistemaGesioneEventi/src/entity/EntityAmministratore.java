package entity;

import database.UtenteDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * La classe EntityAmministratore rappresenta un amministratore del sistema,
 * specializzato dall'entità utente registrato. Questa classe gestisce gli
 * eventi creati e pubblicati dall'amministratore, fornendo metodi per
 * il caricamento, la creazione e la gestione degli eventi.
 */
public class EntityAmministratore extends EntityUtenteRegistrato {
    /**
     * Lista degli eventi associati all'amministratore corrente.
     * Contiene oggetti di tipo {@code EntityEvento} che rappresentano gli eventi creati
     * o pubblicati dall'amministratore.
     */
    private ArrayList<EntityEvento> eventi;

    /**
     * Costruttore predefinito della classe {@code EntityAmministratore}.
     * Inizializza un oggetto vuoto senza impostare alcun attributo o lista
     * associata.
     */
    public EntityAmministratore() {}

    /**
     * Costruttore della classe {@code EntityAmministratore}.
     * Inizializza un oggetto {@code EntityAmministratore} con i dati provenienti
     * da un'istanza di {@code UtenteDAO}, impostando i relativi attributi e
     * inizializzando la lista degli eventi associati all'amministratore.
     *
     * @param utente un oggetto di tipo {@code UtenteDAO} contenente le informazioni
     *               dell'utente da cui derivare i dati per l'oggetto amministratore
     */
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

    /**
     * Restituisce la lista degli eventi pubblicati associati all'amministratore corrente.
     *
     * @return un oggetto {@code ArrayList} contenente gli eventi pubblicati,
     *         ciascuno rappresentato da un'istanza di {@code EntityEvento}.
     */
    public ArrayList<EntityEvento> getEventiPubblicati() {
        return eventi;
    }

    /**
     * Imposta la lista degli eventi associati all'amministratore corrente.
     *
     * @param eventi una lista di oggetti {@code EntityEvento} rappresentante gli eventi
     *               da associare all'amministratore.
     */
    public void setEventi(ArrayList<EntityEvento> eventi) {
        this.eventi = eventi;
    }
}
