package DTO;

public class DTOUtente {
    private String nome;
    private String cognome;
    private String email;
    private String immagine;
    private int TipoUtente;

    public DTOUtente() {
    }

    public DTOUtente(String nome, String cognome, String email, String immagine, int tipoUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.immagine = immagine;
        TipoUtente = tipoUtente;
    }

    public DTOUtente(String nome, String cognome) {
        this.nome=nome;
        this.cognome=cognome;
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

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public int getTipoUtente() {
        return TipoUtente;
    }

    public void setTipoUtente(int tipoUtente) {
        TipoUtente = tipoUtente;
    }

    @Override
    public String toString() {
        return "DTOUtente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", immagine='" + immagine + '\'' +
                ", TipoUtente=" + TipoUtente +
                '}';
    }
}
