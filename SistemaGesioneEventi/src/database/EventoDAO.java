//test commento 1
package database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import exceptions.DBException;

public class EventoDAO {

    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int costo;
    private int capienza;
    private int partecipanti;
    private int amministratoreID;
    private ArrayList<BigliettoDAO> biglietti;

    private static final String ID_DB = "id";
    private static final String TITOLO_DB ="Titolo";
    private static final String DESCRIZIONE_DB ="Descrizione";
    private static final String DATA_DB ="Data";
    private static final String ORARIO_DB ="Orario";
    private static final String LUOGO_DB ="Luogo";
    private static final String PARTECIPANTI_DB ="Partecipanti";
    private static final String AMMINISTRATORE_DB ="Amministratore_id";
    private static final String CAPIENZA_DB ="Capienza";
    private static final String COSTO_DB ="Costo";

    //costruttore con titolo
    public EventoDAO() {}

    public EventoDAO(String titolo){
        this.titolo=titolo;
        this.biglietti= new ArrayList<>();
        try {
            this.caricaDaDBPerTitolo();
        }catch(DBException e){
            e.printStackTrace();
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
     *
     * Questo metodo utilizza una query SQL per inserire i dati dell'evento corrente
     * nella tabella 'eventi' del database. I valori degli attributi dell'oggetto corrente 
     * vengono utilizzati per popolare i campi della tabella corrispondenti.
     *
     * @throws DBException se si verifica un errore durante l'esecuzione della query
     *                     o se l'operazione non può essere completata.
     */
    public void SalvaInDB() throws DBException{
        String query = "INSERT INTO eventi (titolo, descrizione, data, orario, luogo, costo, capienza, partecipanti, Amministratore_id) " +
                "VALUES ('" + this.titolo + "', '" + this.descrizione + "', '" + this.data + "', '" + this.ora + "', '" + this.luogo + "', " +
                this.costo + ", " + this.capienza + ", " + this.partecipanti + ", " + this.amministratoreID + ")";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException _) {
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
     *
     * Questo metodo esegue una query SQL per recuperare i dettagli dell'evento,
     * inclusi titolo, descrizione, data, ora, luogo, numero di partecipanti, capienza, amministratore ID e costo.
     * I dati dell'evento vengono popolati nei rispettivi attributi dell'oggetto.
     *
     * @throws DBException se l'evento non esiste nel database o si verifica un errore di connessione
     *                     o esecuzione della query SQL.
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
                biglietto.setCliente_id(rs.getInt("Cliente_id"));
                biglietto.setEvento_id(rs.getInt("Evento_id"));
                this.biglietti.add(biglietto);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Biglietti non presenti nel DB.");
        }
    }
    /**
     * Rimuove un evento dalla tabella 'eventi' del database in base al titolo dell'istanza corrente.
     *
     * Questo metodo esegue una query SQL DELETE utilizzando il titolo dell'evento memorizzato
     * nella variabile di istanza `titolo` dell'oggetto corrente. L'eliminazione viene effettuata
     * direttamente nella tabella 'eventi' del database.
     *
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
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Errore durante l'eliminazione dell'evento dal DB.");
        }
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public LocalTime getOra() {
        return ora;
    }

    public String getLuogo() {
        return luogo;
    }

    public int getCapienza() {
        return capienza;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public LocalDate getData() {
        return data;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmministratoreId() {
        return amministratoreID;
    }

    public void setAmministratoreId(int amministratoreId) {
        this.amministratoreID = amministratoreId;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public ArrayList<BigliettoDAO> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(ArrayList<BigliettoDAO> biglietti) {
        this.biglietti = biglietti;
    }

}
