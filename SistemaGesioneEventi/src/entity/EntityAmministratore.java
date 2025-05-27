package entity;

import database.DBCliente;
import database.DBamministratore;

public class EntityAmministratore extends UtenteRegistrato{

    public EntityAmministratore(String email, String password, String nome, String cognome) {
        super(email, password, nome, cognome);
    }

    public EntityAmministratore() {
    }

    public int scriviSuDB() {
        DBamministratore a = new DBamministratore();
        a.setNome(getNome());
        a.setEmail(getEmail());
        a.setCognome(getCognome());
        a.setNome(getCognome());

        return a.SalvaInDB();
    }

    public void cercaSuDB(String email){
        DBamministratore dba = new DBamministratore(email);

        setNome(dba.getNome());
        setCognome(dba.getCognome());
        setPassword(dba.getPassword());
        setEmail(dba.getEmail());
    }


}
