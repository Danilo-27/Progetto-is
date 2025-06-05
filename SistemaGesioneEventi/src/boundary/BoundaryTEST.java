package boundary;
import java.time.LocalDate;
import java.time.LocalTime;
import exceptions.*;
import control.*;
import DTO.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BoundaryTEST {

    public static void main(String[] args) {
        String emailUtente = "mario.rossi@example.com";

        try {
            ArrayList<DTOBiglietto> storico = Controller.consultaStoricoBiglietti(emailUtente);

            // Verifica che il risultato non sia null
            assert(storico!=null); //"Lo storico dei biglietti non deve essere null."

            // Verifica che almeno un biglietto sia presente
            assert(!storico.isEmpty()); // "L'utente dovrebbe avere almeno un biglietto."

            // Controllo sul primo biglietto
            DTOBiglietto biglietto = storico.get(0);
            assert(biglietto.getCodiceUnivoco()!=null);
            assert(biglietto.getStato() >= 0);
            assert(biglietto.getTitoloEvento()!=null);
            assert(biglietto.getData().isBefore(LocalDate.now().plusYears(10)));

        } catch (DBException e) {
            System.out.println("Eccezione DBException non prevista: " + e.getMessage());
        }
    }
}


