package boundary;


import DTO.DTOUtente;

public class Sessione {
    private static Sessione instance;

    private String email;
    private int Tipo;  // ad esempio "ADMIN" o "CLIENTE"

    private Sessione() {}

    public static Sessione getInstance() {
        if (instance == null) {
            instance = new Sessione();
        }
        return instance;
    }

    public void setUtenteAutenticato(String email, int Tipo) {
        this.email = email;
        this.Tipo = Tipo;
    }


    public int getTipo() {
        return Tipo;
    }

    public boolean isAdmin() {
        return this.Tipo==1;
    }

    public boolean isCliente() {
        return this.Tipo==0;
    }

    public String getEmail() {
        return email;
    }
}