//test commento 1

package control;

import DTO.DTOEvento;
import DTO.DTOUtente;
import entity.*;
import exceptions.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public static void AcquistoBiglietto(DTOEvento evento_dto, String email,String NumeroCarta,String NomeTitolare,String CognomeTitolare) throws DBException, AcquistoException {
        EntityEvento evento = new EntityEvento(evento_dto.getTitolo());
        if (evento.verificaDisponibilita()) {
            EntityUtente u = new EntityUtente(email);
            // Chiede di inserire i dati del pagamento
            SistemaGestioneAcquisti sga = new SistemaGestioneAcquisti();
            if (sga.elaboraPagamento(NumeroCarta, NomeTitolare, CognomeTitolare)) {
                try {
                    evento.creazioneBiglietto(u);
                } catch (DBException e) {
                    throw new AcquistoException("L'utente " + email + " ha già acquistato un biglietto per l'evento: " + evento.getTitolo());
                }
            } else {
                throw new AcquistoException("Pagamento non riuscito per l'utente: " + email);
            }
        } else {
            throw new AcquistoException("Biglietti esauriti per l'evento: " + evento.getTitolo());
        }
    }

    public static void partecipaEvento(String codiceUnivoco,DTOEvento eventodb) throws BigliettoConsumatoException,BigliettoNotFoundException {
        EntityEvento evento= new EntityEvento(eventodb.getTitolo());
        EntityBiglietto biglietto = evento.verificaCodice(codiceUnivoco);
        if(biglietto == null) {
            throw new BigliettoNotFoundException("Biglietto non trovato");
        }else {
            biglietto.validaBiglietto();
            evento.aggiornaPartecipanti();
        }
    }

    public void pubblicaEvento(String Titolo, String Descrizione, LocalDate Data, LocalTime Ora, String Luogo, int Costo, int Capienza,String emailAmministratore) throws DBException{
        //richiama pubblica evento di utente
        //poi fa aggiungi evento al catalogo (prendendo la instance di catalogo)
        EntityUtente amministratore=new EntityUtente(emailAmministratore);
        EntityEvento evento= amministratore.pubblicaEvento(Titolo, Descrizione, Data, Ora, Luogo, Costo, Capienza,emailAmministratore);
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        catalogo.aggiungiEvento(evento);

    }
    //inseriscievento
    //acquistobiglietto
    //consultaEventiPublicati


}










