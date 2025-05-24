package entity;

import database.DBEvento;

import java.util.Date;

public class EntityEvento {

    private String titolo;
    private String descrizione;
    private Date data;
    private String ora;
    private String luogo;
    private int numeroMassimoPartecipanti;
    private int numeroPartecipanti;

    public EntityEvento(DBEvento evento) {
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.data = evento.getData();
        this.ora = evento.getOra();
        this.luogo = evento.getLuogo();
    }

    public EntityEvento() {

    }


    //getter e setter

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getNumeroMassimoPartecipanti() {
        return numeroMassimoPartecipanti;
    }

    public void setNumeroMassimoPartecipanti(int numeroMassimoPartecipanti) {
        this.numeroMassimoPartecipanti = numeroMassimoPartecipanti;
    }

    public int getNumeroPartecipanti() {
        return numeroPartecipanti;
    }

    public void setNumeroPartecipanti(int numeroPartecipanti) {
        this.numeroPartecipanti = numeroPartecipanti;
    }
    //toString

    @Override
    public String toString() {
        return "EntityEvento{" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", ora='" + ora + '\'' +
                ", luogo='" + luogo + '\'' +
                ", numeroMassimoPartecipanti=" + numeroMassimoPartecipanti +
                ", numeroPartecipanti=" + numeroPartecipanti +
                '}';
    }
}
