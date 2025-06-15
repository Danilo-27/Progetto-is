package control;

import DTO.DTOBiglietto;
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
import java.util.HashMap;

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
    public static List<DTOEvento> consultaCatalogo() {
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
     * Gestisce l'acquisto di un biglietto per un evento specifico da parte di un cliente.
     * Verifica la disponibilità dei biglietti, il pagamento e assicura che il cliente
     * non abbia già acquistato un biglietto per lo stesso evento.
     *
     * @param eventoDto rappresenta l'evento per il quale si desidera acquistare il biglietto.
     * @param email l'indirizzo email dell'utente che effettua l'acquisto.
     * @param numeroCarta il numero della carta di pagamento usata per l'acquisto.
     * @param nomeTitolare il nome del titolare della carta di pagamento.
     * @param cognomeTitolare il cognome del titolare della carta di pagamento.
     * @throws AcquistoException se il pagamento non viene elaborato correttamente o i biglietti sono esauriti.
     * @throws BigliettoNotFoundException se l'evento non viene trovato nel catalogo.
     * @throws RedundancyException se l'utente ha già acquistato un biglietto per lo stesso evento.
     */
    public static void acquistoBiglietto(PagamentoService pagamentoService, DTOEvento eventoDto, String email, String numeroCarta, String nomeTitolare, String cognomeTitolare, String scadenza) throws AcquistoException, BigliettoNotFoundException, RedundancyException {

        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();

        EntityEvento evento = catalogo.cercaEventoPerTitolo(eventoDto.getTitolo());
        evento.caricaBiglietti();
        EntityCliente cliente = piattaforma.cercaClientePerEmail(email);
        cliente.caricaBiglietti();

        if (cliente.haBigliettoPerEvento(evento))
            throw new RedundancyException("Biglietto già acquistato per questo evento");


        if (!evento.verificaDisponibilità())
            throw new AcquistoException("Biglietti esauriti per l'evento "+evento.getTitolo());


        // Chiamata al servizio di pagamento
        PagamentoService.EsitoPagamento esito = pagamentoService.elaboraPagamento(
                numeroCarta, nomeTitolare, cognomeTitolare,scadenza,evento.getCosto());

        if (esito == PagamentoService.EsitoPagamento.SUCCESSO)
            cliente.getBiglietti().add(evento.creaBiglietto(cliente));
        else
            throw new AcquistoException("Pagamento fallito " + esito.name().replace("_", " ").toLowerCase());

    }

    /**
     * Permette a un utente di partecipare a un evento specificato tramite un codice univoco
     * associato al biglietto e i dettagli dell'evento.
     *
     * @param codiceUnivoco Il codice univoco del biglietto da convalidare per partecipare all'evento.
     * @param dtoEvento I dettagli dell'evento a cui l'utente desidera partecipare.
     * @throws BigliettoConsumatoException Se il biglietto è già stato utilizzato in precedenza.
     * @throws BigliettoNotFoundException Se il biglietto con il codice univoco specificato non esiste.
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
     * Metodo responsabile della creazione di un nuovo evento sulla piattaforma.
     * Valida l'evento verificando che non sia già esistente o con una data non valida
     * e, in caso positivo, aggiunge l'evento al catalogo.
     *
     * @param titolo Il titolo dell'evento da creare.
     * @param descrizione Una descrizione dettagliata dell'evento.
     * @param data La data in cui si svolgerà l'evento.
     * @param ora L'orario in cui inizierà l'evento.
     * @param luogo Il luogo in cui si terrà l'evento.
     * @param costo Il costo richiesto per partecipare all'evento.
     * @param capienza Il numero massimo di partecipanti consentito per l'evento.
     * @param emailAmministratore L'email dell'amministratore che crea l'evento.
     * @throws RedundancyException Se l'evento esiste già o la data fornita è precedente alla data corrente.
     * @throws LoadingException Se si verifica un problema durante il caricamento delle informazioni necessarie.
     */
    public static void creaEvento(String titolo, String descrizione, LocalDate data, LocalTime ora, String luogo, int costo, int capienza, String emailAmministratore) throws RedundancyException,LoadingException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();

        if(catalogo.verificaValidita(titolo,data)){
            EntityAmministratore amministratore=piattaforma.cercaAmministratorePerEmail(emailAmministratore);
            EntityEvento evento= amministratore.creazioneEvento(titolo, descrizione, data, ora, luogo, costo, capienza);
            catalogo.aggiungiEvento(evento);
        }else{
            throw new RedundancyException("Evento già creato o ha una data precedente a quella corrente");
        }
    }

    /**
     * Recupera tutti gli eventi pubblicati da un amministratore identificato dall'indirizzo email fornito.
     * Per ogni evento pubblicato, vengono incluse informazioni come i biglietti venduti e, se applicabile,
     * il numero di partecipanti e l'elenco dei partecipanti sotto forma di DTO.
     *
     * @param email L'indirizzo email dell'amministratore di cui recuperare gli eventi pubblicati.
     * @return Una mappa in cui la chiave è un oggetto DTOEvento che rappresenta un evento, e il valore è
     *         un oggetto che contiene informazioni aggiuntive sull'evento (es. numero di biglietti venduti,
     *         numero di partecipanti, lista dei partecipanti).
     * @throws BigliettoNotFoundException Eccezione sollevata se non sono disponibili informazioni sui biglietti relativi agli eventi.
     */
    public static Map<DTOEvento, Object> consultaEventiPubblicati(String email)  throws BigliettoNotFoundException {
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