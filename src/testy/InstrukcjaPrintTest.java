package testy;

import instrukcje.BudowniczyProgramu;
import instrukcje.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wyrazenia.Dodawanie;
import wyrazenia.Literal;
import wyrazenia.Zmienna;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
public class InstrukcjaPrintTest {
    private final PrintStream standardoweWyjscie = System.out;
    private final ByteArrayOutputStream ciagNaWyjsciu = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(ciagNaWyjsciu));
    }


    @Test
    public void TestBlockPrints(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(2137))
                .wypisz(Zmienna.oNazwie('x'))
                .wypisz(Literal.oWartosci(420))
                .zadeklarujZmienna('z', Literal.oWartosci(69))
                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                .zbuduj();

        program.wykonaj();

        assertEquals("21374202206", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }
}