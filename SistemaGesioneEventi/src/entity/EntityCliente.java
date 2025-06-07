package entity;

import database.BigliettoDAO;
import database.UtenteDAO;
import exceptions.DBException;

import java.util.ArrayList;

public class EntityCliente extends EntityUtenteRegistrato{
    private String immagine;
    private ArrayList<EntityBiglietto> storico_biglietti;

    public EntityCliente() {}

    public EntityCliente(UtenteDAO utente){
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.immagine=utente.getImmagine();
        this.storico_biglietti = new ArrayList<>();
    }

    

    public void caricaBiglietti() throws DBException {
        UtenteDAO utentedb = new UtenteDAO(this.getId());
        utentedb.caricaBigliettiDaDB();
        for (BigliettoDAO bigliettoDAO : utentedb.getBiglietti()) {
            EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO);
            this.storico_biglietti.add(biglietto);
        }
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public ArrayList<EntityBiglietto> getBiglietti() {
        return storico_biglietti;
    }

    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.storico_biglietti = biglietti;
    }
}
