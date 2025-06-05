//test commento 1
package entity;

import database.BigliettoDAO;
import exceptions.BigliettoConsumatoException;
import exceptions.DBException;

public class EntityBiglietto {

    private String codiceUnivoco;
    private int stato;
    private EntityEvento evento;
    private EntityUtente utente;

    public static final int OBLITERATO=1;
    public static final int VALIDO=0;


    public EntityBiglietto() {}

    public EntityBiglietto(BigliettoDAO biglietto) {
        this.codiceUnivoco=biglietto.getCodice_univoco();
        this.stato=biglietto.getStato();
    }

    //costruttore per far caricare da parte dell'evento tutti i biglietti e i clienti che hanno acquistato il biglietto
    public EntityBiglietto(BigliettoDAO biglietto,EntityEvento evento) throws DBException {
        this.codiceUnivoco=biglietto.getCodice_univoco();
        this.stato=biglietto.getStato();
        this.evento=evento;
        this.utente=new EntityUtente(biglietto.getCliente_id());
    }



    public void getInfoPartecipante(){

    }

//    public EntityBiglietto(String codiceUnivoco){
//        BigliettoDAO dao=new BigliettoDAO(codiceUnivoco);
//        this.codiceUnivoco=dao.getCodice_univoco();
//        this.stato=dao.getStato();
//        this.caricaEvento(dao);
//        this.caricaUtente(dao);
//    }

    public int scriviSuDB() throws DBException {
        BigliettoDAO b = new BigliettoDAO();
        b.setStato(this.stato);
        b.setCodice_univoco(this.codiceUnivoco);
        System.out.println(this.evento.getId());
        b.setEvento_id(this.evento.getId());
        System.out.println(this.utente.getId());
        b.setCliente_id(this.utente.getId());
        return b.SalvaInDB();
    }
/*
    public BigliettoDAO creaBigliettoDao(){
        BigliettoDAO bigliettoDao = new BigliettoDAO(this.codiceUnivoco);
    }
*/
    public void caricaEvento(BigliettoDAO biglietto) {
        //devo prendere l'evento dal biglietto che ho creato mediante il metodo
        //caricaEventoBigliettoDaDB, chiamato nel costruttore ""public EntityBiglietto(DBBiglietto biglietto)""
        EntityEvento evento = new EntityEvento(biglietto.getEvento());
        this.evento=evento;
    }
    public void caricaUtente(BigliettoDAO biglietto) {
        //devo prendere l'evento dal biglietto che ho creato mediante il metodo
        //caricaEventoBigliettoDaDB, chiamato nel costruttore ""public EntityBiglietto(DBBiglietto biglietto)""
        EntityUtente utente = new EntityUtente(biglietto.getUtente());
        this.utente=utente;
    }



    //DA IMPLEMENTARE
    public boolean verificaBiglietto(){
        //codice
        return false;
    }

    public int validaBiglietto() throws BigliettoConsumatoException {
        if(this.getStato()==EntityBiglietto.OBLITERATO){
            throw new BigliettoConsumatoException("Biglietto già obliterato");
        }else{
            this.setStato(EntityBiglietto.OBLITERATO);
            BigliettoDAO dao = new BigliettoDAO();
            dao.setStato(this.stato);
            dao.setCodice_univoco(this.codiceUnivoco);
            return dao.aggiornaInDB();
        }
    }

    public String getNome_titolare() {
        return this.utente.getNome();
    }

    public void setNome_titolare(String nome_titolare) {
        this.utente.setNome(nome_titolare);
    }

    public String getCodice_univoco() {
        return this.codiceUnivoco;
    }

    public void setCodice_univoco(String codice_univoco) {
        this.codiceUnivoco = codice_univoco;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public int getIDcliente() {
        return this.utente.getId();
    }

    public void setIDcliente(int IDcliente) {
        this.utente.setId(IDcliente);
    }

    public String getTitoloEvento() {
        return this.evento.getTitolo();
    }

    public void setTitoloEvento(String titolo) {
        this.evento.setTitolo(titolo);
    }

    public EntityEvento getEvento() {
        return evento;
    }

    public void setEvento(EntityEvento evento) {
        this.evento = evento;
    }
    public EntityUtente getUtente() {
        return this.utente;
    }
    public void setUtente(EntityUtente utente) {
        this.utente = utente;
    }
}
