package DTO;

public class DTO {
    private String titolo;
    private String descrizione;
    private String data;
    private String ora;
    private String luogo;
    private String

    public myDTO() {
    }

    public myDTO(String campo1, String campo2) {
        this.campo1 = campo1;
        this.campo2 = campo2;
    }

    public String getCampo1() {
        return this.campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public String getCampo2() {
        return this.campo2;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public String toString() {
        return "myDTO [campo1=" + this.campo1 + ", campo2=" + this.campo2 + "]";
    }
}