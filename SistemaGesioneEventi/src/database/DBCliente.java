package database;

import java.sql.SQLException;

public class DBCliente {

    private int numPartecipazione;
    private byte[] immagineProfilo;
    private String email;
    private String password;
    private String nome;
    private String cognome;

    public DBCliente() {}

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

    //getter and setter
    public int getNumPartecipazione() {
        return numPartecipazione;
    }

    public void setNumPartecipazione(int numPartecipazione) {
        this.numPartecipazione = numPartecipazione;
    }

    public byte[] getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(byte[] immagineProofilo) {
        this.immagineProfilo = immagineProofilo;
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
}
