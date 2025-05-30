package entity;

import database.DBBiglietto;
import database.DBCliente;
import database.DBEvento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EntityEvento {

    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int capienza;
    private int partecipanti;
    private int id_amministratore;
    private ArrayList<EntityBiglietto> biglietti;

    public EntityEvento(DBEvento evento) {
        this.id = evento.getId();
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.data = evento.getData();
        this.ora = evento.getOra();
        this.luogo = evento.getLuogo();
    }

    public EntityEvento(int id, int partecipanti, int capienza, String luogo, LocalTime ora, LocalDate data, String descrizione, String titolo) {
        this.id = id;
        this.partecipanti = partecipanti;
        this.capienza = capienza;
        this.luogo = luogo;
        this.ora = ora;
        this.data = data;
        this.descrizione = descrizione;
        this.titolo = titolo;
    }

    public EntityEvento(String titolo) {
        DBEvento evento= new DBEvento(titolo);
        this.titolo = evento.getTitolo();
        this.data=evento.getData();
        this.ora=evento.getOra();
        this.descrizione=evento.getDescrizione();
        this.luogo=evento.getLuogo();
        this.capienza=evento.getCapienza();
        this.id_amministratore=evento.getIdamministratore();
        this.biglietti=new ArrayList<>();
        evento.caricaBigliettiEventiDaDB();

    }

    public EntityEvento() {}

    private int creazioneIDUnivoco(){
        //implementazione
        return -1;
    }
    private boolean verificaCodice(){
        //implementazione
        return false;
    }


    public void caricaBiglietti(DBEvento evento) {
        for(int i = 0; i < evento.getBiglietti().size(); ++i) {
            EntityBiglietto biglietto = new EntityBiglietto((DBBiglietto)evento.getBiglietti().get(i));
            this.biglietti.add(biglietto);
        }

    }


    public int scriviSuDB() {
        DBEvento s = new DBEvento();
        s.setTitolo(this.titolo);
        s.setData(this.data);
        s.setOra(this.ora);
        s.setDescrizione(this.descrizione);
        s.setLuogo(this.luogo);
        s.setCapienza(this.capienza);
        s.setIdamministratore(this.id_amministratore);


        return s.SalvaInDB();
    }




    //getter e setter

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getData() {
        return data;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOra() {
        return ora;
    }


    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public int getPartecipanti() {
        return partecipanti;
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

    public int getId_amministratore() {
        return id_amministratore;
    }

    public void setId_amministratore(int id_amministratore) {
        this.id_amministratore = id_amministratore;
    }

    // questo dovrebbe essere il metodo private ... getListaPartecipanti()
    public ArrayList<EntityBiglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.biglietti = biglietti;
    }


//toString

    @Override
    public String toString() {
        return "EntityEvento{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", ora='" + ora + '\'' +
                ", luogo='" + luogo + '\'' +
                ", capienza=" + capienza +
                ", partecipanti=" + partecipanti +
                '}';
    }

}
