package listy;

import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaZmiennych {
    private ListaZmiennych() {
    }

    public static ArrayList<Zmienna> jakich(Zmienna... parametry) {
        return new ArrayList<>(Arrays.asList(parametry));
    }
}
