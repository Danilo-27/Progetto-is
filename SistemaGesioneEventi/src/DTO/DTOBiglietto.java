package DTO;

import java.time.LocalDate;

/**
 * Rappresenta un Data Transfer Object (DTO) per gestire i dati relativi a un biglietto.
 * Questa classe viene utilizzata per trasferire informazioni come il codice univoco del biglietto,
 * lo stato, il titolo dell'evento e la data, ad esempio tra livelli di applicazione
 * o tra client e server.
 */
public class DTOBiglietto {
    /**
     * Identifica univocamente il biglietto all'interno del sistema.
     * Questo attributo è utilizzato per distinguere un biglietto dagli altri in maniera univoca.
     */
    private String codiceUnivoco;
    /**
     * Rappresenta lo stato del biglietto, indicato come intero.
     * Tipicamente utilizzato per distinguere i diversi stati possibili
     * (ad esempio: 0 per disponibile, 1 per prenotato, 2 per utilizzato, ecc.).
     */
    private int stato;
    /**
     * Rappresenta il titolo dell'evento associato al biglietto.
     */
    private String titoloEvento;
    /**
     * Rappresenta la data associata al biglietto, tipicamente coincidente
     * con la data dell'evento a cui il biglietto si riferisce.
     */
    private LocalDate data;

    /**
     * Costruisce un'istanza della classe DTOBiglietto con valori predefiniti.
     * Questo costruttore vuoto è utile per la creazione di oggetti senza
     * inizializzare immediatamente gli attributi o per scopi di serializzazione.
     */
    public DTOBiglietto() {
    }

    /**
     * Costruttore della classe DTOBiglietto che istanzia un oggetto con i valori
     * specificati per codice univoco, stato del biglietto, titolo dell'evento
     * e data dell'evento.
     *
     * @param codiceUnivoco il codice identificativo univoco del biglietto
     * @param stato lo stato del biglietto rappresentato come un intero
     * @param titoloEvento il titolo dell'evento associato al biglietto
     * @param data la data dell'evento associata al biglietto
     */
    public DTOBiglietto(String codiceUnivoco, int stato, String titoloEvento, LocalDate data) {
        this.codiceUnivoco = codiceUnivoco;
        this.stato = stato;
        this.titoloEvento = titoloEvento;
        this.data = data;
    }

    /**
     * Restituisce il codice univoco che identifica il biglietto all'interno del sistema.
     *
     * @return una stringa contenente il codice univoco del biglietto.
     */
    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    /**
     * Imposta il codice univoco del biglietto, che serve ad identificarlo in modo unico nel sistema.
     *
     * @param codiceUnivoco il codice univoco da assegnare al biglietto
     */
    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    /**
     * Restituisce lo stato del biglietto come un valore intero.
     * Lo stato viene utilizzato per rappresentare diverse condizioni o fasi del biglietto,
     * ad esempio disponibile, prenotato o utilizzato.
     *
     * @return lo stato del biglietto rappresentato come un numero intero.
     */
    public int getStato() {
        return stato;
    }

    /**
     * Imposta lo stato del biglietto.
     * Lo stato è rappresentato come un valore intero, tipicamente utilizzato
     * per indicare i diversi stati possibili del biglietto
     * (ad esempio: 0 per disponibile, 1 per prenotato, 2 per utilizzato, ecc.).
     *
     * @param stato il nuovo stato del biglietto, rappresentato da un valore intero.
     */
    public void setStato(int stato) {
        this.stato = stato;
    }

    /**
     * Restituisce il titolo dell'evento associato al biglietto.
     *
     * @return una stringa contenente il titolo dell'evento.
     */
    public String getTitoloEvento() {
        return titoloEvento;
    }

    /**
     * Imposta il titolo dell'evento associato al biglietto.
     *
     * @param titoloEvento Il titolo dell'evento da assegnare al biglietto.
     *                     Non può essere null e deve rappresentare una descrizione valida dell'evento.
     */
    public void setTitoloEvento(String titoloEvento) {
        this.titoloEvento = titoloEvento;
    }

    /**
     * Restituisce la data associata al biglietto, tipicamente coincidente
     * con la data dell'evento a cui il biglietto si riferisce.
     *
     * @return la data del biglietto come oggetto di tipo LocalDate.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Imposta la data associata al biglietto.
     *
     * @param data la nuova data da assegnare al biglietto, rappresentata come un oggetto LocalDate
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

}