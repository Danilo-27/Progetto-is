package database;

import java.sql.SQLException;

public class DBamministratore {
    private String email;
    private String password;
    private String nome;
    private String cognome;


    public DBamministratore() {}


    public int SalvaInDB() {
        int ret = 0;
        String query = "INSERT INTO amministratori(email,password,nome,cognome) VALUES ( '" + this.email  + "','"+ this.password+ "','" + this.nome + "','" + this.cognome +"')";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }

        return ret;
    }




    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public String getNome() {return nome;}
    public String getCognome() {return cognome;}
}
