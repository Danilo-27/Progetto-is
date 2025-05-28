package entity;

import database.DBBiglietto;

public class EntityBiglietto {

    private int id;
    private  String nome_titolare;
    private String codice_univoco;
    private String stato;
    private int IDcliente;
    private int IDEvento;

    public EntityBiglietto(DBBiglietto biglietto) {
        this.id = biglietto.getId();
        this.nome_titolare=biglietto.getNome_titolare();
        this.codice_univoco=biglietto.getCodice_univoco();
        this.stato=biglietto.getStato();
        this.IDcliente=biglietto.getIDcliente();
        this.IDEvento=biglietto.getIDEvento();
    }

    public EntityBiglietto(int id,String nome_titolare, String codice_univoco, String stato,int IDcliente,int IDEvento) {
        this.id=id;
        this.nome_titolare = nome_titolare;
        this.codice_univoco = codice_univoco;
        this.stato = stato;
        this.IDcliente = IDcliente;
        this.IDEvento = IDEvento;
    }

    public EntityBiglietto() {
    }

    //NON SERVE
    public int scriviSuDB() {
        DBBiglietto s = new DBBiglietto();
        s.setNome_titolare(this.nome_titolare);
        s.setStato(this.stato);
        s.setCodice_univoco(this.codice_univoco);

        return s.SalvaInDB();
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
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


}
