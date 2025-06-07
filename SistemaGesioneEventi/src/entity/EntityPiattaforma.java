//test commento 1
package entity;

import DTO.DTOUtente;
import database.UtenteDAO;
import exceptions.RegistrationFailedException;
import exceptions.DBException;
import exceptions.LoginFailedException;
import exceptions.WrongUserTypeException;
import java.util.ArrayList;

public class EntityPiattaforma {
    private static EntityPiattaforma uniqueInstance;
    private final ArrayList<EntityUtenteRegistrato> utenti;

    public static final int AMMINISTRATORE=1;
    public static final int CLIENTE=0;


    public static EntityPiattaforma getInstance() {
        if (uniqueInstance == null) {
            try{
                uniqueInstance = new EntityPiattaforma();
            }catch(DBException | WrongUserTypeException e){
                System.out.println(e.getMessage());
            }
        }
        return uniqueInstance;
    }

    private EntityPiattaforma() throws WrongUserTypeException,DBException {
        this.utenti = new ArrayList<>();
        for (UtenteDAO utente : UtenteDAO.getUtenti()) {
            EntityUtenteRegistrato utenteRegistrato = this.creaUtenteRegistrato(utente);
            if(utenteRegistrato==null){
                throw new WrongUserTypeException("Utente non autorizzato");
            }else utenti.add(utenteRegistrato);
        }
    }
    public EntityUtenteRegistrato creaUtenteRegistrato(UtenteDAO utenteDao) {
        if(utenteDao.getTipoUtente()==EntityPiattaforma.AMMINISTRATORE){
            return new EntityAmministratore(utenteDao);
        }else if(utenteDao.getTipoUtente()==EntityPiattaforma.CLIENTE){
            return new EntityCliente(utenteDao);
        }
        return null;
    }


    /**
     * Permette di registrare un nuovo utente nel sistema. Se l'email fornita è già presente
     * nel database, la registrazione fallisce e viene sollevata un'eccezione.
     *
     * @param password la PASSWORD dell'utente da registrare
     * @param nome il nome dell'utente da registrare
     * @param cognome il cognome dell'utente da registrare
     * @param email l'email dell'utente da registrare, che deve essere univoca nel sistema
     * @throws RegistrationFailedException se l'email è già associata a un altro utente nel sistema
     *
     */

    public void registrazione(String password, String nome, String cognome, String email) throws RegistrationFailedException {
        if (!findEmail(email)) {
            EntityUtenteRegistrato newUtente= new EntityUtenteRegistrato(nome, cognome, email, password);
            this.utenti.add(newUtente);
            newUtente.aggiornamento();
        } else {
            throw new RegistrationFailedException("Email " +email + " già registrata");
        }
    }

    private boolean findEmail(String email) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public EntityCliente cercaClientePerEmail(String email) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getEmail().equals(email) && (utente instanceof EntityCliente entityCliente)) return entityCliente;
        }
        return null;
    }

    public EntityAmministratore cercaAmministratorePerEmail(String email) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getEmail().equals(email)&&( utente instanceof EntityAmministratore entityAmministratore)) return entityAmministratore;
        }
        return null;

    }

    public EntityCliente cercaClientePerId(int id) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getId()==id && (utente instanceof EntityCliente entityCliente)) return entityCliente;
        }
        return null;
    }

    public EntityAmministratore cercaAmministratorePerId(int id) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getId()==id &&( utente instanceof EntityAmministratore entityAmministratore)) return entityAmministratore;
        }
        return null;

    }

    /**
     * Permette di autenticare un utente esistente nel sistema tramite email e PASSWORD.
     * Se l'autenticazione ha esito positivo, restituisce un oggetto DTO contenente le informazioni
     * dell'utente autenticato. Altrimenti, viene sollevata un'eccezione.
     *
     * @param email l'email dell'utente da autenticare
     * @param password la PASSWORD associata all'utente
     * @return un oggetto di tipo {@code DTOUtente} che rappresenta l'utente autenticato
     * @throws LoginFailedException se l'email non è registrata o la PASSWORD è errata
     */


    public DTOUtente Autenticazione(String email, String password) throws LoginFailedException {
        EntityUtenteRegistrato utente = this.utenti.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new LoginFailedException("Email non registrata"));
        if (utente.verificaCredenziali(password)) {
            if (utente instanceof EntityCliente entityCliente) {
                return new DTOUtente(entityCliente.getNome(), entityCliente.getCognome(), email, entityCliente.getImmagine(), EntityPiattaforma.CLIENTE);
            }

            else {
                return new DTOUtente(utente.getNome(), utente.getCognome(), email, null, EntityPiattaforma.AMMINISTRATORE);
            }
        }
        throw new LoginFailedException("Login fallito, PASSWORD errata");
    }

}
