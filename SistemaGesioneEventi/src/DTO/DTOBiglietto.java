package DTO;

import java.time.LocalDate;

public class DTOBiglietto {
    private String codiceUnivoco;
    private int stato;
    private String titoloEvento;
    private LocalDate data;

    public DTOBiglietto() {
    }

    public DTOBiglietto(String codiceUnivoco, int stato, String titoloEvento, LocalDate data) {
        this.codiceUnivoco = codiceUnivoco;
        this.stato = stato;
        this.titoloEvento = titoloEvento;
        this.data = data;
    }

    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public String getTitoloEvento() {
        return titoloEvento;
    }

    public void setTitoloEvento(String titoloEvento) {
        this.titoloEvento = titoloEvento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DTOBiglietto{" +
                "codiceUnivoco='" + codiceUnivoco + '\'' +
                ", stato=" + stato +
                ", titoloEvento='" + titoloEvento + '\'' +
                ", data=" + data +
                '}';
    }
}