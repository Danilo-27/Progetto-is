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
        EntityEvento evento = new EntityEvento(evento_dto.getTitolo());
        if (evento.verificaDisponibilità()) {
            EntityUtente u = new EntityUtente(email);
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
        EntityUtente amministratore=new EntityUtente();
        amministratore.caricaPerEmail(emailAmministratore);
        EntityEvento evento= amministratore.creazioneEvento(Titolo, Descrizione, Data, Ora, Luogo, Costo, Capienza);
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        catalogo.aggiungiEvento(evento);
    }

    public static Map<DTOEvento, Object> ConsultaEventiPubblicati(String email) throws DBException {
        Map<DTOEvento, Object> eventoPartecipantiMap = new HashMap<>();
        LocalDate oggi = LocalDate.now();
        try {
            EntityUtente utente = new EntityUtente(email);
            List<EntityEvento> eventi = utente.getEventi();

            // Verifica se l'utente ha eventi pubblicati
            if (eventi.isEmpty()) {
                throw new DBException("Nessun evento pubblicato per l'utente: " + email);
            }

            // Processa ogni evento
            for (EntityEvento evento : eventi) {

                DTOEvento dtoEvento = new DTOEvento(evento.getTitolo(), evento.getDescrizione(), evento.getData(), evento.getOra(), evento.getLuogo(), evento.getCosto(), evento.getCapienza(),evento.getNumeroBigliettiVenduti());
                // Crea mappa informazioni evento
                Map<String, Object> infoEvento = new HashMap<>();
                LocalDate dataEvento = evento.getData();

                infoEvento.put("bigliettiVenduti", evento.getNumeroBigliettiVenduti());

                if (dataEvento.isEqual(oggi)) {

                    infoEvento.put("numeroPartecipanti", evento.getNumeroPartecipanti());

                    List<EntityUtente> partecipanti = evento.getpartecipanti();
                    List<DTOUtente> dtoPartecipanti = new ArrayList<>();
                    for (EntityUtente partecipante : partecipanti) {
                        DTOUtente dtoUtente = new DTOUtente(partecipante.getNome(), partecipante.getCognome());
                        dtoPartecipanti.add(dtoUtente);
                    }
                    infoEvento.put("listaPartecipanti", dtoPartecipanti);

                } else if (dataEvento.isBefore(oggi)) {
                    infoEvento.put("numeroPartecipanti", evento.getNumeroPartecipanti());
                }

                dtoEvento.setBigliettivenduti(evento.getNumeroBigliettiVenduti());

                eventoPartecipantiMap.put(dtoEvento, infoEvento);
            }

            return eventoPartecipantiMap;

        } catch (DBException e) {
            // Re-throw delle eccezioni specifiche del database
            throw e;
        } catch (Exception e) {
            // Gestione di altre eccezioni non previste
            throw new DBException("Errore imprevisto durante la consultazione degli eventi: " + e.getMessage());
        }
    }
    public static ArrayList<DTOBiglietto> consultaStoricoBiglietti(String emailUtente) throws DBException {
        ArrayList<DTOBiglietto> biglietti = new ArrayList<>();
        EntityUtente utente = new EntityUtente(emailUtente);
        for(EntityBiglietto biglietto : utente.getBiglietti()){
            String codiceUnivoco=biglietto.getCodice_univoco();
            int stato=biglietto.getStato();
            LocalDate dataEvento = biglietto.getEvento().getData();
            DTOBiglietto dtoBiglietto=new DTOBiglietto(codiceUnivoco,stato,biglietto.getEvento().getTitolo(),dataEvento);
            biglietti.add(dtoBiglietto);
        }
        return biglietti;
    }



}