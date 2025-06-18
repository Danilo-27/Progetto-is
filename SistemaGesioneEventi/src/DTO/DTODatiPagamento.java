package DTO;

/**
 * Classe che rappresenta un Data Transfer Object (DTO) per i dati di pagamento.
 * Contiene le informazioni relative alla carta di pagamento, tra cui numero della carta,
 * nome e cognome del titolare e data di scadenza.
 * Utilizzata per trasferire i dati di pagamento tra i vari livelli dell'applicazione.
 */
public class DTODatiPagamento {
    /**
     * Rappresenta il numero della carta di pagamento associata all'utente.
     * Viene utilizzato per identificare in modo univoco una carta di pagamento.
     */
    private String numeroCarta;
    /**
     * Indica il nome del titolare della carta di pagamento.
     */
    private String nomeTitolare;
    /**
     * Indica il cognome del titolare della carta di pagamento.
     */
    private String cognomeTitolare;
    /**
     * Rappresenta la data di scadenza della carta di pagamento.
     * Il valore è memorizzato come stringa nel formato YY/MM.
     */
    private String dataScadenza; // Formato: YY/MM

    /**
     * Costruttore predefinito della classe DTODatiPagamento.
     * Inizializza un'istanza vuota del DTO, con tutti i campi non inizializzati.
     */
    public DTODatiPagamento() {
    }

    /**
     * Costruttore della classe DTODatiPagamento che consente di inizializzare
     * i dati relativi alla carta di pagamento con i valori forniti.
     *
     * @param numeroCarta Numero della carta di pagamento associata all'utente.
     * @param nomeTitolare Nome del titolare della carta di pagamento.
     * @param cognomeTitolare Cognome del titolare della carta di pagamento.
     * @param dataScadenza Data di scadenza della carta di pagamento, formattata come YY/MM.
     */
    public DTODatiPagamento(String numeroCarta, String nomeTitolare, String cognomeTitolare, String dataScadenza) {
        this.numeroCarta = numeroCarta;
        this.nomeTitolare = nomeTitolare;
        this.cognomeTitolare = cognomeTitolare;
        this.dataScadenza = dataScadenza;
    }


    /**
     * Restituisce il numero della carta di pagamento associata all'utente.
     *
     * @return il numero della carta di pagamento come stringa.
     */
    public String getNumeroCarta() {
        return numeroCarta;
    }

    /**
     * Imposta il numero della carta di pagamento associata all'utente.
     *
     * @param numeroCarta il numero della carta di pagamento da associare. Deve essere una stringa valida in formato numerico.
     */
    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    /**
     * Restituisce il nome del titolare della carta di pagamento.
     *
     * @return una stringa contenente il nome del titolare della carta.
     */
    public String getNomeTitolare() {
        return nomeTitolare;
    }

    /**
     * Imposta il nome del titolare della carta di pagamento.
     *
     * @param nomeTitolare Il nome del titolare della carta. Non deve essere null o vuoto.
     */
    public void setNomeTitolare(String nomeTitolare) {
        this.nomeTitolare = nomeTitolare;
    }

    /**
     * Restituisce il cognome del titolare della carta di pagamento.
     *
     * @return una stringa contenente il cognome del titolare della carta.
     */
    public String getCognomeTitolare() {
        return cognomeTitolare;
    }

    /**
     * Imposta il cognome del titolare della carta di pagamento.
     *
     * @param cognomeTitolare il cognome del titolare della carta di pagamento da assegnare
     */
    public void setCognomeTitolare(String cognomeTitolare) {
        this.cognomeTitolare = cognomeTitolare;
    }

    /**
     * Restituisce la data di scadenza della carta di pagamento.
     * La data è rappresentata come stringa nel formato YY/MM.
     *
     * @return una stringa che rappresenta la data di scadenza della carta di pagamento.
     */
    public String getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Imposta la data di scadenza della carta di pagamento.
     *
     * @param dataScadenza Stringa che rappresenta la data di scadenza della carta, nel formato YY/MM.
     */
    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

}
