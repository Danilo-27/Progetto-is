//test commento 1
package entity;


import database.BigliettoDAO;
import database.EventoDAO;
import exceptions.BigliettoNotFoundException;
import exceptions.DBException;
import exceptions.RedundancyException;
import exceptions.UpdateException;

import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EntityEvento {

    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int costo;
    private int capienza;
    private int partecipanti;
    private int amministratore_id;
    private ArrayList<EntityBiglietto> biglietti;
    private EntityAmministratore amministratore;

    /**
     * Costruttore che crea un EntityEvento a partire da un EventoDAO
     */
    public EntityEvento(EventoDAO evento) {
        inizializzaDaEventoDAO(evento);
        EntityPiattaforma ep =EntityPiattaforma.getInstance();
        this.amministratore=ep.cercaAmministratorePerId(evento.getAmministratoreId());
        this.biglietti = new ArrayList<>();
    }

    /**
     * Costruttore per creare un nuovo evento
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
     * Costruttore che carica un evento dal database dato il titolo
     */
    public EntityEvento(String titolo) throws BigliettoNotFoundException{
        EventoDAO evento = new EventoDAO(titolo);
        inizializzaDaEventoDAO(evento);
        this.biglietti = new ArrayList<>();
        try {
            evento.caricaBigliettiEventiDaDB();
        } catch (DBException _) {
            throw new BigliettoNotFoundException("Biglietto non trovato.");
        }
        this.caricaBiglietti(evento);
    }


    /**
     * Inizializza gli attributi dell'istanza EntityEvento utilizzando i dati forniti dall'oggetto EventoDAO.
     *
     * @param evento l'oggetto EventoDAO contenente i dati per inizializzare questa istanza
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
        } catch (DBException _) {
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
        }catch(DBException _){
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
    public EntityBiglietto verificaCodice(String codice){
        for (EntityBiglietto b : this.biglietti) {
            if (b.getCodice_univoco().equals(codice)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Crea un nuovo biglietto associato all'evento corrente e a un cliente specificato.
     * Genera un codice univoco per il biglietto, associa l'evento e il cliente al biglietto,
     * e archivia il biglietto nel database.
     *
     * @param cliente l'entità del cliente a cui il biglietto verrà associato
     * @return un'entità EntityBiglietto che rappresenta il biglietto creato
     */
    public EntityBiglietto creaBiglietto(EntityCliente cliente) throws RedundancyException{
        String codiceUnivoco = creazioneIDUnivoco();
        EntityBiglietto biglietto = new EntityBiglietto(codiceUnivoco,this,cliente);
        biglietto.scriviSuDB();
        return biglietto;
    }

    /**
     * Verifica la disponibilità di posti per l'evento corrente confrontando la capienza massima
     * con il numero di biglietti già emessi.
     *
     * @return true se ci sono ancora posti disponibili per l'evento, altrimenti false
     */
    public boolean verificaDisponibilità(){
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
        } catch (DBException _) {
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


    public String getTitolo() {
        return titolo;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public String getLuogo() {
        return luogo;
    }

    public int getCapienza() {
        return capienza;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

   public ArrayList<EntityBiglietto> getBiglietti() {
        return biglietti;
   }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }


    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }




    public int getId() {
        return id;
    }

    public int getAmministratore_id() {
        return amministratore_id;
    }


    public EntityAmministratore getAmministratore() {
        return amministratore;
    }

    public void setAmministratore(EntityAmministratore amministratore) {
        this.amministratore = amministratore;
    }

    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.biglietti = biglietti;
    }

    @Override
    public String toString() {
        return "EntityEvento{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", ora=" + ora +
                ", luogo='" + luogo + '\'' +
                ", costo=" + costo +
                ", capienza=" + capienza +
                ", partecipanti=" + partecipanti +
                ", amministratore_id=" + amministratore_id +
                ", biglietti=" + biglietti +
                ", amministratore=" + amministratore +
                '}';
    }
}