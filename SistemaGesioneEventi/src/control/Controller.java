//test commento

package control;

import entity.*;
import exceptions.RegistrationFailedException;



public class Controller {




    public static  void registrazione(String password, String nome, String cognome,String email) throws RegistrationFailedException {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        piattaforma.registrazione(password, nome, cognome, email);
    }





}










