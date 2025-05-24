package control;

import entity.CatalogoEventi;
import entity.EntityEvento;

import java.util.ArrayList;

public class Controller {

    public static  ArrayList<EntityEvento> ConsultaCatalogoEventi(){

            new ArrayList();

            ArrayList<EntityEvento> lista_eventi = CatalogoEventi.getListaEventi();
            return lista_eventi;
        }
    }

