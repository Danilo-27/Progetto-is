//test commento 1
package entity;

import DTO.DTOUtente;
import exceptions.RegistrationFailedException;
import exceptions.DBException;
import exceptions.LoginFailedException;

public class EntityPiattaforma {
    private static EntityPiattaforma uniqueInstance;

    private EntityPiattaforma() {
    }

    public static EntityPiattaforma getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new EntityPiattaforma();
        }
        return uniqueInstance;
    }

    /**
     * Permette di registrare un nuovo utente nel sistema. Se l'email fornita è già presente
     * nel database, la registrazione fallisce e viene sollevata un'eccezione.
     *
     * @param password la password dell'utente da registrare
     * @param nome il nome dell'utente da registrare
     * @param cognome il cognome dell'utente da registrare
     * @param email l'email dell'utente da registrare, che deve essere univoca nel sistema
     * @throws RegistrationFailedException se l'email è già associata a un altro utente nel sistema
     */

    public void registrazione(String password, String nome, String cognome, String email) throws RegistrationFailedException {

        if(verificaEmail(email)==0){
            EntityUtente u = new EntityUtente(nome,cognome,email,password);
            u.scriviSuDB();
        }else{
            throw new RegistrationFailedException(
                    String.format("Registrazione fallita: l'email '%s' è già presente nel sistema.", email)
            );
        }

    }

    /**
     * Verifica se un'email specificata è già registrata nel sistema.
     *
     * @param email l'email da verificare
     * @return 1 se l'email è presente nel sistema, 0 altrimenti
     */

    private int verificaEmail(String email) {
        EntityUtente u = new EntityUtente();
        try {
            u.caricaPerEmail(email);
            return 1;
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     * Permette di autenticare un utente esistente nel sistema tramite email e password.
     * Se l'autenticazione ha esito positivo, restituisce un oggetto DTO contenente le informazioni
     * dell'utente autenticato. Altrimenti, viene sollevata un'eccezione.
     *
     * @param email l'email dell'utente da autenticare
     * @param password la password associata all'utente
     * @return un oggetto di tipo {@code DTOUtente} che rappresenta l'utente autenticato
     * @throws LoginFailedException se l'email non è registrata o la password è errata
     */

    public DTOUtente Autenticazione(String email, String password) throws LoginFailedException {
        try {
            EntityUtente u = new EntityUtente();
            u.caricaPerEmail(email);
            if (u.verificaCredenziali(password)) {
                return new DTOUtente(u.getNome(),u.getCognome(),email, u.getImmagine(), u.getTipoUtente());
            } else {
                throw new LoginFailedException("Login fallito, password errata");
            }
        } catch (DBException e) {
            throw new LoginFailedException("Login fallito, email non registrata");
        }

    }


}
