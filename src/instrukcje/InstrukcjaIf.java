package instrukcje;

import porownania.Porownanie;
import wyjatki.*;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class InstrukcjaIf extends Instrukcja {
    private Porownanie porownanie;
    private ArrayList<Instrukcja> instrukcjeIf, instrukcjeElse;

    // konstruktor dla If
    protected InstrukcjaIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf) {
        this.porownanie = porownanie;
        this.instrukcjeIf = instrukcjeIf;
    }

    // konstruktor dla If-Else
    protected InstrukcjaIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse) {
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
    protected void wykonaj(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych) throws NiepoprawnaWartoscZmiennej, ZmiennaJuzZadeklarowana, NiezadeklarowanaZmienna, NiepoprawnaNazwaZmiennej, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        if (porownanie.ewaluuj(stosZmiennych, stosPoziomowZmiennych)) {
            for (Instrukcja i : instrukcjeIf) {
                i.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
            }
        }
        else if (instrukcjeElse != null) {
            for (Instrukcja i : instrukcjeElse) {
                i.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych);
            }
        }
    }

    @Override
    protected void wykonajZOdpluskwiaczem(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz) throws NiepoprawnaWartoscZmiennej, NiepoprawnaNazwaZmiennej, NiezadeklarowanaZmienna, ZmiennaJuzZadeklarowana, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        if (porownanie.ewaluuj(stosZmiennych, stosPoziomowZmiennych)) {
            for (Instrukcja i : instrukcjeIf) {
                odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, i);
                odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
                i.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
            }
        }
        else if (instrukcjeElse != null) {
            for (Instrukcja i : instrukcjeElse) {
                odpluskwiacz.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, i);
                odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
                i.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
            }
        }
    }

    @Override
    protected void policzInstrukcjeWProgramie(Stack <Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Odpluskwiacz odpluskwiacz) throws NiepoprawnaNazwaZmiennej, ZmiennaJuzZadeklarowana, NiepoprawnaWartoscZmiennej, NiezadeklarowanaZmienna, NiepoprawnaNazwaProcedury, ProceduraJuzZadeklarowana, NiezadeklarowanaProcedura {
        if (porownanie.ewaluuj(stosZmiennych, stosPoziomowZmiennych)) {
            for (Instrukcja i : instrukcjeIf) {
                odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
                i.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
            }
        }
        else if (instrukcjeElse != null) {
            for (Instrukcja i : instrukcjeElse) {
                odpluskwiacz.zwiekszLicznikInstrukcjiWProgramie();
                i.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, odpluskwiacz);
            }
        }
    }

}
