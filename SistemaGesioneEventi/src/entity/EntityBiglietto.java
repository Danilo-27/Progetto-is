//test commento 1
package entity;

import database.BigliettoDAO;
import exceptions.BigliettoConsumatoException;
import exceptions.DBException;
import exceptions.RedundancyException;
import exceptions.UpdateException;

public class EntityBiglietto {

    private String codiceUnivoco;
    private int stato;
    private EntityEvento evento;
    private EntityCliente cliente;

    public static final int OBLITERATO=1;
    public static final int VALIDO=0;


    public EntityBiglietto() {}


    public EntityBiglietto(BigliettoDAO biglietto) {
        EntityPiattaforma piattaforma = EntityPiattaforma.getInstance();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        this.codiceUnivoco=biglietto.getCodice_univoco();
        this.stato=biglietto.getStato();
        this.evento=catalogo.cercaEventoPerId(biglietto.getEvento_id());
        this.cliente =piattaforma.cercaClientePerId(biglietto.getCliente_id());
    }

    public void scriviSuDB() throws RedundancyException{
        BigliettoDAO b = new BigliettoDAO();
        b.setStato(this.stato);
        b.setCodice_univoco(this.codiceUnivoco);
        b.setEvento_id(this.evento.getId());
        b.setCliente_id(this.cliente.getId());
        try {
            b.SalvaInDB();
        } catch (DBException _) {
            throw new RedundancyException("Biglietto già creato.");
        }
    }

    public void validaBiglietto() throws BigliettoConsumatoException {
        if(this.getStato()==EntityBiglietto.OBLITERATO){
            throw new BigliettoConsumatoException("Biglietto già obliterato");
        }else{
            this.setStato(EntityBiglietto.OBLITERATO);
            BigliettoDAO dao = new BigliettoDAO();
            dao.setStato(this.stato);
            dao.setCodice_univoco(this.codiceUnivoco);
            try{
                dao.aggiornaInDB();
            }catch(DBException _){
                throw new UpdateException("Errore di aggiornamento del biglietto");
            }
        }
    }

    public String getNome_titolare() {
        return this.cliente.getNome();
    }

    public void setNome_titolare(String nome_titolare) {
        this.cliente.setNome(nome_titolare);
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

    public int getClienteId() {
        return this.cliente.getId();
    }

    public void setClienteId(int clienteId) {
        this.cliente.setId(clienteId);
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

    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    public void setEvento(EntityEvento evento) {
        this.evento = evento;
    }

    public EntityCliente getCliente() {
        return cliente;
    }

    public void setCliente(EntityCliente cliente) {
        this.cliente = cliente;
    }
}
