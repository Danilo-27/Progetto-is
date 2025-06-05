//test commento 1
package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import DTO.DTOEvento;
import database.EventoDAO;
import exceptions.DBException;
import exceptions.EventoNotFoundException;

public class EntityCatalogo {

    private static EntityCatalogo uniqueInstance;
    private final List<EntityEvento> eventi;

    private EntityCatalogo() {
        eventi = new ArrayList<>();
        try {
            for (EventoDAO evento : EventoDAO.getEventi()) {
                eventi.add(new EntityEvento(evento));
            }
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }

    public static EntityCatalogo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new EntityCatalogo();
        }
        return uniqueInstance;
    }

    /**
     * Aggiunge un nuovo evento al catalogo e lo salva nel database.
     *
     * @param EventoCreato l'oggetto EntityEvento che rappresenta l'evento da aggiungere
     * @throws DBException se si verifica un errore durante il salvataggio dell'evento nel database
     */

    public void aggiungiEvento(EntityEvento EventoCreato) throws DBException {
            EventoCreato.salvaSuDB();
            eventi.add(EventoCreato);
    }

    /**
     * Restituisce la lista di eventi presenti nel catalogo.
     *
     * @return una lista di oggetti di tipo EntityEvento che rappresentano gli eventi presenti nel catalogo
     */
    public List<EntityEvento> ConsultaCatalogo() {
        return eventi;
    }

    /**
     * Searches for events in the catalog based on the specified criteria: title, date, and location.
     * If no events match the criteria, an EventoNotFoundException is thrown.
     *
     * @param titolo the title of the event to search for; can be null to ignore this criterion
     * @param data the date of the event to search for; can be null to ignore this criterion
     * @param luogo the location of the event to search for; can be null to ignore this criterion
     * @return a list of events that match the specified criteria
     * @throws EventoNotFoundException if no events matching the specified criteria are found
     */

    // Metodo aggiornato con gestione eccezione
    public List<EntityEvento> ricercaEvento(String titolo, LocalDate data, String luogo) throws  EventoNotFoundException {
        List<EntityEvento> risultati = new ArrayList<>();

        for (EntityEvento evento : eventi) {
            boolean match = true;

            // Controllo titolo
            if (titolo != null && !evento.getTitolo().equalsIgnoreCase(titolo)) {
                match = false;
            }

            // Controllo data
            if (data != null && !evento.getData().equals(data)) {
                match = false;
            }

            // Controllo luogo
            if (luogo != null && !evento.getLuogo().equalsIgnoreCase(luogo)) {
                match = false;
            }

            // Se tutti i criteri sono soddisfatti, aggiungi alla lista
            if (match) {
                risultati.add(evento);
            }
        }

        // Se non è stato trovato nessun evento, lancia l'eccezione
        if (risultati.isEmpty()) {
            throw new EventoNotFoundException("nessun evento trovato");
        }

        return risultati;
    }






}
