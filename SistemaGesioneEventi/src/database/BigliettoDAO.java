//test commento 1
package database;

import exceptions.DBException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BigliettoDAO{

    private String codice_univoco;
    private int stato;
    private int Cliente_id;
    private int Evento_id;
    private EventoDAO evento;
    private UtenteDAO utente;

    public BigliettoDAO() {}

    //carico un biglietto dato il codice (usato in entityEvento)
    public BigliettoDAO(String codice_univoco){
        this.codice_univoco = codice_univoco;
        this.caricaDaDB();
        this.caricaEventoFromBigliettoDaDB();
        this.caricaUtenteDaBigliettoDaDB();
    }

    public void caricaDaDB() {
        String query = "SELECT * FROM biglietti WHERE  CodiceUnivoco = '" + this.codice_univoco + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.stato = rs.getInt("stato");
                this.Cliente_id = rs.getInt("Cliente_id");
                this.Evento_id = rs.getInt("Evento_id");
            } else {
                System.out.println("Biglietto non trovato nel DB");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void caricaEventoFromBigliettoDaDB() {
        String query = "SELECT * FROM eventi WHERE ID = " + this.Evento_id + ";";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                EventoDAO evento= new EventoDAO();
                evento.setTitolo(rs.getString("Titolo"));
                evento.setAmministratoreid(rs.getInt("Amministratore_id"));
                evento.setCapienza(rs.getInt("capienza"));
                evento.setPartecipanti(rs.getInt("partecipanti"));
                evento.setData(LocalDate.parse(rs.getString("data")));
                evento.setOra(LocalTime.parse(rs.getString("orario")));
                evento.setDescrizione(rs.getString("descrizione"));
                evento.setLuogo(rs.getString("luogo"));

                this.evento = evento;

            } else {
                System.out.println("Evento non trovato nel DB");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void caricaUtenteDaBigliettoDaDB() {
        String query = "SELECT * FROM utenti WHERE ID = " + this.Cliente_id + ";";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                UtenteDAO utente= new UtenteDAO();
                utente.setId(this.Cliente_id);
                utente.setEmail(rs.getString("email"));
                utente.setPassword(rs.getString("password"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setImmagine(rs.getString("immagineProfilo"));
                utente.setTipoUtente(rs.getInt("Tipo"));

                this.utente = utente;

            } else {
                System.out.println("Utente non trovato nel DB");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int SalvaInDB() throws DBException {
        int ret = 0;
        String query = "INSERT INTO biglietti (CodiceUnivoco, stato, Cliente_id, Evento_id) " +
                "VALUES ('" + this.codice_univoco + "', '" + this.stato + "', '" + this.Cliente_id + "', '" + this.Evento_id + "');";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DBException("Violazione dei vincoli di integrità: " + e.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            ret = -1;
        }
        return ret;
    }


    public int aggiornaInDB() {
        int ret = 0;
        String query="UPDATE biglietti SET stato='" + this.stato + "' WHERE CodiceUnivoco ='" + this.codice_univoco + "';";
        try{
            ret=DBConnectionManager.updateQuery(query);
        }catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }
        return ret;
    }






    public String getCodice_univoco() {
        return codice_univoco;
    }

    public void setCodice_univoco(String codice_univoco) {
        this.codice_univoco = codice_univoco;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getCliente_id() {
        return Cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.Cliente_id = cliente_id;
    }

    public int getEvento_id() {
        return Evento_id;
    }

    public void setEvento_id(int evento_id) {
        this.Evento_id = evento_id;
    }

    public EventoDAO getEvento() {
        return evento;
    }

    public UtenteDAO getUtente() {return utente;}
}
