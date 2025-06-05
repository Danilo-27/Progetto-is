//test commento 1
package entity;

import database.BigliettoDAO;
import database.EventoDAO;
import database.UtenteDAO;
import exceptions.DBException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class EntityUtente {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String immagine;
    private int TipoUtente;
    private ArrayList<EntityEvento> eventi;
    private ArrayList<EntityBiglietto> biglietti;

    public static int AMMINISTRATORE=1;
    public static int CLIENTE=0;

    public EntityUtente(){}
    /**
     * Costruttore che crea un EntityEvento a partire da un EventoDAO
     */
    public EntityUtente(UtenteDAO utente) {
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.immagine = utente.getImmagine();
        this.TipoUtente = utente.getTipoUtente();
    }

    /**
     * Costruttore che crea un EntityEvento a partire da un EventoDAO
     */
    public EntityUtente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public EntityUtente(String email) throws DBException {
        this.email = email;
        try {
            UtenteDAO udao = new UtenteDAO(email);
            System.out.println(udao.getId());
            this.id=udao.getId();
            this.nome = udao.getNome();
            this.password = udao.getPassword();
            this.cognome = udao.getCognome();
            this.immagine = udao.getImmagine();
            this.TipoUtente = udao.getTipoUtente();
            if(this.TipoUtente==AMMINISTRATORE){
                //carica eventi
                this.eventi=new ArrayList<>();
                udao.caricaEventiDaDB();
                this.caricaEventiPubblicati(udao);
            }else{
                //carica biglietti
                this.biglietti =new ArrayList<>();
                udao.caricaBigliettiDaDB();
                this.caricaBiglietti(udao);
            }
        }catch(DBException e){
            System.out.println(e.getMessage());
            throw  e;
        }
    }

    public EntityUtente(int id) throws DBException {
        this.id=id;
        this.caricadaDB();
    }


    public int scriviSuDB() {
        UtenteDAO u = new UtenteDAO();
        u.setNome(this.nome);
        u.setEmail(this.email);
        u.setCognome(this.cognome);
        u.setPassword(this.password);
        return u.SalvaInDB();
    }

    public void caricadaDBPerEmail(String email) throws DBException {
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

    public void caricadaDB() throws DBException {
        try{
            UtenteDAO dbCliente = new UtenteDAO(id);
            this.setNome(dbCliente.getNome());
            this.setCognome(dbCliente.getCognome());
            this.setPassword(dbCliente.getPassword());
            this.setEmail(dbCliente.getEmail());
        }catch(DBException e) {
            throw e;
        }

    }

//    public int idUtente(String email) throws DBException {
//        try{
//            UtenteDAO dbCliente = new UtenteDAO();
//            dbCliente.setEmail(email);
//            return dbCliente.cercaInDB();
//        }catch(DBException e) {
//            throw e;
//        }
//    }

    public EntityEvento pubblicaEvento(String Titolo, String Descrizione, LocalDate Data, LocalTime Ora, String Luogo, int Costo, int Capienza) throws DBException {
        return new EntityEvento(Titolo,Descrizione,Data,Ora,Luogo,Costo,Capienza,this);
    }

    public boolean verificaCredenziali(String Password){
        return this.password.equals(Password);
    }


    public void caricaBiglietti(UtenteDAO utente) {
        this.biglietti = new ArrayList<>();
        for (BigliettoDAO bigliettoDAO : utente.getBiglietti()) {
            EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO);
            this.biglietti.add(biglietto);
        }
    }

    public void caricaEventiPubblicati(UtenteDAO utente) {
        this.eventi = new ArrayList<>();
        for (EventoDAO eventoDAO : utente.getEventi()) {
            EntityEvento evento = new EntityEvento(eventoDAO);
            this.eventi.add(evento);
        }
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

    public ArrayList<EntityEvento> getEventi() {
        return eventi;
    }

    public ArrayList<EntityBiglietto> getBiglietti() {
        return biglietti;
    }
}
