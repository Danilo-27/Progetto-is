//test commento

package control;

import DTO.DTOEvento;
import entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Controller {

    private static String UtenteAutenticato;

    public static ArrayList<DTOEvento> ConsultaCatalogoEventi() {

        new ArrayList();

        ArrayList<DTOEvento> lista_eventi = CatalogoEventi.getListaEventi();
        return lista_eventi;
    }

    public static String inserisciEvento(String titolo, String descrizione, String data, String ora, String luogo, int numeroMassimoPartecipanti, int id_amministratore) {
        EntityEvento evento = new EntityEvento();
        evento.setTitolo(titolo);
        evento.setDescrizione(descrizione);
        evento.setData(LocalDate.parse(data));
        evento.setOra(LocalTime.parse(ora));
        evento.setLuogo(luogo);
        evento.setCapienza(numeroMassimoPartecipanti);
        evento.setId_amministratore(id_amministratore);

        if (evento.scriviSuDB() == -1)
            return "L'evento non è stato inserito";
        else
            return "L'evento è stato inserito";
    }

    public static String ricercaCliente(String email) {
        EntityCliente c = EntityUtenteRegistrato.ricercaCliente(email);

        for(int i =0;i<c.getStoricobiglietti().size();i++){
            System.out.println(c.getStoricobiglietti().get(i).getEvento().toString());
        }

        return "Il cliente con email: " + email + " è " + c.getNome()+ ", BIGLIETTI: " + c.getStoricobiglietti().size() ;

    }

    public static ArrayList<DTOEvento> getListaEventi_con_filtro(String titolo, String data, String luogo) {
        LocalDate date = null;

        if (data != null && !data.isEmpty()) {
            date = LocalDate.parse(data);
        }

        ArrayList<DTOEvento> my_dto_list = CatalogoEventi.getListaEventi_con_filtro(titolo, date, luogo);
        return my_dto_list;
    }


    public static void registraCliente(String password, String nome, String cognome, String email) {
        EntityCliente nuovoCliente = new EntityCliente(email, password, nome, cognome);
        try {
            nuovoCliente.scriviSuDB();
        } catch (Exception e) {
            throw new RuntimeException("otheja");
        }
    }


    public static void autenticazioneUtente(String email, String password) {
            try {

                EntityCliente c =new EntityCliente();
                EntityAmministratore a = new EntityAmministratore();

                c.cercaSuDB(email);
                a.cercaSuDB(email);

                if (c.getNome() != null) {
                    if (c.verificaCredenziali(password)) {
                        UtenteAutenticato="Cliente";
                        System.out.println("Login come Cliente");
                    }else{
                        System.out.println("Password errata");
                    }

                    return;
                }

                a.cercaSuDB(email);

                if (a.getNome() != null) {
                    if (a.verificaCredenziali(password)) {
                        UtenteAutenticato="amministratore";
                        System.out.println("Login come Amministratore");
                    }else{
                        System.out.println("password errata");
                    }
                    return;
                }

                // Nessun utente trovato
                System.out.printf("Utente non trovato");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }










