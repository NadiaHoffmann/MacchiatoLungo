package testy;

import instrukcje.BudowniczyBloku;
import instrukcje.BudowniczyProgramu;
import instrukcje.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import porownania.Mniejsze;
import wyrazenia.Dodawanie;
import wyrazenia.Literal;
import wyrazenia.Zmienna;

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

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestDeklaracjaProcedury(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestDeklaracjaZmiennej(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestInstrukcjaIf(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestInstrukcjaPrint(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestPetlaFor(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestProcedura(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestPrzypisanieWartosci(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }
}
