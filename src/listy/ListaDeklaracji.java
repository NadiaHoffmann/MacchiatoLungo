package listy;

import instrukcje.Deklaracja;
import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class ListaDeklaracji {

    private ListaDeklaracji() {

    }

    public static ArrayList<Deklaracja> jakich(Deklaracja... parametry) {
        ArrayList<Deklaracja> lista = new ArrayList<>();
        for (Deklaracja element : parametry) {
            lista.add(element);
        }

        return lista;
    }
}
