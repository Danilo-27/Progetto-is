package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * quando si verifica un errore durante l'aggiornamento di dati o informazioni.
 *
 * Estendendo {@code RuntimeException}, questa eccezione è unchecked, il che
 * significa che non è obbligatorio gestirla esplicitamente nel codice.
 *
 * Viene tipicamente utilizzata per segnalare problemi specifici legati a
 * fallimenti o anomalie nei processi di aggiornamento.
 */
public class UpdateException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione UpdateException con un messaggio di dettaglio specificato.
     * Questa eccezione viene utilizzata per segnalare errori verificatisi durante
     * il processo di aggiornamento di un'entità o di informazioni nel sistema.
     *
     * @param message il messaggio di dettaglio che descrive la natura del problema
     *                riscontrato durante l'aggiornamento.
     */
    public UpdateException(String message) {
        super(message);
    }
}
