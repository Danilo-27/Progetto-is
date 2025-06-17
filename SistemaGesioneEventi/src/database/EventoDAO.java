package database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import exceptions.DBException;
import exceptions.LoadingException;

/**
 * Classe che rappresenta un gestore di eventi per l'interazione con il database.
 * EventoDAO fornisce un'astrazione per la gestione dei dati degli eventi, incluso il recupero,
 * la creazione, l'aggiornamento e la rimozione di eventi dalla tabella 'eventi' di un database.
 * Gli eventi includono informazioni come titolo, descrizione, data, ora, luogo, capienza,
 * partecipanti, costo, amministratore associato e biglietti collegati.
 */
public class EventoDAO {

    /**
     * Identificativo univoco dell'evento nel database.
     * Rappresenta la chiave primaria nella tabella associata.
     */
    private int id;
    /**
     * Rappresenta il titolo dell'evento.
     * Questo attributo memorizza il nome o la designazione identificativa assegnata all'evento.
     */
    private String titolo;
    /**
     * Rappresenta la descrizione di un evento.
     * Viene utilizzata per memorizzare un testo che fornisce informazioni aggiuntive sull'evento.
     */
    private String descrizione;
    /**
     * Rappresenta la data dell'evento.
     * Questo attributo memorizza la data in cui si terrà l'evento, utilizzando il tipo `LocalDate`.
     */
    private LocalDate data;
    /**
     * Rappresenta l'ora dell'evento memorizzata come istanza di {@link LocalTime}.
     * Questo attributo indica l'orario specifico in cui si svolge l'evento.
     */
    private LocalTime ora;
    /**
     * Indica il luogo in cui si svolge l'evento.
     */
    private String luogo;
    /**
     * Rappresenta il costo associato a un evento in termini monetari.
     * Indica il prezzo dell'evento, espresso come intero.
     */
    private int costo;
    /**
     * Indica il numero massimo di partecipanti che un evento può accogliere.
     * Questo valore rappresenta la capienza massima consentita dell'evento
     * nel database e viene utilizzato per controllare se ulteriori partecipanti
     * possono essere registrati.
     */
    private int capienza;
    /**
     * Rappresenta il numero di partecipanti attuali associati a un evento.
     * Questo attributo tiene traccia del numero di persone che hanno
     * confermato la loro partecipazione a un determinato evento.
     */
    private int partecipanti;
    /**
     * Identificativo univoco dell'amministratore associato all'evento.
     * Questo campo rappresenta l'ID del database che collega un evento
     * specifico all'amministratore responsabile.
     */
    private int amministratoreID;
    /**
     * Rappresenta la lista dei biglietti associati a un evento.
     * Ogni elemento della lista è un'istanza di {@code BigliettoDAO} che contiene
     * i dettagli di un singolo biglietto legato all'evento corrente.
     */
    private ArrayList<BigliettoDAO> biglietti;

    /**
     * Costante che rappresenta il nome del campo "id" nella tabella del database associata agli eventi.
     * Viene utilizzata per riferirsi in modo univoco al campo identificativo degli eventi nelle query SQL.
     */
    private static final String ID_DB = "id";
    /**
     * Costante che rappresenta il nome del campo "Titolo" utilizzato nel database.
     * Questa variabile è utilizzata per identificare la colonna relativa al titolo degli eventi
     * all'interno delle query SQL.
     */
    private static final String TITOLO_DB ="Titolo";
    /**
     * Costante che rappresenta il nome della colonna "descrizione" nella tabella del database.
     * Viene utilizzata per mappare il campo "descrizione" degli oggetti evento
     * con il corrispondente attributo nel database.
     */
    private static final String DESCRIZIONE_DB ="Descrizione";
    /**
     * Nome della colonna 'data' nella tabella del database degli eventi.
     * Questo campo viene utilizzato per riferirsi alla colonna nella costruzione
     * di query SQL e nell'interazione con il database.
     */
    private static final String DATA_DB ="Data";
    /**
     * Nome del campo nel database che rappresenta l'orario di un evento.
     * Questa costante viene utilizzata per mappare il nome della colonna
     * del database relativa all'ora dell'evento.
     */
    private static final String ORARIO_DB ="Orario";
    /**
     * Costante che rappresenta il nome del campo "luogo" nella tabella del database.
     * Utilizzata per garantire consistenza nelle query SQL e ridurre la possibilità di errori.
     */
    private static final String LUOGO_DB ="Luogo";
    /**
     * Costante che rappresenta il nome del campo "partecipanti" nella tabella del database.
     * Utilizzata per costruire query SQL relative al numero di partecipanti di un evento.
     */
    private static final String PARTECIPANTI_DB ="Partecipanti";
    /**
     * Costante che rappresenta il nome della colonna nel database associata all'ID
     * dell'amministratore di un evento.
     * Questa costante viene utilizzata per referenziare in modo univoco la colonna
     * relativa all'identificativo dell'amministratore nella tabella 'eventi' del database,
     * garantendo coerenza e riducendo errori dovuti a stringhe duplicate nel codice.
     */
    private static final String AMMINISTRATORE_DB ="Amministratore_id";
    /**
     * Costante che rappresenta il nome del campo "capienza" nella tabella del database.
     * Utilizzata per identificare in modo univoco la colonna relativa alla capienza degli eventi.
     */
    private static final String CAPIENZA_DB ="Capienza";
    /**
     * Costante che rappresenta il nome del campo "costo" nella tabella del database.
     * Utilizzata per costruire query SQL relative al costo degli eventi.
     */
    private static final String COSTO_DB ="Costo";

    /**
     * Costruttore della classe EventoDAO.
     * Inizializza un'istanza vuota di EventoDAO senza impostare alcun valore iniziale agli attributi.
     * La classe EventoDAO rappresenta un'entità evento e fornisce metodi per interagire con i dati degli eventi
     * archiviati in un database, oltre a gestirne i relativi attributi.
     */
    public EventoDAO() {}

    /**
     * Costruttore della classe EventoDAO che inizializza un'istanza basata sul titolo specificato.
     * Questo costruttore crea una nuova lista di biglietti associata all'evento e tenta di
     * caricare i dati dell'evento corrispondente dal database utilizzando il titolo fornito.
     * Se si verifica un errore nella connessione o nell'esecuzione della query SQL, l'eccezione
     * viene catturata e stampata nello stack trace.
     *
     * @param titolo Il titolo dell'evento utilizzato per identificare l'evento nel database.
     */
    public EventoDAO(String titolo){
        this.titolo=titolo;
        this.biglietti= new ArrayList<>();

        try {
            this.caricaDaDBPerTitolo();
        }catch(DBException e){
            throw new LoadingException(e.getMessage());
        }
    }
    /**
     * Recupera una lista di tutti gli eventi dal database.
     * <p>
     * Questo metodo esegue una query SQL per ottenere tutte le righe dalla
     * tabella 'eventi' nel database e le converte in istanze della classe
     * EventoDAO. Ogni riga corrisponde a uno specifico evento con i suoi
     * dettagli, che vengono memorizzati in un oggetto EventoDAO e aggiunti
     * alla lista restituita.
     *
     * @return una lista di oggetti EventoDAO che rappresentano tutti gli eventi
     * recuperati dal database in ordine ascendente per data
     * @throws DBException se si verifica un errore durante l'operazione sul
     *                     database o la connessione
     */
    public static ArrayList<EventoDAO> getEventi() throws DBException {
        ArrayList<EventoDAO> lista_temp = new ArrayList<>();
        String query = "SELECT * FROM eventi WHERE Data;";

        try {
            try (ResultSet rs = DBConnectionManager.selectQuery(query)) {

                while (rs.next()) {
                    EventoDAO eventoTemp = new EventoDAO();
                    eventoTemp.setId(rs.getInt(EventoDAO.ID_DB));
                    eventoTemp.setTitolo(rs.getString(EventoDAO.TITOLO_DB));
                    eventoTemp.setDescrizione(rs.getString(EventoDAO.DESCRIZIONE_DB));
                    eventoTemp.setData(rs.getDate(EventoDAO.DATA_DB).toLocalDate());
                    eventoTemp.setOra(LocalTime.parse(rs.getString(EventoDAO.ORARIO_DB)));
                    eventoTemp.setLuogo(rs.getString(EventoDAO.LUOGO_DB));
                    eventoTemp.setPartecipanti(rs.getInt(EventoDAO.PARTECIPANTI_DB));
                    eventoTemp.setCapienza(rs.getInt(EventoDAO.CAPIENZA_DB));
                    eventoTemp.setAmministratoreId(rs.getInt(EventoDAO.AMMINISTRATORE_DB));
                    eventoTemp.setCosto(rs.getInt(EventoDAO.COSTO_DB));


                    lista_temp.add(eventoTemp);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
        }

        return lista_temp;
    }

    /**
     * Salva i dati dell'evento nel database.
     * Questo metodo utilizza una query SQL per inserire i dati dell'evento corrente
     * nella tabella 'eventi' del database. I valori degli attributi dell'oggetto corrente 
     * vengono utilizzati per popolare i campi della tabella corrispondenti.
     * @throws DBException se si verifica un errore durante l'esecuzione della query
     *                     o se l'operazione non può essere completata.
     */
    public void SalvaInDB() throws DBException{
        String query = "INSERT INTO eventi (titolo, descrizione, data, orario, luogo, costo, capienza, partecipanti, Amministratore_id) " +
                "VALUES ('" + this.titolo + "', '" + this.descrizione + "', '" + this.data + "', '" + this.ora + "', '" + this.luogo + "', " +
                this.costo + ", " + this.capienza + ", " + this.partecipanti + ", " + this.amministratoreID + ")";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Evento già creato");
        }
    }

    /**
     * Aggiorna il numero di partecipanti dell'evento nel database.
     * <p>
     * Questo metodo costruisce una query SQL UPDATE per modificare il numero
     * di partecipanti dell'evento identificato tramite il titolo nella
     * tabella 'eventi'. Il nuovo numero di partecipanti viene preso
     * dal valore della variabile di istanza dell'oggetto corrente.
     *
     * @throws DBException se si verifica un errore durante l'operazione di
     *                     aggiornamento, come un problema di connessione al
     *                     database o un errore nell'esecuzione della query SQL.
     */
    public void AggiornaInDB() throws DBException{
        String query="UPDATE eventi SET Partecipanti='" + this.partecipanti + "' WHERE titolo ='" + this.titolo + "';";
        try{
            DBConnectionManager.updateQuery(query);
        }catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Problema nell'aggiornare l'evento");
        }
    }

    /**
     * Carica i dati di un evento dal database associato all'identificativo univoco dell'istanza corrente.
     * Questo metodo esegue una query SQL per recuperare i dettagli dell'evento,
     * inclusi titolo, descrizione, data, ora, luogo, numero di partecipanti, capienza, amministratore ID e costo.
     * I dati dell'evento vengono popolati nei rispettivi attributi dell'oggetto.
     *
     * @throws DBException se l'evento non esiste nel database o si verifica un errore di connessione
     * o esecuzione della query SQL.
     */
    public void caricaDaDB() throws DBException {
        String query = "SELECT * FROM eventi WHERE id = " + this.id + ";";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.titolo = rs.getString(EventoDAO.TITOLO_DB);
                this.descrizione = rs.getString(EventoDAO.DESCRIZIONE_DB);
                this.data = rs.getDate(EventoDAO.DATA_DB).toLocalDate();
                this.ora = LocalTime.parse(rs.getString(EventoDAO.ORARIO_DB));
                this.luogo = rs.getString(EventoDAO.LUOGO_DB);
                this.partecipanti = rs.getInt(EventoDAO.PARTECIPANTI_DB);
                this.capienza = rs.getInt(EventoDAO.CAPIENZA_DB);
                this.amministratoreID = rs.getInt(EventoDAO.AMMINISTRATORE_DB);
                this.costo = rs.getInt(EventoDAO.COSTO_DB);
            } else {
                throw new DBException(String.format("Evento con ID '%d' non esistente", this.id));
            }
            rs.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new DBException("Evento non presente nel DB.");
        }
    }

    /**
     * Carica i dettagli dell'evento dal database in base al titolo dell'istanza corrente.
     * <p>
     * Questo metodo esegue una query SQL per recuperare i dettagli dell'evento
     * corrispondente al titolo dell'istanza corrente. Se l'evento viene trovato,
     * vengono popolati i suoi attributi come ID, descrizione, data, ora, luogo,
     * numero di partecipanti e capienza. Se non viene trovato nessun evento
     * corrispondente, viene lanciata una DBException.
     *
     * @throws DBException se l'evento non esiste nel database o se si verifica
     *                     un errore durante l'esecuzione della query SQL o la
     *                     connessione al database.
     */
    public void caricaDaDBPerTitolo() throws DBException{
        String query = "SELECT * FROM eventi WHERE Titolo='" + this.titolo + "';";
        try{
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.id=(rs.getInt(EventoDAO.ID_DB));
                this.titolo=(rs.getString(EventoDAO.TITOLO_DB));
                this.descrizione=(rs.getString(EventoDAO.DESCRIZIONE_DB));
                this.data=(rs.getDate(EventoDAO.DATA_DB).toLocalDate());
                this.ora=(LocalTime.parse(rs.getString(EventoDAO.ORARIO_DB)));
                this.luogo=(rs.getString(EventoDAO.LUOGO_DB));
                this.partecipanti=(rs.getInt(EventoDAO.PARTECIPANTI_DB));
                this.capienza=(rs.getInt(EventoDAO.CAPIENZA_DB));

            } else {
                throw new DBException(String.format("Evento '%s' non esistente", id));
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException("Errore nel caricamento dell'evento dal database.");
        }
    }

    /**
     * Carica i biglietti associati all'evento corrente dal database e popola
     * la lista dei biglietti per l'istanza dell'evento.
     * <p>
     * Questo metodo esegue una query SQL per recuperare le informazioni sui biglietti dell'evento
     * identificato dall'ID dell'istanza corrente. Per ogni biglietto trovato nel database,
     * viene creato un nuovo oggetto {@code BigliettoDAO} e popolato con i dettagli del biglietto,
     * inclusi il suo codice univoco, stato, ID cliente e ID evento. Gli oggetti creati vengono
     * aggiunti alla lista interna dei biglietti dell'evento.
     *
     * @throws DBException se si verifica una delle seguenti condizioni:
     *                     - Si verifica un errore nell'interrogazione o nella connessione al database.
     *                     - Non vengono trovati biglietti per l'evento nel database.
     */

    public void caricaBigliettiEventiDaDB() throws DBException{
        String query = "SELECT * FROM biglietti WHERE Evento_id = " + this.id + ";";

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while(rs.next()) {
                BigliettoDAO biglietto = new BigliettoDAO();
                biglietto.setCodice_univoco(rs.getString("CodiceUnivoco"));
                biglietto.setStato(rs.getInt("stato"));
                biglietto.setClienteId(rs.getInt("Cliente_id"));
                biglietto.setEventoId(rs.getInt("Evento_id"));
                this.biglietti.add(biglietto);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Biglietti non presenti nel DB.");
        }
    }
    /**
     * Rimuove un evento dalla tabella 'eventi' del database in base al titolo dell'istanza corrente.
     * Questo metodo esegue una query SQL DELETE utilizzando il titolo dell'evento memorizzato
     * nella variabile di istanza `titolo` dell'oggetto corrente. L'eliminazione viene effettuata
     * direttamente nella tabella 'eventi' del database.
     * Se si verifica un errore durante l'interazione con il database, come un problema di connessione
     * o un errore nell'esecuzione della query SQL, viene lanciata una {@link DBException}.
     *
     * @throws DBException se si verifica un errore durante l'interazione con il database, 
     *                     inclusi errori di connessione o esecuzione della query.
     */
    public void eliminaEventoDaDb() throws DBException{
        String query = "DELETE FROM eventi WHERE Titolo = '" + this.titolo + "'";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Errore durante l'eliminazione dell'evento dal DB.");
        }
    }

    /**
     * Recupera il titolo dell'evento associato a questa istanza.
     *
     * @return Il titolo dell'evento come stringa.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Restituisce la descrizione dell'evento.
     *
     * @return una stringa contenente la descrizione dell'evento.
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce l'orario associato all'istanza dell'evento.
     *
     * @return l'orario dell'evento come oggetto {@code LocalTime}.
     */
    public LocalTime getOra() {
        return ora;
    }

    /**
     * Restituisce il luogo associato all'evento.
     *
     * @return una stringa che rappresenta il luogo dell'evento.
     */
    public String getLuogo() {
        return luogo;
    }

    /**
     * Restituisce la capienza massima dell'evento.
     *
     * @return un intero che rappresenta il numero massimo di persone che possono partecipare all'evento.
     */
    public int getCapienza() {
        return capienza;
    }

    /**
     * Restituisce il numero attuale di partecipanti all'evento.
     *
     * @return il numero di partecipanti associati all'evento.
     */
    public int getPartecipanti() {
        return partecipanti;
    }

    /**
     * Restituisce la data associata all'evento.
     *
     * @return la data dell'evento, rappresentata come un oggetto {@code LocalDate}.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Imposta la capienza massima dell'evento.
     *
     * @param capienza Il numero massimo di partecipanti consentiti per l'evento.
     */
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    /**
     * Imposta il titolo dell'evento.
     *
     * Questo metodo permette di assegnare un nuovo valore all'attributo
     * "titolo" dell'oggetto dell'evento, sovrascrivendo quello eventualmente
     * presente.
     *
     * @param titolo Il nuovo titolo dell'evento da impostare.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Imposta la descrizione dell'oggetto.
     *
     * @param descrizione la descrizione da assegnare all'oggetto
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Imposta l'ora dell'evento.
     *
     * @param ora L'ora dell'evento da assegnare, rappresentata come un oggetto {@code LocalTime}.
     */
    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    /**
     * Imposta la data associata all'evento.
     *
     * @param data La data dell'evento, rappresentata come un'istanza di {@link LocalDate}.
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Imposta il luogo dell'evento.
     *
     * @param luogo Il luogo dove si terrà l'evento. Deve essere una stringa non null e valida.
     */
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    /**
     * Imposta il numero di partecipanti.
     *
     * @param partecipanti il numero di partecipanti da impostare
     */
    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    /**
     * Restituisce l'identificativo univoco dell'istanza corrente dell'evento.
     *
     * @return l'identificativo univoco dell'evento.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificativo unico dell'evento.
     *
     * @param id L'identificativo univoco da assegnare all'evento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce l'identificatore univoco dell'amministratore associato all'evento.
     *
     * @return l'ID dell'amministratore come valore intero.
     */
    public int getAmministratoreId() {
        return amministratoreID;
    }

    /**
     * Imposta l'identificativo dell'amministratore.
     *
     * @param amministratoreId L'identificativo univoco dell'amministratore da assegnare.
     */
    public void setAmministratoreId(int amministratoreId) {
        this.amministratoreID = amministratoreId;
    }

    /**
     * Recupera il costo associato all'evento.
     *
     * @return il costo dell'evento come valore intero.
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Imposta il costo associato a un evento.
     *
     * @param costo Il costo dell'evento espresso come valore intero.
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * Restituisce la lista di biglietti sotto forma di oggetti BigliettoDAO.
     *
     * @return una lista di oggetti BigliettoDAO che rappresentano i biglietti disponibili.
     */
    public ArrayList<BigliettoDAO> getBiglietti() {
        return biglietti;
    }

    /**
     * Imposta l'elenco dei biglietti.
     *
     * @param biglietti una lista di oggetti {@code BigliettoDAO} da assegnare.
     */
    public void setBiglietti(ArrayList<BigliettoDAO> biglietti) {
        this.biglietti = biglietti;
    }

}
