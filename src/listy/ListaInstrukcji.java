package listy;

import instrukcje.Instrukcja;
import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class ListaInstrukcji {

    private ListaInstrukcji() {

    }

    public ArrayList<Instrukcja> jakich(Instrukcja... parametry) {
        ArrayList<Instrukcja> lista = new ArrayList<>();
        for (Instrukcja element : parametry) {
            lista.add(element);
        }
        return lista;
    }
}
