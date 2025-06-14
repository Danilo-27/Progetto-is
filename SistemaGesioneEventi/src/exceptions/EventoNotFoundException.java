package exceptions;

/**
 * Eccezione personalizzata che viene sollevata quando un evento specifico
 * non viene trovato all'interno del sistema o durante il recupero dei dati
 * relativi agli eventi.
 *
 * È tipicamente utilizzata per gestire casi in cui una ricerca di un evento
 * restituisce un risultato nullo o non conforme alle aspettative.
 */
public class EventoNotFoundException extends Exception {
    /**
     * Costruisce una nuova eccezione EventoNotFoundException con un messaggio di dettaglio specificato.
     * Questa eccezione viene sollevata quando un evento specifico non viene trovato all'interno del sistema
     * o durante il recupero dei dati relativi agli eventi.
     *
     * @param message il messaggio di dettaglio che descrive il motivo per cui l'eccezione è stata sollevata.
     */
    public EventoNotFoundException(String message) {
        super(message);
    }
}
