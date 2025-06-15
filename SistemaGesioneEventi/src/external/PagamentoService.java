package external;

/**
 * Interfaccia che definisce un contratto per l'elaborazione di pagamenti.
 */
public interface PagamentoService {

    enum EsitoPagamento {
        SUCCESSO,
        NUMERO_CARTA_NON_VALIDO,
        CARTA_SCADUTA,
        SALDO_INSUFFICIENTE
    }

    /**
     * Elabora un pagamento in base ai dati forniti.
     *
     * @param numeroCarta numero della carta
     * @param nomeTitolare nome del titolare
     * @param cognomeTitolare cognome del titolare
     * @param scadenza
     * @param importo importo da addebitare
     * @return esito del pagamento
     */
    EsitoPagamento elaboraPagamento(String numeroCarta, String nomeTitolare, String cognomeTitolare,
                                    String scadenza, double importo);
}