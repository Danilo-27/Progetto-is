package control;

import entity.CatalogoEventi;
import entity.EntityCliente;
import entity.EntityEvento;
import entity.EntityUtenteRegistrato;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Controller {

    public static  ArrayList<EntityEvento> ConsultaCatalogoEventi(){

            new ArrayList();

            ArrayList<EntityEvento> lista_eventi = CatalogoEventi.getListaEventi();
            return lista_eventi;
    }

    public static String inserisciEvento(String titolo, String descrizione, String data, String ora , String luogo, int numeroMassimoPartecipanti, int id_amministratore) {
        EntityEvento evento = new EntityEvento();
        evento.setTitolo(titolo);
        evento.setDescrizione(descrizione);
        evento.setData(LocalDate.parse(data));
        evento.setOra(LocalTime.parse(ora));
        evento.setLuogo(luogo);
        evento.setNumeroMassimoPartecipanti(numeroMassimoPartecipanti);
        evento.setId_amministratore(id_amministratore);

        if( evento.scriviSuDB() == -1)
            return "L'evento non è stato inserito";
        else
            return "L'evento è stato inserito";
    }

    public static String inserisciAmministratore(String nome,String cognome, String email, String password) {
        EntityUtenteRegistrato ua = new EntityUtenteRegistrato();
        ua.setNome(nome);
        ua.setCognome(cognome);
        ua.setEmail(email);
        ua.setPassword(password);

        if( ua.scriviSuDB() == -1)
            return "L'amministratore non è stato inserito ";
        else
            return "L'amministratore è stato inserito";
    }

    public static String inserisciAmministratore(byte[] immagineProfilo,int numPartecipazione,String nome,String cognome, String email, String password) {
        EntityCliente c = new EntityCliente();

        c.setImmagineProfilo(immagineProfilo);
        c.setNumPartecipazione(numPartecipazione);
        c.setNome(nome);
        c.setCognome(cognome);
        c.setEmail(email);
        c.setPassword(password);

        if( c.scriviSuDB() == -1)
            return "Il cliente non è stato inserito ";
        else
            return "Il cliente è stato inserito";
    }



}



