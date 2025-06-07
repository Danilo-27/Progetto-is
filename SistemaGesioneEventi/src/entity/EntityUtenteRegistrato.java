package entity;

import database.UtenteDAO;


public class EntityUtenteRegistrato {
    protected int id;
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;



    public EntityUtenteRegistrato(){}

    /***
     *
     *
     * @param nome
     * @param cognome
     * @param email
     * @param password
     */
    public EntityUtenteRegistrato(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public void aggiornamento(){
        UtenteDAO u = new UtenteDAO();
        u.setNome(this.nome);
        u.setEmail(this.email);
        u.setCognome(this.cognome);
        u.setPassword(this.password);
        u.SalvaInDB();
    }

    public boolean verificaCredenziali(String Password){
        return this.password.equals(Password);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
