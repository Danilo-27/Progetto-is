package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * per indicare un problema di ridondanza all'interno del sistema.
 * Viene tipicamente utilizzata per segnalare situazioni in cui un'entità
 * o un elemento è già presente e non è consentita la duplicazione.
 *
 * Estendendo {@code RuntimeException}, questa eccezione è unchecked,
 * il che significa che non è obbligatorio gestirla esplicitamente nel codice.
 */
public class RedundancyException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione RedundancyException con un messaggio di dettaglio specificato.
     * Questa eccezione viene utilizzata per segnalare un errore di ridondanza, ad esempio quando un'entità
     * è già presente nel sistema e non può essere duplicata.
     *
     * @param message il messaggio di dettaglio che descrive il motivo per cui l'eccezione è stata sollevata.
     */
    public RedundancyException(String message) {
        super(message);
    }
}
