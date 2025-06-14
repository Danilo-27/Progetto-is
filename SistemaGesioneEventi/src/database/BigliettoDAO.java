//test commento 1
package database;

import exceptions.DBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe BigliettoDAO.
 *
 * Questa classe rappresenta il Data Access Object (DAO) per la gestione
 * dei dati relativi ai biglietti all'interno di un'applicazione. Fornisce
 * metodi per effettuare operazioni CRUD (Create, Read, Update, Delete) sui
 * biglietti memorizzati nel database, come il caricamento dei dati di un
 * biglietto, l'associazione degli eventi e degli utenti ad esso correlati,
 * e la gestione dello stato del biglietto.
 *
 * La classe utilizza il campo 'codice_univoco' come identificatore univoco
 * per accedere ai dati dei biglietti. Inoltre, include riferimenti ad altre
 * entità del modello (come EventoDAO e UtenteDAO) per rappresentare le
 * associazioni relazionali contenute nel database.
 *
 * Responsabilità principali:
 * - Caricare dati del biglietto dal database.
 * - Associare eventi e clienti al biglietto.
 * - Salvare e aggiornare i dati del biglietto nel database.
 * - Gestire errori relativi al database tramite eccezioni personalizzate.
 */
public class BigliettoDAO{

    /**
     * Rappresenta un codice univoco associato a un biglietto.
     * Questo campo è utilizzato per identificare in modo univoco
     * un biglietto all'interno del sistema e del database.
     */
    private String codice_univoco;
    /**
     * Rappresenta lo stato del biglietto all'interno del sistema.
     * Può indicare, ad esempio, se il biglietto è valido, scaduto o cancellato,
     * utilizzando valori numerici definiti nel contesto applicativo.
     */
    private int stato;
    /**
     * Identificativo univoco del cliente associato al biglietto.
     * Questo campo rappresenta una chiave esterna che collega il biglietto
     * al cliente nella tabella "utenti" del database.
     */
    private int Cliente_id;
    /**
     * Identificativo univoco dell'evento associato al biglietto.
     * Viene utilizzato per collegare un biglietto a un evento specifico
     * all'interno del sistema e per eseguire operazioni di recupero o associazione
     * dei dati relativi al suddetto evento.
     */
    private int Evento_id;
    /**
     * Rappresenta il riferimento all'oggetto di tipo EventoDAO associato al biglietto corrente.
     * Questo attributo viene utilizzato per gestire e accedere ai dati dell'evento
     * associato tramite operazioni di caricamento e manipolazione nel database.
     */
    private EventoDAO evento;
    /**
     * Rappresenta un oggetto di tipo UtenteDAO associato al biglietto corrente.
     * Questo campo viene utilizzato per gestire i dati dell'utente relativo
     * al biglietto, come il recupero o l'aggiornamento delle informazioni
     * dell'utente nel database.
     */
    private UtenteDAO utente;

    /**
     * Classe Data Access Object (DAO) per la gestione dei dati relativi ai biglietti.
     * Fornisce metodi per interagire con la tabella "biglietti" del database,
     * permettendo operazioni di creazione, lettura, aggiornamento ed eliminazione (CRUD)
     * dei record associati ai biglietti.
     * La classe include metodi specifici per il caricamento di informazioni aggiuntive,
     * come dati relativi ai clienti e agli eventi collegati al biglietto.
     */
    public BigliettoDAO() {}

    /**
     * Costruttore della classe BigliettoDAO che carica un biglietto dal database
     * utilizzando il codice univoco fornito. Questo metodo inizializza
     * l'oggetto BigliettoDAO e carica le informazioni correlate, come l'evento
     * e l'utente associati al biglietto.
     *
     * @param codice_univoco Il codice univoco del biglietto da caricare dal database.
     * @throws DBException Se si verifica un errore durante l'accesso al database
     *                     o se il biglietto non viene trovato.
     */
    //carico un biglietto dato il codice (usato in entityEvento)
    public BigliettoDAO(String codice_univoco) throws DBException{
        this.codice_univoco = codice_univoco;
        this.caricaDaDB();
        this.caricaEventoFromBigliettoDaDB();
        this.caricaUtenteDaBigliettoDaDB();
    }

    /**
     * Carica i dati dell'oggetto corrente dal database utilizzando il codice univoco del biglietto.
     * Il metodo esegue una query sulla tabella biglietti per recuperare il record
     * associato al codice univoco memorizzato nel campo 'codice_univoco'.
     * <p>
     * In caso di recupero riuscito, aggiorna i campi dell'oggetto 'stato',
     * 'Cliente_id' ed 'Evento_id' con i valori corrispondenti dal database.
     * Se nessun record viene trovato, il metodo lancia una DBException che indica che
     * il biglietto non è stato trovato nel database.
     *
     * @throws DBException se si verifica un errore SQL o se il biglietto non viene trovato nel database.
     */
    
    public void caricaDaDB() throws DBException {
        String query = "SELECT * FROM biglietti WHERE  CodiceUnivoco = '" + this.codice_univoco + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.stato = rs.getInt("stato");
                this.Cliente_id = rs.getInt("Cliente_id");
                this.Evento_id = rs.getInt("Evento_id");
            } else {
                throw new SQLException();
            }
        }catch (SQLException |ClassNotFoundException e){
            throw new DBException("Bigleitto non trovato");
        }
    }

    /**
     * Carica i dati dell'evento associato al biglietto corrente dal database.
     * Questo metodo esegue una query sulla tabella "eventi" utilizzando l'ID evento
     * memorizzato nel campo 'Evento_id' dell'oggetto corrente. Se viene trovato un record
     * corrispondente, i dati dell'evento vengono caricati e utilizzati per popolare
     * l'oggetto interno 'evento'.
     * <p>
     * Se non viene trovato alcun evento corrispondente nel database, o se si verifica
     * un errore durante l'interazione con il database, viene lanciata una DBException
     * che indica il fallimento.
     *
     * @throws DBException se l'evento non viene trovato nel database o se si verifica
     *                     un errore SQL/di connessione.
     */
    public void caricaEventoFromBigliettoDaDB() throws DBException{
        String query = "SELECT * FROM eventi WHERE ID = " + this.Evento_id + ";";
        try{
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                EventoDAO evento= new EventoDAO();
                evento.setTitolo(rs.getString("Titolo"));
                evento.setAmministratoreId(rs.getInt("Amministratore_id"));
                evento.setCapienza(rs.getInt("capienza"));
                evento.setPartecipanti(rs.getInt("partecipanti"));
                evento.setData(LocalDate.parse(rs.getString("data")));
                evento.setOra(LocalTime.parse(rs.getString("orario")));
                evento.setDescrizione(rs.getString("descrizione"));
                evento.setLuogo(rs.getString("luogo"));

                this.evento = evento;
            } else {
                throw new SQLException();
            }
        }catch(SQLException | ClassNotFoundException e){
            throw new DBException("Evento non trovato");
        }
    }

    /**
     * Carica i dati dell'utente associato al biglietto corrente dal database.
     * Questo metodo esegue una query sulla tabella "utenti" utilizzando il campo 'Cliente_id'
     * dell'oggetto corrente per recuperare i dati utente corrispondenti. Se viene trovato un record,
     * i dati recuperati vengono utilizzati per popolare un oggetto interno 'utente'.
     * <p>
     * Se non viene trovato alcun utente corrispondente nel database, viene lanciata una DBException.
     *
     * @throws DBException se l'utente non viene trovato nel database o se si verifica
     *                     un errore durante l'interazione con il database.
     */
    public void caricaUtenteDaBigliettoDaDB() throws DBException {
        String query = "SELECT * FROM utenti WHERE ID = " + this.Cliente_id + ";";
        ResultSet rs = null;
        try {
            rs = DBConnectionManager.selectQuery(query);

            if (rs.next()) {
                UtenteDAO user = new UtenteDAO();
                user.setId(this.Cliente_id);
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setNome(rs.getString("nome"));
                user.setCognome(rs.getString("cognome"));
                user.setImmagine(rs.getString("immagineProfilo"));
                user.setRuolo(rs.getInt("Tipo"));
                this.utente = user;
            } else {
                throw new SQLException();
            }
        }catch (ClassNotFoundException | SQLException e){
            throw new DBException("Cliente non trovato");
        }

    }

    /**
     * Salva i dati dell'oggetto corrente nel database.
     *
     * Questo metodo crea una query SQL di tipo INSERT per inserire un nuovo record
     * nella tabella "biglietti" utilizzando i valori delle proprietà
     * 'codice_univoco', 'stato', 'Cliente_id' ed 'Evento_id' dell'oggetto corrente.
     * In caso di successo, il nuovo biglietto viene persistito nel database.
     *
     * Se si verifica una violazione dei vincoli di integrità (ad esempio,
     * chiavi duplicate o riferimenti non validi), viene lanciata una
     * DBException con un messaggio dettagliato sull'errore.
     *
     * @throws DBException Se si verifica un errore durante l'esecuzione della query
     *                     o una violazione dei vincoli di integrità.
     */
    public void SalvaInDB() throws DBException {
        String query = "INSERT INTO biglietti (CodiceUnivoco, stato, Cliente_id, Evento_id) " +
                "VALUES ('" + this.codice_univoco + "', '" + this.stato + "', '" + this.Cliente_id + "', '" + this.Evento_id + "');";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DBException("Violazione dei vincoli di integrità: " + e.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Biglietto già creato.");
        }
    }


    /**
     * Aggiorna lo stato del biglietto corrente nel database.
     * <p>
     * Questo metodo genera una query SQL di tipo UPDATE per modificare lo stato del biglietto
     * nella tabella "biglietti". La query aggiorna il campo "stato" del record
     * identificato dal campo "CodiceUnivoco" dell'oggetto corrente.
     * <p>
     * Se l'aggiornamento ha successo, il record nel database rifletterà il nuovo stato.
     * In caso di errori durante l'interazione con il database (come errori SQL o di connessione),
     * il metodo lancia una DBException.
     *
     * @throws DBException se si verifica un errore durante l'operazione di aggiornamento o se il biglietto
     *                     non viene trovato nel database.
     */
    public void aggiornaInDB() throws DBException {
        String query="UPDATE biglietti SET stato='" + this.stato + "' WHERE CodiceUnivoco ='" + this.codice_univoco + "';";
        try{
            DBConnectionManager.updateQuery(query);
        }catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Biglietto non trovato");
        }
    }

    /**
     * Elimina un biglietto dal database utilizzando il codice univoco dell'oggetto corrente.
     *
     * Questo metodo esegue una query SQL di tipo DELETE sulla tabella "biglietti"
     * per rimuovere il record associato al valore del campo 'codice_univoco' dell'oggetto corrente.
     *
     * Se si verifica un errore durante l'esecuzione della query, come problemi di connessione
     * al database o errori SQL, viene lanciata una DBException con un messaggio che descrive
     * l'errore verificatosi.
     *
     * @throws DBException se si verifica un errore durante l'eliminazione del biglietto dal database.
     */
    public void eliminaBigliettoDaDb() throws DBException{
        String query = "DELETE FROM biglietti WHERE CodiceUnivoco= '" + this.codice_univoco + "'";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Errore durante l'eliminazione del biglietto dal DB.");
        }
    }

    /**
     * Restituisce il valore del codice univoco associato al biglietto corrente.
     *
     * @return Il codice univoco del biglietto come stringa.
     */
    public String getCodice_univoco() {
        return codice_univoco;
    }

    /**
     * Imposta il codice univoco del biglietto.
     * Il codice univoco identifica in modo univoco un biglietto all'interno del sistema.
     *
     * @param codice_univoco Il codice univoco da associare al biglietto.
     */
    public void setCodice_univoco(String codice_univoco) {
        this.codice_univoco = codice_univoco;
    }

    /**
     * Restituisce lo stato del biglietto associato all'oggetto corrente.
     * Lo stato rappresenta un valore numerico che indica la condizione o il livello
     * di progressione del biglietto, ad esempio se è attivo, scaduto, utilizzato, ecc.
     *
     * @return Un intero che rappresenta lo stato del biglietto.
     */
    public int getStato() {
        return stato;
    }

    /**
     * Imposta il valore del campo "stato" per il biglietto corrente.
     * Questo metodo consente di aggiornare lo stato interno del biglietto,
     * che potrebbe rappresentare informazioni come la validità o il progresso
     * di utilizzo del biglietto.
     *
     * @param stato Il nuovo valore dello stato da assegnare al biglietto.
     */
    public void setStato(int stato) {
        this.stato = stato;
    }

    /**
     * Restituisce l'identificativo del cliente associato al biglietto.
     *
     * @return l'ID del cliente associato al biglietto.
     */
    public int getClienteId() {
        return Cliente_id;
    }

    /**
     * Imposta l'ID del cliente associato al biglietto.
     *
     * @param clienteId L'identificativo univoco del cliente da associare al biglietto.
     */
    public void setClienteId(int clienteId) {
        this.Cliente_id = clienteId;
    }

    /**
     * Restituisce l'ID dell'evento associato al biglietto.
     *
     * @return un intero che rappresenta l'ID dell'evento associato al biglietto.
     */
    public int getEventoId() {
        return Evento_id;
    }

    /**
     * Imposta l'ID dell'evento associato al biglietto corrente.
     *
     * @param eventoId L'ID dell'evento da assegnare al biglietto.
     */
    public void setEventoId(int eventoId) {
        this.Evento_id = eventoId;
    }

    /**
     * Restituisce l'oggetto EventoDAO associato al biglietto corrente.
     * Questo oggetto rappresenta i dati dell'evento collegato
     * al biglietto e può includere informazioni come il nome, la data
     * e la location dell'evento.
     *
     * @return l'oggetto EventoDAO associato al biglietto. Se l'evento
     *         non è stato caricato, potrebbe restituire null.
     */
    public EventoDAO getEvento() {
        return evento;
    }

    /**
     * Restituisce l'oggetto UtenteDAO associato al biglietto corrente.
     *
     * @return l'istanza di UtenteDAO che rappresenta l'utente collegato al biglietto.
     */
    public UtenteDAO getUtente() {return utente;}
}
