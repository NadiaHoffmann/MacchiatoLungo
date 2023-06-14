package instrukcje;

import porownania.Porownanie;
import wyjatki.NiepoprawnaNazwaZmiennej;
import wyjatki.NiezadeklarowanaZmienna;
import wyjatki.ZmiennaJuzZadeklarowana;
import wyjatki.NiepoprawnaWartoscZmiennej;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Stack;

public class InstrukcjaIf extends Instrukcja {
    private Porownanie porownanie;
    private ArrayList<Instrukcja> instrukcjeIf, instrukcjeElse;

    // konstruktor dla If
    public InstrukcjaIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf) {
        this.porownanie = porownanie;
        this.instrukcjeIf = instrukcjeIf;
    }

    // konstruktor dla If-Else
    public InstrukcjaIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse) {
        this.porownanie = porownanie;
        this.instrukcjeIf = instrukcjeIf;
        this.instrukcjeElse = instrukcjeElse;
    }

    @Override
    protected String wypisz(int liczbaTabow) {
        String tab = "";
        for (int i = 0; i < liczbaTabow; i++) {
            tab += '\t';
        }

        String tekst = "\tif (" + porownanie + ")\n";
        for (Instrukcja i : instrukcjeIf) {
            tekst += tab;
            tekst += i.wypisz(liczbaTabow + 1);
            tekst += '\n';
        }
        if (instrukcjeElse != null) {
            tekst += "\telse\n";
            for (Instrukcja i : instrukcjeElse) {
                tekst += tab;
                tekst += i.wypisz(liczbaTabow + 1);
                tekst += '\n';
            }
        }
        tekst = tekst.substring(0, tekst.length() - 1);
        return tekst;
    }

    @Override
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur) throws NiepoprawnaWartoscZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej {
        if (porownanie.ewaluuj(stosZmiennych, stosPoziomowZmiennych)) {
            for (Instrukcja i : instrukcjeIf) {
                i.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
            }
        }
        else if (instrukcjeElse != null) {
            for (Instrukcja i : instrukcjeElse) {
                i.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
            }
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana {
        if (porownanie.ewaluuj(stosZmiennych, stosPoziomowZmiennych)) {
            for (Instrukcja i : instrukcjeIf) {
                odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosPoziomowZmiennych, i);
                odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
                i.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
            }
        }
        else if (instrukcjeElse != null) {
            for (Instrukcja i : instrukcjeElse) {
                odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosPoziomowZmiennych, i);
                odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
                i.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
            }
        }
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<Procedura[]> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Stack<Integer[]> stosPoziomowProcedur, Odpluskwiacz odpluskwiacz) throws NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna {
        if (porownanie.ewaluuj(stosZmiennych, stosPoziomowZmiennych)) {
            for (Instrukcja i : instrukcjeIf) {
                odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
                i.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
            }
        }
        else if (instrukcjeElse != null) {
            for (Instrukcja i : instrukcjeElse) {
                odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
                i.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
            }
        }
    }

}
