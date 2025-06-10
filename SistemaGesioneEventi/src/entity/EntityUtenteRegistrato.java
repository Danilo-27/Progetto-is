package entity;

import database.UtenteDAO;
import exceptions.DBException;
import exceptions.RedundancyException;


public class EntityUtenteRegistrato {
    protected int id;
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;


    public EntityUtenteRegistrato(){}

    /***
     * 
     * @param nome Il nome dell'utente Registrato 
     * @param cognome Il cognome dell'utente registrato
     * @param email La email dell'utente registrato
     * @param password La password dell'utente registrato
     */
    public EntityUtenteRegistrato(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }


    /**
     * Aggiorna i dettagli dell'utente corrente nel database.
     * Il metodo costruisce un nuovo oggetto dati utente utilizzando gli attributi dell'entità corrente
     * e tenta di salvarlo nel database. Se si verifica un conflitto nel database (ad esempio, un utente
     * con gli stessi dettagli esiste già), viene lanciata una RedundancyException.
     *
     * @throws RedundancyException se l'utente è già presente nel database.
     */
    public void aggiornamento()throws RedundancyException{
        UtenteDAO u = new UtenteDAO();
        u.setNome(this.nome);
        u.setEmail(this.email);
        u.setCognome(this.cognome);
        u.setPassword(this.password);
        try {
            u.SalvaInDB();
        } catch (DBException _) {
            throw new RedundancyException("Utente già registrato.");
        }
    }


    /**
     * Verifica se la password fornita corrisponde a quella associata all'utente registrato.
     *
     * @param Password La password da confrontare con quella registrata.
     * @return true se la password fornita corrisponde a quella registrata, false altrimenti.
     */
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
