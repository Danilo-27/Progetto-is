package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class BigliettoDAO{

    private int id;
    private String nome_titolare;
    private String codice_univoco;
    private int stato;
    private int Cliente_id;
    private int Evento_id;
    private EventoDAO evento;

    public BigliettoDAO() {}

    public BigliettoDAO(String codice_univoco){
        this.codice_univoco = codice_univoco;
        this.caricaDaDB();
    }

    public void caricaDaDB() {
        String query = "SELECT * FROM biglietti WHERE = codice_univco'" + this.codice_univoco + "';";
        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            if (rs.next()) {
                this.nome_titolare = rs.getString("nome_titolare");
                this.stato = rs.getInt("stato");
                this.Cliente_id = rs.getInt("Cliente_id");
                this.Evento_id = rs.getInt("IDEvento");
            } else {
                System.out.println("Utente non trovato nel DB");
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
                evento.setAmministrazioneid(rs.getInt("IDamministratore"));
                evento.setCapienza(rs.getInt("capienza"));
                evento.setPartecipanti(rs.getInt("partecipanti"));
                evento.setData(LocalDate.parse(rs.getString("data")));
                evento.setOra(LocalTime.parse(rs.getString("ora")));
                evento.setDescrizione(rs.getString("descrizione"));
                evento.setLuogo(rs.getString("luogo"));

                this.evento = evento;

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
        String query = "INSERT INTO biglietti(codice_univoco,stato,Cliente_id,IDEvento) VALUES ( '" + this.codice_univoco + "','"+ this.nome_titolare + "','" + this.stato + "','" + this.Cliente_id + "','" + this.Evento_id + "');";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }
        return ret;
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

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
