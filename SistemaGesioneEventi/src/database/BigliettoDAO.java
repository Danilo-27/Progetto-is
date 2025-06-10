//test commento 1
package database;

import exceptions.DBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;

public class BigliettoDAO{

    private String codice_univoco;
    private int stato;
    private int Cliente_id;
    private int Evento_id;
    private EventoDAO evento;
    private UtenteDAO utente;

    public BigliettoDAO() {}

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
        }catch (SQLException |ClassNotFoundException _){
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
        }catch(SQLException | ClassNotFoundException _){
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
                UtenteDAO utente = new UtenteDAO();
                utente.setId(this.Cliente_id);
                utente.setEmail(rs.getString("email"));
                utente.setPassword(rs.getString("PASSWORD"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setImmagine(rs.getString("immagineProfilo"));
                utente.setRuolo(rs.getInt("Tipo"));
                this.utente = utente;
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
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Errore durante l'eliminazione del biglietto dal DB.");
        }
    }

    public String getCodice_univoco() {
        return codice_univoco;
    }

    public void setCodice_univoco(String codice_univoco) {
        this.codice_univoco = codice_univoco;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getCliente_id() {
        return Cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.Cliente_id = cliente_id;
    }

    public int getEvento_id() {
        return Evento_id;
    }

    public void setEvento_id(int evento_id) {
        this.Evento_id = evento_id;
    }

    public EventoDAO getEvento() {
        return evento;
    }

    public UtenteDAO getUtente() {return utente;}
}
