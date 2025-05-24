package boundary;
import  control.Controller;
public class BoundaryUtenteRegistrato {



    public static void main(String[] args) {
        for(int i =0; i<Controller.ConsultaCatalogoEventi().size(); i++){
            System.out.println(Controller.ConsultaCatalogoEventi().get(i).toString());
        }

    }

}
