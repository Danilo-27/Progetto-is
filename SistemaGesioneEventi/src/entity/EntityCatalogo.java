package entity;

import java.util.ArrayList;
import java.util.List;
import database.
import DTO.DTOEvento;
import database.EventoDAO;

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


}
