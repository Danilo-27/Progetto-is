package entity;

import database.EventoDAO;
import database.UtenteDAO;
import database.UtenteDAO;
import exceptions.DBException;


public class EntityUtente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String immagine;
    private int TipoUtente;

    public EntityUtente(){}

    public EntityUtente(UtenteDAO utente) {
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.immagine = utente.getImmagine();
        this.TipoUtente = utente.getTipoUtente();
    }

    public EntityUtente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public EntityUtente(String email) throws DBException {
        this.email = email;
        try {
            UtenteDAO dao = new UtenteDAO(email);
            this.nome = dao.getNome();
            this.password = dao.getPassword();
            this.cognome = dao.getCognome();
            this.immagine = dao.getImmagine();
            this.TipoUtente = dao.getTipoUtente();
        }catch(DBException e){
            System.out.println(e.getMessage());
            throw  e;
        }
    }

    public int scriviSuDB() {
        UtenteDAO u = new UtenteDAO();
        u.setNome(this.nome);
        u.setEmail(this.email);
        u.setCognome(this.cognome);
        u.setPassword(this.password);
        return u.SalvaInDB();
    }

    public void cercaSuDB(String email) throws DBException {
        try{
            UtenteDAO dbCliente = new UtenteDAO(email);
            this.setNome(dbCliente.getNome());
            this.setCognome(dbCliente.getCognome());
            this.setPassword(dbCliente.getPassword());
            this.setEmail(dbCliente.getEmail());
        }catch(DBException e) {
            throw e;
        }
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

    public String getImmagine() {
        return
                immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public int getTipoUtente() {
        return TipoUtente;
    }

    @Override
    public String toString() {
        return "EntityUtente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", immagine='" + immagine + '\'' +
                ", TipoUtente=" + TipoUtente +
                '}';
    }
}
