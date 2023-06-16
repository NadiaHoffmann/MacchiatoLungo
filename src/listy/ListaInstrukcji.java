package listy;

import instrukcje.Instrukcja;
import wyrazenia.Wyrazenie;

import java.util.ArrayList;

public class ListaInstrukcji {
    private ArrayList<Instrukcja> lista;

    private ListaInstrukcji() {

    }

    public ArrayList jakich(Instrukcja... parametry) {
        for (Instrukcja element : parametry) {
            lista.add(element);
        }
        return this.lista;
    }
}
