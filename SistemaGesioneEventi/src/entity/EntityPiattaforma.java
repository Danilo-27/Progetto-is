package entity;

public class EntityPiattaforma {

    private static EntityPiattaforma uniqueInstance;

    private EntityPiattaforma() {}

    public static EntityPiattaforma getInstance() {
        if(uniqueInstance == null) {
            uniqueInstance = new EntityPiattaforma();
        }
        return uniqueInstance;
    }

    public static String registrazione(String password, String nome, String cognome, String email) {

        EntityCliente nuovoCliente = new EntityCliente(email, password, nome, cognome);

        int res = nuovoCliente.scriviSuDB();

        if (res == -1){
            return "Errore nella registrazione";
        }else{
            return nuovoCliente.getNome()+" registrato correttamente";
        }
    }
    public static String registrazione(String password, String email) {
        //implementare
        return null; //ELIMINARE
    }
    private void verificaEmail(String email){
        //implementazione
    }


}
