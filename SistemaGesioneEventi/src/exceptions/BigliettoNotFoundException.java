package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * per indicare che un biglietto specifico non è stato trovato durante
 * l'esecuzione di un'operazione.
 *
 * Viene tipicamente utilizzata in sistemi di gestione dei biglietti o eventi,
 * per segnalare che il biglietto cercato non esiste o non è disponibile.
 */
public class BigliettoNotFoundException extends Exception {
    /**
     * Costruisce una nuova eccezione BigliettoNotFoundException con un messaggio di dettaglio specificato.
     * Questa eccezione viene sollevata per indicare che un biglietto non è stato trovato
     * durante l'esecuzione di un'operazione, come la ricerca di un biglietto in un sistema di gestione.
     *
     * @param message il messaggio di dettaglio che descrive la natura del problema
     *                riscontrato, ad esempio che il biglietto ricercato non esiste.
     */
    public BigliettoNotFoundException(String message) {
        super(message);
    }
}