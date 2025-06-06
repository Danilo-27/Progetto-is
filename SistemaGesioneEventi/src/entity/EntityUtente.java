//test commento 1
package entity;

import database.BigliettoDAO;
import database.EventoDAO;
import database.UtenteDAO;
import exceptions.DBException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class EntityUtente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String immagine;
    private int TipoUtente;
    private ArrayList<EntityEvento> eventi;
    private ArrayList<EntityBiglietto> biglietti;

    public static int AMMINISTRATORE=1;
    public static int CLIENTE=0;

    public EntityUtente(){}

    /**
     * Costruttore che crea un'istanza di EntityUtente a partire da un oggetto UtenteDAO.
     *
     * @param utente un'istanza di UtenteDAO contenente le informazioni dell'utente
     *               da cui inizializzare l'oggetto EntityUtente
     */
    public EntityUtente(UtenteDAO utente) {
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.immagine = utente.getImmagine();
        this.TipoUtente = utente.getTipoUtente();
    }

    /**
     * Costruttore che crea un'istanza di EntityUtente con i dati specificati.
     *
     * @param nome     il nome dell'utente
     * @param cognome  il cognome dell'utente
     * @param email    l'email dell'utente
     * @param password la password dell'utente
     */
    public EntityUtente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    /**
     * Costruttore che inizializza un'istanza di EntityUtente a partire
     * dall'indirizzo email fornito. Vengono anche popolati gli eventi creati se amministratore
     * oppure se cliente verrano popolati i biglietit creati
     *
     * @param email l'indirizzo email dell'utente da cui inizializzare
     *              l'istanza di EntityUtente
     * @throws DBException se si verifica un errore durante l'accesso al database
     */
    public EntityUtente(String email) throws DBException {
        this.email = email;
        UtenteDAO udao = new UtenteDAO(email);
        //System.out.println(udao.getId());
        this.id=udao.getId();
        this.nome = udao.getNome();
        this.password = udao.getPassword();
        this.cognome = udao.getCognome();
        this.immagine = udao.getImmagine();
        this.TipoUtente = udao.getTipoUtente();
        if(this.TipoUtente==AMMINISTRATORE){
                //carica eventi creati
            this.eventi=new ArrayList<>();
            udao.caricaEventiDaDB();
            this.caricaEventiPubblicati(udao);
        }else{
                //carica biglietti acquistati
            this.biglietti =new ArrayList<>();
            udao.caricaBigliettiDaDB();
            this.caricaBiglietti(udao);
        }
    }
    /**
    *Costruttore che crea un EntityUtente dato il suo id
     */
    public EntityUtente(int id) throws DBException {
        this.id=id;
        this.caricadaDB();
    }

    /**
    *Metodo che permette di creare un utente data la sua email senza chiedere i biglietti acquistati
     * oppure gli eventi creati
     */

    public void caricaPerEmail(String email) throws DBException {
        try{
            UtenteDAO dbCliente = new UtenteDAO(email);
            this.setId(dbCliente.getId());
            this.setNome(dbCliente.getNome());
            this.setCognome(dbCliente.getCognome());
            this.setPassword(dbCliente.getPassword());
            this.setEmail(dbCliente.getEmail());
            this.setTipoUtente(dbCliente.getTipoUtente());
        }catch(DBException e) {
            throw e;
        }
    }

    private void setTipoUtente(int tipoUtente) {
        this.TipoUtente = tipoUtente;
    }

    /**
     * Salva l'utente corrente nel database utilizzando un'istanza di UtenteDAO.
     *
     * @return Un intero che rappresenta l'esito del salvataggio nel database:
     *         un valore maggiore di 0 indica che il salvataggio è avvenuto con successo,
     *         mentre un valore pari -1 indica un errore.
     */

    public int scriviSuDB() {
        UtenteDAO u = new UtenteDAO();
        u.setNome(this.nome);
        u.setEmail(this.email);
        u.setCognome(this.cognome);
        u.setPassword(this.password);
        return u.SalvaInDB();
    }

    /**
     * Carica i dati dell'utente dal database identificato tramite
     * il suo id e aggiorna l'istanza corrente con le informazioni recuperate.
     *
     * @throws DBException se si verifica un errore durante l'accesso al database.
     */
    public void caricadaDB() throws DBException {
        try{
            UtenteDAO dbCliente = new UtenteDAO(id);
            this.setNome(dbCliente.getNome());
            this.setCognome(dbCliente.getCognome());
            this.setPassword(dbCliente.getPassword());
            this.setEmail(dbCliente.getEmail());
        }catch(DBException e) {
            throw e;
        }

    }

    /**
     * Crea un nuovo evento e lo ritorna.L'evento sarà associato all'amministratore corrente che l'ha creato
     *
     * @param Titolo il titolo dell'evento
     * @param Descrizione la descrizione dell'evento
     * @param Data la data dell'evento
     * @param Ora l'ora dell'evento
     * @param Luogo la locazione dell'evento
     * @param Costo il costo dell'evento
     * @param Capienza la capienza dell'evento
     * @return Un istanza di EntityEvento che rappresenta l'evento creato
     *
     */
    public EntityEvento creazioneEvento(String Titolo, String Descrizione, LocalDate Data, LocalTime Ora, String Luogo, int Costo, int Capienza) {
        return new EntityEvento(Titolo,Descrizione,Data,Ora,Luogo,Costo,Capienza,this);
    }

    /**
     * Verifica se la password specificata corrisponde alla password dell'utente.
     *
     * @param Password la password da verificare
     * @return true se la password specificata corrisponde alla password dell'utente, false altrimenti
     */
    public boolean verificaCredenziali(String Password){
        return this.password.equals(Password);
    }

    /**
     * Carica la lista di biglietti associati a un utente.
     * Per ogni biglietto presente nella lista restituita dal DAO dell'utente,
     * viene creata una nuova istanza di EntityBiglietto, che viene aggiunta
     * alla lista dei biglietti dell'utente corrente.
     *
     * @param utente l'oggetto UtenteDAO che contiene le informazioni e i biglietti
     *               dell'utente dal database
     */

    public void caricaBiglietti(UtenteDAO utente) throws DBException {
        this.biglietti = new ArrayList<>();
        for (BigliettoDAO bigliettoDAO : utente.getBiglietti()) {
            EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO,this);
            this.biglietti.add(biglietto);
        }
    }

    /**
     * Carica la lista degli eventi pubblicati associati a un utente.
     * Per ogni evento presente nella lista restituita dal DAO dell'utente,
     * viene creata una nuova istanza di EntityEvento, che viene aggiunta
     * alla lista degli eventi dell'utente corrente.
     *
     * @param utente l'oggetto UtenteDAO che rappresenta l'utente contenente
     *               le informazioni sugli eventi pubblicati.
     */
    public void caricaEventiPubblicati(UtenteDAO utente) {
        this.eventi = new ArrayList<>();
        for (EventoDAO eventoDAO : utente.getEventi()) {
            EntityEvento evento = new EntityEvento(eventoDAO);
            this.eventi.add(evento);
        }
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getImmagine() {
        return
                immagine;
    }
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    public int getTipoUtente() {
        return TipoUtente;
    }
    public ArrayList<EntityEvento> getEventi() {
        return eventi;
    }
    public ArrayList<EntityBiglietto> getBiglietti() {
        return biglietti;
    }
}
