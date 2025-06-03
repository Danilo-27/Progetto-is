package boundary;
import DTO.DTOEvento;
import  control.Controller;
import entity.*;
import exceptions.AcquistoException;
import exceptions.DBException;
import exceptions.EventoNotFoundException;

import java.util.List;

public class boundary {

    public static void main(String[] args) {

//        List<DTOEvento> eventi = Controller.ConsultaCatalogo();
//        if (eventi.isEmpty()) {
//            System.out.println("Nessun evento disponibile.");
//        } else {
//            System.out.println("Catalogo Eventi:");
//            for (DTOEvento evento : eventi) {
//                System.out.println("----------------------------");
//                System.out.println("Titolo: " + evento.getTitolo());
//                System.out.println("Descrizione: " + evento.getDescrizione());
//                System.out.println("Data: " + evento.getData());
//                System.out.println("Ora: " + evento.getOra());
//                System.out.println("Luogo: " + evento.getLuogo());
//                System.out.println("Costo: €" + evento.getCosto());
//                System.out.println("Capienza: " + evento.getCapienza() + " persone");
//            }
//        }

        try {
            EntityEvento evento = new EntityEvento("Workshop di Fotografia");

            // Simula disponibilità e utente
            Controller.AcquistoBiglietto(evento,"mario.rossi@example.com");
            System.out.println("Acquisto completato con successo.");

        } catch (AcquistoException e) {
            System.err.println("Errore durante l'acquisto: " + e.getMessage());
        } catch (DBException e) {
            System.err.println("Errore del database: " + e.getMessage());
        }





    }








}
