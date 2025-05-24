package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;



public class DBEvento {


    private String titolo;
    private String descrizione;
    private Date data;
    private String ora;
    private String luogo;
    private int numeroMassimoPartecipanti;
    private int numeroPartecipanti;

/*    public static void main(String[] args) {
        ArrayList<DBEvento> lista = DBEvento.getListaEventi();
         for(int i = 0; i < lista.size(); i++){
             System.out.println(lista.get(i).getTitolo());
             System.out.println(lista.get(i).getDescrizione());
             System.out.println(lista.get(i).getData());
             System.out.println(lista.get(i).getOra());
             System.out.println(lista.get(i).getLuogo());
             System.out.println(lista.get(i).getNumeroMassimoPartecipanti());
             System.out.println(lista.get(i).getNumeroPartecipanti());
         }

    }
 */
    public DBEvento() {
        this.caricaDaDB();
    }


    public void caricaDaDB() {
        String query = "SELECT * FROM eventi";

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while (rs.next()) {
                this.titolo=rs.getString("Titolo");
                this.descrizione=rs.getString("Descrizione");
                this.data=rs.getDate("Data");
                this.ora=rs.getString("Ora");
                this.luogo= rs.getString("Luogo");
                this.numeroPartecipanti=rs.getInt("Numpartecipanti");
                this.numeroMassimoPartecipanti=rs.getInt("Nummaxpartecipanti");
            }
        } catch (SQLException | ClassNotFoundException e) {
                ((Exception)e).printStackTrace();
        }

    }

//metodo per prelevare tutti gli eventi dal database

    public ArrayList<DBEvento> getListaEventi() {
        ArrayList<DBEvento> lista_temp = new ArrayList();
        String query = "SELECT * FROM eventi;";

        try {
            try (ResultSet rs = DBConnectionManager.selectQuery(query)) {

                while (rs.next()) {
                    DBEvento evento_temp = new DBEvento();
                    evento_temp.setTitolo(rs.getString("Titolo"));
                    evento_temp.setDescrizione(rs.getString("Descrizione"));
                    evento_temp.setData(rs.getDate("Data"));
                    evento_temp.setOra(rs.getString("Ora"));
                    evento_temp.setOra(rs.getString("Luogo"));
                    evento_temp.setOra(rs.getString("Numpartecipanti"));
                    evento_temp.setOra(rs.getString("Nummaxpartecipanti"));

                    lista_temp.add(evento_temp);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
        }

        return lista_temp;
    }

//getter e setter

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getOra() {
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

    public Date getData() {
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

    public void setData(Date data) {
        this.data = data;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setNumeroPartecipanti(int numeroPartecipanti) {
        this.numeroPartecipanti = numeroPartecipanti;
    }
}


