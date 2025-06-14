package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * quando viene rilevato un tipo di utente non autorizzato o non valido nel sistema.
 * Viene utilizzata per gestire errori specifici legati al tipo di utente durante
 * l'esecuzione di operazioni nell'applicazione.
 */
public class WrongUserTypeException extends Exception {
    /**
     * Costruisce una nuova WrongUserTypeException con il messaggio di dettaglio specificato.
     * Questa eccezione viene sollevata quando viene rilevato un tipo di utente non autorizzato.
     *
     * @param message il messaggio di dettaglio che fornisce ulteriori informazioni
     *                sull'eccezione.
     */
    public WrongUserTypeException(String message) {
        super(message);
    }
}