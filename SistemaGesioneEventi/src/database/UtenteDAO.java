//test commento 1
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import exceptions.DBException;

public class UtenteDAO {
    private int id;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String immagineProfilo;
    private int ruolo;
    private ArrayList<BigliettoDAO> biglietti;
    private ArrayList<EventoDAO> eventi;

    //Costruttori

    public UtenteDAO() {}


    public UtenteDAO(int id){
        this.id = id;
    }

    /**
     * Salva l'istanza corrente dell'utente nel database inserendo i suoi dettagli
     * (email, password, nome e cognome) nella tabella "utenti".
     * <p>
     * Il metodo costruisce una query SQL INSERT usando gli attributi dell'istanza e
     * la esegue. Se l'esecuzione fallisce a causa dei vincoli del database, come
     * una voce duplicata per un campo univoco come l'email, viene lanciata un'eccezione.
     *
     * @throws DBException se si verifica un errore SQL o class-not-found durante
     *                     l'esecuzione della query. Il messaggio di eccezione specifica
     *                     le possibili cause, come una voce duplicata nel database.
     */
    public void SalvaInDB() throws DBException {
        String query = "INSERT INTO utenti(email,PASSWORD,nome,cognome) VALUES ( '" + this.email + "','" + this.password + "','" + this.nome + "','" + this.cognome + "')";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Utente già presente nel DB.");
        }
    }

    /**
     * Recupera una lista di tutti gli utenti dal database.
     *
     * @return un ArrayList di oggetti UtenteDAO che rappresentano gli utenti recuperati dal database
     * @throws DBException se si verifica un errore durante l'operazione sul database
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
     * Carica dal database la lista dei biglietti associati all'utente
     * e popola il campo `biglietti` dell'istanza `UtenteDAO`.
     * <p>
     * Viene eseguita una query SELECT sul database per recuperare i record dei biglietti
     * collegati all'utente identificato dal campo `id` di questa istanza.
     * Per ogni record nel result set, viene creato un oggetto `BigliettoDAO`
     * utilizzando il metodo `createBigliettoDao` e aggiunto alla lista `biglietti`.
     *
     * @throws DBException se si verifica un errore di accesso al database, la query fallisce,
     *                     o non vengono trovati biglietti per l'utente.
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
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Biglietto non trovato.");
        }
    }

    /**
     * Crea un oggetto BigliettoDAO popolando i suoi campi con i dati recuperati
     * dal ResultSet fornito.
     *
     * @param rs il ResultSet contenente i dati per popolare l'oggetto BigliettoDAO
     * @return un oggetto BigliettoDAO popolato con i dati dal ResultSet
     * @throws DBException se si verifica un errore nell'accesso al ResultSet o nel recupero dei dati
     */
    private BigliettoDAO createBigliettoDao(ResultSet rs) throws DBException {
        BigliettoDAO biglietto = new BigliettoDAO();
        try {
            biglietto.setCodice_univoco(rs.getString("CodiceUnivoco"));
            biglietto.setStato(rs.getInt("stato"));
            biglietto.setCliente_id(rs.getInt("Cliente_id"));
            biglietto.setEvento_id(rs.getInt("Evento_id"));
            return biglietto;

        } catch (SQLException e) {

            throw new DBException("Errore nel caricamento dei biglietti.");
        }
    }

    /**
     * Carica dal database una lista di eventi associati all'utente corrente.
     * <p>
     * Il metodo esegue una query SQL per recuperare tutti i record degli eventi dove
     * l'`Amministratore_id` corrisponde all'`id` dell'oggetto corrente. Ogni record
     * recuperato viene utilizzato per creare un'istanza della classe `EventoDAO`, che
     * viene popolata con i campi dati del record e poi aggiunta alla lista `eventi`.
     *
     * @throws DBException se si verifica un errore durante l'operazione sul database,
     *                     inclusi problemi con la query SQL o la connessione al database.
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
     * Rimuove dal database tutti gli eventi associati all'email dell'utente corrente.
     * <p>
     * Questo metodo costruisce ed esegue una query SQL DELETE per rimuovere tutti gli eventi
     * relativi all'email dell'utente. Il valore email utilizzato per la cancellazione viene
     * preso dalla proprietà `email` dell'istanza corrente. Se si verifica un errore durante
     * l'esecuzione della query o durante l'accesso al database, viene lanciata una {@code DBException}.
     *
     * @throws DBException se si verifica un errore di accesso al database o se la cancellazione fallisce.
     */
    public void eliminaEventiDaDb() throws DBException {
        String query = "DELETE FROM utenti WHERE email = '" + this.email + "'";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Errore durante l'eliminazione dell'utente dal DB.");
        }
    }

    //metodi set e get

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImmagine() {
        return immagineProfilo;
    }

    public void setImmagine(String immagine) {
        this.immagineProfilo = immagine;
    }

    public int getRuolo() {
        return ruolo;
    }

    public void setRuolo(int ruolo) {this.ruolo = ruolo;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<BigliettoDAO> getBiglietti() {
        return biglietti;
    }

    public ArrayList<EventoDAO> getEventi() {
        return eventi;
    }
}

