package external;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Stub che simula un sistema di pagamento per scopi di test.
 */
public class StubSistemaGestioneAcquisti implements PagamentoService {

    /**
     * Numero di carta valido utilizzato come dato fittizio per scopi di test.
     * Viene confrontato con il numero di carta fornito durante l'elaborazione
     * del pagamento per verificarne la validità.
     */
    // Dati fittizi per test
    private static final String NUMERO_CARTA_VALIDO = "1234";
    /**
     * Indica il saldo disponibile simulato per il sistema di pagamento.
     * Questo valore viene utilizzato per verificare se un'operazione di pagamento
     * può essere eseguita o se il saldo risulta insufficiente.
     */
    private static final double SALDO_DISPONIBILE = 100.0;
    /**
     * Data di scadenza corretta, utilizzata come riferimento nei test per simulare una
     * carta di credito valida. Il valore rappresenta il mese e l'anno di una scadenza futura.
     */
    private static final YearMonth DATA_SCADENZA_CORRETTA = YearMonth.of(2026, 12);

    /**
     * Costruttore predefinito della classe StubSistemaGestioneAcquisti.
     * Inizializza un'istanza del sistema fittizio di gestione acquisti,
     * utilizzato principalmente per simulazioni e test.
     */
    public StubSistemaGestioneAcquisti() {}

    /**
     * Elabora un pagamento verificando la validità dei dati forniti, tra cui numero di carta,
     * scadenza e disponibilità del saldo, e restituisce l'esito dell'operazione.
     *
     * @param numeroCarta numero della carta da utilizzare per il pagamento
     * @param nomeTitolare nome del titolare della carta
     * @param cognomeTitolare cognome del titolare della carta
     * @param scadenza data di scadenza della carta nel formato MM/yy
     * @param importo importo da addebitare sulla carta
     * @return esito dell'operazione di pagamento, che può indicare successo
     *         o eventuali errori come carta non valida, scadenza errata o saldo insufficiente
     */
    @Override
    public EsitoPagamento elaboraPagamento(String numeroCarta, String nomeTitolare, String cognomeTitolare,
                                           String scadenza, double importo) {

        // Verifica numero carta
        if (!NUMERO_CARTA_VALIDO.equals(numeroCarta)) {
            return EsitoPagamento.NUMERO_CARTA_NON_VALIDO;
        }

        // Verifica scadenza (formato MM/yy)
        YearMonth dataFornita;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            dataFornita = YearMonth.parse(scadenza, formatter);
        } catch (DateTimeParseException e) {
            return EsitoPagamento.CARTA_SCADUTA;
        }

        if (dataFornita.isBefore(YearMonth.now())) {
            return EsitoPagamento.CARTA_SCADUTA;
        }

        // Verifica saldo
        if (importo > SALDO_DISPONIBILE) {
            return EsitoPagamento.SALDO_INSUFFICIENTE;
        }

        // Tutto ok
        return EsitoPagamento.SUCCESSO;
    }
}