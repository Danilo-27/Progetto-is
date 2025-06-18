package control;

import DTO.DTOBiglietto;
import DTO.DTODatiPagamento;
import DTO.DTOEvento;
import DTO.DTOUtente;
import entity.*;
import exceptions.*;
import external.PagamentoService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * La classe Controller rappresenta il livello di interazione tra
 * l'utente e lo strato logico dell'applicazione. Fornisce metodi statici
 * per la gestione delle operazioni della piattaforma, come registrazione,
 * autenticazione, gestione degli eventi e acquisto di biglietti.
 * Delegando le operazioni principali a specifiche entità, la classe
 * facilita il coordinamento tra gli strati logici e gestisce le eccezioni
 * per assicurare un'esperienza utente fluida.
 */
public class Controller {

    /**
     * Costruttore privato della classe Controller.
     * Questo costruttore privato impedisce l' istanziazione diretta della classe,
     * garantendo che i metodi statici siano l'unico modo per accedere alle funzionalità offerte.
     */
    private Controller(){}
    
    /**
     * Metodo che permette la registrazione di un nuovo utente sulla piattaforma.
     * Viene inoltrata una richiesta ad un'entità di gestione interna per completare il processo.
     *
     * @param password Password scelta dall'utente per il proprio account.
     * @param nome Nome dell'utente da registrare.
     * @param cognome Cognome dell'utente da registrare.
     * @param email Email dell'utente, utilizzata anche come identificativo univoco.
     * @throws RegistrationFailedException Eccezione lanciata se si verifica un errore
     *         durante il processo di registrazione, ad esempio se l'email è già presente nel sistema.
     */
    
    public static  void registrazione(String password, String nome, String cognome,String email) throws RegistrationFailedException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        piattaforma.registrazione(password, nome, cognome, email);
    }

    /**
     * Esegue l'autenticazione di un utente sulla piattaforma utilizzando l'email e la password forniti.
     * Se l'autenticazione ha successo, restituisce un oggetto {@code DTOUtente} con i dettagli dell'utente autenticato.
     * In caso contrario, genera un'eccezione {@code LoginFailedException}.
     *
     * @param email L'indirizzo email dell'utente che desidera autenticarsi.
     * @param password La password associata all'account utente.
     * @return Un oggetto {@code DTOUtente} contenente i dati dell'utente autenticato.
     * @throws LoginFailedException Se l'autenticazione fallisce a causa di credenziali errate
     *                              o di problemi legati all'accesso.
     */
    public static DTOUtente autenticazione(String email, String password) throws LoginFailedException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        return piattaforma.Autenticazione(email, password);
    }

    /**
     * Consulta il catalogo degli eventi disponibili e restituisce una lista di Data Transfer Object (DTO) degli eventi.
     * Ogni DTO contiene le informazioni principali dell'evento, rendendole facilmente accessibili e trasferibili.
     *
     * @return una lista di oggetti {@code DTOEvento} che rappresentano gli eventi disponibili nel catalogo.
     */
    public static List<DTOEvento> consultaCatalogo() throws EventoNotFoundException {
        List<DTOEvento> eventiDTO = new ArrayList<>();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        List<EntityEvento> eventi = null;
        try {
            eventi = catalogo.ConsultaCatalogo();
            for (EntityEvento evento: eventi) {
                DTOEvento dto = new DTOEvento(evento.getTitolo(),evento.getDescrizione(),evento.getData(),evento.getOra(),evento.getLuogo(), evento.getCosto(),evento.getCapienza(),evento.getBiglietti().toArray().length);
                eventiDTO.add(dto);
            }
            return eventiDTO;
        } catch (EventoNotFoundException e) {
            throw e;
        }
    }

    /**
     * Metodo che permette di cercare eventi all'interno del catalogo in base ai parametri specificati.
     * Restituisce una lista di oggetti DTOEvento corrispondenti agli eventi trovati.
     *
     * @param titolo Il titolo dell'evento da cercare. Può essere parziale o completo.
     * @param data La data dell'evento da cercare. Può essere null per ignorare il filtro sulla data.
     * @param luogo Il luogo dell'evento da cercare. Può essere parziale o completo.
     * @return Una lista di oggetti DTOEvento che rappresentano gli eventi trovati.
     * @throws EventoNotFoundException Se nessun evento corrisponde ai criteri di ricerca specificati.
     */
    public static List<DTOEvento> ricercaEvento(String titolo, LocalDate data, String luogo) throws EventoNotFoundException {
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
     * Metodo per gestire l'acquisto di un biglietto da parte di un cliente per un evento specifico.
     * Utilizza i servizi di pagamento forniti e aggiorna le entità coinvolte come l'evento e il cliente.
     *
     * @param pagamentoService Il servizio utilizzato per processare il pagamento.
     * @param eventoDto Un oggetto {@code DTOEvento} contenente i dettagli dell'evento per cui acquistare il biglietto.
     * @param email L'indirizzo email del cliente che intende acquistare il biglietto.
     * @param dtoDatiPagamento I dettagli del pagamento sotto forma di oggetto {@code DTODatiPagamento}.
     * @throws AcquistoException Eccezione generata in caso di errori durante il processo di acquisto.
     * @throws BigliettoNotFoundException Eccezione generata se non sono disponibili biglietti per l'evento specificato.
     * @throws UpdateException Eccezione generata se si verifica un problema durante l'aggiornamento dello stato delle entità.
     */
    public static void acquistoBiglietto(PagamentoService pagamentoService, DTOEvento eventoDto, String email, DTODatiPagamento dtoDatiPagamento) throws AcquistoException, BigliettoNotFoundException, UpdateException,LoadingException {

        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        EntityEvento evento = catalogo.cercaEventoPerTitolo(eventoDto.getTitolo());

        evento.acquistoBiglietto(email,pagamentoService,dtoDatiPagamento);

    }

    /**
     * Metodo che consente la verifica della partecipazione di un utente a un evento specifico.
     * Utilizza il codice univoco del biglietto per validare l'accesso e segnare la partecipazione effettuata.
     *
     * @param codiceUnivoco Il codice univoco associato al biglietto dell'utente.
     * @param dtoEvento Un oggetto {@code DTOEvento} che rappresenta i dettagli dell'evento a cui partecipare.
     * @throws BigliettoConsumatoException Se il biglietto è già stato utilizzato per accedere all'evento.
     * @throws BigliettoNotFoundException Se il codice univoco fornito non corrisponde ad alcun biglietto valido.
     */
    public static void partecipazioneEvento(String codiceUnivoco, DTOEvento dtoEvento,String emailUtente) throws BigliettoConsumatoException, BigliettoNotFoundException {
        EntityEvento evento= new EntityEvento(dtoEvento.getTitolo());
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityCliente cliente = piattaforma.cercaClientePerEmail(emailUtente);
        evento.partecipazioneEvento(codiceUnivoco,cliente);
    }

    /**
     * Metodo che permette di creare un evento e pubblicarlo attraverso un amministratore identificato
     * dall'indirizzo email fornito. L'evento viene registrato sulla piattaforma con i dettagli specificati.
     *
     * @param titolo Il titolo dell'evento da creare.
     * @param descrizione La descrizione dell'evento, che può includere informazioni utili per i partecipanti.
     * @param data La data in cui si terrà l'evento.
     * @param ora L'orario in cui avrà luogo l'evento.
     * @param luogo Il luogo in cui si svolgerà l'evento.
     * @param costo Il costo del biglietto per partecipare all'evento, espresso in unità monetarie.
     * @param capienza La capienza massima dell'evento, cioè il numero massimo di partecipanti ammessi.
     * @param emailAmministratore L'indirizzo email dell'amministratore che pubblica l'evento.
     * @throws RedundancyException Eccezione lanciata se esiste già un evento identico al nuovo evento da creare.
     * @throws LoadingException Eccezione lanciata se si verifica un errore durante il caricamento dei dati
     *         o delle entità necessarie per pubblicare l'evento.
     */
    public static void creaEvento(String titolo, String descrizione, LocalDate data, LocalTime ora, String luogo, int costo, int capienza, String emailAmministratore) throws RedundancyException,LoadingException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityAmministratore amministratore=piattaforma.cercaAmministratorePerEmail(emailAmministratore);
        amministratore.pubblicaEvento(titolo, descrizione, data, ora, luogo, costo, capienza);
    }

    /**
     * Metodo che consente di consultare la lista degli eventi pubblicati da un amministratore
     * identificato tramite l'indirizzo email fornito. Gli eventi pubblicati vengono restituiti
     * come una mappa, dove ogni chiave rappresenta un oggetto di tipo {@code DTOEvento} e il
     * valore associato può contenere ulteriori dettagli relativi all'evento.
     *
     * @param email L'indirizzo email dell'amministratore che desidera consultare gli eventi pubblicati.
     * @return Una mappa contenente gli eventi pubblicati dall'amministratore, con ciascun evento
     *         rappresentato da un oggetto {@code DTOEvento} e il relativo valore associato.
     * @throws BigliettoNotFoundException Eccezione lanciata se non vengono trovati eventi pubblicati
     *         associati all'amministratore specificato.
     */
    public static Map<DTOEvento, Object> consultaEventiPubblicati(String email)  throws BigliettoNotFoundException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityAmministratore amministratore = piattaforma.cercaAmministratorePerEmail(email);
        return amministratore.consultaEventiPubblicati();
    }
    /**
     * Consulta lo storico dei biglietti acquistati da un utente identificato tramite la sua email.
     * I dati vengono recuperati per ogni biglietto associato al cliente e trasformati in oggetti
     * di tipo DTOBiglietto per una facile gestione e trasferimento.
     *
     * @param emailUtente l'email dell'utente per il quale si desidera consultare lo storico dei biglietti
     * @return una lista di oggetti DTOBiglietto rappresentanti i biglietti acquistati dall'utente
     * @throws BigliettoNotFoundException se non vengono trovati biglietti associati all'utente
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