package boundary;

public class Sessione {
    private static Sessione instance;

    private String email;
    private int Ruolo;  // ad esempio "ADMIN" o "CLIENTE"

    private Sessione() {}

    public static Sessione getInstance() {
        if (instance == null) {
            instance = new Sessione();
        }
        return instance;
    }

    public void setUtenteAutenticato(String email, int Ruolo) {
        this.email = email;
        this.Ruolo = Ruolo;
    }


    public int getRuolo() {
        return Ruolo;
    }

    public boolean isAdmin() {
        return this.Ruolo ==1;
    }

    public boolean isCliente() {
        return this.Ruolo ==0;
    }

    public String getEmail() {
        return email;
    }
}