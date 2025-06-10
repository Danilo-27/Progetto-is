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

    private Controller(){}
    
    /**
     * Gestisce la registrazione di un nuovo utente delegando l'operazione all'entità piattaforma.
     *
     * @param password la password scelta dall'utente
     * @param nome     il nome dell'utente
     * @param cognome  il cognome dell'utente
     * @param email    l'indirizzo email dell'utente
     * @throws RegistrationFailedException se il processo di registrazione fallisce
     */
    
    public static  void registrazione(String password, String nome, String cognome,String email) throws RegistrationFailedException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        piattaforma.registrazione(password, nome, cognome, email);
    }

    /**
     * Effettua l'autenticazione di un utente delegando l'operazione all'entità piattaforma.
     *
     * @param email    l'indirizzo email fornito dall'utente per l'autenticazione
     * @param password la password fornita dall'utente per l'autenticazione
     * @return un oggetto DTOUtente contenente le informazioni relative all'utente autenticato
     * @throws LoginFailedException se l'autenticazione fallisce
     */
    public static DTOUtente Autenticazione(String email, String password) throws LoginFailedException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        return piattaforma.Autenticazione(email, password);
    }

    /**
     * Recupera il catalogo degli eventi dallo strato entity, converte gli oggetti entity
     * in oggetti DTOEvento e restituisce una lista di questi oggetti DTOEvento.
     *
     * @return una lista di oggetti DTOEvento che rappresentano gli eventi nel catalogo
     */
    public static List<DTOEvento>ConsultaCatalogo() {
        List<DTOEvento> eventiDTO = new ArrayList<>();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        List<EntityEvento> eventi = catalogo.ConsultaCatalogo();
        for (EntityEvento evento: eventi) {
            DTOEvento dto = new DTOEvento(evento.getTitolo(),evento.getDescrizione(),evento.getData(),evento.getOra(),evento.getLuogo(), evento.getCosto(),evento.getCapienza(),evento.getBiglietti().toArray().length);
            eventiDTO.add(dto);
        }
        return eventiDTO;
    }

    /**
     * Cerca eventi nel catalogo in base ai parametri specificati e restituisce una lista di eventi corrispondenti.
     *
     * @param titolo il titolo dell'evento da cercare
     * @param data la data dell'evento da cercare 
     * @param luogo il luogo dell'evento da cercare
     * @return una lista di DTOEvento che rappresentano gli eventi che corrispondono ai criteri di ricerca
     * @throws EventoNotFoundException se non vengono trovati eventi con i criteri specificati
         */
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

    /**
     * Permette di acquistare un biglietto per un determinato evento se disponibile.
     *
     * @param evento_dto       un oggetto DTOEvento che rappresenta l'evento per il quale effettuare l'acquisto
     * @param email            l'indirizzo email dell'utente che effettua l'acquisto
     * @param NumeroCarta      il numero della carta di pagamento utilizzata per l'acquisto
     * @param NomeTitolare     il nome del titolare della carta di pagamento
     * @param CognomeTitolare  il cognome del titolare della carta di pagamento
     * @throws AcquistoException            se il pagamento fallisce o i biglietti per l'evento sono esauriti
     * @throws BigliettoNotFoundException   se il biglietto dell'evento non viene trovato
     */
    public static void AcquistoBiglietto(DTOEvento evento_dto, String email,String NumeroCarta,String NomeTitolare,String CognomeTitolare) throws AcquistoException,BigliettoNotFoundException,RedundancyException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityEvento evento = new EntityEvento(evento_dto.getTitolo());
        if (evento.verificaDisponibilità()) {
            EntityCliente u = piattaforma.cercaClientePerEmail(email);
            SistemaGestioneAcquisti sga = new SistemaGestioneAcquisti();
            if (sga.elaboraPagamento(NumeroCarta, NomeTitolare, CognomeTitolare)) {
                u.getBiglietti().add(evento.creazioneBiglietto(u));
            } else {
                throw new AcquistoException("Pagamento non riuscito per l'utente: " + email);
            }
        } else {
            throw new AcquistoException("Biglietti esauriti per l'evento: " + evento.getTitolo());
        }
    }

    /**
     * Consente a un partecipante di partecipare a un evento verificando il codice univoco del biglietto
     * associato e aggiornando il numero di partecipanti all'evento. Solleva eccezioni in caso di
     * problemi durante la validazione del biglietto o di errori nel database.
     *
     * @param codiceUnivoco   il codice univoco del biglietto da validare
     * @param dtoEvento       un oggetto DTOEvento contenente le informazioni sull'evento
     * @throws BigliettoConsumatoException  se il biglietto è già stato utilizzato
     * @throws BigliettoNotFoundException   se il biglietto non viene trovato
     */
    public static void partecipaEvento(String codiceUnivoco,DTOEvento dtoEvento) throws BigliettoConsumatoException, BigliettoNotFoundException {
        EntityEvento evento= new EntityEvento(dtoEvento.getTitolo());
        EntityBiglietto biglietto = evento.verificaCodice(codiceUnivoco);
        if(biglietto == null) {
            throw new BigliettoNotFoundException("Biglietto non trovato");
        }else {
            biglietto.validaBiglietto();
            evento.aggiornaPartecipanti();
        }
    }

    /**
     * Pubblica un nuovo evento sulla piattaforma associandolo all'amministratore specificato.
     *
     * @param Titolo              il titolo dell'evento da pubblicare
     * @param Descrizione         la descrizione dell'evento
     * @param Data                la data in cui si terrà l'evento
     * @param Ora                 l'orario in cui si terrà l'evento
     * @param Luogo               il luogo dove si svolgerà l'evento
     * @param Costo               il costo per partecipare all'evento
     * @param Capienza            la capienza massima di partecipanti all'evento
     * @param emailAmministratore l'indirizzo email dell'amministratore che pubblica l'evento
     */
    public static void creaEvento(String Titolo, String Descrizione, LocalDate Data, LocalTime Ora, String Luogo, int Costo, int Capienza, String emailAmministratore) throws RedundancyException,LoadingException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityAmministratore amministratore=piattaforma.cercaAmministratorePerEmail(emailAmministratore);
        EntityEvento evento= amministratore.creazioneEvento(Titolo, Descrizione, Data, Ora, Luogo, Costo, Capienza);
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        catalogo.aggiungiEvento(evento);
    }

    /**
     * Consulta gli eventi pubblicati da un amministratore e restituisce la lista degli eventi
     * con le relative informazioni sui biglietti venduti e partecipanti.
     *
     * @param email l'indirizzo email dell'amministratore di cui si vogliono consultare gli eventi pubblicati
     * @return una mappa contenente per ogni DTOEvento un oggetto con le informazioni sui biglietti venduti
     * e, se l'evento è in corso o passato, il numero di partecipanti e la lista dei partecipanti
     * @throws BigliettoNotFoundException se si verifica un errore durante il recupero dei biglietti associati agli eventi
     */
    public static Map<DTOEvento, Object> ConsultaEventiPubblicati(String email)  throws BigliettoNotFoundException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        Map<DTOEvento, Object> eventoPartecipantiMap = new HashMap<>();
        LocalDate oggi = LocalDate.now();
        EntityAmministratore amministratore = piattaforma.cercaAmministratorePerEmail(email);
        amministratore.caricaEventiPubblicati();
        for (EntityEvento evento : amministratore.getEventiPubblicati()) {
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
            dtoEvento.setBigliettiVenduti(evento.getNumeroBigliettiVenduti());
            eventoPartecipantiMap.put(dtoEvento, infoEvento);
            }
            return eventoPartecipantiMap;
    }

    /**
     * Recupera lo storico biglietti per uno specifico utente basato sulla sua email.
     * Questo metodo cerca l'utente tramite email, carica i suoi biglietti,
     * e converte i dati dei biglietti in una lista di oggetti DTOBiglietto.
     *
     * @param emailUtente l'email dell'utente di cui si richiede lo storico biglietti
     * @return una lista di DTOBiglietto che rappresenta lo storico biglietti dell'utente
     * @throws BigliettoNotFoundException se non vengono trovati biglietti per l'utente
     */

    public static ArrayList<DTOBiglietto> consultaStoricoBiglietti(String emailUtente) throws BigliettoNotFoundException {
        ArrayList<DTOBiglietto> biglietti = new ArrayList<>();
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityCliente cliente = piattaforma.cercaClientePerEmail(emailUtente);
        cliente.caricaBiglietti();
        for(EntityBiglietto biglietto : cliente.getBiglietti()){
            DTOBiglietto dtoBiglietto=new DTOBiglietto(biglietto.getCodice_univoco(),
                    biglietto.getStato(), biglietto.getEvento().getTitolo(),
                    biglietto.getEvento().getData());
            biglietti.add(dtoBiglietto);
        }
        return biglietti;
    }
}