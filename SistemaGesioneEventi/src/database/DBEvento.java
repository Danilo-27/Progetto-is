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
    private int numeroMassimoPartecipanti;
    private int numeroPartecipanti;
    private int id_amministratore;



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
                    evento_temp.setNumeroPartecipanti(rs.getInt("Numpartecipanti"));
                    evento_temp.setNumeroMassimoPartecipanti(rs.getInt("Nummaxpartecipanti"));

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
        String query = "INSERT INTO eventi(titolo,descrizione,data,ora,luogo,Nummaxpartecipanti,amministratore_id) VALUES ( '" + this.titolo + "','"+ this.descrizione+ "','" + this.data + "','" + this.ora + "','" + this.luogo + "','"+ this.numeroMassimoPartecipanti + "','"+ this.id_amministratore+"')";
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

    public int getNumeroMassimoPartecipanti() {
        return numeroMassimoPartecipanti;
    }

    public int getNumeroPartecipanti() {
        return numeroPartecipanti;
    }

    public LocalDate getData() {
        return data;
    }

    public void setNumeroMassimoPartecipanti(int numeroMassimoPartecipanti) {
        this.numeroMassimoPartecipanti = numeroMassimoPartecipanti;
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

    public void setNumeroPartecipanti(int numeroPartecipanti) {
        this.numeroPartecipanti = numeroPartecipanti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_amministratore() {
        return id_amministratore;
    }

    public void setId_amministratore(int id_amministratore) {
        this.id_amministratore = id_amministratore;
    }
}


