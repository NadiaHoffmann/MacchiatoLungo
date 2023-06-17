package glowny;

import instrukcje.*;
import listy.*;
import wyrazenia.*;

public class Main {
    public static void main(String[] args) {
        /*ArrayList<Deklaracja> deklaracje1 = new ArrayList<>();
        deklaracje1.add(new DeklaracjaZmiennej('n', new Literal(30)));
        ArrayList<Instrukcja> instrukcje1 = new ArrayList<>();
        ArrayList<Deklaracja> deklaracje2 = new ArrayList<>();
        deklaracje2.add(new DeklaracjaZmiennej('p', new Literal(1)));
        ArrayList<Instrukcja> instrukcje2 = new ArrayList<>();
        instrukcje2.add(new PrzypisanieWartosci('k', new Dodawanie(new Zmienna('k'), new Literal(2))));
        ArrayList<Instrukcja> instrukcje3 = new ArrayList<>();
        instrukcje3.add(new PrzypisanieWartosci('i', new Dodawanie(new Zmienna('i'), new Literal(2))));
        ArrayList<Instrukcja> instrukcje4 = new ArrayList<>();
        instrukcje4.add(new PrzypisanieWartosci('p', new Literal(0)));
        instrukcje3.add(new InstrukcjaIf(new Rowne(new Modulo(new Zmienna('k'), new Zmienna('i')), new Literal(0)), instrukcje4));
        instrukcje2.add(new PetlaFor('i', new Odejmowanie(new Zmienna('k'), new Literal(2)), instrukcje3));
        ArrayList<Instrukcja> instrukcje5 = new ArrayList<>();
        instrukcje5.add(new InstrukcjaPrint(new Zmienna('k')));
        instrukcje2.add(new InstrukcjaIf(new Rowne(new Zmienna('p'), new Literal(1)), instrukcje5));
        ArrayList<Instrukcja> instrukcje6 = new ArrayList<>();
        instrukcje1.add(new PetlaFor('k', new Odejmowanie(new Zmienna('n'), new Literal(1)), instrukcje6));
        instrukcje6.add(new Blok(deklaracje2, instrukcje2));
        Program main = new Program(deklaracje1, instrukcje1);
        main.wykonaj();
        main.wykonajZOdpluskwiaczem();*/

        var program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(101))
                .zadeklarujZmienna('y', Literal.oWartosci(1))
                .zadeklarujProcedure('o', ListaWyrazen.jakich(Zmienna.oNazwie('a')), new BudowniczyBloku()
                        .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('a'), Zmienna.oNazwie('x')))
                        .zbuduj()
                )
                .przypiszWartoscZmiennej('x', Odejmowanie.odSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('y')))
                .wywolajProcedure('o', ListaWyrazen.jakich(Zmienna.oNazwie('x')))
                .wywolajProcedure('o', ListaWyrazen.jakich(Literal.oWartosci(100)))
                .dodajBlok(new BudowniczyBloku()
                        .zadeklarujZmienna('x', Literal.oWartosci(10))
                        .wywolajProcedure('o', ListaWyrazen.jakich(Literal.oWartosci(100)))
                        .zbuduj()
                )
                .zbuduj();

        program.wykonaj();
    }
}
