package external;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Stub che simula un sistema di pagamento per scopi di test.
 */
public class StubSistemaGestioneAcquisti implements PagamentoService {

    // Dati fittizi per test
    private static final String NUMERO_CARTA_VALIDO = "1234";
    private static final double SALDO_DISPONIBILE = 100.0;
    private static final YearMonth DATA_SCADENZA_CORRETTA = YearMonth.of(2026, 12); // Non più usata direttamente

    public StubSistemaGestioneAcquisti() {}

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
            return EsitoPagamento.CARTA_SCADUTA; // Oppure un errore di formato, se vuoi distinguere
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