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


    public DTOUtente(String nome, String cognome) {
        this.nome=nome;
        this.cognome=cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public int getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(int tipoUtente) {
        this.tipoUtente = tipoUtente;
    }

}
