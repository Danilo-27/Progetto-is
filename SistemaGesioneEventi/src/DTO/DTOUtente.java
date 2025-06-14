package DTO;

/**
 * DTOUtente è una classe Data Transfer Object utilizzata per rappresentare i dati
 * relativi a un utente in modo strutturato. La classe include informazioni come
 * il nome, il cognome, l'email, l'immagine associata e il tipo di utente.
 *
 * Viene generalmente impiegata per il trasferimento di dati tra i livelli di un'applicazione
 * o tra sistemi diversi.
 */
public class DTOUtente {
    /**
     * Contiene il nome dell'utente.
     */
    private String nome;
    /**
     * Rappresenta il cognome dell'utente.
     */
    private String cognome;
    /**
     * Rappresenta l'indirizzo email associato all'utente.
     * Deve essere univoco e valido per garantire la corretta identificazione
     * e comunicazione con l'utente.
     */
    private String email;
    /**
     * Rappresenta il percorso o l'URL di un'immagine associata all'utente.
     */
    private String immagine;
    /**
     * Indica il tipo di utente, rappresentato come un valore intero.
     * Può essere utilizzato per distinguere tra diverse categorie di utenti
     * come amministratori, moderatori o utenti standard.
     */
    private int tipoUtente;

    /**
     * Costruttore predefinito della classe DTOUtente.
     * Inizializza un'istanza vuota dell'oggetto, senza impostare alcun valore
     * per gli attributi della classe.
     */
    public DTOUtente() {
    }

    /**
     * Costruttore della classe DTOUtente che permette di creare un'istanza
     * con tutti gli attributi inizializzati ai valori specificati.
     *
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param email L'indirizzo email associato all'utente.
     * @param immagine Il percorso o l'URL dell'immagine associata all'utente.
     * @param tipoUtente Il tipo di utente, rappresentato come valore intero.
     */
    public DTOUtente(String nome, String cognome, String email, String immagine,int tipoUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.immagine = immagine;
        this.tipoUtente = tipoUtente;
    }


    /**
     * Costruttore della classe DTOUtente che consente di creare un'istanza
     * con i campi nome e cognome inizializzati.
     *
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     */
    public DTOUtente(String nome, String cognome) {
        this.nome=nome;
        this.cognome=cognome;
    }

    /**
     * Restituisce il nome associato all'istanza corrente.
     *
     * @return il nome come stringa.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore del nome dell'utente.
     *
     * @param nome Il nome da assegnare all'utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return Il cognome dell'utente come stringa.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome Il cognome da assegnare all'utente.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'indirizzo email associato all'utente.
     *
     * @return L'indirizzo email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'indirizzo email associato all'utente.
     *
     * @param email L'indirizzo email da assegnare all'utente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce il valore dell'attributo immagine, che rappresenta il percorso
     * o l'URL dell'immagine associata all'utente.
     *
     * @return Una stringa contenente il percorso o l'URL dell'immagine associata all'utente.
     */
    public String getImmagine() {
        return immagine;
    }

    /**
     * Imposta il valore dell'attributo immagine per il DTOUtente.
     *
     * @param immagine Il percorso o l'URL dell'immagine associata all'utente.
     */
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    /**
     * Restituisce il tipo dell'utente.
     *
     * @return Il tipo di utente, rappresentato come un valore intero.
     */
    public int getTipoUtente() {
        return tipoUtente;
    }

    /**
     * Imposta il tipo dell'utente specificando un valore numerico.
     *
     * @param tipoUtente Valore intero che rappresenta il tipo di utente. Può essere usato
     *                   per distinguere diverse categorie o ruoli dell'utente.
     */
    public void setTipoUtente(int tipoUtente) {
        this.tipoUtente = tipoUtente;
    }

}
