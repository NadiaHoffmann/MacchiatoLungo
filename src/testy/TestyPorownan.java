package testy;

import instrukcje.BudowniczyBloku;
import instrukcje.BudowniczyProgramu;
import instrukcje.Program;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import porownania.Mniejsze;
import porownania.MniejszeRowne;
import porownania.Rowne;
import wyrazenia.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestyPorownan {
    private final PrintStream standardoweWyjscie = System.out;
    private final ByteArrayOutputStream ciagNaWyjsciu = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(ciagNaWyjsciu));
    }

    @Test
    public void TestMniejsze1(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(84))
                .zadeklarujZmienna('z', Dodawanie.doSiebie(Literal.oWartosci(69), Zmienna.oNazwie('x')))
                .wywolajInstrukcjeIf(Mniejsze.coOdCzego(Zmienna.oNazwie('z'), Zmienna.oNazwie('x')),
                        new BudowniczyBloku()
                                .wypisz(Zmienna.oNazwie('z'))
                                .zbuduj())
                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }
    @Test
    public void TestMniejsze2(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(1))
                .zadeklarujZmienna('z', Literal.oWartosci(1))
                .wywolajInstrukcjeIf(Mniejsze.coOdCzego(Zmienna.oNazwie('z'), Zmienna.oNazwie('x')),
                        new BudowniczyBloku()
                                .wypisz(Zmienna.oNazwie('z'))
                                .zbuduj())
                .zbuduj();

        program.wykonaj();

        assertEquals("", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestMniejszeRowne1(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Modulo.z(Literal.oWartosci(2137), Literal.oWartosci(65)))
                .zadeklarujZmienna('z', Literal.oWartosci(56))
                .wywolajInstrukcjeIfElse(MniejszeRowne.coOdCzego(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')),
                        new BudowniczyBloku()
                                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                                .zbuduj(),
                        new BudowniczyBloku()
                                .wypisz(Odejmowanie.odSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                                .zbuduj()
                )
                .zbuduj();

        program.wykonaj();

        assertEquals("1", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestMniejszeRowne2(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Modulo.z(Literal.oWartosci(2137), Literal.oWartosci(65)))
                .zadeklarujZmienna('z', Literal.oWartosci(57))
                .wywolajInstrukcjeIfElse(MniejszeRowne.coOdCzego(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')),
                        new BudowniczyBloku()
                                .wypisz(Dodawanie.doSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                                .zbuduj(),
                        new BudowniczyBloku()
                                .wypisz(Odejmowanie.odSiebie(Zmienna.oNazwie('x'), Zmienna.oNazwie('z')))
                                .zbuduj()
                )
                .zbuduj();

        program.wykonaj();

        assertEquals("114", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestRowne1(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(3))
                .wywolajInstrukcjeIf(Rowne.coZCzym(Zmienna.oNazwie('x'), Modulo.z(Literal.oWartosci(52), Literal.oWartosci(7))),
                        new BudowniczyBloku()
                                .wypisz(Modulo.z(Literal.oWartosci(50), Zmienna.oNazwie('x')))
                                .zbuduj())
                .zbuduj();

        program.wykonaj();

        assertEquals("2", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }

    @Test
    public void TestRowne2(){
        Program program = new BudowniczyProgramu()
                .zadeklarujZmienna('x', Literal.oWartosci(2))
                .wywolajInstrukcjeIfElse(Rowne.coZCzym(Zmienna.oNazwie('x'), Modulo.z(Literal.oWartosci(52), Literal.oWartosci(7))),
                        new BudowniczyBloku()
                                .wywolajPetleFor('a', Literal.oWartosci(300),
                                        new BudowniczyBloku()
                                                .wypisz(Zmienna.oNazwie('a'))
                                                .zbuduj())
                                .zbuduj(),
                        new BudowniczyBloku()
                                .wypisz(Modulo.z(Literal.oWartosci(50), Zmienna.oNazwie('x')))
                                .zbuduj()
                )
                .zbuduj();

        program.wykonaj();

        assertEquals("0", ciagNaWyjsciu
                .toString().trim().replace("\n", "")
                .replace("\r", "")
                .replace("Program zakonczyl dzialanie.", ""));
    }


}
