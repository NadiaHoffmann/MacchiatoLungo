package glowny;

import instrukcje.*;
import listy.*;
import wyrazenia.*;

public class Main {
    public static void main(String[] args) {
        var program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(101))
                .zadeklarujZmienna('y', Literal.oWartosci(1))
                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('y'), Zmienna.oNazwie('x')))
                .zadeklarujProcedure("o", ListaZmiennych.jakich(Zmienna.oNazwie('a')),
                        new BudowniczyBloku()
                        .przypiszWartoscZmiennej('x', Odejmowanie.odSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('a')))
                        .wypisz(Zmienna.oNazwie('x'))
                        .zbuduj()
                )
                .wywolajProcedure("o", ListaWyrazen.jakich(Zmienna.oNazwie('x')))
                .wywolajProcedure("o", ListaWyrazen.jakich(Literal.oWartosci(100)))
                .dodajBlok(new BudowniczyBloku()
                        .zadeklarujZmienna('x', Literal.oWartosci(10))
                        .wywolajProcedure("o", ListaWyrazen.jakich(Literal.oWartosci(100)))
                        .wypisz(Zmienna.oNazwie('y'))
                        .zbuduj()
                )
                .zbuduj();

        program.wykonaj();
        program.wykonajZOdpluskwiaczem();
    }
}
