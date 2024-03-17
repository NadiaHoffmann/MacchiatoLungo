package testy;

import instrukcje.Blok;
import instrukcje.BudowniczyBloku;
import instrukcje.BudowniczyProgramu;
import instrukcje.Program;
import listy.ListaWyrazen;
import listy.ListaZmiennych;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import porownania.Mniejsze;
import porownania.MniejszeRowne;
import wyrazenia.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestyInstrukcji {
    private final PrintStream standardoweWyjscie = System.out;
    private final ByteArrayOutputStream ciagNaWyjsciu = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(ciagNaWyjsciu));
    }

    @Test
    public void TestBlok(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('a', Literal.oWartosci(55))
                .zadeklarujProcedure("test", ListaZmiennych.jakich(Zmienna.oNazwie('b'), Zmienna.oNazwie('c')),
                        new BudowniczyBloku()
                                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('b'), Zmienna.oNazwie('c')))
                                .zbuduj())
                .dodajBlok(new BudowniczyBloku()
                        .zadeklarujZmienna('a', Literal.oWartosci(123))
                        .zadeklarujProcedure("test", ListaZmiennych.jakich(Zmienna.oNazwie('b'), Zmienna.oNazwie('c')),
                                new BudowniczyBloku()
                                        .wypisz(Odejmowanie.odSiebie(Zmienna.oNazwie('b'), Zmienna.oNazwie('c')))
                                        .zbuduj())
                        .wypisz(Zmienna.oNazwie('a'))
                        .wywolajProcedure("test", ListaWyrazen.jakich(Zmienna.oNazwie('a'), Literal.oWartosci(1)))
                        .zbuduj())
                .wypisz(Zmienna.oNazwie('a'))
                .wywolajProcedure("test", ListaWyrazen.jakich(Zmienna.oNazwie('a'), Literal.oWartosci(1)))
                .zbuduj();

        program.wykonaj();

        assertEquals("1231225556", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestDeklaracjaProcedury(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(60))
                .zadeklarujProcedure("proc", ListaZmiennych.jakich(Zmienna.oNazwie('b'), Zmienna.oNazwie('c')),
                        new BudowniczyBloku()
                                .wypisz(Modulo.z(Zmienna.oNazwie('b'), Zmienna.oNazwie('c')))
                                .zbuduj())
                .wywolajProcedure("proc", ListaWyrazen.jakich(Zmienna.oNazwie('x'), Literal.oWartosci(23)))
                .zbuduj();

        program.wykonaj();

        assertEquals("14", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestDeklaracjaZmiennej(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('b', Literal.oWartosci(10))
                .zadeklarujZmienna('a', Dzielenie.przezSiebie(Literal.oWartosci(55), Zmienna.oNazwie('b')))
                .wypisz(Zmienna.oNazwie('a'))
                .wypisz(Zmienna.oNazwie('b'))
                .zbuduj();

        program.wykonaj();

        assertEquals("510", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestInstrukcjaIfElse(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Modulo.z(Literal.oWartosci(2137), Literal.oWartosci(65)))
                .zadeklarujZmienna('z', Literal.oWartosci(56))
                .wywolajInstrukcjeIfElse(MniejszeRowne.coOdCzego(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')),
                        new BudowniczyBloku()
                                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                                .zbuduj(),
                        new BudowniczyBloku()
                                .wypisz(Odejmowanie.odSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                                .zbuduj())
                .zbuduj();

        program.wykonaj();

        assertEquals("1", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestInstrukcjaIf(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Modulo.z(Literal.oWartosci(2137), Literal.oWartosci(65)))
                .zadeklarujZmienna('z', Literal.oWartosci(56))
                .wywolajInstrukcjeIf(MniejszeRowne.coOdCzego(Zmienna.oNazwie('z'), Zmienna.oNazwie('x')),
                        new BudowniczyBloku()
                                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                                .zbuduj())
                .zbuduj();

        program.wykonaj();

        assertEquals("113", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestInstrukcjaPrint(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(50))
                .wypisz(Dodawanie.doSiebie(Dzielenie.przezSiebie(Literal.oWartosci(20), Zmienna.oNazwie('x')),
                        Modulo.z(Zmienna.oNazwie('x'), Literal.oWartosci(13))))
                .zbuduj();

        program.wykonaj();

        assertEquals("11", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestPetlaFor(){
        Program program = new BudowniczyProgramu()
                .wywolajPetleFor('x', Literal.oWartosci(10),
                        new BudowniczyBloku()
                                .wypisz(Zmienna.oNazwie('x'))
                                .zbuduj())
                .zbuduj();

        program.wykonaj();

        assertEquals("0123456789", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestProcedura(){
        Program program = new BudowniczyProgramu()
                .zadeklarujProcedure("fun", ListaZmiennych.jakich(Zmienna.oNazwie('a')),
                        new BudowniczyBloku()
                                .wywolajPetleFor('d', Literal.oWartosci(5),
                                        new BudowniczyBloku()
                                                .wypisz(Zmienna.oNazwie('a'))
                                                .wypisz(Zmienna.oNazwie('d'))
                                                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('a'), Zmienna.oNazwie('d')))
                                                .zbuduj())
                                .wypisz(Zmienna.oNazwie('a'))
                                .zbuduj())
                .wywolajProcedure("fun", ListaWyrazen.jakich(Literal.oWartosci(10)))
                .zbuduj();

        program.wykonaj();

        assertEquals("100101011110212103131041410", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestPrzypisanieWartosci(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('s', Dzielenie.przezSiebie(Literal.oWartosci(9), Literal.oWartosci(2)))
                .wypisz(Zmienna.oNazwie('s'))
                .przypiszWartoscZmiennej('s', Literal.oWartosci(20))
                .wypisz(Zmienna.oNazwie('s'))
                .zbuduj();

        program.wykonaj();

        assertEquals("420", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }
}
