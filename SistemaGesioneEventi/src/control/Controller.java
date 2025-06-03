//test commento

package control;

import DTO.DTOEvento;
import DTO.DTOUtente;
import entity.*;
import exceptions.*;

import java.time.LocalDate;
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

    public static List<DTOEvento> RicercaEvento(String titolo, LocalDate data, String luogo) throws EventoNotFoundException {
        List<DTOEvento> eventiDTO = new ArrayList<>();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        List<EntityEvento> eventiTrovati = catalogo.ricercaEvento(titolo,data,luogo);
        for (EntityEvento evento: eventiTrovati) {
            DTOEvento dto = new DTOEvento(evento.getTitolo(),evento.getDescrizione(),evento.getData(),evento.getOra(),evento.getLuogo(), evento.getCosto(),evento.getCapienza());
            eventiDTO.add(dto);
        }



        return eventiDTO;
    }

    public static void AcquistoBiglietto(EntityEvento evento, String email) throws DBException, AcquistoException {
        if (evento.verificaDisponibilita()) {
            EntityUtente u = new EntityUtente(email);
            // Chiede di inserire i dati del pagamento
            SistemaGestioneAcquisti sga = new SistemaGestioneAcquisti();
            if (sga.elaboraPagamento("1234", u.getNome(), u.getCognome())) {
                evento.creazioneBiglietto(u);
            } else {
                throw new AcquistoException("Pagamento non riuscito per l'utente: " + email);
            }
        } else {
            throw new AcquistoException("Biglietti esauriti per l'evento: " + evento.getTitolo());
        }
    }

    //inseriscievento
    //partecipaevento
    //acquistobiglietto
    //consultaEventiPublicati


}










