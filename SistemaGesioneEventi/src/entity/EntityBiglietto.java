package entity;

import database.BigliettoDAO;

public class EntityBiglietto {

    private int id;
    private String codiceUnivoco;
    private int stato;
    private EntityEvento evento;
    private EntityUtente utente;

    public EntityBiglietto() {}

    //questo è per caricare un biglietto già nel DB
    public EntityBiglietto(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
        BigliettoDAO dao=new BigliettoDAO(codiceUnivoco);
        this.id = dao.getId();
        this.codiceUnivoco=dao.getCodice_univoco();
        this.stato=dao.getStato();
        this.caricaEvento(dao);
        this.caricaUtente(dao);
    }
    //questo invece è per caricare il biglietto nel DB


    //NON SERVE
    public int scriviSuDB() {
        BigliettoDAO s = new BigliettoDAO();
        s.setStato(this.stato);
        s.setCodice_univoco(this.codiceUnivoco);
        return s.SalvaInDB();
    }

    public void caricaEvento(BigliettoDAO biglietto) {
        //devo prendere l'evento dal biglietto che ho creato mediante il metodo
        //caricaEventoBigliettoDaDB, chiamato nel costruttore ""public EntityBiglietto(DBBiglietto biglietto)""
        EntityEvento evento = new EntityEvento(biglietto.getEvento());
        this.evento=evento;
    }
    public void caricaUtente(BigliettoDAO biglietto) {
        //devo prendere l'evento dal biglietto che ho creato mediante il metodo
        //caricaEventoBigliettoDaDB, chiamato nel costruttore ""public EntityBiglietto(DBBiglietto biglietto)""
        EntityUtente utente = new EntityUtente(biglietto.getUtente());
        this.utente=utente;
    }


    @Override
    public String toString() {
        return "EntityBiglietto{" +
                "id=" + this.id +
                ", nome_titolare='" + this.utente.getNome() + '\'' +
                ", codice_univoco='" + this.codiceUnivoco + '\'' +
                ", stato='" + this.stato + '\'' +
                ", IDcliente=" + this.utente.getId() +
                ", Evento=" + this.evento.getTitolo() +
                '}';
    }

    //DA IMPLEMENTARE
    public boolean verificaBiglietto(){
        //codice
        return false;
    }
    public boolean validaBiglietto(){
        if(this.getStato()==1){
            return false;
        }else{
            this.setStato(1);
        }
        return true;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_titolare() {
        return this.utente.getNome();
    }

    public void setNome_titolare(String nome_titolare) {
        this.utente.setNome(nome_titolare);
    }

    public String getCodice_univoco() {
        return this.codiceUnivoco;
    }

    public void setCodice_univoco(String codice_univoco) {
        this.codiceUnivoco = codice_univoco;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getIDcliente() {
        return this.utente.getId();
    }

    public void setIDcliente(int IDcliente) {
        this.utente.setId(IDcliente);
    }

    public String getTitoloEvento() {
        return this.evento.getTitolo();
    }

    public void setTitoloEvento(String titolo) {
        this.evento.setTitolo(titolo);
    }

    public EntityEvento getEvento() {
        return evento;
    }

    public void setEvento(EntityEvento evento) {
        this.evento = evento;
    }
    public EntityUtente getUtente() {
        return this.utente;
    }
    public void setUtente(EntityUtente utente) {
        this.utente = utente;
    }
}
