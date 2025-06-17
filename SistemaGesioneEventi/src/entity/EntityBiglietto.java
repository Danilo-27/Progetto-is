//test commento 1
package entity;

import DTO.DTOEvento;
import database.BigliettoDAO;
import exceptions.*;

/**
 * Rappresenta l'entità "Biglietto" nel sistema.
 * La classe gestisce le informazioni relative a un biglietto, incluse le sue
 * proprietà, lo stato e i legami con le entità correlate come evento e cliente.
 * Inoltre, fornisce metodi per la persistenza dei dati e la gestione dello stato del biglietto.
 */
public class EntityBiglietto {

    /**
     * Identifica univocamente un biglietto mediante una stringa.
     * Questo valore è utilizzato per distinguere un biglietto dagli altri
     * all'interno del sistema.
     */
    private String codiceUnivoco;
    /**
     * Rappresenta lo stato del biglietto, espresso come valore numerico.
     * Possono essere definiti valori specifici per indicare diversi stati,
     * come validità o obliterazione del biglietto.
     */
    private int stato;
    /**
     * Rappresenta l'evento associato al biglietto.
     * Contiene le informazioni relative all'evento a cui
     * il biglietto consente l'accesso.
     */
    private EntityEvento evento;
    /**
     * Rappresenta il cliente associato al biglietto. L'attributo memorizza
     * un oggetto di tipo {@code EntityCliente} per mantenere le informazioni
     * relative al proprietario del biglietto.
     */
    private EntityCliente cliente;

    /**
     * Costante che rappresenta lo stato "OBLITERATO" di un biglietto.
     * Indica che il biglietto è stato verificato e utilizzato per l'accesso a un evento.
     */
    public static final int OBLITERATO=1;
    /**
     * Costante che rappresenta lo stato "VALIDO" di un biglietto.
     * Utilizzata per identificare i biglietti che sono stati validati
     * e risultano pronti per l'uso.
     */
    public static final int VALIDO=0;


    /**
     * Costruttore di default per la classe EntityBiglietto.
     * Inizializza un'istanza vuota della classe senza impostare alcun valore
     * per gli attributi interni.
     */
    public EntityBiglietto() {}

    /**
     * Costruttore della classe EntityBiglietto che consente di creare un'istanza
     * del biglietto associandolo a un evento e a un cliente, con un identificativo univoco.
     *
     * @param codiceUnivoco il codice univoco identificativo del biglietto
     * @param evento l'evento associato al biglietto
     * @param cliente il cliente titolare del biglietto
     */
    public EntityBiglietto(String codiceUnivoco, EntityEvento evento, EntityCliente cliente) {
        this.codiceUnivoco = codiceUnivoco;
        this.evento = evento;
        this.cliente = cliente;
    }

    /**
     * Costruttore della classe EntityBiglietto che consente di inizializzare un'istanza
     * del biglietto assegnando i valori recuperati da un oggetto BigliettoDAO. Associa
     * il biglietto a un evento e a un cliente corrispondenti.
     *
     * @param biglietto Oggetto BigliettoDAO contenente le informazioni necessarie per
     *                  inizializzare l'istanza di EntityBiglietto, come codice univoco,
     *                  stato, ID dell'evento e ID del cliente associati.
     */
    public EntityBiglietto(BigliettoDAO biglietto) {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        this.codiceUnivoco=biglietto.getCodice_univoco();
        this.stato=biglietto.getStato();
        this.evento=catalogo.cercaEventoPerId(biglietto.getEventoId());
        this.cliente =piattaforma.cercaClientePerId(biglietto.getClienteId());
    }

    /**
     * Salva i dati del biglietto corrente nel database.
     * Viene utilizzato un oggetto {@code BigliettoDAO} per persistire i dati del biglietto,
     * inclusi stato, codice univoco, evento associato e cliente correlato.
     * Se il biglietto esiste già nel database, viene sollevata un'eccezione {@code RedundancyException}.
     *
     * @throws UpdateException se ci sono errori nella permenenza dei dati del biglietto
     */
    public void scriviSuDB() throws UpdateException{
        BigliettoDAO b = new BigliettoDAO();
        b.setStato(this.stato);
        b.setCodice_univoco(this.codiceUnivoco);
        b.setEventoId(this.evento.getId());
        b.setClienteId(this.cliente.getId());
        try {
            b.SalvaInDB();
        } catch (DBException e) {
            throw new UpdateException("Erorre Nella creazione del Biglietto");
        }
    }

    /**
     * Valida il biglietto impostandone lo stato come "OBLITERATO" e aggiornando i dati nello storage persistente.
     * Se il biglietto è già stato obliterato, solleva un'eccezione.
     *
     * @throws BigliettoConsumatoException se il biglietto è già stato obliterato.
     * @throws UpdateException se si verifica un errore durante l'aggiornamento dello stato del biglietto nel database.
     */
    public void validaBiglietto() throws BigliettoConsumatoException {
        if(this.getStato()==EntityBiglietto.OBLITERATO){
            throw new BigliettoConsumatoException("Biglietto già obliterato");
        }else{
            this.setStato(EntityBiglietto.OBLITERATO);
            BigliettoDAO dao = new BigliettoDAO();
            dao.setStato(this.stato);
            dao.setCodice_univoco(this.codiceUnivoco);
            try{
                dao.aggiornaInDB();
            }catch(DBException e){
                throw new UpdateException("Errore di aggiornamento del biglietto");
            }
        }
    }


    /**
     * Restituisce il nome del titolare del biglietto. Il nome è recuperato
     * dall'attributo cliente associato al biglietto.
     *
     * @return Il nome del titolare del biglietto.
     */
    public String getNome_titolare() {
        return this.cliente.getNome();
    }

    /**
     * Imposta il nome del titolare del biglietto, aggiornando l'informazione
     * relativa al cliente associato.
     *
     * @param nome_titolare il nome del titolare da assegnare al cliente associato al biglietto
     */
    public void setNome_titolare(String nome_titolare) {
        this.cliente.setNome(nome_titolare);
    }

    /**
     * Restituisce il codice univoco identificativo del biglietto.
     *
     * @return una stringa che rappresenta il codice univoco del biglietto.
     */
    public String getCodice_univoco() {
        return this.codiceUnivoco;
    }

    /**
     * Imposta il codice univoco del biglietto.
     *
     * @param codice_univoco il codice univoco da assegnare al biglietto
     */
    public void setCodice_univoco(String codice_univoco) {
        this.codiceUnivoco = codice_univoco;
    }

    /**
     * Restituisce lo stato corrente del biglietto.
     * Lo stato è rappresentato come un valore intero che indica la condizione del biglietto
     * (ad esempio OBLITERATO o VALIDO).
     *
     * @return lo stato del biglietto come valore intero.
     */
    public int getStato() {
        return stato;
    }

    /**
     * Imposta lo stato del biglietto con il valore specificato.
     *
     * @param stato il nuovo stato da assegnare al biglietto. Può rappresentare
     *              uno stato predefinito, come "obliterato" o "valido".
     */
    public void setStato(int stato) {
        this.stato = stato;
    }

    /**
     * Restituisce l'identificativo univoco (ID) del cliente associato al biglietto.
     *
     * @return l'ID del cliente associato a questo biglietto.
     */
    public int getClienteId() {
        return this.cliente.getId();
    }

    /**
     * Assegna un nuovo identificativo univoco al cliente associato al biglietto.
     *
     * @param clienteId l'identificativo univoco del cliente da impostare.
     */
    public void setClienteId(int clienteId) {
        this.cliente.setId(clienteId);
    }

    /**
     * Restituisce il titolo dell'evento associato al biglietto.
     *
     * @return una stringa contenente il titolo dell'evento.
     */
    public String getTitoloEvento() {
        return this.evento.getTitolo();
    }

    /**
     * Imposta il titolo dell'evento associato al biglietto.
     *
     * @param titolo Il titolo da assegnare all'evento.
     */
    public void setTitoloEvento(String titolo) {
        this.evento.setTitolo(titolo);
    }

    /**
     * Restituisce l'evento associato al biglietto.
     *
     * @return l'istanza di {@code EntityEvento} associata a questo biglietto.
     */
    public EntityEvento getEvento() {
        return evento;
    }

    /**
     * Restituisce il codice univoco identificativo dell'istanza del biglietto.
     *
     * @return il codice univoco del biglietto come stringa.
     */
    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    /**
     * Imposta il codice univoco identificativo per il biglietto.
     *
     * @param codiceUnivoco il codice univoco da assegnare al biglietto
     */
    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    /**
     * Imposta l'evento associato al biglietto.
     *
     * @param evento l'istanza di {@code EntityEvento} da associare a questo biglietto
     */
    public void setEvento(EntityEvento evento) {
        this.evento = evento;
    }

    /**
     * Restituisce il cliente associato a questa istanza di biglietto.
     *
     * @return un oggetto {@code EntityCliente} che rappresenta il cliente titolare del biglietto.
     */
    public EntityCliente getCliente() {
        return cliente;
    }

    /**
     * Imposta il cliente associato al biglietto.
     *
     * @param cliente un oggetto di tipo {@code EntityCliente} che rappresenta
     *                il cliente da associare a questo biglietto.
     */
    public void setCliente(EntityCliente cliente) {
        this.cliente = cliente;
    }
}
