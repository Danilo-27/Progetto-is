package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * per segnalare errori relativi al processo di acquisto all'interno del sistema.
 *
 * È utilizzata per gestire anomalie o situazioni impreviste che si verificano
 * durante l'esecuzione di operazioni di acquisto, fornendo un messaggio dettagliato
 * per diagnosticare e comunicare la natura dell'errore.
 */
public class AcquistoException extends Exception {
    /**
     * Costruisce una nuova eccezione AcquistoException con un messaggio di dettaglio specificato.
     * Questa eccezione viene utilizzata per segnalare errori relativi al processo di acquisto
     * all'interno del sistema.
     *
     * @param message il messaggio di dettaglio che descrive la natura dell'errore verificatosi
     *                durante il processo di acquisto.
     */
    public AcquistoException(String message) {
        super(message);
    }
}