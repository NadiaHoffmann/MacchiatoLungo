package testy;

import instrukcje.BudowniczyProgramu;
import instrukcje.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestDzielenie(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestLiteral(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestMnozenie(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestModulo(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestOdejmowanie(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestZmienna(){
        Program program = new BudowniczyProgramu()

                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }
}
