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
    private int Amministratoreid;
    private ArrayList<BigliettoDAO> biglietti;

    //costruttore vuoto
    public EventoDAO(){}

    //costruttore con titolo
    public EventoDAO(String titolo){
        this.titolo=titolo;
        this.biglietti=new ArrayList<>();
        try {
            this.caricaDaDB();
        }catch(DBException e){

        }
    }

    //metodo per prelevare tutti gli eventi dal database

    public static ArrayList<EventoDAO> getEventi() throws DBException {
        ArrayList<EventoDAO> lista_temp = new ArrayList<>();
        String query = "SELECT * FROM eventi;";

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
                    evento_temp.setAmministrazioneid(rs.getInt("Amministratore_id"));
                    evento_temp.setCosto(rs.getInt("Costo"));


                    lista_temp.add(evento_temp);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(String.format("Errore nel caricamento degli eventi.%n%s", e.getMessage()));
        }

        return lista_temp;
    }

    public int SalvaInDB() {
        int ret = 0;
        String query = "INSERT INTO eventi (titolo,descrizione,data,orario,luogo,capienza,Amministratore_id) VALUES ( '" + this.titolo + "','"+ this.descrizione+ "','" + this.data + "','" + this.ora + "','" + this.luogo + "','"+ this.capienza + "','"+ this.Amministratoreid +"')";
        try {
            ret = DBConnectionManager.updateQuery(query);
        } catch (SQLException | ClassNotFoundException e) {
            ((Exception)e).printStackTrace();
            ret = -1;
        }

        return ret;
    }
    public void caricaDaDB() throws DBException{
        String query = "SELECT * FROM eventi WHERE titolo='" + this.titolo + "';";
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

        String query = new String("select * from biglietti where IDEvento IN (select id from eventi where titolo = '" + this.titolo + "')");

        try {
            ResultSet rs = DBConnectionManager.selectQuery(query);
            while(rs.next()) {
                BigliettoDAO biglietto = new BigliettoDAO();
                biglietto.setCodice_univoco(rs.getString("codice_univoco"));
                biglietto.setStato(rs.getInt("stato"));
                biglietto.setCliente_id(rs.getInt("IDcliente"));
                biglietto.setEvento_id(rs.getInt("IDEvento"));

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

    public int getAmministrazioneid() {
        return Amministratoreid;
    }

    public void setAmministrazioneid(int amministratoreid) {
        this.Amministratoreid = amministratoreid;
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

    public int getAmministratoreid() {
        return Amministratoreid;
    }



    //    //METODO PER FARE IL FILTRAGGIO DEGLI EVENTI PER DATA LUOGO O TITOLO DAL DATABASE
//    public void caricaDaDB(String titolo,String luogo,LocalDate data) {
//
//        // Inizializzo la query base con WHERE 1=1 per facilitare l'aggiunta dinamica di filtri
//        StringBuilder query = new StringBuilder( "SELECT * FROM eventi WHERE 1=1");
//
//        // Controllo se il parametro 'titolo' è non nullo e non vuoto
//        if (titolo != null && !titolo.isEmpty()) {
//            // Aggiungo una condizione LIKE per il titolo, con percentuali per la ricerca parziale
//            // Prima di concatenare il titolo, sostituisco ogni singolo apostrofo con due apostrofi per evitare errori SQL
//            query.append(" AND titolo LIKE '%").append(titolo.replace("'", "''")).append("%");
//        }
//
//        // Controllo se il parametro 'luogo' è valorizzato (non nullo e non vuoto)
//        if (luogo != null && !luogo.isEmpty()) {
//            // Aggiungo una condizione LIKE per il luogo, con percentuali per la ricerca parziale
//            // Anche qui sostituisco ogni apostrofo con due apostrofi
//            query.append(" AND luogo LIKE '%").append(luogo.replace("'", "''")).append("%'");
//        }
//
//        // Controllo se il parametro 'data' è valorizzato (non nullo)
//        if (data != null) {
//            // Aggiungo una condizione di uguaglianza per la data, formattandola come stringa ISO
//            query.append(" AND data = '").append(Date.valueOf(data).toString()).append("'");
//        }
//
//        try {
//            // Eseguo la query costruita dinamicamente passando la stringa a selectQuery
//            ResultSet rs = DBConnectionManager.selectQuery(query.toString());
//
//            // Se la query ha restituito almeno una riga, popolo i campi dell'oggetto con i dati ottenuti
//            if (rs.next()) {
//                this.id = rs.getInt("id");
//
//                // Se i campi filtro sono nulli, li recupero dal DB
//                if (this.titolo == null) {
//                    this.titolo = rs.getString("titolo");
//                }
//                if (this.luogo == null) {
//                    this.luogo = rs.getString("luogo");
//                }
//                if (this.data == null) {
//                    this.data = rs.getDate("data").toLocalDate();
//                }
//
//                // Recupero sempre gli altri campi
//                this.descrizione = rs.getString("descrizione");
//                this.ora = rs.getTime("ora").toLocalTime();
//                this.costo = rs.getInt("costo");
//                this.capienza = rs.getInt("capienza");
//                this.partecipanti = rs.getInt("partecipanti");
//                this.Amministrazioneid = rs.getInt("Amministrazioneid");
//            }
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//
//   }

}


