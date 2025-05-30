package entity;

import DTO.DTOEvento;
import database.DBEvento;

import java.time.LocalDate;
import java.util.ArrayList;

public class EntityCatalogo {

    private static EntityCatalogo uniqueInstance;
    private static ArrayList<EntityEvento> elencoEventi;

    private EntityCatalogo() {
        elencoEventi = new ArrayList();
    }

    public static EntityCatalogo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new EntityCatalogo();
        }
        return uniqueInstance;
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
            evento_temp.setPartecipanti(((DBEvento)lista_db_eventi.get(i)).getPartecipanti());
            evento_temp.setCapienza(((DBEvento)lista_db_eventi.get(i)).getCapienza());

            elencoEventi.add(evento_temp);
        }

    }
    public static boolean aggiungiEvento(){
        //DA IMPLEMENTARE
        return false;
    }
    public static ArrayList<DTOEvento> consultaCatalogo() {
        ArrayList<DTOEvento> lista_eventi = new ArrayList();

        DBEvento e = new DBEvento();
        ArrayList<DBEvento> lista_db_eventi = e.getListaEventi();

        for(int i = 0; i < lista_db_eventi.size(); ++i) {
            String titolo = (String)((lista_db_eventi.get(i)).getTitolo());
            String descrizione = (String)((lista_db_eventi.get(i)).getTitolo());
            String data = (String)((lista_db_eventi.get(i)).getTitolo());
            String ora = (String)((lista_db_eventi.get(i)).getTitolo());
            String luogo = (String)((lista_db_eventi.get(i)).getTitolo());
            String numpartecipanti = (String)((lista_db_eventi.get(i)).getTitolo());
            String capienza = (String)((lista_db_eventi.get(i)).getTitolo());

            DTOEvento temp = new DTOEvento(titolo,descrizione,data,ora,luogo,numpartecipanti,capienza);
            lista_eventi.add(temp);
        }

        return lista_eventi;
    }


    public static ArrayList<DTOEvento> ricercaEvento(String titolo, LocalDate data, String luogo) {

        caricaListaDaDB();

        ArrayList<DTOEvento> lista_dto = new ArrayList();

        for (EntityEvento evento : elencoEventi) {
            boolean match = true;

            String titoloDTO = evento.getTitolo();
            LocalDate dataTemp = evento.getData();
            String luogoDTO = evento.getLuogo();

            if (titolo != null) {
                if (!evento.getTitolo().equalsIgnoreCase(titolo)) {
                    match = false;
                } else {
                    titoloDTO = titolo;
                }
            }

            if (data != null) {
                if (!evento.getData().equals(data)) {
                    match = false;
                } else {
                    dataTemp = data;
                }
            }

            if (luogo != null) {
                if (!evento.getLuogo().equalsIgnoreCase(luogo)) {
                    match = false;
                } else {
                    luogoDTO = luogo;
                }
            }

            if (match) {
                String descrizioneDTO = evento.getDescrizione();
                String oraDTO = String.valueOf(evento.getOra());
                String dataDTO = String.valueOf(dataTemp);
                String numeroPartecipantiDTO = String.valueOf(evento.getPartecipanti());
                String numeroMaxPartecipantiDTO = String.valueOf(evento.getCapienza());

                DTOEvento dto = new DTOEvento(titoloDTO,descrizioneDTO,dataDTO,oraDTO,luogoDTO,numeroPartecipantiDTO,numeroMaxPartecipantiDTO);

                lista_dto.add(dto);
            }
        }

        return lista_dto;
    }

}
