package boundary;
import DTO.DTOEvento;
import  control.Controller;

import java.util.ArrayList;

public class BoundaryUtenteRegistrato {

    public static void main(String[] args) {
        /*
        for(int i =0; i<Controller.ConsultaCatalogoEventi().size(); i++){
            System.out.println(Controller.ConsultaCatalogoEventi().get(i).toString());
        }
        */


        //Controller.inserisciEvento("bibi","otherrrrr","2025-03-20" , "21:00", "Teatro alla Scala Milano", 800,1);

       /* Controller.registraCliente("mammeta","pappone","ciao","sddd");*/

        //Controller.loginUtente("mario.rossi@eventmanager.com", "passwordf123");


        ArrayList<DTOEvento> events = Controller.getListaEventi_con_filtro("mammt",null,"Teatro alla Scala Milano");

        for(int i = 0; i < events.size(); i++){
            System.out.println(events.get(i).toString());
        }


    }

}
