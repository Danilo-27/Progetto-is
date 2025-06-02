package boundary;
import  control.Controller;
import exceptions.RegistrationFailedException;

public class BoundaryUtenteRegistrato {

    public static void main(String[] args)  {

        String email = "mario.rossi@example.com";

        try {
            Controller.registrazione("mamma", "papa", "otheja", email);
            System.out.println("Registrazione avvenuta con successo!");
        } catch (RegistrationFailedException e) {
            System.out.println("Errore durante la registrazione: " + e.getMessage());
        }




    }

}
