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
            System.out.println(Controller.consultaStoricoBiglietti(emailUtente).toString());

            // Controllo sul primo biglietto


        } catch (DBException e) {
            System.out.println("Eccezione DBException non prevista: " + e.getMessage());
        }
    }
}


