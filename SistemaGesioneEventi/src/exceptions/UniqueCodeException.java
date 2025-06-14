package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * quando si verifica un errore legato alla gestione di codici univoci.
 * Può essere utilizzata per segnalare problemi come violazioni di unicità
 * o incongruenze nei codici generati o assegnati.
 */
public class UniqueCodeException extends Exception {

	/**
	 * Costruisce una nuova eccezione UniqueCodeException con un messaggio di dettaglio specificato.
	 * Questa eccezione viene utilizzata per segnalare errori relativi alla gestione di codici univoci,
	 * come violazioni di unicità o incongruenze nei codici.
	 *
	 * @param message il messaggio di dettaglio che descrive la natura del problema riscontrato.
	 */
	public UniqueCodeException(String message) {
		super(message);
	}
}
