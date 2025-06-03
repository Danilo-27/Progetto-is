package entity;


import database.EventoDAO;

import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EntityEvento {
    private String titolo;
    private String descrizione;
    private LocalDate data;
    private LocalTime ora;
    private String luogo;
    private int capienza;
    private int partecipanti;
    private ArrayList<EntityBiglietto> biglietti;

    public EntityEvento(EventoDAO evento) {
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.data = evento.getData();
        this.ora = evento.getOra();
        this.luogo = evento.getLuogo();
    }

    public EntityEvento(String titolo) {
        EventoDAO evento= new EventoDAO(titolo);
        this.titolo = evento.getTitolo();
        this.data=evento.getData();
        this.ora=evento.getOra();
        this.descrizione=evento.getDescrizione();
        this.luogo=evento.getLuogo();
        this.capienza=evento.getCapienza();
        this.biglietti=new ArrayList<>();
        evento.caricaBigliettiEventiDaDB();
    }

    private String creazioneIDUnivoco(){
        String eventoSanificato = this.titolo.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        String prefisso = eventoSanificato.substring(0, Math.min(3, eventoSanificato.length()));
        String uuidParte = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 8);

        return prefisso + "-" + uuidParte;
    }

    private boolean verificaCodice(String codice) {
        for (EntityBiglietto b : this.biglietti) {
            if (b.getCodiceUnivoco().equals(codice)) {
                return true;
            }
        }
        return false;
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

    public int getId_amministratore() {
        return id_amministratore;
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

    public void setBiglietti(ArrayList<EntityBiglietto> biglietti) {
        this.biglietti = biglietti;
    }
}
