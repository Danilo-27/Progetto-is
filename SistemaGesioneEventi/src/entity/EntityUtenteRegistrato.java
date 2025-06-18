package entity;

import database.UtenteDAO;
import exceptions.DBException;
import exceptions.RedundancyException;


/**
 * Classe che rappresenta un'entità utente registrato all'interno del sistema.
 * Contiene i dati personali e di autenticazione associati all'utente, come
 * nome, cognome, email e password. Fornisce metodi per la gestione dell'utente,
 * inclusi aggiornamento delle informazioni e verifica delle credenziali.
 */
public class EntityUtenteRegistrato {
    /**
     * Identificativo univoco dell'utente registrato.
     */
    protected int id;
    /**
     * Rappresenta il nome dell'utente registrato.
     */
    protected String nome;
    /**
     * Rappresenta il cognome associato all'utente registrato.
     */
    protected String cognome;
    /**
     * Rappresenta l'indirizzo email associato all'utente registrato.
     * Utilizzato per identificare univocamente l'utente e per scopi di comunicazione.
     */
    protected String email;
    /**
     * Rappresenta la password associata all'utente registrato.
     * Utilizzata per l'autenticazione e la verifica delle credenziali.
     */
    protected String password;


    /**
     * La classe EntityUtenteRegistrato rappresenta un'entità che modella un utente registrato
     * all'interno del sistema. La classe gestisce informazioni come nome, cognome, email e password,
     * e fornisce metodi per la gestione e la verifica delle credenziali dell'utente.
     */
    public EntityUtenteRegistrato(){}

    /**
     * Costruisce un'istanza dell'entità EntityUtenteRegistrato con i dettagli forniti.
     *
     * @param nome Il nome dell'utente registrato.
     * @param cognome Il cognome dell'utente registrato.
     * @param email L'indirizzo email dell'utente registrato.
     * @param password La password associata all'utente registrato.
     */
    public EntityUtenteRegistrato(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }


    /**
     * Aggiorna i dettagli dell'utente corrente nel database.
     * Il metodo costruisce un nuovo oggetto dati utente utilizzando gli attributi dell'entità corrente
     * e tenta di salvarlo nel database. Se si verifica un conflitto nel database (ad esempio, un utente
     * con gli stessi dettagli esiste già), viene lanciata una RedundancyException.
     *
     * @throws RedundancyException se l'utente è già presente nel database.
     */
    public void aggiornamento()throws RedundancyException{
        UtenteDAO u = new UtenteDAO();
        u.setNome(this.nome);
        u.setEmail(this.email);
        u.setCognome(this.cognome);
        u.setPassword(this.password);
        try {
            u.SalvaInDB();
        } catch (DBException e) {
            throw new RedundancyException("Utente già registrato.");
        }
    }


    /**
     * Verifica se la password fornita corrisponde a quella associata all'utente registrato.
     *
     * @param password La password da confrontare con quella registrata.
     * @return true se la password fornita corrisponde a quella registrata, false altrimenti.
     */
    public boolean verificaCredenziali(String password){
        return this.password.equals(password);
    }


    /**
     * Restituisce l'identificativo univoco (ID) associato all'utente registrato.
     *
     * @return l'ID univoco dell'utente.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Imposta l'identificativo univoco per l'utente registrato.
     *
     * @param id L'identificativo univoco da assegnare all'utente registrato.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il nome associato all'utente registrato.
     *
     * @return Il nome dell'utente registrato.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'utente registrato.
     *
     * @param nome Il nuovo nome da assegnare all'utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dell'utente registrato.
     *
     * @return Il cognome dell'utente registrato.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'utente registrato.
     *
     * @param cognome Il cognome da assegnare all'utente registrato.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'indirizzo email associato all'utente registrato.
     *
     * @return La stringa che rappresenta l'email dell'utente registrato.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'indirizzo email dell'utente registrato.
     *
     * @param email La nuova email da assegnare all'utente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce la password associata all'utente registrato.
     *
     * @return La password dell'utente registrato.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password associata all'utente registrato.
     *
     * @param password La nuova password da associare all'utente.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
