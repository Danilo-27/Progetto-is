//test commento

package control;

import DTO.DTOEvento;
import entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Controller {



    public static void test(String email){
        EntityUtente utente = new EntityUtente(email);

        System.out.println("Nome: " + utente.getNome());
        System.out.println("Cognome: " + utente.getCognome());
        System.out.println("Email: " + utente.getEmail());
        System.out.println("Password: " + utente.getPassword());
        System.out.println("Immagine: " + utente.getImmagine());


    }



}










