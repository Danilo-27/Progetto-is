package boundary;
import java.time.LocalDate;
import java.time.LocalTime;
import exceptions.*;
import control.*;

public class
BoundaryTEST {


        public static void main(String[] args) throws DBException {
            // Crea un'istanza del servizio che contiene il metodo
//
//            // Definisci i dati dell'evento
//            String titolo = "prova";
//            String descrizione = "Concerto di musica jazz dal vivo";
//            LocalDate data = LocalDate.of(2025, 6, 5);
//            LocalTime ora = LocalTime.of(21, 0);
//            String luogo = "Teatro Comunale";
//            int costo = 20;
//            int capienza = 100;
//            String emailAmministratore = "marios.rossi@example.com";
//
//            try {
//                // Chiama il metodo da testare
//                Controller.pubblicaEvento(titolo, descrizione, data, ora, luogo, costo, capienza, emailAmministratore);
//                System.out.println("Evento pubblicato con successo!");
//            } catch (DBException e) {
//                System.err.println("Errore durante la pubblicazione dell'evento: " + e.getMessage());
//            }

            Controller.ConsultaEventiPubblicati("marios.rossi@example.com");




        }
}

