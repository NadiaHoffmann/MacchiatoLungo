package instrukcje;

import wyjatki.*;
import wyrazenia.Zmienna;

import java.util.ArrayList;
import java.util.Stack;

public class Program {
    private Blok glowny;
    private Stack <Zmienna[]> stosZmiennych;

    private Stack<Procedura[]> stosProcedur;
    private Stack <Integer[]> stosPoziomowZmiennych;

    private Stack <Integer[]> stosPoziomowProcedur;

    private Program(ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
        this.glowny = new Blok(deklaracje, instrukcje);
    }

    protected Program(Blok glowny) {
        this.glowny = glowny;
    }



    public void wykonaj() {
        try {
            this.stosZmiennych = new Stack<>();
            this.stosPoziomowZmiennych = new Stack<>();
            this.stosPoziomowProcedur = new Stack<>();
            this.stosProcedur = new Stack<>();
            glowny.wykonaj(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur);
        }
        catch (NiepoprawnaWartoscZmiennej e) {
            System.out.println("Niepoprawna wartosc zmiennej w wyrazeniu " + e.getWyrazenie() + ".");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        }
         catch (ZmiennaJuzZadeklarowana e) {
             System.out.println("Zmienna " + e.getNazwa() + " juz zostala zadeklarowana.");
             this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
             System.exit(1);
         }
        catch (NiezadeklarowanaZmienna e) {
            System.out.println("Zmienna " + e.getNazwa() + " nie zostala zadeklarowana.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        }
        catch (NiepoprawnaNazwaZmiennej e) {
            System.out.println("Nazwa " + e.getNazwa() + " jest niepoprawna. Poprawne nazwy to male litery od a do z.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        } catch (NiepoprawnaNazwaProcedury e) {
            System.out.println("Nazwa " + e.getNazwa() + " jest niepoprawna nazwa procedury. Poprawne nazwy to male litery od a do z.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        } catch (ProceduraJuzZadeklarowana e) {
            System.out.println("Procedura " + e.getNazwa() + " już została zadeklarowana.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        } catch (NiezadeklarowanaProcedura e) {
            System.out.println("Zmienna " + e.getNazwa() + " nie zostala zadeklarowana.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        }
    };

    public void wykonajZOdpluskwiaczem() {
        try {
            this.stosZmiennych = new Stack<>();
            this.stosProcedur = new Stack<>();
            this.stosPoziomowZmiennych = new Stack<>();
            this.stosPoziomowProcedur = new Stack<>();
            Odpluskwiacz odpluskwiacz = new Odpluskwiacz();

            // policzInstrukcjeWProgramie wykonuje program, liczac kolejne kroki
            glowny.policzInstrukcjeWProgramie(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
            odpluskwiacz.zmniejszLicznikInstrukcjiWProgramie();
            glowny.wykonajZOdpluskwiaczem(stosZmiennych, stosProcedur, stosPoziomowZmiennych, stosPoziomowProcedur, odpluskwiacz);
        }
        catch (NiepoprawnaWartoscZmiennej e) {
            System.out.println("Niepoprawna wartosc zmiennej w wyrazeniu " + e.getWyrazenie() + ".");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        }
        catch (ZmiennaJuzZadeklarowana e) {
            System.out.println("Zmienna " + e.getNazwa() + " juz zostala zadeklarowana.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        }
        catch (NiezadeklarowanaZmienna e) {
            System.out.println("Zmienna " + e.getNazwa() + " nie zostala zadeklarowana.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        }
        catch (NiepoprawnaNazwaZmiennej e) {
            System.out.println("Nazwa " + e.getNazwa() + " jest niepoprawna nazwa zmiennej. Poprawne nazwy to male litery od a do z.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        } catch (NiepoprawnaNazwaProcedury e) {
            System.out.println("Nazwa " + e.getNazwa() + " jest niepoprawna nazwa procedury. Poprawne nazwy to male litery od a do z.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        } catch (ProceduraJuzZadeklarowana e) {
            System.out.println("Procedura " + e.getNazwa() + " już została zadeklarowana.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        } catch (NiezadeklarowanaProcedura e) {
            System.out.println("Procedura " + e.getNazwa() + " nie zostala zadeklarowana.");
            this.wypiszZmienne(stosZmiennych, stosPoziomowZmiennych);
            System.exit(1);
        }
    }

    private void wypiszZmienne(Stack <Zmienna[]> stosZmiennych, Stack <Integer[]> stosPoziomow) {
        Zmienna[] tablicaZmiennych = stosZmiennych.peek();
        Integer[] tablicaPoziomow = stosPoziomow.peek();
        String widoczneZmienne = "";
        for (int znak = 0; znak < ('z' - 'a' + 1); znak++) {
            int poziom = tablicaPoziomow[znak];
            if (poziom >= 0) {
                int wartosc = tablicaZmiennych[znak].getWartosc();
                char nazwa = (char) ('a' + znak);
                widoczneZmienne += (nazwa + " = " + wartosc + '\n');
            }
        }

        if (widoczneZmienne.equals("")) {
            System.out.println("Brak widocznych zmiennych na poziomie, na ktorym wystapil blad.");
        } else {
            widoczneZmienne = widoczneZmienne.substring(0, widoczneZmienne.length() - 1);
            System.out.println("Zmienne widoczne na poziomie, na ktorym wystapil blad:");
            System.out.println(widoczneZmienne);
        }
    }
}
