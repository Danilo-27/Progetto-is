package DTO;

public class DTOEvento {
    private String titolo;
    private String descrizione;
    private String data;
    private String ora;
    private String luogo;
    private String NumPartecipanti;
    private String NumMaxPartecipanti;

    public DTOEvento() {
    }

    public DTOEvento(String titolo, String descrizione, String data, String ora, String luogo, String numPartecipanti, String numMaxPartecipanti) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.ora = ora;
        this.luogo = luogo;
        NumPartecipanti = numPartecipanti;
        NumMaxPartecipanti = numMaxPartecipanti;
    }


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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public String getNumPartecipanti() {
        return NumPartecipanti;
    }

    public void setNumPartecipanti(String numPartecipanti) {
        NumPartecipanti = numPartecipanti;
    }

    public String getNumMaxPartecipanti() {
        return NumMaxPartecipanti;
    }

    public void setNumMaxPartecipanti(String numMaxPartecipanti) {
        NumMaxPartecipanti = numMaxPartecipanti;
    }

    @Override
    public String toString() {
        return "DTOEvento{" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data='" + data + '\'' +
                ", ora='" + ora + '\'' +
                ", luogo='" + luogo + '\'' +
                ", NumPartecipanti='" + NumPartecipanti + '\'' +
                ", NumMaxPartecipanti='" + NumMaxPartecipanti + '\'' +
                '}';
    }
}