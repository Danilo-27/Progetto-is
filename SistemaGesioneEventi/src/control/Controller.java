//test commento

package control;

import DTO.DTOEvento;
import DTO.DTOUtente;
import entity.*;
import exceptions.RegistrationFailedException;
import exceptions.LoginFailedException;

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










}










