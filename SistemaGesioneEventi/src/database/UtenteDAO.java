package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import exceptions.DBException;

public class UtenteDAO {
    private int id;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String immagineProfilo;
    private int tipoUtente;

    //Costruttori

    public UtenteDAO(){};

    //costruttore di un utente data l'email
    public UtenteDAO(String email) throws DBException {
        this.email = email;
        int idUtente = cercaInDB();
        if (idUtente == -1) {
            throw new DBException(String.format("Utente '%s' non esistente", this.email));
        }
        this.id=idUtente;
        this.caricaDaDB();
    }


    //metodo per caricare da DB un utente tramite la sua PK
    public void caricaDaDB() throws DBException {
        String query = "SELECT * FROM utenti WHERE id='" + this.id+ "';";
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
        System.out.println(query);
        try (ResultSet rs = DBConnectionManager.selectQuery(query)){
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
        String query = "INSERT INTO utenti(email,password,nome,cognome) VALUES ( '" + this.email  + "','"+ this.password+ "','" + this.nome + "','" + this.cognome +"')";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }

        return ret;
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
}

