package wyrazenia;

import instrukcje.ModyfikacjaZmiennych;
import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyjatki.NiezadeklarowanaZmienna;

import java.util.Stack;

public class Zmienna extends Wyrazenie implements ModyfikacjaZmiennych {
    private char nazwa;
    private int wartosc;

    @Override
    public int wartosc(Stack <Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        if (this.getWartoscZmiennej(this.nazwa, stosZmiennych, stosPoziomow) != null) {
            return this.getWartoscZmiennej(this.nazwa, stosZmiennych, stosPoziomow).wartosc;
        }
        else {
            throw new NiezadeklarowanaZmienna(this.nazwa);
        }
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }

    public int getWartosc() {
        return wartosc;
    }

    public Zmienna(char nazwa){
        this.nazwa = nazwa;
    }

    public char getNazwa() {
        return nazwa;
    }

    @Override
    public Zmienna getWartoscZmiennej(char nazwa, Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomowZmiennych) throws NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        if (nazwa < 'a' || nazwa > 'z') {
            throw new NiepoprawnaNazwaZmiennej(this.nazwa);
        }
        return ModyfikacjaZmiennych.super.getWartoscZmiennej(nazwa, stosZmiennych, stosPoziomowZmiennych);
    }

    @Override
    public String toString() {
        return nazwa + "";
    }
}
