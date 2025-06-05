//test commento 1
package database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import exceptions.DBException;



public class EventoDAO {

    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int costo;
    private int capienza;
    private int partecipanti;
    private int amministratoreID;
    private ArrayList<BigliettoDAO> biglietti;

    //costruttore vuoto
    public EventoDAO(){}

    //costruttore con titolo
    public EventoDAO(String titolo){
        this.titolo=titolo;
        this.biglietti= new ArrayList<>();
        try {
            this.caricaDaDBPerTitolo();
        }catch(DBException e){
            e.printStackTrace();
        }
    }

    public EventoDAO(int id) {
        this.id = id;
        this.biglietti = new ArrayList<>();
        try {
            this.caricaDaDB();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    //metodo per prelevare tutti gli eventi dal database

    public static ArrayList<EventoDAO> getEventi() throws DBException {
        ArrayList<EventoDAO> lista_temp = new ArrayList<>();
        String query = "SELECT * FROM eventi WHERE Data >= CURRENT_DATE;";;

        try {
            try (ResultSet rs = DBConnectionManager.selectQuery(query)) {

                while (rs.next()) {
                    EventoDAO evento_temp = new EventoDAO();
                    evento_temp.setId(rs.getInt("id"));
                    evento_temp.setTitolo(rs.getString("Titolo"));
                    evento_temp.setDescrizione(rs.getString("Descrizione"));
                    evento_temp.setData(rs.getDate("Data").toLocalDate());
                    evento_temp.setOra(LocalTime.parse(rs.getString("Orario")));
                    evento_temp.setLuogo(rs.getString("Luogo"));
                    evento_temp.setPartecipanti(rs.getInt("Partecipanti"));
                    evento_temp.setCapienza(rs.getInt("Capienza"));
                    evento_temp.setAmministratoreid(rs.getInt("Amministratore_id"));
                    evento_temp.setCosto(rs.getInt("Costo"));


                    lista_temp.add(evento_temp);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
        }

        return lista_temp;
    }

    public int SalvaInDB() throws DBException{
        int ret = 0;
        String query = "INSERT INTO eventi (titolo, descrizione, data, orario, luogo, costo, capienza, partecipanti, Amministratore_id) " +
                "VALUES ('" + this.titolo + "', '" + this.descrizione + "', '" + this.data + "', '" + this.ora + "', '" + this.luogo + "', " +
                this.costo + ", " + this.capienza + ", " + this.partecipanti + ", " + this.amministratoreID + ")";
        System.out.println(query);

        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException("Evento già creato");
        }

        return ret;
    }
    public int AggiornaInDB() {
        int ret = 0;
        String query="UPDATE eventi SET Partecipanti='" + this.partecipanti + "' WHERE titolo ='" + this.titolo + "';";
        try{
            ret=DBConnectionManager.updateQuery(query);
        }catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }
        return ret;
    }


    public void caricaDaDB() throws DBException {
        String query = "SELECT * FROM eventi WHERE id = " + this.id + ";";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.titolo = rs.getString("Titolo");
                this.descrizione = rs.getString("Descrizione");
                this.data = rs.getDate("Data").toLocalDate();
                this.ora = LocalTime.parse(rs.getString("Orario"));
                this.luogo = rs.getString("Luogo");
                this.partecipanti = rs.getInt("Partecipanti");
                this.capienza = rs.getInt("Capienza");
                this.amministratoreID = rs.getInt("Amministratore_id");
                this.costo = rs.getInt("Costo");
            } else {
                throw new DBException(String.format("Evento con ID '%d' non esistente", this.id));
            }
            rs.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new DBException("Errore durante il caricamento dell'evento dal database.");
        }
    }



    public void caricaDaDBPerTitolo() throws DBException{
        String query = "SELECT * FROM eventi WHERE Titolo='" + this.titolo + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.id=(rs.getInt("id"));
                this.titolo=(rs.getString("Titolo"));
                this.descrizione=(rs.getString("Descrizione"));
                this.data=(rs.getDate("Data").toLocalDate());
                this.ora=(LocalTime.parse(rs.getString("Orario")));
                this.luogo=(rs.getString("Luogo"));
                this.partecipanti=(rs.getInt("Partecipanti"));
                this.capienza=(rs.getInt("Capienza"));

            } else {
                throw new DBException(String.format("Evento '%s' non esistente", id));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



    public void caricaBigliettiEventiDaDB() {
        String query = "SELECT * FROM biglietti WHERE Evento_id = " + this.id + ";";
        System.out.println(query);

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while(rs.next()) {
                BigliettoDAO biglietto = new BigliettoDAO();
                biglietto.setCodice_univoco(rs.getString("CodiceUnivoco"));
                biglietto.setStato(rs.getInt("stato"));
                biglietto.setCliente_id(rs.getInt("Cliente_id"));
                biglietto.setEvento_id(rs.getInt("Evento_id"));
                this.biglietti.add(biglietto);
            }
            rs.close();
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
        }

    }





//getter e setter

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public LocalTime getOra() {
        return ora;
    }

    public String getLuogo() {
        return luogo;
    }

    public int getCapienza() {
        return capienza;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public LocalDate getData() {
        return data;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmministratoreid() {
        return amministratoreID;
    }

    public void setAmministratoreid(int amministratoreid) {
        this.amministratoreID = amministratoreid;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public ArrayList<BigliettoDAO> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(ArrayList<BigliettoDAO> biglietti) {
        this.biglietti = biglietti;
    }


}


