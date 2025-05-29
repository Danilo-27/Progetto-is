package entity;

import database.DBBiglietto;
import database.DBCliente;

import java.util.ArrayList;

public class EntityCliente extends UtenteRegistrato {

    private int numPartecipazione;
    private String immagineProfilo;
    private ArrayList<EntityBiglietto> storicobiglietti;

    public EntityCliente(String email, String password, String nome, String cognome) {
        super(email, password, nome, cognome);
    }

    public EntityCliente(String email) {
        DBCliente cliente = new DBCliente(email);
        this.numPartecipazione = cliente.getNumPartecipazione();
        this.immagineProfilo = cliente.getImmagineProfilo();
        this.setEmail(email);
        this.setNome(cliente.getNome());
        this.setCognome(cliente.getCognome());
        this.setPassword(cliente.getPassword());
        this.storicobiglietti = new ArrayList<>();
        cliente.caricaBigliettiClientiDaDB();
        this.caricaBiglietti(cliente);

    }

    public EntityCliente(DBCliente cliente) {
        this.numPartecipazione = cliente.getNumPartecipazione();
        this.immagineProfilo = cliente.getImmagineProfilo();
        this.setNome(cliente.getNome());
        this.setCognome(cliente.getCognome());
        this.setEmail(cliente.getEmail());
        this.setPassword(cliente.getPassword());
        this.storicobiglietti = new ArrayList<>();
        cliente.caricaBigliettiClientiDaDB();
        this.caricaBiglietti(cliente);
    }

    public void caricaBiglietti(DBCliente cliente) {
        for(int i = 0; i < cliente.getStoricobiglietti().size(); ++i) {
            EntityBiglietto corso = new EntityBiglietto((DBBiglietto) cliente.getStoricobiglietti().get(i));
            this.storicobiglietti.add(corso);
        }

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


    public EntityCliente(){this.storicobiglietti = new ArrayList<>();}

    public int getNumPartecipazione() {
        return numPartecipazione;
    }

    public void setNumPartecipazione(int numPartecipazione) {
        this.numPartecipazione = numPartecipazione;
    }

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public ArrayList<EntityBiglietto> getStoricobiglietti() {
        return storicobiglietti;
    }

    @Override
    public String toString() {
        return "EntityCliente{" +
                "numPartecipazione=" + numPartecipazione +
                ", immagineProfilo='" + immagineProfilo + '\'' +
                ", storicobiglietti=" + storicobiglietti +
                '}';
    }
}
