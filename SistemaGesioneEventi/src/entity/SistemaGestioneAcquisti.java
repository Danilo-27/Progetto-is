//test commento 1
package entity;

// Stub semplice per sistema di pagamento
public class SistemaGestioneAcquisti {

    public boolean elaboraPagamento(String numeroCarta, String nomeTitolare, String cognomeTitolare) {

        // Dati CORRETTI per test
        String numeroCorretto = "1234";
        String nomeCorretto = nomeTitolare;
        String cognomeCorretto = cognomeTitolare;

        // Controlla se tutti i dati corrispondono a quelli validi
        if (numeroCorretto.equals(numeroCarta) &&
                nomeCorretto.equals(nomeTitolare) &&
                cognomeCorretto.equals(cognomeTitolare)) {
            return true;
        }

        return false;
    }
}