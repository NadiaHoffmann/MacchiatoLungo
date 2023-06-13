package instrukcje;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyrazenia.Zmienna;
import java.util.Stack;

public interface ModyfikacjaZmiennych {
    default Zmienna getWartoscZmiennej(char nazwa, Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        int nazwaJakoIndeks = nazwa - 'a';
        if (nazwaJakoIndeks < ('a' - 'a') || nazwaJakoIndeks >= ('z' - 'a' + 1)) {
            throw new NiepoprawnaNazwaZmiennej(nazwa);
        }

        Integer[] poziom = stosPoziomow.pop();
        int i = poziom[nazwaJakoIndeks];

        if (i >= 0) {
            stosPoziomow.push(poziom);
            return stosZmiennych.elementAt(stosPoziomow.size() - 1 - i)[nazwaJakoIndeks];
        }
        else {
            return null;
        }
    }
}
