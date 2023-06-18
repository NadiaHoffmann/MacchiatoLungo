package instrukcje;

import wyjatki.*;
import wyrazenia.Wyrazenie;
import wyrazenia.Zmienna;

import java.util.HashMap;
import java.util.Stack;

public class InstrukcjaPrint extends Instrukcja {
    private final Wyrazenie wyrazenie;

    protected InstrukcjaPrint(Wyrazenie wyrazenie) {
        this.wyrazenie = wyrazenie;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        return "\t" + "print " + this.wyrazenie;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych) throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        String wartosc;
        try {
            wartosc = String.valueOf(this.wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych));
        }
        catch (Exception e) {
            wartosc = "";
        }
        System.out.println(wartosc);
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        try {
            this.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
        } catch (ZmiennaJuzZadeklarowana zmiennaJuzZadeklarowana) {
            throw new RuntimeException(zmiennaJuzZadeklarowana);
        } catch (ProceduraJuzZadeklarowana proceduraJuzZadeklarowana) {
            throw new RuntimeException(proceduraJuzZadeklarowana);
        } catch (NiezadeklarowanaProcedura niezadeklarowanaProcedura) {
            throw new RuntimeException(niezadeklarowanaProcedura);
        }
    }

    // nie wypisuje, bo tylko liczy kroki
    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        String wartosc = String.valueOf(this.wyrazenie.wartosc(stosZmiennych, stosPoziomowZmiennych));
    }
}
