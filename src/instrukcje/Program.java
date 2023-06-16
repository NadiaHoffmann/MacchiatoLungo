package instrukcje;

import porownania.Porownanie;
import wyjatki.*;
import wyrazenia.Wyrazenie;
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

    private Program(Blok glowny) {
        this.glowny = glowny;
    }

    public static final class BudowniczyProgramu implements BudowniczyInstrukcji {
        private Blok glowny;

        public BudowniczyProgramu() {}

        public BudowniczyProgramu zadeklarujZmienna(char nazwa, Wyrazenie wyrazenie) {
            glowny.dodajDeklaracje(new DeklaracjaZmiennej(nazwa, wyrazenie));
            return this;
        }

        public BudowniczyProgramu zadeklarujProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
            glowny.dodajDeklaracje(new DeklaracjaProcedury(nazwa, parametry, deklaracje, instrukcje));
            return this;
        }

        public BudowniczyProgramu wywolajProcedure(char nazwa, ArrayList<Wyrazenie> parametry, ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
            glowny.dodajInstrukcje(new Procedura(nazwa, parametry, deklaracje, instrukcje));
            return this;
        }

        public BudowniczyProgramu wywolajInstrukcjeIf(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf) {
            glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf));
            return this;
        }

        public BudowniczyProgramu wywolajInstrukcjeIfElse(Porownanie porownanie, ArrayList<Instrukcja> instrukcjeIf, ArrayList<Instrukcja> instrukcjeElse) {
            glowny.dodajInstrukcje(new InstrukcjaIf(porownanie, instrukcjeIf, instrukcjeElse));
            return this;
        }

        public BudowniczyProgramu wypisz(Wyrazenie wyrazenie) {
            glowny.dodajInstrukcje(new InstrukcjaPrint(wyrazenie));
            return this;
        }

        public BudowniczyProgramu wywolajPetleFor(char nazwa, Wyrazenie wyrazenie, ArrayList<Instrukcja> instrukcje) {
            glowny.dodajInstrukcje(new PetlaFor(nazwa, wyrazenie, instrukcje));
            return this;
        }

        public BudowniczyProgramu przypiszWartoscZmiennej(char nazwa, Wyrazenie wyrazenie) {
            glowny.dodajInstrukcje(new PrzypisanieWartosci(nazwa, wyrazenie));
            return this;
        }

        public BudowniczyProgramu dodajBlok(ArrayList<Deklaracja> deklaracje, ArrayList<Instrukcja> instrukcje) {
            glowny.dodajInstrukcje(new Blok(deklaracje, instrukcje));
            return this;
        }

        public Program zbuduj() {
            return new Program(this.glowny);
        }
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
