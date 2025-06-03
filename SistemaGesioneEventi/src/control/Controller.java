//test commento

package control;

import DTO.DTOEvento;
import DTO.DTOUtente;
import entity.*;
import exceptions.DBException;
import exceptions.RegistrationFailedException;
import exceptions.LoginFailedException;
import exceptions.UniqueCodeException;

import java.util.ArrayList;
import java.util.List;


public class Controller {




    public static  void registrazione(String password, String nome, String cognome,String email) throws RegistrationFailedException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        piattaforma.registrazione(password, nome, cognome, email);
    }

    public static DTOUtente Autenticazione(String email, String password) throws LoginFailedException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        return piattaforma.Autenticazione(email, password);
    }



    public static List<DTOEvento> ConsultaCatalogo() {
        List<DTOEvento> eventiDTO = new ArrayList<>();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        List<EntityEvento> eventi = catalogo.ConsultaCatalogo();
        for (EntityEvento evento: eventi) {
            DTOEvento dto = new DTOEvento(evento.getTitolo(),evento.getDescrizione(),evento.getData(),evento.getOra(),evento.getLuogo(), evento.getCosto(),evento.getCapienza());
            eventiDTO.add(dto);
        }



        return eventiDTO;
    }

    public static void PartecipaEvento(String codiceUnivoco,EntityEvento evento) throws UniqueCodeException,DBException {
        EntityBiglietto biglietto=evento.verificaCodice(codiceUnivoco);
        if(biglietto!=null) {
            evento.setPartecipanti(evento.getPartecipanti()+1);//scrittura sul DB
            biglietto.setStato(1);
            if(biglietto.aggiornaSuDB()==-1){
                throw new DBException("Errore nel DB");
            }
        }else{
            throw new UniqueCodeException("Biglietto non trovato");
        }
    }


   // EntityUtente u = new EntityUtente();
    // u.idUtente(email);









}










