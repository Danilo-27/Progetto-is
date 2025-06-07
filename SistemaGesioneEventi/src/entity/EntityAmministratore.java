package entity;

import database.UtenteDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EntityAmministratore extends EntityUtenteRegistrato {
    private ArrayList<EntityEvento> eventi;

    public EntityAmministratore() {}

    public EntityAmministratore(UtenteDAO utente){
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.eventi=new ArrayList<>();
    }

    public void caricaEventiPubblicati() {
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        this.eventi=catalogo.get_EventiPubblicati(this);
    }

    public EntityEvento creazioneEvento(String Titolo, String Descrizione, LocalDate Data, LocalTime Ora, String Luogo, int Costo, int Capienza) {
        return new EntityEvento(Titolo,Descrizione,Data,Ora,Luogo,Costo,Capienza,this);
    }

    public ArrayList<EntityEvento> getEventiPubblicati() {
        return eventi;
    }

    public void setEventi(ArrayList<EntityEvento> eventi) {
        this.eventi = eventi;
    }
}
