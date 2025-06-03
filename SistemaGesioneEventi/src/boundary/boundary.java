package boundary;
import DTO.DTOEvento;
import  control.Controller;
import entity.*;
import exceptions.EventoNotFoundException;

import java.util.List;

public class boundary {

    public static void main(String[] args) throws EventoNotFoundException {

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


        List<DTOEvento> eventi = Controller.RicercaEvento("Workshop di Fotografia",null,null);
        if (eventi.isEmpty()) {
            System.out.println("Nessun evento disponibile.");
        } else {
           System.out.println("Catalogo Eventi:");
           for (DTOEvento evento : eventi) {
               System.out.println("----------------------------");
               System.out.println("Titolo: " + evento.getTitolo());
                System.out.println("Descrizione: " + evento.getDescrizione());
                System.out.println("Data: " + evento.getData());
               System.out.println("Ora: " + evento.getOra());
                System.out.println("Luogo: " + evento.getLuogo());
                System.out.println("Costo: €" + evento.getCosto());
                System.out.println("Capienza: " + evento.getCapienza() + " persone");
            }
       }




    }








}
