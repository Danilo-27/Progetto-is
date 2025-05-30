package entity;

public class EntityPiattaforma {

    public EntityPiattaforma() {}

    public static String registrazione(String password, String nome, String cognome, String email) {

        EntityCliente nuovoCliente = new EntityCliente(email, password, nome, cognome);

        int res = nuovoCliente.scriviSuDB();

        if (res == -1){
            return "Errore nella registrazione";
        }else{
            return nuovoCliente.getNome()+" registrato correttamente";
        }
    }
}
