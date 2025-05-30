package entity;

import database.DBEvento;
import database.DBamministratore;

public class EntityUtenteRegistrato {
    private String email;
    private String password;
    private String nome;
    private String cognome;


    public int scriviSuDB() {
        DBamministratore a = new DBamministratore();
        a.setNome(this.nome);
        a.setEmail(this.email);
        a.setCognome(this.cognome);
        a.setPassword(this.password);

        return a.SalvaInDB();
    }

    public static EntityCliente ricercaCliente(String email) {
        EntityCliente cliente = new EntityCliente(email);
        return cliente;
    }

    private boolean verificaCredenziali(){
        //IMPLEMENTARE
        return false;
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
