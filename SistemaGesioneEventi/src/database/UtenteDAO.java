//test commento 1
package database;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

import exceptions.DBException;

/**
 * Classe che rappresenta un oggetto Utente nel contesto dell'applicazione,
 * gestendo le relative operazioni sui dati associati come il salvataggio,
 * il recupero e il caricamento di biglietti ed eventi dal database.
 * La classe si occupa di mappare i dati dell'entità "utente" del database
 * alle corrispondenti istanze Java e di fornire funzionalità di gestione.
 */
public class UtenteDAO {
    /**
     * Identificativo univoco dell'utente, utilizzato per rappresentare e distinguere
     * l'entità "utente" nel database e nel sistema applicativo.
     */
    private int id;
    /**
     * Indirizzo email associato all'utente.
     * Questo campo rappresenta l'indirizzo email utilizzato per identificare
     * l'utente nel sistema e per eventuali comunicazioni.
     */
    private String email;
    /**
     * Variabile che rappresenta la password associata all'entità utente nel database.
     * Utilizzata per autenticare e verificare l'accesso dell'utente.
     */
    private String password;
    /**
     * Rappresenta il nome dell'utente.
     * Questo campo memorizza il nome proprio associato all'utente
     * nel contesto della gestione del database degli utenti.
     */
    private String nome;
    /**
     * Rappresenta il cognome associato a un'istanza di UtenteDAO.
     * Questo attributo memorizza il cognome dell'utente e viene utilizzato
     * per identificare o gestire i dati personali nel contesto
     * dell'applicazione o del database.
     */
    private String cognome;
    /**
     * Rappresenta il percorso o l'URL dell'immagine di profilo associata all'utente.
     * Questo campo può essere utilizzato per identificare visivamente l'utente
     * all'interno dell'applicazione.
     */
    private String immagineProfilo;
    /**
     * Indica il ruolo dell'utente all'interno del sistema.
     * Il valore numerico rappresenta un livello o una categoria specifica di autorizzazione,
     * come ad esempio utente base, amministratore o moderatore.
     */
    private int ruolo;
    /**
     * Rappresenta una lista di oggetti {@code BigliettoDAO} associati all'utente.
     * Questo attributo memorizza i biglietti acquistati o gestiti dall'utente,
     * popolati tramite operazioni di caricamento dal database.
     */
    private ArrayList<BigliettoDAO> biglietti;
    /**
     * Lista di eventi associati all'utente corrente.
     * Ogni elemento della lista è un'istanza di {@code EventoDAO} che rappresenta un evento
     * amministrato o collegato all'utente.
     */
    private ArrayList<EventoDAO> eventi;

    /**
     * Classe UtenteDAO rappresenta l'accesso e la gestione dei dati relativi agli utenti
     * all'interno di un database. Fornisce metodi per operazioni CRUD, gestione di
     * eventi e biglietti associati, e recupero di informazioni relative agli utenti.
     */
    public UtenteDAO() {}


    /**
     * Costruttore della classe UtenteDAO che permette di creare un'istanza
     * utilizzando l'identificativo univoco dell'utente.
     *
     * @param id Identificativo univoco dell'utente nel database.
     */
    public UtenteDAO(int id){
        this.id = id;
    }

    /**
     * Salva un nuovo utente nel database inserendo i valori di email, password,
     * nome e cognome nei rispettivi campi della tabella "utenti".
     * Se l'utente è già presente nel database, viene sollevata un'eccezione
     * di tipo {@link DBException}.
     *
     * @throws DBException se si verifica un errore durante l'inserimento
     *                     dei dati nel database, come un'email già esistente.
     */
    public void salvaInDB() throws DBException {
        String query = "INSERT INTO utenti (email, password, nome, cognome) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, this.email);
            stmt.setString(2, this.password);
            stmt.setString(3, this.nome);
            stmt.setString(4, this.cognome);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.id = rs.getInt(1);
                } else {
                    throw new DBException("Errore");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    /**
     * Recupera la lista di utenti presenti nel database.
     *
     * @return Una lista di oggetti {@code UtenteDAO} contenente gli utenti recuperati dal database.
     * @throws DBException Se si verifica un errore durante l'accesso al database o l'elaborazione dei dati.
     */
    public static ArrayList<UtenteDAO> getUtenti() throws DBException {
        ArrayList<UtenteDAO> lista_temp = new ArrayList<>();
        String query = "SELECT * FROM utenti;";

        try {
            try (ResultSet rs = DBConnectionManager.selectQuery(query)) {

                while (rs.next()) {
                    UtenteDAO dao = new UtenteDAO();
                    dao.setId(rs.getInt("id"));
                    dao.setNome(rs.getString("nome"));
                    dao.setCognome(rs.getString("cognome"));
                    dao.setEmail(rs.getString("email"));
                    dao.setPassword(rs.getString("PASSWORD"));
                    dao.setRuolo(rs.getInt("Ruolo"));
                    dao.setImmagine(rs.getString("ImmagineProfilo"));
                    lista_temp.add(dao);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
        }

        return lista_temp;
    }


    /**
     * Carica tutti i biglietti associati a un determinato utente dal database e li
     * memorizza nella lista dei biglietti dell'istanza corrente.
     * Utilizza l'ID dell'utente per filtrare i biglietti da caricare.
     *
     * @throws DBException se si verifica un errore durante il recupero dei dati
     * dal database, come errori nella query o problemi di connessione.
     */
    public void caricaBigliettiDaDB() throws DBException {
        this.biglietti = new ArrayList<>();
        String query = "SELECT * FROM biglietti WHERE Cliente_id = " + this.id + ";";

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while (rs.next()) {
                BigliettoDAO bigliettoDao = this.createBigliettoDao(rs);
                this.biglietti.add(bigliettoDao);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Biglietto non trovato.");
        }
    }

    /**
     * Crea e restituisce un'istanza di {@code BigliettoDAO} basandosi sui dati presenti
     * nel {@code ResultSet} fornito come parametro. Imposta i campi del biglietto
     * leggendo i valori dal database e gestisce eventuali errori di lettura.
     *
     * @param rs il {@code ResultSet} contenente i dati del biglietto da mappare.
     * @return un'istanza di {@code BigliettoDAO} con i campi valorizzati.
     * @throws DBException se si verifica un errore durante il caricamento dei dati
     *                     dal {@code ResultSet}.
     */
    private BigliettoDAO createBigliettoDao(ResultSet rs) throws DBException {
        BigliettoDAO biglietto = new BigliettoDAO();
        try {
            biglietto.setCodice_univoco(rs.getString("CodiceUnivoco"));
            biglietto.setStato(rs.getInt("stato"));
            biglietto.setClienteId(rs.getInt("Cliente_id"));
            biglietto.setEventoId(rs.getInt("Evento_id"));
            return biglietto;

        } catch (SQLException e) {

            throw new DBException("Errore nel caricamento dei biglietti.");
        }
    }

    /**
     * Carica gli eventi associati a un amministratore specifico dal database
     * e li memorizza nella lista locale degli eventi dell'oggetto corrente.
     * Ogni evento viene estratto dai risultati della query e trasformato
     * in un'istanza di {@code EventoDAO}, la quale viene aggiunta alla lista degli eventi.
     * In caso di errore di connessione, query fallita o altre problematiche
     * relative al database, viene sollevata una {@code DBException}.
     *
     * @throws DBException se si verifica un errore durante il caricamento degli eventi dal database.
     */
    public void caricaEventiDaDB() throws DBException {
        this.eventi = new ArrayList<>();
        String query = "SELECT * FROM eventi WHERE Amministratore_id = " + this.id + ";";
        try (ResultSet rs = DBConnectionManager.selectQuery(query)) {
            while (rs.next()) {
                EventoDAO eventoTemp = new EventoDAO();
                eventoTemp.setId(rs.getInt("id"));
                eventoTemp.setTitolo(rs.getString("Titolo"));
                eventoTemp.setDescrizione(rs.getString("Descrizione"));
                eventoTemp.setData(rs.getDate("Data").toLocalDate());
                eventoTemp.setOra(LocalTime.parse(rs.getString("Orario")));
                eventoTemp.setLuogo(rs.getString("Luogo"));
                eventoTemp.setPartecipanti(rs.getInt("Partecipanti"));
                eventoTemp.setCapienza(rs.getInt("Capienza"));
                eventoTemp.setAmministratoreId(rs.getInt("Amministratore_id"));
                eventoTemp.setCosto(rs.getInt("Costo"));
                this.eventi.add(eventoTemp);

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
        }
    }

    /**
     * Elimina gli eventi associati a un utente specifico dal database.
     * L'operazione utilizza l'email dell'utente come identificativo per
     * eseguire la cancellazione dei dati correlati nella tabella corrispondente.
     *
     * @throws DBException se si verifica un errore durante l'interazione con il database,
     *                     ad esempio errori SQL o problemi di connessione.
     */
    public void eliminaEventiDaDb() throws DBException {
        String query = "DELETE FROM utenti WHERE email = '" + this.email + "'";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Errore durante l'eliminazione dell'utente dal DB.");
        }
    }


    /**
     * Restituisce il cognome dell'utente.
     *
     * @return una stringa contenente il cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome Il cognome da assegnare all'utente.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'indirizzo email associato all'utente DAO.
     *
     * @return una stringa contenente l'indirizzo email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'indirizzo email per l'utente corrente.
     *
     * @param email L'indirizzo email da assegnare all'utente. Deve essere una stringa valida
     *              che rappresenta un'email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce la password associata a questo oggetto UtenteDAO.
     *
     * @return La password dell'utente come stringa.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta una nuova password per l'utente.
     *
     * @param password la nuova password dell'utente. Deve essere valida e conforme
     * ai criteri di sicurezza definiti dall'applicazione.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return una stringa contenente il nome dell'utente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore del nome per l'oggetto UtenteDAO.
     *
     * @param nome il nome da assegnare all'utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il percorso o il riferimento all'immagine del profilo associata all'utente.
     *
     * @return una stringa che rappresenta il percorso o l'URL dell'immagine del profilo.
     */
    public String getImmagine() {
        return immagineProfilo;
    }

    /**
     * Imposta l'immagine del profilo dell'utente.
     *
     * @param immagine il percorso o l'URL dell'immagine da associare al profilo dell'utente.
     */
    public void setImmagine(String immagine) {
        this.immagineProfilo = immagine;
    }

    /**
     * Restituisce il valore del ruolo associato all'utente.
     *
     * @return un intero che rappresenta il ruolo dell'utente.
     */
    public int getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo per l'oggetto UtenteDAO.
     *
     * @param ruolo il codice numerico che rappresenta il ruolo dell'utente
     *              (ad esempio, amministratore, moderatore, utente standard, ecc.).
     */
    public void setRuolo(int ruolo) {this.ruolo = ruolo;}

    /**
     * Restituisce l'identificativo univoco (ID) dell'utente.
     *
     * @return l'ID dell'utente come valore intero.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificativo univoco dell'utente.
     *
     * @param id l'identificativo univoco da assegnare all'utente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce la lista dei biglietti associati a questo utente.
     *
     * @return un'ArrayList contenente gli oggetti BigliettoDAO collegati all'utente corrente.
     */
    public ArrayList<BigliettoDAO> getBiglietti() {
        return biglietti;
    }

    /**
     * Restituisce la lista degli eventi associati all'utente.
     *
     * @return un'ArrayList di oggetti {@code EventoDAO} che rappresentano gli eventi dell'utente.
     */
    public ArrayList<EventoDAO> getEventi() {
        return eventi;
    }
}

