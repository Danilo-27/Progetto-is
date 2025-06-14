package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * quando si verifica un errore durante un processo di caricamento.
 *
 * Estendendo {@code RuntimeException}, questa eccezione è unchecked, il che
 * significa che non è necessario gestirla obbligatoriamente nel codice.
 *
 * Può essere utilizzata per segnalare problemi legati al caricamento di risorse,
 * file, dati o altre operazioni simili in cui il caricamento non riesce.
 */
public class LoadingException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione LoadingException con un messaggio di dettaglio specificato.
     * Questa eccezione viene sollevata per indicare un errore durante un processo di caricamento,
     * come il caricamento di risorse, file o dati che non è andato a buon fine.
     *
     * @param message il messaggio di dettaglio che descrive la natura dell'errore di caricamento.
     */
    public LoadingException(String message) {
        super(message);
    }
}
