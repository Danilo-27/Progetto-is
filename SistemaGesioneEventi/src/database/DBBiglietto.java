package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBBiglietto {
    private  String nomeTitolare;
    private String codiceUnivoco;
    private String stato;

    public DBBiglietto() {}

    public DBBiglietto(String codiceUnivoco){
        this.codiceUnivoco = codiceUnivoco;
        this.caricaDaDB();
    }

    public void caricaDaDB() {
        String query = "SELECT * FROM biglietti WHERE = codiceunivoco'" + this.codiceUnivoco + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.nomeTitolare = rs.getString("nomeTitolare");
                this.stato = rs.getString("stato");
            } else {
                System.out.println("Utente non trovato nel DB");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
