package exceptions;

/**
 * Eccezione personalizzata che viene sollevata quando un utente specifico
 * non viene trovato all'interno del sistema o durante il recupero dei dati.
 * Questa classe estende {@code RuntimeException}, rendendo l'eccezione
 * unchecked, pertanto non è obbligatorio gestirla esplicitamente.
 *
 * È comunemente utilizzata in situazioni in cui si cerca un utente nel
 * database o in una collezione, e il risultato della ricerca è nullo o
 * non corrisponde ai dati attesi.
 *
 * Esempi di utilizzo includono contesti di ricerca, autenticazione o
 * interazioni con i dati relativi agli utenti.
 */
public class UtenteNotFoundException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione UtenteNotFoundException con il messaggio di dettaglio specificato.
     * Questa eccezione viene sollevata quando un utente non viene trovato nel sistema, ad esempio
     * durante un'operazione di ricerca o interazione sui dati utente.
     *
     * @param message il messaggio di dettaglio che descrive il motivo per cui l'eccezione è stata sollevata.
     */
    public UtenteNotFoundException(String message) {
        super(message);
    }
}
