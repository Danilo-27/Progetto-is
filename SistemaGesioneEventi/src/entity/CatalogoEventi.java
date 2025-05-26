package entity;

import database.DBEvento;

import java.util.ArrayList;
import java.util.Date;

public class CatalogoEventi {

    private static ArrayList<EntityEvento> elencoEventi;

    public CatalogoEventi() {
        elencoEventi = new ArrayList();
    }

    public static void caricaListaDaDB() {
        elencoEventi = new ArrayList();

        DBEvento s = new DBEvento();

        ArrayList<DBEvento> lista_db_eventi = s.getListaEventi();

        for(int i = 0; i < lista_db_eventi.size(); ++i) {
            EntityEvento evento_temp = new EntityEvento();
            evento_temp.setId(lista_db_eventi.get(i).getId());
            evento_temp.setTitolo(((DBEvento)lista_db_eventi.get(i)).getTitolo());
            evento_temp.setDescrizione(((DBEvento)lista_db_eventi.get(i)).getDescrizione());
            evento_temp.setData(((DBEvento)lista_db_eventi.get(i)).getData());
            evento_temp.setOra(((DBEvento)lista_db_eventi.get(i)).getOra());
            evento_temp.setLuogo(((DBEvento)lista_db_eventi.get(i)).getLuogo());
            evento_temp.setNumeroPartecipanti(((DBEvento)lista_db_eventi.get(i)).getNumeroPartecipanti());
            evento_temp.setNumeroMassimoPartecipanti(((DBEvento)lista_db_eventi.get(i)).getNumeroMassimoPartecipanti());

            elencoEventi.add(evento_temp);
        }

    }

    public static ArrayList<EntityEvento> getListaEventi() {
        ArrayList<EntityEvento> lista_eventi = new ArrayList();
        DBEvento e = new DBEvento();
        ArrayList<DBEvento> lista_db_eventi = e.getListaEventi();

        for (DBEvento dbEvento : lista_db_eventi) {
            EntityEvento temp = new EntityEvento(dbEvento);
            lista_eventi.add(temp);
        }

        return lista_eventi;
    }

}
