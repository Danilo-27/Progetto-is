package entity;

import database.DBCliente;

public class EntityCliente extends UtenteRegistrato {

    private int numPartecipazione;
    private byte[] immagineProfilo;

    public EntityCliente(String email, String password, String nome, String cognome) {
        super(email, password, nome, cognome);
    }

    public EntityCliente(){}

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

    public int scriviSuDB() {
        DBCliente c = new DBCliente();

        c.setNome(getNome());
        c.setEmail(getEmail());
        c.setCognome(getCognome());
        c.setPassword(getPassword());
        c.setNumPartecipazione(0);

        return c.SalvaInDB();
    }

    public void cercaSuDB(String email){
        DBCliente dbCliente = new DBCliente(email);

        setNome(dbCliente.getNome());
        setCognome(dbCliente.getCognome());
        setPassword(dbCliente.getPassword());
        setEmail(dbCliente.getEmail());
    }



}
