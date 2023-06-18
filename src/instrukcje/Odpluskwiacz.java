package instrukcje;

import wyjatki.NiepoprawnaLiczbaKrokow;
import wyjatki.NiepoprawnyPoziom;
import wyjatki.PustaKomenda;
import wyrazenia.Zmienna;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Odpluskwiacz {
    private char instrukcjaDebuggera;
    private int liczbaKrokow;
    private int liczbaInstrukcjiWProgramie;

    protected Odpluskwiacz() {
        this.instrukcjaDebuggera = '.';
        this.liczbaKrokow = -1;
    }

    protected void zwiekszLicznikInstrukcjiWProgramie() {
        this.liczbaInstrukcjiWProgramie++;
    }

    protected void zmniejszLicznikInstrukcjiWProgramie() {
        this.liczbaInstrukcjiWProgramie--;
    }

    private void zmniejszLicznikKrokow() {
        this.liczbaKrokow--;
    }

    private void ustawInstrukcjeDebugera(char instrukcjaDebuggera) {
        this.instrukcjaDebuggera = instrukcjaDebuggera;
    }

    private void ustawLicznik(int licznik) {
        this.liczbaKrokow = licznik;
    }

    protected void puscOdpluskwiacz(Stack<Zmienna[]> stosZmiennych, Stack<HashMap<String, Procedura>> stosProcedur, Stack<Integer[]> stosPoziomowZmiennych, Instrukcja instrukcja) {
        char obecnaInstrukcjaDebuggera = this.instrukcjaDebuggera;

        try {
            if (obecnaInstrukcjaDebuggera == 's') {
                if (this.liczbaInstrukcjiWProgramie == 0) {
                    System.out.println("Program zakonczyl sie " + this.liczbaKrokow + " krokow przed koncem dzialania komendy s(tep).");
                }
                else if (this.liczbaKrokow > 0) {
                    this.zmniejszLicznikKrokow();
                }
                else {
                    System.out.println("Nastepna instrukcja: ");
                    System.out.println(instrukcja.wypisz(0));
                    this.ustawInstrukcjeDebugera('.');
                    this.ustawLicznik(-1);
                }
            }
            else if (this.instrukcjaDebuggera == 'c') {
                if (this.liczbaInstrukcjiWProgramie == 0) {
                    System.out.println("Program zakonczyl dzialanie.");
                }
            }
            else {
                System.out.println("Podaj instrukcje dla odpluskwiacza: ");
                Scanner scanner = new Scanner(System.in);
                String daneWejsciowe = scanner.nextLine();
                char znakInstrukcji;
                try {
                    znakInstrukcji = daneWejsciowe.charAt(0);
                }
                catch (StringIndexOutOfBoundsException e) {
                    throw new PustaKomenda();
                }

                znakInstrukcji = daneWejsciowe.charAt(0);
                switch (znakInstrukcji) {
                    case 'e' -> {
                        System.out.println("Zakonczenie dzialania odpluskwiacza oraz programu.");
                        System.exit(0);
                    }
                    case 'c' -> this.ustawInstrukcjeDebugera('c');
                    case 's' -> {
                        daneWejsciowe = daneWejsciowe.replaceAll("\\s", "");
                        daneWejsciowe = daneWejsciowe.replaceAll("s", "");
                        try {
                            int liczbaKrokow = Integer.parseInt(daneWejsciowe);
                            if (liczbaKrokow <= 0) {
                                throw new NiepoprawnaLiczbaKrokow(daneWejsciowe);
                            }
                            if (liczbaKrokow == 1) {
                                System.out.println("Nastepna instrukcja:");
                                System.out.println(instrukcja.wypisz(0));
                                this.ustawInstrukcjeDebugera('.');
                                this.ustawLicznik(-1);
                            } else {
                                this.ustawInstrukcjeDebugera('s');
                                this.ustawLicznik(liczbaKrokow - 1);
                            }
                        } catch (NumberFormatException e) {
                            throw new NiepoprawnaLiczbaKrokow(daneWejsciowe);
                        }
                    }
                    case 'd' -> {
                        int liczbaPoziomow;
                        daneWejsciowe = daneWejsciowe.replaceAll("\\s", "");
                        daneWejsciowe = daneWejsciowe.replaceAll("d", "");
                        try {
                            liczbaPoziomow = Integer.parseInt(daneWejsciowe);
                            if (liczbaPoziomow < 0) {
                                throw new NiepoprawnyPoziom(daneWejsciowe);
                            }
                        } catch (NumberFormatException e) {
                            throw new NiepoprawnyPoziom(daneWejsciowe);
                        }
                        if (liczbaPoziomow >= stosPoziomowZmiennych.size()) {
                            throw new NiepoprawnyPoziom(String.valueOf(liczbaPoziomow));
                        } else {
                            Integer[] poziomyZmiennychZPoziomu = stosPoziomowZmiennych.elementAt(liczbaPoziomow);
                            Zmienna[] tablicaZmiennychZPoziomu = stosZmiennych.elementAt(liczbaPoziomow);
                            String widoczneZmienne = "";
                            for (int znak = 0; znak < ('z' - 'a' + 1); znak++) {
                                int poziom = poziomyZmiennychZPoziomu[znak];
                                if (poziom >= 0) {
                                    int wartosc = tablicaZmiennychZPoziomu[znak].getWartosc();
                                    char nazwa = (char) ('a' + znak);
                                    widoczneZmienne += (nazwa + " = " + wartosc + '\n');
                                }
                            }

                            if (widoczneZmienne.equals("")) {
                                System.out.println("Brak widocznych zmiennych na poziomie " + liczbaPoziomow + ".");
                            } else {
                                widoczneZmienne = widoczneZmienne.substring(0, widoczneZmienne.length() - 1);
                                System.out.println("Zmienne widoczne na poziomie " + liczbaPoziomow + ":");
                                System.out.println(widoczneZmienne);
                            }

                            this.ustawLicznik(-1);
                            this.ustawInstrukcjeDebugera('.');
                            this.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, instrukcja);
                        }
                    }
                    case 'm' -> {
                        String sciezkaPliku = daneWejsciowe.substring(2);
                        String daneDoZapisu = "Widoczne zmienne: \n";
                        Integer[] poziomyZmiennychZPoziomu = stosPoziomowZmiennych.peek();
                        Zmienna[] tablicaZmiennychZPoziomu = stosZmiennych.peek();

                        String widoczneZmienne = "";
                        for (int znak = 0; znak < ('z' - 'a' + 1); znak++) {
                            int poziom = poziomyZmiennychZPoziomu[znak];
                            if (poziom >= 0) {
                                int wartosc = tablicaZmiennychZPoziomu[znak].getWartosc();
                                char nazwa = (char) ('a' + znak);
                                widoczneZmienne += (nazwa + " = " + wartosc + '\n');
                            }
                        }

                        if (widoczneZmienne.equals("")) {
                            daneDoZapisu += "brak";
                        } else {
                            daneDoZapisu += widoczneZmienne;
                        }

                        daneDoZapisu += "Widoczne procedury: \n";

                        HashMap<String, Procedura> mapaProcedurZPoziomu = stosProcedur.peek();
                        String widoczneProcedury = "";

                        for (String nazwa : mapaProcedurZPoziomu.keySet()) {
                            ArrayList<Zmienna> parametryZProcedury = mapaProcedurZPoziomu.get(nazwa).getParametry();

                            String parametry = "";
                            for (Zmienna z : parametryZProcedury) {
                                parametry += (z.toString() + ", ");
                            }

                            parametry = parametry.substring(0, parametry.length() - 2);
                            widoczneProcedury += (nazwa + "(" + parametry + ")\n");
                        }

                        if (widoczneProcedury.equals("")) {
                            daneDoZapisu += "brak";
                        } else {
                            daneDoZapisu += widoczneProcedury;
                        }

                        try {
                            File plik = new File(sciezkaPliku);
                            plik.createNewFile();
                            FileWriter zapisywacz = new FileWriter(sciezkaPliku);
                            zapisywacz.write(daneDoZapisu);
                            zapisywacz.close();
                        }
                        catch (IOException e) {
                            System.out.println("Zapis do pliku " + sciezkaPliku + " nie powiódł się.");
                        }

                        this.ustawLicznik(-1);
                        this.ustawInstrukcjeDebugera('.');
                        this.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, instrukcja);
                    }
                    default -> {
                        System.out.println(znakInstrukcji + " nie jest poprawna instrukcja odpluskwiacza.");
                        System.out.println("Dostepne instrukcje: c(ontinue), s(tep) <liczba>, d(isplay) <liczba>, e(xit).");
                        this.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, instrukcja);
                    }
                }
            }
        }
        catch (PustaKomenda e) {
            System.out.println("Nie zostala wpisana zadna komenda.");
            System.out.println("Dostepne instrukcje: c(ontinue), s(tep) <liczba>, d(isplay) <liczba>, e(xit).");
            this.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, instrukcja);
        }
        catch (NiepoprawnaLiczbaKrokow e) {
            System.out.println(e.getLiczba() + " nie jest poprawna liczba krokow.");
            System.out.println("Poprawna liczba krokow jest wieksza od 0.");
            this.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, instrukcja);
        }
        catch (NiepoprawnyPoziom e) {
            System.out.println(e.getLiczba() + " nie jest poprawnym poziomem.");
            System.out.println("Poprawny poziom jest wiekszy rowny 0 i mniejszy od " + stosPoziomowZmiennych.size() + ".");
            this.puscOdpluskwiacz(stosZmiennych, stosProcedur, stosPoziomowZmiennych, instrukcja);
        }

    }
}
