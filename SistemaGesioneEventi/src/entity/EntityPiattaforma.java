//test commento 1
package entity;

import DTO.DTOUtente;
import database.UtenteDAO;
import exceptions.*;

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
            }catch(WrongUserTypeException e){
                System.out.println(e.getMessage());
            }
        }
        return uniqueInstance;
    }

    private EntityPiattaforma() throws WrongUserTypeException,UtenteNotFoundException{
        this.utenti = new ArrayList<>();
        try {
            for (UtenteDAO utente : UtenteDAO.getUtenti()) {
                EntityUtenteRegistrato utenteRegistrato = this.creaUtenteRegistrato(utente);
                if(utenteRegistrato==null){
                    throw new WrongUserTypeException("Utente non autorizzato");
                }else utenti.add(utenteRegistrato);
            }
        } catch (DBException _) {
            throw new UtenteNotFoundException("Utente non trovato");
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

    /**
     * Cerca la presenza di un'email data nella lista degli utenti registrati.
     *
     * @param email l'indirizzo email da cercare all'interno della lista degli utenti
     * @return true se l'email specificata viene trovata, false altrimenti
     */
    private boolean findEmail(String email) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cerca un cliente nella lista degli utenti registrati in base all'indirizzo email fornito.
     * Se l'email corrisponde a un'istanza di {@code EntityCliente}, il metodo la restituisce.
     * Se nessun cliente viene trovato con l'email specificata, il metodo restituisce {@code null}.
     *
     * @param email l'indirizzo email da cercare nella lista degli utenti registrati
     * @return un'istanza di {@code EntityCliente} se esiste un cliente con l'email specificata,
     * oppure {@code null} se nessun cliente viene trovato
     */
    public EntityCliente cercaClientePerEmail(String email) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getEmail().equals(email) && (utente instanceof EntityCliente entityCliente)) return entityCliente;
        }
        return null;
    }

    /**
     * Cerca un amministratore nella lista degli utenti registrati utilizzando l'indirizzo email fornito.
     * Se viene trovato un amministratore con l'email specificata, restituisce l'istanza corrispondente di {@code EntityAmministratore}.
     * Se non viene trovato nessun amministratore, restituisce {@code null}.
     *
     * @param email l'indirizzo email da cercare nella lista degli utenti registrati
     * @return un'istanza di {@code EntityAmministratore} se viene trovato un amministratore con l'email specificata, oppure {@code null} se non esiste
     */
    public EntityAmministratore cercaAmministratorePerEmail(String email) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getEmail().equals(email)&&( utente instanceof EntityAmministratore entityAmministratore)) return entityAmministratore;
        }
        return null;

    }

    /**
     * Cerca un cliente nella lista degli utenti registrati tramite il suo identificativo univoco.
     * Se l'identificativo corrisponde a un'istanza di {@code EntityCliente}, il metodo la restituisce.
     * Se nessun cliente viene trovato con l'id specificato, il metodo restituisce {@code null}.
     *
     * @param id l'identificativo univoco del cliente da cercare
     * @return un'istanza di {@code EntityCliente} se esiste un cliente con l'id specificato,
     * oppure {@code null} se nessun cliente viene trovato
     */
    public EntityCliente cercaClientePerId(int id) {
        for (EntityUtenteRegistrato utente : this.utenti) {
            if (utente.getId()==id && (utente instanceof EntityCliente entityCliente)) return entityCliente;
        }
        return null;
    }

    /**
     * Cerca un amministratore nella lista degli utenti registrati utilizzando un identificativo univoco.
     * Se l'identificativo corrisponde a un'istanza di {@code EntityAmministratore}, il metodo lo restituisce.
     * Se nessun amministratore viene trovato con l'ID specificato, il metodo restituisce {@code null}.
     *
     * @param id l'identificativo univoco dell'amministratore da cercare
     * @return un'istanza di {@code EntityAmministratore} se viene trovato un amministratore con l'ID specificato,
     * oppure {@code null} se non esiste
     */
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
