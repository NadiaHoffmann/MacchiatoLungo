package wyrazenia;

import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.NiezadeklarowanaZmienna;

import java.util.Stack;

public abstract class Wyrazenie {
    public Zmienna getWartoscZmiennej(char znak, Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        Integer[] poziom = stosPoziomow.pop();

        int i = poziom[znak - 'a'];

        if (i >= 0) {
            stosPoziomow.push(poziom);
            return stosZmiennych.elementAt(i)[znak - 'a'];
        } else {
            return null;
        }
    }

    public int priorytet(){
        return 1000;
    }

    public abstract int wartosc(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej;


    protected Wyrazenie dodajLiteral(Literal w) {
        return new Dodawanie(w, this);
    }


    protected Wyrazenie pomnozPrzezLiteral(Literal w) {
        return new Mnozenie(w, this);
    }


    protected Wyrazenie podzielPrzezLiteral(Literal w) throws NiepoprawnaWartoscZmiennej {
        return new Dzielenie(this, w);
    }


    protected Wyrazenie odejmijLiteral(Literal w) {
        return new Odejmowanie(this, w);
    }

    protected Wyrazenie moduloLiteral(Literal w) throws NiepoprawnaWartoscZmiennej {
        return new Modulo(this, w);
    }

    @Override
    public abstract String toString();
}
