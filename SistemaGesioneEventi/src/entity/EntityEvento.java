package entity;

import database.DBEvento;

import java.time.LocalDate;
import java.time.LocalTime;

public class EntityEvento {

    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int numeroMassimoPartecipanti;
    private int numeroPartecipanti;
    private int id_amministratore;

    public EntityEvento(DBEvento evento) {
        this.id = evento.getId();
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.data = evento.getData();
        this.ora = evento.getOra();
        this.luogo = evento.getLuogo();
    }

    public EntityEvento(int id,int numeroPartecipanti, int numeroMassimoPartecipanti, String luogo, LocalTime ora, LocalDate data, String descrizione, String titolo) {
        this.id = id;
        this.numeroPartecipanti = numeroPartecipanti;
        this.numeroMassimoPartecipanti = numeroMassimoPartecipanti;
        this.luogo = luogo;
        this.ora = ora;
        this.data = data;
        this.descrizione = descrizione;
        this.titolo = titolo;
    }

    public EntityEvento() {}


    public int scriviSuDB() {
        DBEvento s = new DBEvento();
        s.setTitolo(this.titolo);
        s.setData(this.data);
        s.setOra(this.ora);
        s.setDescrizione(this.descrizione);
        s.setLuogo(this.luogo);
        s.setCapienza(this.numeroMassimoPartecipanti);
        s.setIdamministratore(this.id_amministratore);


        return s.SalvaInDB();
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

    public LocalDate getData() {
        return data;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOra() {
        return ora;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_amministratore() {
        return id_amministratore;
    }

    public void setId_amministratore(int id_amministratore) {
        this.id_amministratore = id_amministratore;
    }

    //toString

    @Override
    public String toString() {
        return "EntityEvento{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", ora='" + ora + '\'' +
                ", luogo='" + luogo + '\'' +
                ", numeroMassimoPartecipanti=" + numeroMassimoPartecipanti +
                ", numeroPartecipanti=" + numeroPartecipanti +
                '}';
    }
}
