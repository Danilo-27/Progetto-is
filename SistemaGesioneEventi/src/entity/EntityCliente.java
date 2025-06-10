package entity;

import database.BigliettoDAO;
import database.UtenteDAO;
import exceptions.BigliettoNotFoundException;
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


    /**
     * Carica i biglietti associati all'utente corrente dal database e li aggiunge alla cronologia biglietti.
     * Questo metodo recupera i biglietti dell'utente invocando l'operazione sul database
     * attraverso un'istanza di {@code UtenteDAO}. Elabora ogni biglietto ottenuto
     * dal database, lo converte in un oggetto {@code EntityBiglietto} e lo aggiunge
     * alla lista {@code storico_biglietti} dell'utente corrente.
     *
     * @throws BigliettoNotFoundException se non vengono trovati biglietti o si verifica un problema nel recuperarli dal database.
     */
    public void caricaBiglietti() throws BigliettoNotFoundException {
        UtenteDAO utenteDao = new UtenteDAO(this.getId());
        try {
            utenteDao.caricaBigliettiDaDB();
        } catch (DBException _) {
            throw new BigliettoNotFoundException("Biglietto non trovato.");
        }
        for (BigliettoDAO bigliettoDAO : utenteDao.getBiglietti()) {
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
