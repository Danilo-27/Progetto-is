package boundary;
import  control.Controller;

public class BoundaryUtenteRegistrato {

    public static void main(String[] args) {
        /*
        for(int i =0; i<Controller.ConsultaCatalogoEventi().size(); i++){
            System.out.println(Controller.ConsultaCatalogoEventi().get(i).toString());
        }
        */


        //Controller.inserisciEvento("banana","otherrrrr","2025-05-28" , "21:00", "Teatro alla Scala Milano", 800,1);

        System.out.println(Controller.registrazione("mamma","pappa","ciao","sddd"));

        //Controller.autenticazione("mario.rossi@eventmanager.com", "passwordf123");

//        String dataOggi = LocalDate.now().toString();
//        System.out.println(dataOggi);
//        ArrayList<DTOEvento> events = Controller.ricercaEvento("banana",dataOggi,"Teatro alla Scala Milano");
//
//        for(int i = 0; i < events.size(); i++){
//            System.out.println(events.get(i).toString());
//        }

        //System.out.println(Controller.ricercaCliente("luca.verdi@example.com"));

    }

}
