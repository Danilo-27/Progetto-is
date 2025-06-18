package external;

/**
 * Interfaccia che definisce un contratto per l'elaborazione di pagamenti.
 */
public interface PagamentoService {
    /**
     * Enum che rappresenta i possibili esiti di un pagamento.
     * Ogni valore dell'enum indica un determinato risultato del processo di pagamento,
     * come successo o errori specifici legati al numero di carta, alla scadenza
     * o alla disponibilità di saldo.
     */
    enum EsitoPagamento {
        /**
         * Indica che l'operazione di pagamento è stata completata con successo
         * senza errori o problemi rilevati.
         */
        SUCCESSO,
        /**
         * Rappresenta l'esito di un pagamento non riuscito a causa di un numero di carta non valido.
         * Questo valore viene utilizzato per indicare che il numero di carta fornito non è riconosciuto
         * o non corrisponde a un numero accettabile nel sistema.
         */
        NUMERO_CARTA_NON_VALIDO,
        /**
         * Valore dell'enum che indica che il pagamento non può essere completato
         * poiché la carta di credito fornita è scaduta.
         */
        CARTA_SCADUTA,
        /**
         * Rappresenta l'esito di un pagamento fallito a causa di saldo insufficiente sulla carta.
         */
        SALDO_INSUFFICIENTE
    }


    /**
     * Elabora un pagamento in base ai dati forniti.
     *
     * @param numeroCarta numero della carta
     * @param nomeTitolare nome del titolare
     * @param cognomeTitolare cognome del titolare
     * @param scadenza scadenza della carta di credito
     * @param importo importo da addebitare
     * @return esito del pagamento
     */
    EsitoPagamento elaboraPagamento(String numeroCarta, String nomeTitolare, String cognomeTitolare,
                                    String scadenza, double importo);
}