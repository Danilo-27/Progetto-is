package boundary;

import entity.EntityUtente;

public class Sessione {
    private static Sessione instance;
    private EntityUtente utenteAutenticato;

    private Sessione() {}

    public static Sessione getInstance() {
        if (instance == null) {
            instance = new Sessione();
        }
        return instance;
    }

    public void setUtenteAutenticato(EntityUtente utente) {
        this.utenteAutenticato = utente;
    }

    public EntityUtente getUtenteAutenticato() {
        return this.utenteAutenticato;
    }

    public int getUtenteId() {
        return this.utenteAutenticato.getId();
    }

    public void clearSession() {
        this.utenteAutenticato = null;
    }
}
