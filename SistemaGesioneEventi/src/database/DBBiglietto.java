package database;
//test
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBBiglietto {

    private int id;
    private  String nome_titolare;
    private String codice_univoco;
    private String stato;
    private int IDcliente;
    private int IDEvento;
    private DBEvento evento;

    public DBBiglietto() {}

    public DBBiglietto(String codice_univoco){
        this.codice_univoco = codice_univoco;
        this.caricaDaDB();
    }

    public void caricaDaDB() {
        String query = "SELECT * FROM biglietti WHERE = codice_univco'" + this.codice_univoco + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.nome_titolare = rs.getString("nome_titolare");
                this.stato = rs.getString("stato");
                this.IDcliente = rs.getInt("IDcliente");
                this.IDEvento = rs.getInt("IDEvento");
            } else {
                System.out.println("Utente non trovato nel DB");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void caricaEventoBigliettoDaDB() {
        String query = "SELECT * FROM eventi WHERE = ID'" + this.IDEvento + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                DBEvento evento= new DBEvento();
                evento.setTitolo(rs.getString("Titolo"));
                evento.setIdamministratore(rs.getInt("IDamministratore"));
                evento.setCapienza(rs.getInt("capienza"));
                evento.setPartecipanti(rs.getInt("partecipanti"));
                evento.setData(LocalDate.parse(rs.getString("data")));
                evento.setOra(LocalTime.parse(rs.getString("ora")));
                evento.setDescrizione(rs.getString("descrizione"));
                evento.setLuogo(rs.getString("luogo"));
            } else {
                System.out.println("Utente non trovato nel DB");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

//NON SERVE
    public int SalvaInDB() {
        int ret = 0;
        String query = "INSERT INTO biglietti(codice_univoco,nome_titolare,stato,IDcliente,IDEvento) VALUES ( '" + this.codice_univoco + "','"+ this.nome_titolare + "','" + this.stato + "','" + this.IDcliente + "','" + this.IDEvento + "');";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }
        return ret;
    }

    public void caricaEventoBigliettoDaDB(){
        String query = "select * from eventi where ID = '" + this.IDEvento + "');";
    }
//

    public String getNome_titolare() {
        return nome_titolare;
    }

    public void setNome_titolare(String nome_titolare) {
        this.nome_titolare = nome_titolare;
    }

    public String getCodice_univoco() {
        return codice_univoco;
    }

    public void setCodice_univoco(String codice_univoco) {
        this.codice_univoco = codice_univoco;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIDcliente() {
        return IDcliente;
    }

    public void setIDcliente(int IDcliente) {
        this.IDcliente = IDcliente;
    }

    public int getIDEvento() {
        return IDEvento;
    }

    public void setIDEvento(int IDEvento) {
        this.IDEvento = IDEvento;
    }
}
