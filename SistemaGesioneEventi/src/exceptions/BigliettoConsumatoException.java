package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * per indicare che un biglietto è stato già consumato e non è più valido.
 *
 * L'eccezione può essere utilizzata per gestire situazioni in cui si tenta
 * di utilizzare un biglietto che non è più disponibile o valido in un sistema
 * di gestione di biglietti.
 */
public class BigliettoConsumatoException extends Exception {
    /**
     * Costruisce una nuova eccezione BigliettoConsumatoException con un messaggio di dettaglio specificato.
     * Questa eccezione viene sollevata per indicare che un biglietto è stato già consumato e non è più valido.
     *
     * @param message il messaggio di dettaglio che descrive il motivo per cui l'eccezione è stata sollevata.
     */
    public BigliettoConsumatoException(String message) {
        super(message);
    }
}