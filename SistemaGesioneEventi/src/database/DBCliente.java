package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBCliente {

    private int id;
    private String immagineProfilo;
    private int numPartecipazione;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private ArrayList<DBBiglietto> storicobiglietti;

    public DBCliente() {this.storicobiglietti = new ArrayList<>();}

    public DBCliente(String email){
        this.email = email;
        this.storicobiglietti = new ArrayList<>();
        this.caricaDaDB();
    }


    public int SalvaInDB() {

        int ret = 0;

        String query = "INSERT INTO clienti(immagineProfilo,numPartecipazione,email,password,nome,cognome) VALUES ( '"+this.immagineProfilo +"','"+ this.numPartecipazione + "','" + this.email  + "','"+ this.password+ "','" + this.nome + "','" + this.cognome +"')";

        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }

        return ret;
    }


    public void caricaDaDB() {
        String query = "SELECT * FROM clienti WHERE email='" + this.email + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.password = rs.getString("password");
                this.nome = rs.getString("nome");
                this.cognome = rs.getString("cognome");
                this.immagineProfilo = rs.getString("immagineProfilo");
                this.numPartecipazione = rs.getInt("numPartecipazione");

            } else {
                System.out.println("Utente non trovato nel DB");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void caricaBigliettiClientiDaDB() {

        String query = new String("select * from biglietti where IDcliente IN (select id from clienti where email = '" + this.email + "')");

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while(rs.next()) {
                DBBiglietto biglietto = new DBBiglietto();
                biglietto.setId(rs.getInt("id"));
                biglietto.setNome_titolare(rs.getString("nome_titolare"));
                biglietto.setCodice_univoco(rs.getString("codice_univoco"));
                biglietto.setStato(rs.getString("stato"));
                biglietto.setIDcliente(rs.getInt("IDcliente"));
                biglietto.setIDEvento(rs.getInt("IDEvento"));
                this.storicobiglietti.add(biglietto);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
        }


    }

    //getter and setter
    public int getNumPartecipazione() {
        return numPartecipazione;
    }
    public void setNumPartecipazione(int numPartecipazione) {
        this.numPartecipazione = numPartecipazione;
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
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public ArrayList<DBBiglietto> getStoricobiglietti() {
        return storicobiglietti;
    }
}
