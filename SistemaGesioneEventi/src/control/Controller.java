//test commento 1

package control;

import DTO.DTOBiglietto;
import DTO.DTOEvento;
import DTO.DTOUtente;
import entity.*;
import exceptions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;




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
            DTOEvento dto = new DTOEvento(evento.getTitolo(),evento.getDescrizione(),evento.getData(),evento.getOra(),evento.getLuogo(), evento.getCosto(),evento.getCapienza(),evento.getBiglietti().toArray().length);
            eventiDTO.add(dto);
        }
        return eventiDTO;
    }

    public static List<DTOEvento> RicercaEvento(String titolo, LocalDate data, String luogo) throws EventoNotFoundException {
        List<DTOEvento> eventiDTO = new ArrayList<>();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        List<EntityEvento> eventiTrovati = catalogo.ricercaEvento(titolo,data,luogo);
        for (EntityEvento evento: eventiTrovati) {
            DTOEvento dto = new DTOEvento(evento.getTitolo(),evento.getDescrizione(),evento.getData(),evento.getOra(),evento.getLuogo(), evento.getCosto(),evento.getCapienza(),evento.getBiglietti().toArray().length);
            eventiDTO.add(dto);
        }

        return eventiDTO;
    }

    public static void AcquistoBiglietto(DTOEvento evento_dto, String email,String NumeroCarta,String NomeTitolare,String CognomeTitolare) throws DBException, AcquistoException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityEvento evento = new EntityEvento(evento_dto.getTitolo());
        if (evento.verificaDisponibilità()) {
            EntityCliente u = piattaforma.cercaClientePerEmail(email);
            // Chiede di inserire i dati del pagamento
            SistemaGestioneAcquisti sga = new SistemaGestioneAcquisti();
            if (sga.elaboraPagamento(NumeroCarta, NomeTitolare, CognomeTitolare)) {
                try {
                    u.getBiglietti().add(evento.creazioneBiglietto(u));
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

    public static void partecipaEvento(String codiceUnivoco,DTOEvento eventodb) throws BigliettoConsumatoException, BigliettoNotFoundException, DBException {
        EntityEvento evento= new EntityEvento(eventodb.getTitolo());
        EntityBiglietto biglietto = evento.verificaCodice(codiceUnivoco);
        if(biglietto == null) {
            throw new BigliettoNotFoundException("Biglietto non trovato");
        }else {
            biglietto.validaBiglietto();
            evento.aggiornaPartecipanti();
        }
    }

    public static void pubblicaEvento(String Titolo, String Descrizione, LocalDate Data, LocalTime Ora, String Luogo, int Costo, int Capienza,String emailAmministratore) throws DBException{
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityAmministratore amministratore=piattaforma.cercaAmministratorePerEmail(emailAmministratore);
        EntityEvento evento= amministratore.creazioneEvento(Titolo, Descrizione, Data, Ora, Luogo, Costo, Capienza);
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        catalogo.aggiungiEvento(evento);
    }

    public static Map<DTOEvento, Object> ConsultaEventiPubblicati(String email){
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        Map<DTOEvento, Object> eventoPartecipantiMap = new HashMap<>();
        LocalDate oggi = LocalDate.now();
        EntityAmministratore amministratore = piattaforma.cercaAmministratorePerEmail(email);
        amministratore.caricaEventiPubblicati();
        for (EntityEvento evento : amministratore.getEventiPubblicati()) {
            System.out.println(evento);
        }

        for (EntityEvento evento : amministratore.getEventiPubblicati()) {
            //creo un dto evento da ritornare all'interfaccia
            DTOEvento dtoEvento = new DTOEvento(evento.getTitolo(), evento.getDescrizione(), evento.getData(), evento.getOra(), evento.getLuogo(), evento.getCosto(), evento.getCapienza(), evento.getNumeroBigliettiVenduti());
            Map<String, Object> infoEvento = new HashMap<>();
            infoEvento.put("bigliettiVenduti", evento.getNumeroBigliettiVenduti());

            if (evento.getData().isEqual(oggi)) {
                infoEvento.put("numeroPartecipanti", evento.getNumeroPartecipanti());
                List<EntityCliente> partecipanti = evento.listaPartecipanti();
                List<DTOUtente> dtoPartecipanti = new ArrayList<>();
                for (EntityCliente partecipante : partecipanti) {
                    DTOUtente dtoUtente = new DTOUtente(partecipante.getNome(), partecipante.getCognome());
                    dtoPartecipanti.add(dtoUtente);
                }
                infoEvento.put("listaPartecipanti", dtoPartecipanti);
            } else if (evento.getData().isBefore(oggi)) {
                infoEvento.put("numeroPartecipanti", evento.getNumeroPartecipanti());
            }

            dtoEvento.setBigliettivenduti(evento.getNumeroBigliettiVenduti());

            eventoPartecipantiMap.put(dtoEvento, infoEvento);
        }
        System.out.println(eventoPartecipantiMap);
        return eventoPartecipantiMap;
    }

    public static ArrayList<DTOBiglietto> consultaStoricoBiglietti(String emailUtente) throws DBException {
        ArrayList<DTOBiglietto> biglietti = new ArrayList<>();

        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityCliente cliente = piattaforma.cercaClientePerEmail(emailUtente);
        cliente.caricaBiglietti();
        //per ogni biglietto acquistato ne creo un DTO con le sue informazioni e le informazioni
        //all'evento per cui è stato acquistato il biglietto
        for(EntityBiglietto biglietto : cliente.getBiglietti()){

            DTOBiglietto dtoBiglietto=new DTOBiglietto(biglietto.getCodice_univoco(),
                    biglietto.getStato(), biglietto.getEvento().getTitolo(),
                    biglietto.getEvento().getData());

            biglietti.add(dtoBiglietto);
        }

        return biglietti;
    }

}