//test commento 1
package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import database.EventoDAO;
import exceptions.DBException;
import exceptions.EventoNotFoundException;

public class EntityCatalogo {

    private static EntityCatalogo uniqueInstance;
    private final List<EntityEvento> eventi;

    private EntityCatalogo() throws DBException {
        eventi = new ArrayList<>();
        for (EventoDAO evento : EventoDAO.getEventi()) {
            eventi.add(new EntityEvento(evento));
        }
    }

    public static EntityCatalogo getInstance() {
        if (uniqueInstance == null) {
            try{
                uniqueInstance = new EntityCatalogo();
            }catch(DBException e) {
                System.out.println(e.getMessage());
            }
        }
        return uniqueInstance;
    }

    public void aggiungiEvento(EntityEvento EventoCreato) throws DBException {
            EventoCreato.salvaSuDB();
            eventi.add(EventoCreato);
    }

    public EntityEvento cercaEventoPerId(int id) {
        EntityEvento Evento = null;
        for (EntityEvento evento : this.eventi) {
            if (evento.getId() == id) Evento = evento;
        }
        return Evento;
    }

    public EntityEvento cercaEventoPerTitolo(String titolo){
        EntityEvento Evento = null;
        for (EntityEvento evento : this.eventi) {
            if (Objects.equals(evento.getTitolo(), titolo)) Evento = evento;
        }
        return Evento;
    }




    public List<EntityEvento> ConsultaCatalogo() {
        LocalDate oggi = LocalDate.now();
        List<EntityEvento> eventiValidi = new ArrayList<>();
        for (EntityEvento evento : eventi) {
            if (evento.getData().isAfter(oggi) || evento.getData().isEqual(oggi)) {
                eventiValidi.add(evento);
            }
        }
        return eventiValidi;
    }


    public List<EntityEvento> ricercaEvento(String titolo, LocalDate data, String luogo) throws  EventoNotFoundException {
        List<EntityEvento> risultati = new ArrayList<>();
        for (EntityEvento evento : this.ConsultaCatalogo()) {
            boolean match = titolo == null || evento.getTitolo().equalsIgnoreCase(titolo);
            if (data != null && !evento.getData().equals(data)) {
                match = false;
            }
            if (luogo != null && !evento.getLuogo().equalsIgnoreCase(luogo)) {
                match = false;
            }
            if (match) {
                risultati.add(evento);
            }
        }
        if (risultati.isEmpty()) {
            throw new EventoNotFoundException("nessun evento trovato");
        }
        return risultati;
    }

    public ArrayList<EntityEvento> get_EventiPubblicati(EntityAmministratore amministratore){
        ArrayList<EntityEvento> risultati = new ArrayList<>();
        for (EntityEvento evento : this.eventi) {
            if(evento.getAmministratore()== amministratore){
                risultati.add(evento);
            }
        }
        return risultati;
    }




}
