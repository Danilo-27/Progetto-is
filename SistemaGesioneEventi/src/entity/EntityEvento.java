//test commento 1
package entity;


import database.BigliettoDAO;
import database.EventoDAO;
import exceptions.DBException;

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
    private int amministratore_id;
    private ArrayList<EntityBiglietto> biglietti;
    private EntityAmministratore amministratore;

    /**
     * Costruttore che crea un EntityEvento a partire da un EventoDAO
     */
    public EntityEvento(EventoDAO evento) {
        inizializzaDaEventoDAO(evento);
        EntityPiattaforma ep =EntityPiattaforma.getInstance();
        this.amministratore=ep.cercaAmministratorePerId(evento.getAmministratoreId());
        this.biglietti = new ArrayList<>();
    }

    /**
     * Costruttore per creare un nuovo evento
     */
    public EntityEvento(String titolo, String descrizione, LocalDate data, LocalTime ora,
                       String luogo, int costo, int capienza, EntityAmministratore amministratore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.ora = ora;
        this.luogo = luogo;
        this.costo = costo;
        this.capienza = capienza;
        this.partecipanti = 0;
        this.biglietti = new ArrayList<>();
        this.amministratore = amministratore;
    }

    /**
     * Costruttore che carica un evento dal database dato il titolo
     */
    public EntityEvento(String titolo) {
        EventoDAO evento = new EventoDAO(titolo);
        inizializzaDaEventoDAO(evento);
        this.biglietti = new ArrayList<>();
        evento.caricaBigliettiEventiDaDB();
        this.caricaBiglietti(evento);
    }


    private void inizializzaDaEventoDAO(EventoDAO evento) {
        this.id = evento.getId();
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.data = evento.getData();
        this.ora = evento.getOra();
        this.luogo = evento.getLuogo();
        this.costo = evento.getCosto();
        this.capienza = evento.getCapienza();
        this.amministratore_id=evento.getAmministratoreId();
    }

    public void salvaSuDB() throws DBException{
        EventoDAO evento = new EventoDAO();
        evento.setTitolo(titolo);
        evento.setDescrizione(descrizione);
        evento.setData(data);
        evento.setOra(ora);
        evento.setLuogo(luogo);
        evento.setCosto(costo);
        evento.setCapienza(capienza);
        evento.setPartecipanti(partecipanti);
        evento.setAmministratoreId(amministratore.getId());
        evento.SalvaInDB();
    }
    public void aggiornaPartecipanti() {
        this.partecipanti++;
        EventoDAO dao = new EventoDAO();
        dao.setTitolo(this.titolo);
        dao.setPartecipanti(this.partecipanti);
        dao.AggiornaInDB();
    }
    private String creazioneIDUnivoco(){
        String eventoSanificato = this.titolo.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        String prefisso = eventoSanificato.substring(0, Math.min(3, eventoSanificato.length()));
        String uuidParte = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 8);
        return prefisso + "-" + uuidParte;
    }
    public EntityBiglietto verificaCodice(String codice) {
        for (EntityBiglietto b : this.biglietti) {
            if (b.getCodice_univoco().equals(codice)) {
                return b;
            }
        }
        return null;
    }
    public EntityBiglietto creazioneBiglietto(EntityCliente cliente) throws DBException {
        //creazione ID univoco
        String codiceUnivoco = creazioneIDUnivoco();
        //creazione di entity biglietto con param ingresso ID univoco
        EntityBiglietto biglietto = new EntityBiglietto();
        biglietto.setCodice_univoco(codiceUnivoco);
        biglietto.setEvento(this);
        biglietto.setCliente(cliente);
        //return entityBiglietto
        biglietto.scriviSuDB();
        return biglietto;
    }
    public boolean verificaDisponibilità(){
        return this.capienza>this.biglietti.size();
    }
    public void caricaBiglietti(EventoDAO eventoDAO) {
        this.biglietti = new ArrayList<>();
        if(eventoDAO.getBiglietti() !=null){
            for (BigliettoDAO bigliettoDAO : eventoDAO.getBiglietti()) {
                EntityBiglietto biglietto = new EntityBiglietto(bigliettoDAO);
                this.biglietti.add(biglietto);
            }
        }else{
            System.out.println("non ha biglietti");
        }

    }
    public ArrayList<EntityCliente> listaPartecipanti() {
        //carico in memoria tutti i biglietti associati all'evento
        EventoDAO eventoDAO = new EventoDAO(this.titolo);
        eventoDAO.caricaBigliettiEventiDaDB();
        this.caricaBiglietti(eventoDAO);
        //a questo punto per ogni biglietto conosco il codice univoco e stato
        ArrayList<EntityCliente> partecipanti=new ArrayList<>();
        for (EntityBiglietto biglietto : this.biglietti) {
            if(biglietto.getStato()==EntityBiglietto.OBLITERATO){
                partecipanti.add(biglietto.getCliente());
            }
        }
        return partecipanti;
    }

    public int getNumeroPartecipanti() {
        ArrayList<EntityCliente> partecipanti = listaPartecipanti();
        return partecipanti.size();
    }

    public int getNumeroBigliettiVenduti() {
        return this.biglietti.size();
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


    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }




    public int getId() {
        return id;
    }

    public int getAmministratore_id() {
        return amministratore_id;
    }


    public EntityAmministratore getAmministratore() {
        return amministratore;
    }

    public void setAmministratore(EntityAmministratore amministratore) {
        this.amministratore = amministratore;
    }

    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.biglietti = biglietti;
    }

    @Override
    public String toString() {
        return "EntityEvento{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data=" + data +
                ", ora=" + ora +
                ", luogo='" + luogo + '\'' +
                ", costo=" + costo +
                ", capienza=" + capienza +
                ", partecipanti=" + partecipanti +
                ", amministratore_id=" + amministratore_id +
                ", biglietti=" + biglietti +
                ", amministratore=" + amministratore +
                '}';
    }
}