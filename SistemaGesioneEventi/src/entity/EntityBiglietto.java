package entity;

import database.BigliettoDAO;

public class EntityBiglietto {

    private int id;
    private String codiceUnivoco;
    private int stato;
    private EntityEvento evento;
    private EntityUtente;

    public EntityBiglietto() {
    }

    public EntityBiglietto(BigliettoDAO biglietto) {
        this.id = biglietto.getId();
        this.codiceUnivoco=biglietto.getCodice_univoco();
        this.stato=biglietto.getStato();
        this.IDcliente=biglietto.getIDcliente(); //togliere
        this.IDEvento=biglietto.getIDEvento(); //togliere
        biglietto.caricaEventoBigliettoDaDB();
        this.caricaEvento(biglietto);
    }


    //NON SERVE
    public int scriviSuDB() {
        BigliettoDAO s = new BigliettoDAO();
        s.setStato(this.stato);
        s.setCodice_univoco(this.codiceUnivoco);
        return s.SalvaInDB();
    }

    public void caricaEvento(DBBiglietto biglietto) {
        //devo prendere l'evento dal biglietto che ho creato mediante il metodo
        //caricaEventoBigliettoDaDB, chiamato nel costruttore ""public EntityBiglietto(DBBiglietto biglietto)""
        EntityEvento evento = new EntityEvento(biglietto.getEvento());
        this.evento=evento;
    }


    @Override
    public String toString() {
        return "EntityBiglietto{" +
                "id=" + id +
                ", nome_titolare='" + nome_titolare + '\'' +
                ", codice_univoco='" + codice_univoco + '\'' +
                ", stato='" + stato + '\'' +
                ", IDcliente=" + IDcliente +
                ", IDEvento=" + IDEvento +
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
        return nome_titolare;
    }

    public void setNome_titolare(String nome_titolare) {
        this.nome_titolare = nome_titolare;
    }

    public String getCodice_univoco() {
        return codice_univoco;
    }

    public void setCodice_univoco(String codice_univoco) {
        this.codice_univoco = codice_univoco;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getIDcliente() {
        return IDcliente;
    }

    public void setIDcliente(int IDcliente) {
        this.IDcliente = IDcliente;
    }

    public int getIDEvento() {
        return IDEvento;
    }

    public void setIDEvento(int IDEvento) {
        this.IDEvento = IDEvento;
    }

    public EntityEvento getEvento() {
        return evento;
    }

    public void setEvento(EntityEvento evento) {
        this.evento = evento;
    }
}
