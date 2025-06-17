package entity;

import DTO.DTOEvento;
import DTO.DTOUtente;
import database.UtenteDAO;
import exceptions.BigliettoNotFoundException;
import exceptions.RedundancyException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe EntityAmministratore rappresenta un amministratore del sistema,
 * specializzato dall'entità utente registrato. Questa classe gestisce gli
 * eventi creati e pubblicati dall'amministratore, fornendo metodi per
 * il caricamento, la creazione e la gestione degli eventi.
 */
public class EntityAmministratore extends EntityUtenteRegistrato {
    /**
     * Lista degli eventi associati all'amministratore corrente.
     * Contiene oggetti di tipo {@code EntityEvento} che rappresentano gli eventi creati
     * o pubblicati dall'amministratore.
     */
    private ArrayList<EntityEvento> eventi;

    /**
     * Costruttore predefinito della classe {@code EntityAmministratore}.
     * Inizializza un oggetto vuoto senza impostare alcun attributo o lista
     * associata.
     */
    public EntityAmministratore() {}

    /**
     * Costruttore della classe {@code EntityAmministratore}.
     * Inizializza un oggetto {@code EntityAmministratore} con i dati provenienti
     * da un'istanza di {@code UtenteDAO}, impostando i relativi attributi e
     * inizializzando la lista degli eventi associati all'amministratore.
     *
     * @param utente un oggetto di tipo {@code UtenteDAO} contenente le informazioni
     *               dell'utente da cui derivare i dati per l'oggetto amministratore
     */
    public EntityAmministratore(UtenteDAO utente){
        this.id=utente.getId();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.eventi=new ArrayList<>();
    }

    /**
     * Carica gli eventi pubblicati dall'amministratore corrente e li salva nella lista interna degli eventi.
     * Questa operazione recupera i dati utilizzando il metodo {@code get_EventiPubblicati}
     * della classe {@code EntityCatalogo}.
     */

    public Map<DTOEvento, Object> consultaEventiPubblicati() throws BigliettoNotFoundException {
        Map<DTOEvento, Object> eventoPartecipantiMap = new HashMap<>();
        LocalDate oggi = LocalDate.now();
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        this.eventi = catalogo.getEventiPubblicati(this);
        for (EntityEvento evento : this.getEventiPubblicati()) {
            DTOEvento dtoEvento = new DTOEvento(evento.getTitolo(), evento.getDescrizione(), evento.getData(), evento.getOra(), evento.getLuogo(), evento.getCosto(), evento.getCapienza(), evento.getNumeroBigliettiVenduti());
            Map<String, Object> infoEvento = new HashMap<>();
            infoEvento.put("bigliettiVenduti", evento.getNumeroBigliettiVenduti());
            if (evento.getData().isEqual(oggi)) {
                infoEvento.put("numeroPartecipanti", evento.getNumeroPartecipanti());
                List<EntityCliente> partecipanti = evento.getListaPartecipanti();
                List<DTOUtente> dtoPartecipanti = new ArrayList<>();
                for (EntityCliente partecipante : partecipanti) {
                    DTOUtente dtoUtente = new DTOUtente(partecipante.getNome(), partecipante.getCognome());
                    dtoPartecipanti.add(dtoUtente);
                }
                infoEvento.put("listaPartecipanti", dtoPartecipanti);
            } else if (evento.getData().isBefore(oggi)) {
                infoEvento.put("numeroPartecipanti", evento.getNumeroPartecipanti());
            }
            dtoEvento.setBigliettiVenduti(evento.getNumeroBigliettiVenduti());
            eventoPartecipantiMap.put(dtoEvento, infoEvento);
        }
        return eventoPartecipantiMap;
    }

    /**
     * Crea un nuovo evento con i dettagli specificati e lo associa all'amministratore corrente.
     *
     * @param titolo      il titolo dell'evento
     * @param descrizione la descrizione dell'evento
     * @param data        la data dell'evento
     * @param ora         l'ora dell'evento
     * @param luogo       il luogo dell'evento
     * @param costo       il costo dell'evento
     * @param capienza    la capienza massima dell'evento
     */
    public void pubblicaEvento(String titolo, String descrizione, LocalDate data, LocalTime ora, String luogo, int costo, int capienza) throws RedundancyException{
        EntityCatalogo catalogo = EntityCatalogo.getInstance();
        if(catalogo.verificaValidita(titolo)){
            EntityEvento evento= new EntityEvento(titolo, descrizione, data, ora, luogo, costo, capienza, this);
            catalogo.aggiungiEvento(evento);
        }else{
            throw new RedundancyException("Evento già creato");
        }
    }

    /**
     * Restituisce la lista degli eventi pubblicati associati all'amministratore corrente.
     *
     * @return un oggetto {@code ArrayList} contenente gli eventi pubblicati,
     *         ciascuno rappresentato da un'istanza di {@code EntityEvento}.
     */
    public ArrayList<EntityEvento> getEventiPubblicati() {
        return eventi;
    }

    /**
     * Imposta la lista degli eventi associati all'amministratore corrente.
     *
     * @param eventi una lista di oggetti {@code EntityEvento} rappresentante gli eventi
     *               da associare all'amministratore.
     */
    public void setEventi(ArrayList<EntityEvento> eventi) {
        this.eventi = eventi;
    }
}
