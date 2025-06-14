package entity;

import database.BigliettoDAO;
import database.UtenteDAO;
import exceptions.BigliettoNotFoundException;
import exceptions.DBException;

import java.util.ArrayList;

public class EntityCliente extends EntityUtenteRegistrato{
    private String immagineProfilo;
    private ArrayList<EntityBiglietto> storico_biglietti;

    public EntityCliente() {}

    public EntityCliente(UtenteDAO utente){
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.immagineProfilo =utente.getImmagine();
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
            boolean giàPresente;
            giàPresente = false;
            for (EntityBiglietto b : storico_biglietti) {
                if (b.getCodice_univoco().equals(biglietto.getCodice_univoco())) {
                    giàPresente = true;
                    break;
                }
            }
            if (!giàPresente) {
                storico_biglietti.add(biglietto);
            }
        }
    }

    public boolean haBigliettoPerEvento(EntityEvento evento) {
        for (EntityBiglietto biglietto : storico_biglietti) {
            if (biglietto.getEvento().equals(evento)) {
                return true;
            }
        }
        return false;
    }

    public String getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(String immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public ArrayList<EntityBiglietto> getBiglietti() {
        return storico_biglietti;
    }

    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.storico_biglietti = biglietti;
    }
}
