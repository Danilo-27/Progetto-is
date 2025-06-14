package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * per indicare errori relativi a operazioni sul database.
 *
 * È progettata per gestire eccezioni legate a problemi come fallimenti nelle
 * query, connessioni non riuscite o altre anomalie durante l'interazione
 * con il database. In tal modo, fornisce una gestione specifica per
 * problematiche relative alla persistenza dei dati.
 */
public class DBException extends Exception {

	/**
	 * Costruisce una nuova eccezione DBException con un messaggio di dettaglio specificato.
	 * Questa eccezione è utilizzata per segnalare errori legati a operazioni sul database,
	 * come fallimenti nelle query, connessioni non riuscite o altre anomalie connesse.
	 *
	 * @param message il messaggio di dettaglio che descrive la natura dell'errore riscontrato.
	 */
	public DBException(String message) {
		super(message);
	}
}
