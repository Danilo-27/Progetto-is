//test commento 1
package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import database.EventoDAO;
import exceptions.DBException;
import exceptions.EventoNotFoundException;
import exceptions.LoadingException;
import exceptions.RedundancyException;

/**
 * La classe EntityCatalogo rappresenta un catalogo unico contenente una collezione di eventi.
 * La classe segue il pattern Singleton per garantire l'esistenza di una sola istanza condivisa
 * del catalogo, permettendo di gestire tutte le operazioni relative agli eventi e alle loro interazioni.
 * Include funzionalità per aggiungere, cercare, filtrare e consultare gli eventi del catalogo.
 */
public class EntityCatalogo {

    /**
     * Riferimento alla singola istanza della classe {@code EntityCatalogo},
     * implementando il pattern Singleton per garantire che esista
     * un'unica istanza condivisa all'interno dell'applicazione.
     */
    private static EntityCatalogo uniqueInstance;
    /**
     * Rappresenta la lista degli eventi presenti nel catalogo.
     * Questo attributo contiene oggetti di tipo {@code EntityEvento}
     * e rappresenta l'insieme dei dati gestiti dalla classe {@code EntityCatalogo}.
     */
    private final List<EntityEvento> eventi;

    /**
     * Costruttore privato della classe EntityCatalogo.
     * Inizializza il catalogo degli eventi caricandoli dal database tramite il DAO di riferimento.
     * Se si verifica un errore durante il caricamento dei dati dal database, viene sollevata
     * un'eccezione di tipo {@code LoadingException}.
     *
     * @throws LoadingException se il processo di caricamento degli eventi dal database fallisce.
     */
    private EntityCatalogo() throws LoadingException {
        eventi = new ArrayList<>();
        try{
            for (EventoDAO evento : EventoDAO.getEventi()) {
                eventi.add(new EntityEvento(evento));
            }
        }catch(DBException e){
            throw new LoadingException("Errore nel caricamento degli eventi");
        }
    }

    /**
     * Restituisce l'istanza singleton di {@code EntityCatalogo}. Se l'istanza non è stata
     * ancora inizializzata, viene creata una nuova istanza della classe e caricati
     * gli eventi dal database. Questo metodo garantisce che venga utilizzata sempre
     * la stessa istanza dell'oggetto.
     *
     * @return l'istanza singleton di {@code EntityCatalogo}.
     * @throws LoadingException se si verifica un errore durante il caricamento degli eventi dal database.
     */
    public static EntityCatalogo getInstance() throws LoadingException {
        if (uniqueInstance == null) {
            uniqueInstance = new EntityCatalogo();
        }
        return uniqueInstance;
    }

    /**
     * Aggiunge un nuovo evento al catalogo e lo salva sul database..
     *
     * @param entityEvento l'evento da aggiungere al catalogo. Deve essere un' istanza di EntityEvento.
     * @throws RedundancyException Se l'evento è già presente nel catalogo.
     */
    public void aggiungiEvento(EntityEvento entityEvento) throws RedundancyException {
        entityEvento.salvaSuDB();
        eventi.add(entityEvento);
    }

    /**
     * Cerca un evento nel catalogo tramite il suo identificativo univoco.
     *
     * @param id l'identificativo univoco dell'evento da cercare.
     * @return l'evento con l'id specificato, o null se non viene trovato nessun evento con l'id dato.
     */
    public EntityEvento cercaEventoPerId(int id) {
        EntityEvento Evento = null;
        for (EntityEvento evento : this.eventi) {
            if (evento.getId() == id) Evento = evento;
        }
        return Evento;
    }

    /**
     * Cerca un evento nel catalogo tramite il suo titolo.
     *
     * @param titolo il titolo dell'evento da cercare
     * @return l'evento corrispondente al titolo dato, o null se non viene trovato nessun evento con il titolo specificato
     */
    public EntityEvento cercaEventoPerTitolo(String titolo) {
        EntityEvento Evento = null;
        for (EntityEvento evento : this.eventi) {
            if (Objects.equals(evento.getTitolo(), titolo)) Evento = evento;
        }
        return Evento;
    }


    /**
     * Recupera una lista di eventi validi dal catalogo. Gli eventi validi sono quelli
     * che si verificano alla data corrente o successivamente.
     *
     * @return una lista contenente gli oggetti EntityEvento validi dal catalogo.
     */
    public List<EntityEvento> ConsultaCatalogo() throws EventoNotFoundException {
        LocalDate oggi = LocalDate.now();
        List<EntityEvento> eventiValidi = new ArrayList<>();
        for (EntityEvento evento : eventi) {
            if (evento.getData().isAfter(oggi) || evento.getData().isEqual(oggi)) {
                eventiValidi.add(evento);
            }
        }
        if(eventiValidi.isEmpty()){
            throw new EventoNotFoundException("Non ci sono eventi disponibili");
        }
        return eventiValidi;
    }


    /**
     * Cerca eventi nel catalogo in base al titolo, data e luogo specificati.
     * Se non vengono trovati eventi che corrispondono ai criteri, viene lanciata una {@code EventoNotFoundException}.
     *
     * @param titolo il titolo dell'evento da cercare, o {@code null} per corrispondere a qualsiasi titolo
     * @param data   la data dell'evento da cercare, o {@code null} per corrispondere a qualsiasi data
     * @param luogo  il luogo dell'evento da cercare, o {@code null} per corrispondere a qualsiasi luogo
     * @return una lista di oggetti {@code EntityEvento} che corrispondono ai criteri di ricerca
     * @throws EventoNotFoundException se nessun evento corrisponde ai criteri specificati
     */
    public List<EntityEvento> ricercaEvento(String titolo, LocalDate data, String luogo) throws EventoNotFoundException {
        List<EntityEvento> risultati = new ArrayList<>();
        for (EntityEvento evento : this.ConsultaCatalogo()) {
            boolean match = titolo == null || evento.getTitolo().equalsIgnoreCase(titolo);
            if (data != null && !evento.getData().equals(data)) {
                match = false;
            }
            if (luogo != null && !evento.getLuogo().equalsIgnoreCase(luogo)) {
                match = false;
            }
            if (match) {
                risultati.add(evento);
            }
        }
        if (risultati.isEmpty()) {
            throw new EventoNotFoundException("Nessun evento trovato");
        }
        return risultati;
    }

    /**
     * Recupera tutti gli eventi pubblicati dall'amministratore specificato.
     *
     * @param amministratore l'amministratore di cui recuperare gli eventi pubblicati.
     * @return un ArrayList di istanze EntityEvento pubblicate dall'amministratore specificato.
     */
    public ArrayList<EntityEvento> getEventiPubblicati(EntityAmministratore amministratore) {
        ArrayList<EntityEvento> risultati = new ArrayList<>();
        for (EntityEvento evento : this.eventi) {
            if (evento.getAmministratore() == amministratore) {
                risultati.add(evento);
            }
        }
        return risultati;
    }

    /**
     * Verifica la validità di un evento controllando se esiste un evento con il titolo specificato
     *
     * @param titolo il titolo dell'evento da verificare.
     * @return {@code true} se l'evento con il titolo specificato esiste
     */
    public boolean verificaValidita(String titolo){
        return this.cercaEventoPerTitolo(titolo) == null;
    }
}
