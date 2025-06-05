//test commento 1
package entity;


import DTO.DTOEvento;
import database.BigliettoDAO;
import database.EventoDAO;
import exceptions.DBException;
import exceptions.UniqueCodeException;

import java.util.UUID;
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
    private int costo;
    private int capienza;
    private int partecipanti;
    private ArrayList<EntityBiglietto> biglietti;
    private EntityUtente utente;

    public EntityEvento(EventoDAO evento) {
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.data = evento.getData();
        this.ora = evento.getOra();
        this.luogo = evento.getLuogo();
        this.costo = evento.getCosto();
        this.capienza = evento.getCapienza();
    }
    public EntityEvento(String Titolo,String Descrizione,LocalDate Data,LocalTime Ora,String Luogo,int Costo,int Capienza) {
        this.titolo = Titolo;
        this.descrizione = Descrizione;
        this.data = Data;
        this.ora=Ora;
        this.luogo = Luogo;
        this.costo = Costo;
        this.capienza = Capienza;
        this.partecipanti = 0;
        this.biglietti = new ArrayList<>();
        this.salvaSuDB();
    }
    public EntityEvento(String titolo) {
        EventoDAO evento= new EventoDAO(titolo);
        this.id = evento.getId();
        this.titolo = evento.getTitolo();
        this.data=evento.getData();
        this.ora=evento.getOra();
        this.descrizione=evento.getDescrizione();
        this.luogo=evento.getLuogo();
        this.capienza=evento.getCapienza();
        this.biglietti=new ArrayList<>();
        evento.caricaBigliettiEventiDaDB();
        this.caricaBiglietti(evento);
    }
    public void salvaSuDB(){
        EventoDAO evento = new EventoDAO();
        evento.setTitolo(titolo);
        evento.setDescrizione(descrizione);
        evento.setData(data);
        evento.setOra(ora);
        evento.setLuogo(luogo);
        evento.setCosto(costo);
        evento.setCapienza(capienza);
        evento.setPartecipanti(partecipanti);
        evento.setAmministratoreid(utente.getId());
        evento.SalvaInDB();
    }
    public int scriviSuDB() {
        EventoDAO s = new EventoDAO();
        s.setTitolo(this.titolo);
        s.setData(this.data);
        s.setOra(this.ora);
        s.setDescrizione(this.descrizione);
        s.setLuogo(this.luogo);
        s.setCapienza(this.capienza);
        s.setCosto(this.costo);
        //s.setIdamministratore(this.id_amministratore);
        return s.SalvaInDB();
    }
    public int aggiornaPartecipanti() {
        this.partecipanti++;
        EventoDAO dao = new EventoDAO();
        dao.setTitolo(this.titolo);
        dao.setPartecipanti(this.partecipanti);
        return dao.AggiornaInDB();
    }


    private String creazioneIDUnivoco(){
        String eventoSanificato = this.titolo.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        String prefisso = eventoSanificato.substring(0, Math.min(3, eventoSanificato.length()));
        String uuidParte = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 8);
        String codice=prefisso + "-" + uuidParte;
        return codice;
    }

    public EntityBiglietto verificaCodice(String codice) {
        for (EntityBiglietto b : this.biglietti) {
            if (b.getCodice_univoco().equals(codice)) {
                return b;
            }
        }
        return null;
    }

    public EntityBiglietto creazioneBiglietto(EntityUtente utente) throws DBException {
        //creazione ID univoco
        String codiceUnivoco = creazioneIDUnivoco();
        System.out.println("CODICE UNIVOCO GENERATO "+codiceUnivoco);
        //creazione di entity biglietto con param ingresso ID univoco
        EntityBiglietto biglietto = new EntityBiglietto();
        biglietto.setCodice_univoco(codiceUnivoco);
        biglietto.setEvento(this);
        biglietto.setUtente(utente);
        //return entityBiglietto
        biglietto.scriviSuDB();
        return biglietto;
    }

    public boolean verificaDisponibilita(){
        return this.capienza>this.biglietti.size();
    }

    public void caricaBiglietti(EventoDAO eventoDAO) {
        this.biglietti = new ArrayList<>();

        for (BigliettoDAO bigliettoDAO : eventoDAO.getBiglietti()) {
            EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO);
            this.biglietti.add(biglietto);
        }
    }




    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public LocalDate getData() {
        return data;
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

   public ArrayList<EntityBiglietto> getBiglietti() {
        return biglietti;
   }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    //public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
    //    this.biglietti = biglietti;
    //}

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "EntityEvento{" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", ora=" + ora +
                ", luogo='" + luogo + '\'' +
                ", costo=" + costo +
                ", capienza=" + capienza +
                ", partecipanti=" + partecipanti +
                '}';
    }

    public int getId() {
        return id;
    }

    public EntityUtente getUtente() {
        return utente;
    }

    public void setUtente(EntityUtente utente) {
        this.utente = utente;
    }
}
