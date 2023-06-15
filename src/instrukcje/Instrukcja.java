package instrukcje;

import wyjatki.*;
import wyrazenia.Zmienna;

import java.util.Stack;

public abstract class Instrukcja {

    protected abstract String wypisz(int liczbaTabow);

    protected abstract void wykonaj(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur)
            throws NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaZmiennej, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura;

    protected abstract void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura;

    protected abstract void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack <Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz)
            throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura;
}
