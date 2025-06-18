package exceptions;

/**
 * Questa classe rappresenta un'eccezione personalizzata che viene sollevata
 * quando il processo di registrazione di un utente fallisce.
 *
 * È tipicamente utilizzata per segnalare errori come conflitti di unicità
 * (ad esempio, un'email già registrata) o altre anomalie riscontrate
 * durante l'operazione di registrazione.
 */
public class RegistrationFailedException extends Exception {
	/**
	 * Costruisce una nuova eccezione RegistrationFailedException con il messaggio di dettaglio specificato.
	 * Questa eccezione viene utilizzata per segnalare un fallimento durante la registrazione di un utente,
	 * ad esempio quando si verifica un conflitto di unicità con un'email preesistente nel sistema.
	 *
	 * @param message il messaggio di dettaglio che descrive il motivo per cui l'eccezione è stata sollevata
	 */
	public RegistrationFailedException(String message) {
		super(message);
	}
}
