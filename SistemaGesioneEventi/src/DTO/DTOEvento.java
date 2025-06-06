package DTO;

import entity.EntityEvento;

import java.time.LocalDate;
import java.time.LocalTime;

public class DTOEvento {
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int costo;
    private int capienza;
    private int partecipanti;
    private int bigliettivenduti;

    public DTOEvento() {
    }


    public DTOEvento(String titolo, String descrizione, LocalDate data, LocalTime ora, String luogo, int costo, int capienza,int bigliettivenduti) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.ora = ora;
        this.luogo = luogo;
        this.costo = costo;
        this.capienza = capienza;
        this.bigliettivenduti = bigliettivenduti;
    }


    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public String getLuogo() {
        return luogo;
    }

    public int getCosto() {
        return costo;
    }

    public int getCapienza() {
        return capienza;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public int getBigliettivenduti() {
        return bigliettivenduti;
    }

    public void setBigliettivenduti(int bigliettivenduti) {
        this.bigliettivenduti = bigliettivenduti;
    }
}