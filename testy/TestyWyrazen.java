package testy;

import instrukcje.BudowniczyProgramu;
import instrukcje.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wyrazenia.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestyWyrazen {
    private final PrintStream standardoweWyjscie = System.out;
    private final ByteArrayOutputStream ciagNaWyjsciu = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(ciagNaWyjsciu));
    }

    @Test
    public void TestDodawanie(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(13))
                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('x'), Literal.oWartosci(5)))
                .zbuduj();

        program.wykonaj();

        assertEquals("18", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestDzielenie(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(13))
                .wypisz(Dzielenie.przezSiebie(Zmienna.oNazwie('x'), Literal.oWartosci(5)))
                .zbuduj();

        program.wykonaj();

        assertEquals("2", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestLiteral(){
        Program program = new BudowniczyProgramu()
                .wypisz(Literal.oWartosci(5))
                .zbuduj();

        program.wykonaj();

        assertEquals("5", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestMnozenie(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(13))
                .wypisz(Mnozenie.przezSiebie(Zmienna.oNazwie('x'), Literal.oWartosci(5)))
                .zbuduj();

        program.wykonaj();

        assertEquals("65", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestModulo(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(13))
                .wypisz(Modulo.z(Zmienna.oNazwie('x'), Literal.oWartosci(5)))
                .zbuduj();

        program.wykonaj();

        assertEquals("3", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestOdejmowanie(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(13))
                .wypisz(Odejmowanie.odSiebie(Zmienna.oNazwie('x'), Literal.oWartosci(5)))
                .zbuduj();

        program.wykonaj();

        assertEquals("8", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestZmienna(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(13))
                .wypisz(Zmienna.oNazwie('x'))
                .zbuduj();

        program.wykonaj();

        assertEquals("13", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }
}
