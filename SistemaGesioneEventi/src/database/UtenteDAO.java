//test commento 1
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import exceptions.DBException;
import exceptions.EventoNotFoundException;

public class UtenteDAO {
    private int id;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String immagineProfilo;
    private int tipoUtente;
    private ArrayList<BigliettoDAO> biglietti;
    private ArrayList<EventoDAO> eventi;

    //Costruttori

    public UtenteDAO() {}


    public UtenteDAO(int id){
        this.id = id;
    }

    public void SalvaInDB() throws DBException{
        String query = "INSERT INTO utenti(email,PASSWORD,nome,cognome) VALUES ( '" + this.email + "','" + this.password + "','" + this.nome + "','" + this.cognome + "')";
        try {
            DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Utente già presente nel DB.");
        }
    }

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
                    dao.setTipoUtente(rs.getInt("Tipo"));
                    dao.setImmagine(rs.getString("ImmagineProfilo"));
                    lista_temp.add(dao);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
        }

        return lista_temp;
    }


    public void caricaBigliettiDaDB() throws DBException{
        this.biglietti = new ArrayList<>();
        String query = "SELECT * FROM biglietti WHERE Cliente_id = " + this.id + ";";

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while (rs.next()) {
                BigliettoDAO bigliettoDao=this.createBigliettoDao(rs);
                this.biglietti.add(bigliettoDao);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException _) {
            throw new DBException("Biglietto non trovato.");
        }
    }

    private BigliettoDAO createBigliettoDao(ResultSet rs) throws DBException{
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

    public void caricaEventiDaDB () throws DBException {
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
        } catch(SQLException | ClassNotFoundException e){
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
        }
    }
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

    public int getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(int tipoUtente) {this.tipoUtente = tipoUtente;}

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

