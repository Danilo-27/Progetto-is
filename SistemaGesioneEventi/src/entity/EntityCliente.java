package entity;

import database.DBCliente;
import database.DBamministratore;

public class EntityCliente extends EntityUtenteRegistrato{

    private int numPartecipazione;
    private byte[] immagineProfilo;
    private String email;
    private String password;
    private String nome;
    private String cognome;

    public int scriviSuDB() {
        DBCliente c = new DBCliente();

        c.setImmagineProfilo(this.immagineProfilo);
        c.setNumPartecipazione(this.numPartecipazione);
        c.setNome(this.nome);
        c.setEmail(this.email);
        c.setCognome(this.cognome);
        c.setPassword(this.password);

        return c.SalvaInDB();
    }

    public int getNumPartecipazione() {
        return numPartecipazione;
    }

    public void setNumPartecipazione(int numPartecipazione) {
        this.numPartecipazione = numPartecipazione;
    }

    public byte[] getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(byte[] immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
