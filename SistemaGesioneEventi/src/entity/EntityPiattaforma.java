package entity;

import database.UtenteDAO;
import exceptions.RegistrationFailedException;
import exceptions.DBException;

public class EntityPiattaforma {
    private static EntityPiattaforma uniqueInstance;

    private EntityPiattaforma() {
    }

    public static EntityPiattaforma getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new EntityPiattaforma();
        }
        return uniqueInstance;
    }

    public void registrazione(String password, String nome, String cognome, String email) throws RegistrationFailedException {

        if(verificaEmail(email)==0){
            EntityUtente u = new EntityUtente(nome,cognome,email,password);
            u.scriviSuDB();
        }else{
            throw new RegistrationFailedException(
                    String.format("Registrazione fallita: l'email '%s' è già presente nel sistema.", email)
            );
        }

    }

    private int verificaEmail(String email) {
        EntityUtente u = new EntityUtente();
        try {
            u.cercaSuDB(email);
            return 1;
        } catch (DBException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }



}
