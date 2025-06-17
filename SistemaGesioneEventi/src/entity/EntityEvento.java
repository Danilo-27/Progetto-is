//test commento 1
package entity;


import DTO.DTODatiPagamento;
import database.BigliettoDAO;
import database.EventoDAO;
import exceptions.*;
import external.PagamentoService;

import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * La classe EntityEvento rappresenta un'entità che incapsula tutte le informazioni
 * relative a un evento. È progettata per essere un modello di dominio che interagisce
 * con DAO (Data Access Object) per gestire operazioni di persistenza sul database.
 * Offre metodi per la creazione, il recupero, l'aggiornamento e la gestione degli eventi,
 * inclusi la gestione dei biglietti, dei partecipanti e della capienza.
 */
public class EntityEvento {

    /**
     * Identificativo univoco dell'evento.
     * Questo valore è utilizzato per distinguere e identificare univocamente un'entità di tipo evento.
     */
    private int id;
    /**
     * Rappresenta il titolo dell'evento.
     * Indica il nome o la denominazione che identifica univocamente l'evento.
     */
    private String titolo;
    /**
     * Descrizione dettagliata dell'evento. Utilizzata per fornire informazioni aggiuntive
     * riguardo al contenuto o allo scopo dell'evento.
     */
    private String descrizione;
    /**
     * Rappresenta la data in cui si terrà l'evento.
     */
    private LocalDate data;
    /**
     * Rappresenta l'orario di un evento.
     * Questa variabile memorizza l'orario specifico in cui l'evento è programmato.
     */
    private LocalTime ora;
    /**
     * Rappresenta il luogo in cui si svolge l'evento.
     */
    private String luogo;
    /**
     * Rappresenta il costo associato all'evento in termini monetari.
     * Espresso come valore intero che rappresenta l'importo del costo.
     */
    private int costo;
    /**
     * Indica la capienza massima di partecipanti consentita per l'evento.
     */
    private int capienza;
    /**
     * Campo che indica il numero di partecipanti attualmente registrati per l'evento.
     */
    private int partecipanti;
    /**
     * Identificativo univoco associato all'amministratore responsabile dell'evento.
     * Utilizzato per correlare l'evento a un oggetto EntityAmministratore.
     */
    private int amministratore_id;
    /**
     * Rappresenta la lista di biglietti associati a un evento.
     * Ogni elemento della lista è un'istanza di EntityBiglietto.
     */
    private ArrayList<EntityBiglietto> biglietti;
    /**
     * Rappresenta l'amministratore associato all'evento.
     * Questo attributo contiene un'istanza di EntityAmministratore che identifica
     * l'amministratore responsabile della gestione dell'evento.
     */
    private EntityAmministratore amministratore;

    /**
     * Costruttore che inizializza un'istanza di EntityEvento utilizzando i dati forniti da un oggetto EventoDAO.
     * Popola i dettagli essenziali dell'evento, associa l'amministratore corrispondente e inizializza la lista dei biglietti.
     *
     * @param evento l'oggetto EventoDAO contenente i dati relativi all'evento da inizializzare
     */
    public EntityEvento(EventoDAO evento) {
        inizializzaDaEventoDAO(evento);
        EntityPiattaforma ep = EntityPiattaforma.getInstance();
        this.amministratore=ep.cercaAmministratorePerId(evento.getAmministratoreId());
        this.biglietti = new ArrayList<>();
    }


    /**
     * Costruttore che crea una nuova istanza della classe EntityEvento con i dettagli specificati.
     * Questo costruttore inizializza le informazioni dell'evento, associa un amministratore
     * responsabile e imposta la lista dei biglietti come vuota.
     *
     * @param titolo il titolo dell'evento
     * @param descrizione una descrizione dell'evento
     * @param data la data in cui si terrà l'evento
     * @param ora l'orario dell'evento
     * @param luogo il luogo in cui si svolgerà l'evento
     * @param costo il costo di partecipazione all'evento
     * @param capienza il numero massimo di partecipanti consentiti per l'evento
     * @param amministratore l'entità EntityAmministratore responsabile dell'evento
     */
    public EntityEvento(String titolo, String descrizione, LocalDate data, LocalTime ora,
                       String luogo, int costo, int capienza, EntityAmministratore amministratore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.ora = ora;
        this.luogo = luogo;
        this.costo = costo;
        this.capienza = capienza;
        this.partecipanti = 0;
        this.biglietti = new ArrayList<>();
        this.amministratore = amministratore;
    }

    /**
     * Costruttore della classe EntityEvento che inizializza un'entità evento
     * sulla base del titolo fornito e carica i biglietti associati dall'archivio.
     *
     * @param titolo Il titolo dell'evento da utilizzare per il caricamento delle informazioni.
     * @throws BigliettoNotFoundException Se non è possibile trovare i biglietti associati all'evento nel database.
     */
    public EntityEvento(String titolo) throws BigliettoNotFoundException{
        EventoDAO evento = new EventoDAO(titolo);
        inizializzaDaEventoDAO(evento);
        this.biglietti = new ArrayList<>();
        try {
            evento.caricaBigliettiEventiDaDB();
        } catch (DBException e) {
            throw new BigliettoNotFoundException("Biglietto non trovato.");
        }
        this.caricaBiglietti(evento);
    }


    /**
     * Inizializza i campi dell'oggetto corrente utilizzando i dati forniti da un'istanza di {@code EventoDAO}.
     *
     * @param evento Un'istanza di {@code EventoDAO} da cui vengono recuperati i dati
     *               per popolare i campi dell'oggetto corrente.
     */
    private void inizializzaDaEventoDAO(EventoDAO evento) {
        this.id = evento.getId();
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.data = evento.getData();
        this.ora = evento.getOra();
        this.luogo = evento.getLuogo();
        this.costo = evento.getCosto();
        this.capienza = evento.getCapienza();
        this.amministratore_id=evento.getAmministratoreId();
    }

    /**
     * Salva nel database l'istanza corrente dell'evento.
     * Questo metodo crea un oggetto EventoDAO e inizializza le sue proprietà
     * in base agli attributi dell'istanza corrente dell'evento.
     * Tenta poi di salvare l'evento nel database tramite il metodo SalvaInDB di EventoDAO.
     * Se l'evento già esiste nel database viene lanciata una RedundancyException.
     *
     * @throws RedundancyException nel caso in cui l'evento da salvare sia già presente nel DB
     */
    public void salvaSuDB() throws RedundancyException {
        EventoDAO evento = new EventoDAO();
        evento.setTitolo(titolo);
        evento.setDescrizione(descrizione);
        evento.setData(data);
        evento.setOra(ora);
        evento.setLuogo(luogo);
        evento.setCosto(costo);
        evento.setCapienza(capienza);
        evento.setPartecipanti(partecipanti);
        evento.setAmministratoreId(amministratore.getId());
        try {
            evento.SalvaInDB();
        } catch (DBException e) {
            throw new RedundancyException("Evento già creato");
        }
    }

    /**
     * Aggiorna il numero dei partecipanti per l'evento corrente e riflette il cambiamento nel database.
     * Questo metodo incrementa il numero di partecipanti dell'evento e aggiorna il relativo record
     * nel database utilizzando un oggetto EventoDAO. Se si verifica un errore durante l'aggiornamento,
     * viene lanciata una UpdateException.
     *
     * @throws UpdateException se si verifica un problema nell'aggiornamento dell'evento nel database
     */
    public void aggiornaPartecipanti() throws UpdateException {
        this.partecipanti++;
        EventoDAO dao = new EventoDAO();
        dao.setTitolo(this.titolo);
        dao.setPartecipanti(this.partecipanti);
        try{
            dao.AggiornaInDB();
        }catch(DBException e){
            throw new UpdateException("Errore nell'aggiornamento");
        }

    }

    /**
     * Genera un ID univoco per un evento mediante la combinazione di un prefisso derivato dal titolo e una
     * componente casuale generata utilizzando un UUID.
     * Il prefisso è costituito dai primi tre caratteri alfanumerici del titolo, convertiti in maiuscolo e
     * privati di eventuali caratteri non alfanumerici. Se il titolo contiene meno di tre caratteri, viene
     * usata l'intera stringa disponibile. La parte casuale è composta dai primi otto caratteri di un UUID
     * senza separatori.
     *
     * @return una stringa che rappresenta l'ID univoco generato nel formato "PREFISSO-UUIDPARTE".
     */
    
    private String creazioneIDUnivoco(){
        String eventoSanificato = this.titolo.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        String prefisso = eventoSanificato.substring(0, Math.min(3, eventoSanificato.length()));
        String uuidParte = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 8);
        return prefisso + "-" + uuidParte;
    }

    /**
     * Cerca un EntityBiglietto nella lista dei biglietti associati all'evento corrente,
     * utilizzando un dato codice univoco. Se viene trovato un biglietto con il codice specificato,
     * questo viene restituito; altrimenti, viene restituito null.
     *
     * @param codice il codice univoco del biglietto da cercare
     * @return l'EntityBiglietto corrispondente se trovato, o null se non esiste un biglietto corrispondente
     */
    private EntityBiglietto verificaCodice(String codice){
        for (EntityBiglietto b : this.biglietti) {
            if (b.getCodice_univoco().equals(codice)) {
                return b;
            }
        }
        return null;
    }

    public void partecipaEvento(String codiceUnivoco) throws BigliettoConsumatoException,BigliettoNotFoundException{
        EntityBiglietto biglietto = this.verificaCodice(codiceUnivoco);
        if(biglietto == null) {
            throw new BigliettoNotFoundException("Biglietto non trovato");
        }else {
            biglietto.validaBiglietto();
            this.aggiornaPartecipanti();
        }
    }

    public void acquistoBiglietto(EntityCliente cliente, PagamentoService ps, DTODatiPagamento dtoDatiPagamento) throws AcquistoException {
        if (cliente.haBigliettoPerEvento(this))
            throw new RedundancyException("Acquisto già effettuato");

        if (!this.verificaDisponibilità())
            throw new AcquistoException("Posti non disponibili");

        PagamentoService.EsitoPagamento esito = ps.elaboraPagamento(dtoDatiPagamento.getNumeroCarta(), dtoDatiPagamento.getNomeTitolare(), dtoDatiPagamento.getCognomeTitolare(), dtoDatiPagamento.getDataScadenza(), this.getCosto());

        if (esito == PagamentoService.EsitoPagamento.SUCCESSO)
            this.creaBiglietto(cliente);
        else
            throw new AcquistoException("Pagamento fallito " + esito.name().replace("_", " ").toLowerCase());
    }

    /**
     * Crea un nuovo biglietto associato all'evento corrente e a un cliente specificato.
     * Genera un codice univoco per il biglietto, associa l'evento e il cliente al biglietto,
     * e archivia il biglietto nel database.
     *
     * @param cliente l'entità del cliente a cui il biglietto verrà associato
     */
    private void creaBiglietto(EntityCliente cliente) throws UpdateException{
        String codiceUnivoco = creazioneIDUnivoco();
        EntityBiglietto biglietto = new EntityBiglietto(codiceUnivoco,this,cliente);
        biglietto.scriviSuDB();
        this.biglietti.add(biglietto);
        cliente.getBiglietti().add(biglietto);
    }

    /**
     * Verifica la disponibilità di posti per l'evento corrente confrontando la capienza massima
     * con il numero di biglietti già emessi.
     *
     * @return true se ci sono ancora posti disponibili per l'evento, altrimenti false
     */
    private boolean verificaDisponibilità(){
        return this.capienza>this.biglietti.size();
    }

    /**
     * Carica i biglietti associati a un evento specificato tramite l'oggetto EventoDAO e li memorizza
     * in una lista di oggetti EntityBiglietto. Se l'evento specificato non contiene biglietti, viene
     * lanciata un'eccezione BigliettoNotFoundException.
     *
     * @param eventoDAO l'oggetto EventoDAO da cui recuperare i biglietti associati all'evento
     * @throws BigliettoNotFoundException se non sono presenti biglietti associati all'evento nel database
     */
    public void caricaBiglietti(EventoDAO eventoDAO) throws BigliettoNotFoundException{
        this.biglietti = new ArrayList<>();
        if(eventoDAO.getBiglietti() !=null){
            for (BigliettoDAO bigliettoDAO : eventoDAO.getBiglietti()) {
                EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO);
                this.biglietti.add(biglietto);
            }
        }else{
            throw new BigliettoNotFoundException("Biglietti assenti nel DB.");
        }

    }

    public void caricaBiglietti() throws BigliettoNotFoundException {
        EventoDAO eventoDAO = new EventoDAO(this.titolo);
        this.biglietti.clear();
        try {
            eventoDAO.caricaBigliettiEventiDaDB();
        } catch (DBException e) {
            System.out.println("nessun biglietto venduto");
        }
        if(eventoDAO.getBiglietti() !=null){
            for (BigliettoDAO bigliettoDAO : eventoDAO.getBiglietti()) {
                EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO);
                this.biglietti.add(biglietto);
            }
        }else{
            throw new BigliettoNotFoundException("Biglietti assenti nel DB.");
        }

    }

    /**
     * Restituisce una lista di partecipanti associati all'evento corrente.
     * Un partecipante è identificato come cliente il cui biglietto
     * relativo all'evento risulta obliterato.
     * Durante l'esecuzione, i biglietti dell'evento vengono caricati
     * dal database, e successivamente vengono filtrati in base al loro stato.
     * Viene lanciata un'eccezione se non sono presenti biglietti associati
     * all'evento nel database.
     *
     * @return una lista di oggetti EntityCliente che rappresentano i partecipanti all'evento
     * @throws BigliettoNotFoundException se non sono presenti biglietti per l'evento nel database
     */
    public ArrayList<EntityCliente> listaPartecipanti() throws BigliettoNotFoundException{
        //carico in memoria tutti i biglietti associati all'evento
        EventoDAO eventoDAO = new EventoDAO(this.titolo);
        try {
            eventoDAO.caricaBigliettiEventiDaDB();
        } catch (DBException e) {
            throw new BigliettoNotFoundException("Biglietti non presenti nel DB.");
        }
        this.caricaBiglietti(eventoDAO);
        //a questo punto per ogni biglietto conosco il codice univoco e stato
        ArrayList<EntityCliente> partecipanti=new ArrayList<>();
        for (EntityBiglietto biglietto : this.biglietti) {
            if(biglietto.getStato()==EntityBiglietto.OBLITERATO){
                partecipanti.add(biglietto.getCliente());
            }
        }
        return partecipanti;
    }

    /**
     * Restituisce il numero totale di partecipanti associati all'evento corrente.
     * I partecipanti vengono determinati tramite la lista di clienti che hanno biglietti validi
     * per l'evento.
     *
     * @return il numero di partecipanti all'evento
     * @throws BigliettoNotFoundException se non sono presenti biglietti per l'evento nel database
     */
    public int getNumeroPartecipanti() throws BigliettoNotFoundException {
        ArrayList<EntityCliente> partecipanti = listaPartecipanti();
        return partecipanti.size();
    }

    /**
     * Restituisce il numero totale di biglietti venduti associati all'evento corrente.
     *
     * @return il numero totale di biglietti venduti
     */
    public int getNumeroBigliettiVenduti() {
        return this.biglietti.size();
    }


    /**
     * Restituisce il titolo associato a questa istanza.
     *
     * @return il titolo come stringa.
     */
    public String getTitolo() {
        return titolo;
    }
    /**
     * Restituisce la descrizione associata.
     *
     * @return la descrizione come stringa.
     */
    public String getDescrizione() {
        return descrizione;
    }
    /**
     * Restituisce la data associata all'oggetto.
     *
     * @return la data come oggetto {@link LocalDate}
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Restituisce l'oggetto LocalTime che rappresenta l'ora corrente o un'ora specifica.
     *
     * @return l'oggetto LocalTime associato all'ora gestita.
     */
    public LocalTime getOra() {
        return ora;
    }

    /**
     * Restituisce il valore del luogo associato.
     *
     * @return Una stringa che rappresenta il luogo.
     */
    public String getLuogo() {
        return luogo;
    }

    /**
     * Restituisce la capienza attuale.
     *
     * @return un valore intero che rappresenta la capienza.
     */
    public int getCapienza() {
        return capienza;
    }

    /**
     * Restituisce il numero di partecipanti associati.
     *
     * @return il numero di partecipanti.
     */
    public int getPartecipanti() {
        return partecipanti;
    }

   /**
    * Recupera l'elenco di tutti i biglietti disponibili.
    *
    * @return una lista di oggetti {@code EntityBiglietto} che rappresenta i biglietti.
    */
   public ArrayList<EntityBiglietto> getBiglietti() {
        return biglietti;
   }

    /**
     * Imposta il valore del titolo.
     *
     * @param titolo Il nuovo titolo da assegnare.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Imposta la descrizione dell'oggetto.
     *
     * @param descrizione la descrizione da assegnare all'oggetto
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Imposta il valore della data.
     *
     * @param data la data da assegnare
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Imposta l'ora specificata per l'oggetto.
     *
     * @param ora un oggetto {@code LocalTime} che rappresenta l'ora da assegnare
     */
    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    /**
     * Imposta il valore del luogo.
     *
     * @param luogo Il nome del luogo da assegnare.
     */
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    /**
     * Imposta la capienza per l'oggetto corrente.
     *
     * @param capienza il valore della capienza da assegnare, rappresentato come un intero
     */
    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    /**
     * Imposta il numero di partecipanti.
     *
     * @param partecipanti Il numero di partecipanti da assegnare.
     */
    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }


    /**
     * Restituisce il valore del costo associato.
     *
     * @return il valore del costo come intero.
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Imposta il costo con il valore specificato.
     *
     * @param costo il valore intero da assegnare al costo
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }




    /**
     * Restituisce l'identificatore univoco associato a questa istanza.
     *
     * @return l'identificatore univoco di tipo intero.
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce l'identificativo univoco dell'amministratore.
     *
     * @return l'ID dell'amministratore come valore intero.
     */
    public int getAmministratore_id() {
        return amministratore_id;
    }


    /**
     * Restituisce l'istanza di EntityAmministratore associata.
     *
     * @return l'oggetto EntityAmministratore attualmente gestito.
     */
    public EntityAmministratore getAmministratore() {
        return amministratore;
    }

    /**
     * Imposta l'oggetto amministratore associato.
     *
     * @param amministratore l'istanza di EntityAmministratore da assegnare
     *                       come amministratore.
     */
    public void setAmministratore(EntityAmministratore amministratore) {
        this.amministratore = amministratore;
    }

    /**
     * Imposta la lista dei biglietti.
     *
     * @param biglietti La lista di oggetti di tipo EntityBiglietto da assegnare.
     */
    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.biglietti = biglietti;
    }

}