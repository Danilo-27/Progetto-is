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
    private int tipoUtente;
    private ArrayList<BigliettoDAO> biglietti;
    private ArrayList<EventoDAO> eventi;

    //Costruttori

    public UtenteDAO() {}

    //costruttore di un utente data l'email
    public UtenteDAO(String email) throws DBException {
        this.email = email;
        int idUtente = cercaInDB();
        if (idUtente == -1) {
            throw new DBException(String.format("Utente '%s' non esistente", this.email));
        }
        this.id = idUtente;
        this.biglietti = new ArrayList<>();
        this.eventi = new ArrayList<>();
        this.caricaDaDB();
    }
    //costruttore di un utente dato l'id
    public UtenteDAO(int id) throws DBException {
        this.id=id;
        this.caricaDaDB();
    }

    //metodo per caricare da DB un utente tramite la sua PK
    public void caricaDaDB() throws DBException {
        String query = "SELECT * FROM utenti WHERE id='" + this.id + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.password = rs.getString("password");
                this.nome = rs.getString("nome");
                this.cognome = rs.getString("cognome");
                this.email = rs.getString("email");
                this.immagineProfilo = rs.getString("ImmagineProfilo");
                this.tipoUtente = rs.getInt("Tipo");
            } else {
                throw new DBException(String.format("Utente '%s' non esistente", id));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int cercaInDB() throws DBException {
        String query = String.format("SELECT * FROM utenti WHERE email = '%s';", this.email);
        try (ResultSet rs = DBConnectionManager.selectQuery(query)) {
            if (!rs.next())
                return -1;
            return rs.getInt("id");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getStackTrace());
            throw new DBException(String.format("Errore nella ricerca dell'utente '%s'.%n%s", this.email, e.getMessage()));
        }
    }

    public int SalvaInDB() {
        int ret = 0;
        String query = "INSERT INTO utenti(email,password,nome,cognome) VALUES ( '" + this.email + "','" + this.password + "','" + this.nome + "','" + this.cognome + "')";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception) e).printStackTrace();
            ret = -1;
        }

        return ret;
    }

    public void caricaBigliettiDaDB(){
        String query = "SELECT * FROM biglietti WHERE Cliente_id = " + this.id + ";";

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while (rs.next()) {
                BigliettoDAO biglietto = new BigliettoDAO();
                biglietto.setCodice_univoco(rs.getString("CodiceUnivoco"));
                biglietto.setStato(rs.getInt("stato"));
                biglietto.setCliente_id(rs.getInt("Cliente_id"));
                biglietto.setEvento_id(rs.getInt("Evento_id"));
                this.biglietti.add(biglietto);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception) e).printStackTrace();
        }
    }

    public void caricaEventiDaDB () throws DBException {
        String query = "SELECT * FROM eventi WHERE Amministratore_id = " + this.id + ";";
        try (ResultSet rs = DBConnectionManager.selectQuery(query)) {
            while (rs.next()) {
                EventoDAO evento_temp = new EventoDAO();
                evento_temp.setId(rs.getInt("id"));
                evento_temp.setTitolo(rs.getString("Titolo"));
                evento_temp.setDescrizione(rs.getString("Descrizione"));
                evento_temp.setData(rs.getDate("Data").toLocalDate());
                evento_temp.setOra(LocalTime.parse(rs.getString("Orario")));
                evento_temp.setLuogo(rs.getString("Luogo"));
                evento_temp.setPartecipanti(rs.getInt("Partecipanti"));
                evento_temp.setCapienza(rs.getInt("Capienza"));
                evento_temp.setAmministratoreid(rs.getInt("Amministratore_id"));
                evento_temp.setCosto(rs.getInt("Costo"));
                System.out.println(evento_temp);
                this.eventi.add(evento_temp);

            }
        } catch(SQLException | ClassNotFoundException e){
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
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

