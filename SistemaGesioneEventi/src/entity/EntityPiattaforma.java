//test commento 1
package entity;

import DTO.DTOUtente;
import database.UtenteDAO;
import exceptions.RegistrationFailedException;
import exceptions.DBException;
import exceptions.LoginFailedException;

import java.util.ArrayList;

public class EntityPiattaforma {
    private static EntityPiattaforma uniqueInstance;
    private final ArrayList<EntityUtente> utenti;


    private EntityPiattaforma() throws DBException {
        this.utenti = new ArrayList<>();
        for (UtenteDAO utente : UtenteDAO.getUtenti()) {
            utenti.add(new EntityUtente(utente));
        }
    }

    public static EntityPiattaforma getInstance() {
        if (uniqueInstance == null) {
            try{
                uniqueInstance = new EntityPiattaforma();
            }catch(DBException e){
                System.out.println(e.getMessage());
            }


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
     *
     */

    public void registrazione(String password, String nome, String cognome, String email) throws RegistrationFailedException {

        if(verificaEmail(email)){
            EntityUtente u = new EntityUtente(nome,cognome,email,password);
            this.utenti.add(u);
            u.Aggiornamento();
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

    private boolean verificaEmail(String email) {
        return this.cercaInUtenti(email) != null;
    }


    public EntityUtente cercaInUtenti(String email) {
        for (EntityUtente utente : this.utenti) {
            if (utente.getEmail().equals(email)) {
                return utente;
            }
        }
        return null;
    }
    public EntityUtente cercaInUtenti(int id) {
        for (EntityUtente utente : this.utenti) {
            if (utente.getId()==id) {
                return utente;
            }
        }
        return null;
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
        //u.caricaPerEmail(email);
        EntityUtente utente = this.cercaInUtenti(email);
        if(utente==null){
            throw new LoginFailedException("Utente non registrato");
        }else{
            if (utente.verificaCredenziali(password)) {
                return new DTOUtente(utente.getNome(),utente.getCognome(),email, utente.getImmagine(), utente.getTipoUtente());
            } else {
                throw new LoginFailedException("Login fallito, password errata");
            }
        }
    }


}
