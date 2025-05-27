package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class DBEvento {

    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int costo;
    private int capienza;
    private int partecipanti;
    private int Idamministratore;



    public DBEvento(){}



//metodo per prelevare tutti gli eventi dal database

    public ArrayList<DBEvento> getListaEventi() {
        ArrayList<DBEvento> lista_temp = new ArrayList();
        String query = "SELECT * FROM eventi;";

        try {
            try (ResultSet rs = DBConnectionManager.selectQuery(query)) {

                while (rs.next()) {
                    DBEvento evento_temp = new DBEvento();
                    evento_temp.setId(rs.getInt("id"));
                    evento_temp.setTitolo(rs.getString("Titolo"));
                    evento_temp.setDescrizione(rs.getString("Descrizione"));
                    evento_temp.setData(rs.getDate("Data").toLocalDate());
                    evento_temp.setOra(LocalTime.parse(rs.getString("Ora")));
                    evento_temp.setLuogo(rs.getString("Luogo"));
                    evento_temp.setPartecipanti(rs.getInt("Numpartecipanti"));
                    evento_temp.setCapienza(rs.getInt("Nummaxpartecipanti"));

                    lista_temp.add(evento_temp);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
        }

        return lista_temp;
    }

    public int SalvaInDB() {
        int ret = 0;
        String query = "INSERT INTO eventi(titolo,descrizione,data,ora,luogo,Nummaxpartecipanti,amministratore_id) VALUES ( '" + this.titolo + "','"+ this.descrizione+ "','" + this.data + "','" + this.ora + "','" + this.luogo + "','"+ this.capienza + "','"+ this.Idamministratore +"')";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }

        return ret;
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

    public int getIdamministratore() {
        return Idamministratore;
    }

    public void setIdamministratore(int idamministratore) {
        this.Idamministratore = idamministratore;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}


