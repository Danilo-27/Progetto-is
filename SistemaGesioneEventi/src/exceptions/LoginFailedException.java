package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * quando il processo di autenticazione di un utente fallisce.
 *
 * È tipicamente utilizzata per gestire errori legati all'accesso, come l'inserimento
 * di credenziali errate o problemi che impediscono il completamento del login.
 */
public class LoginFailedException extends Exception {
	/**
	 * Costruisce una nuova eccezione LoginFailedException con un messaggio
	 * di dettaglio specificato. Questa eccezione viene sollevata per indicare
	 * un fallimento durante il processo di login, come credenziali errate
	 * o altri errori legati all'autenticazione.
	 *
	 * @param message il messaggio di dettaglio che descrive il motivo
	 *                per cui l'eccezione è stata sollevata.
	 */
	public LoginFailedException(String message) {
		super(message);
	}
}
