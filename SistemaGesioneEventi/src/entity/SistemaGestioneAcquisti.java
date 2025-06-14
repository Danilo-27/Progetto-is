//test commento 1
package entity;

/**
 * Classe che rappresenta un sistema per la gestione degli acquisti,
 * con funzionalità per elaborare i pagamenti verificando la correttezza dei dati forniti.
 */
// Stub semplice per sistema di pagamento
public class SistemaGestioneAcquisti {
    
    /**
     * Verifica la correttezza dei dati forniti per un pagamento,
     * confrontandoli con valori predefiniti validi.
     *
     * @param numeroCarta il numero della carta di credito da verificare
     * @param nomeTitolare il nome del titolare della carta di credito
     * @param cognomeTitolare il cognome del titolare della carta di credito
     * @return true se tutti i dati forniti corrispondono ai valori validi, altrimenti false
     */
    public boolean elaboraPagamento(String numeroCarta, String nomeTitolare, String cognomeTitolare) {

        // Dati CORRETTI per test
        String numeroCorretto = "1234";
        String nomeCorretto = nomeTitolare;
        String cognomeCorretto = cognomeTitolare;

        // Controlla se tutti i dati corrispondono a quelli validi

        return numeroCorretto.equals(numeroCarta) &&
                nomeCorretto.equals(nomeTitolare) &&
                cognomeCorretto.equals(cognomeTitolare);
    }
}