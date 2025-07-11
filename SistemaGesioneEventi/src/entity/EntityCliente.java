package entity;

import database.BigliettoDAO;
import database.UtenteDAO;
import exceptions.BigliettoNotFoundException;
import exceptions.DBException;
import java.util.ArrayList;

/**
 * Rappresenta un cliente all'interno del sistema, estendendo le proprietà
 * e le funzionalità di base di un utente registrato. La classe gestisce
 * informazioni specifiche del cliente, come l'immagine di profilo e lo
 * storico dei biglietti associati.
 */
public class EntityCliente extends EntityUtenteRegistrato{
    /**
     * Contiene il percorso o riferimento all'immagine associata al profilo dell'utente.
     */
    private String immagineProfilo;
    /**
     * Rappresenta lo storico dei biglietti associati a un cliente specifico.
     * Contiene una lista di oggetti {@code EntityBiglietto} che rappresentano
     * i biglietti acquistati o assegnati all'utente.
     */
    private ArrayList<EntityBiglietto> storicoBiglietti;

    /**
     * Rappresenta un'entità che modella un cliente registrato nel sistema con
     * funzionalità aggiuntive rispetto all'entità base {@code EntityUtenteRegistrato}.
     * Fornisce informazioni specifiche come l'immagine di profilo e lo storico dei biglietti.
     * La classe include metodi per gestire e recuperare tali informazioni.
     */
    public EntityCliente() {}

    /**
     * Costruttore della classe EntityCliente che inizializza un'istanza basata sui dati di un oggetto {@code UtenteDAO}.
     * Imposta i campi specifici dell'entità cliente utilizzando i valori forniti dall'oggetto {@code UtenteDAO}.
     *
     * @param utente oggetto di tipo {@code UtenteDAO} da cui vengono estrapolati i dati per inizializzare l'entità cliente.
     */
    public EntityCliente(UtenteDAO utente){
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.immagineProfilo =utente.getImmagine();
        this.storicoBiglietti = new ArrayList<>();
    }

    public EntityCliente(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
    }

    /**
     * Carica i biglietti associati all'utente corrente dal database e li aggiunge alla cronologia biglietti.
     * Questo metodo recupera i biglietti dell'utente invocando l'operazione sul database
     * attraverso un'istanza di {@code UtenteDAO}. Elabora ogni biglietto ottenuto
     * dal database, lo converte in un oggetto {@code EntityBiglietto} e lo aggiunge
     * alla lista {@code storico_biglietti} dell'utente corrente.
     *
     * @throws BigliettoNotFoundException se non vengono trovati biglietti o si verifica un problema nel recuperarli dal database.
     */
    public void caricaBiglietti() throws BigliettoNotFoundException {
        UtenteDAO utenteDao = new UtenteDAO(this.getId());
        storicoBiglietti.clear();
        try {
            utenteDao.caricaBigliettiDaDB();
        } catch (DBException e) {
            throw new BigliettoNotFoundException("Biglietto non trovato.");
        }
        for (BigliettoDAO bigliettoDAO : utenteDao.getBiglietti()) {
            EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO);
            storicoBiglietti.add(biglietto);
        }
    }



    /**
     * Verifica se l'entità cliente possiede un biglietto per un determinato evento.
     * Scorre lo storico dei biglietti del cliente per determinare se esiste un biglietto
     * associato all'evento fornito.
     *
     * @param evento l'oggetto {@code EntityEvento} per il quale si vuole verificare la presenza di un biglietto.
     * @return {@code true} se il cliente possiede un biglietto per l'evento specificato, {@code false} altrimenti.
     */
    public boolean haBigliettoPerEvento(EntityEvento evento){
        for(EntityBiglietto biglietto:storicoBiglietti){
            if(biglietto.getEvento().getId() == evento.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * Restituisce l'immagine di profilo associata all'entità cliente.
     *
     * @return una stringa rappresentante il percorso o l' URL dell'immagine di profilo.
     */
    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    /**
     * Imposta l'immagine di profilo dell'entità cliente.
     *
     * @param immagineProfilo una stringa che rappresenta il percorso o l' URL dell'immagine da impostare come immagine di profilo.
     */
    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    /**
     * Restituisce la lista dei biglietti associati al cliente corrente.
     * I biglietti rappresentano gli eventi a cui il cliente ha partecipato o per i quali ha acquistato un biglietto.
     *
     * @return una lista di oggetti {@code EntityBiglietto} che rappresenta lo storico dei biglietti del cliente.
     */
    public ArrayList<EntityBiglietto> getBiglietti() {
        return storicoBiglietti;
    }

    /**
     * Imposta la lista dei biglietti associati al cliente.
     * Questo metodo consente di aggiornare lo storico biglietti di un cliente
     * con una nuova lista di biglietti fornita in ingresso.
     *
     * @param biglietti la nuova lista di oggetti {@code EntityBiglietto} da associare al cliente.
     */
    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.storicoBiglietti = biglietti;
    }
}
