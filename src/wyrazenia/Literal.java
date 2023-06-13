package wyrazenia;

import wyjatki.NiepoprawnaWartoscZmiennej;

import java.util.Stack;

public class Literal extends Wyrazenie {
    private final int wartosc;

    public Literal(int wartosc) {
        this.wartosc = wartosc;
    }

    @Override
    public String toString() {
        return String.valueOf(wartosc);
    }

    @Override
    public int wartosc(Stack<Zmienna[]> stosZmiennych, Stack<Integer[]> stosPoziomow) throws NiepoprawnaWartoscZmiennej {
        return wartosc;
    }

    public Wyrazenie pomnozPrzezLiteral(Literal w) {
        return new Literal(w.wartosc * wartosc);
    }


    public Wyrazenie dodajLiteral(Literal w) {
        return new Literal(w.wartosc + wartosc);
    }


    public Wyrazenie odejmijLiteral(Literal w) {
        return new Literal(w.wartosc - wartosc);
    }


    public Wyrazenie podzielPrzezLiteral(Literal w) throws NiepoprawnaWartoscZmiennej {
        if (wartosc == 0) {
            throw new NiepoprawnaWartoscZmiennej(new Dzielenie(w, new Literal(wartosc)));
        }

        return new Literal(w.wartosc / wartosc);
    }


    public Wyrazenie moduloLiteral(Literal w) throws NiepoprawnaWartoscZmiennej {
        if (wartosc == 0) {
            throw new NiepoprawnaWartoscZmiennej(new Modulo(new Literal(w.wartosc), new Literal(wartosc)));
        }

        return new Literal(w.wartosc / wartosc);
    }

}
