# Część 1.

## Jeszcze Jeden Odpluskwiacz

*Info.PO.22/23*

*ver 1.0*

W tym zadaniu należy napisać zestaw klas, które reprezentują instrukcje prostego języka programowania *Macchiato*. Programy w tym języku są pojedynczym blokiem (p. opis instrukcji dalej).

Klasy będące rozwiązaniem powinny oczywiście umożliwiać wykonywanie programów. Efektem wykonania programu ma być wykonanie poszczególnych instrukcji (zgodnie z ich opisem dalej) i wypisanie na standardowe wyjście wartości wszystkich zmiennych z głównego bloku programu. W tym zadaniu **nie** jest wymagane wczytywanie programu z plików tekstowych. Przykładowe programy w Macchiato mają być budowane w kodzie programu w Javie z zaimplementowanych w Państwa rozwiązaniu klas.

Oprócz samej możliwości wykonania programu interesuje nas też śledzenie tego wykonania (czyli realizacja prostego odpluskwiacza). W związku z tym Państwa rozwiązanie powinno dawać dwie możliwości wykonywania programu:
- wykonywanie bez odpluskwiania - tu program jest wykonywany od początku do końca (chyba, że nastąpi błąd wykonania, ale nawet wtedy wypisujemy tylko komunikat i kończymy wykonywanie, bez uruchamiania odpluskwiacza),
- wykonywanie z odpluskwianiem - tu program od razu się zatrzymuje (przed wykonaniem pierwszej instrukcji) i czeka na polecenia podawane ze standardowego wejścia.

Wszystkie polecenia odpluskwiacza są jednoznakowe. Po nazwie niektórych poleceń (p. dalej) może wystąpić po (jednej lub więcej) spacji pojedyncza liczba całkowita.

Zestaw poleceń odpluskwiacza:
- **c**(ontinue)
  - bezparametrowa, odpluskwiany program zaczyna się dalej wykonywać (do zakończenia). Jeśli program już się zakończył, to polecenie wypisuje tylko stosowny komunikat (i nic więcej nie robi).
- **s**(tep) \<liczba\>
  - odpluskwiany program wykonuje dokładnie \<liczba\> kroków. Przez krok rozumiemy wykonanie pojedynczej instrukcji (liczymy także wykonania wszystkich instrukcji składowych, dowolnie zagnieżdżonych). Po wykonaniu zadanej liczby kroków wypisywana jest instrukcja (być może złożona), która ma być wykonana jako następna. Jeśli wykonywanie dojdzie do końca programu przed wykonaniem zadanej liczby instrukcji, to tylko wypisujemy stosowny komunikat, program kończy się normalnie.
- **d**(isplay) \<liczba\>
  - wyświetla bieżące wartościowanie. Parametr podaje z o ile poziomów wyższego bloku wartościowanie zmiennych ma być wyświetlone. Polecenie *d 0* oznacza polecenie wyświetlenia bieżącego wartościowania. Jeśli podana liczba jest większa niż poziom zagnieżdżenia bieżącej instrukcji programu, to wypisywany jest tylko stosowny komunikat.
- **e**(xit)
  - przerywa wykonanie programu i kończy działanie debuggera. Nie wypisujemy końcowego wartościowania zmiennych.

Język Macchiato dopuszcza jedynie zmienne o nazwach jednoliterowych (litery alfabetu angielskiego od 'a' do 'z'). Wszystkie zmienne są typu int (tego samego co w Javie).

Programy mogą zawierać następujące instrukcje:
- Blok
  - blok zawiera ciąg deklaracji zmiennych oraz sekwencję instrukcji. Każda z tych części może być pusta. Deklaracje umieszczone w bloku są widoczne od swojego końca do końca swojego bloku (i nigdzie więcej). Lokalne deklaracja mogą przesłaniać deklaracje zewnętrzne.
- Pętla for \<zmienna\> \<wyrażenie\> \<instrukcje\>
  - wykonuje instrukcje <wartość razy>, przy każdej iteracji \<instrukcje\> są wykonywane w bloku ze \<zmienną\> przyjmującą kolejną wartość z zakresu 0..\<wartość wyrażenia\>-1. Wartość wyrażenia jest wyliczana tylko raz, tuż przed rozpoczęciem wykonywania pętli (więc nawet jeśli wykonywane instrukcje zmienią tę wartość, to i tak nie zmieni się liczba obrotów tej pętli). Podana zmienna jest inicjowana kolejnymi wartościami przy każdym obrocie pętli, więc ewentualne przypisania na nią w instrukcjach składowych pętli nie zmienią przebiegu sterowania (liczby obrotów i wartości zmiennej na początku kolejnych obrotów) tej pętli. Jeśli wyliczona wartość wyrażenia jest mniejsza lub równa zeru, to pętla nie wykonuje ani jednego obrotu. Błąd przy wyliczaniu wyrażenia przerywa dalsze wykonywanie pętli (instrukcje w pętli nie zostaną ani razu wykonane).
- Instrukcja warunkowa if \<wyr1\> =|\<\>|\<|\>|\<=|\>= \<wyr2\> then \<instrukcje\> else \<instrukcje\>
  - znaczenie standardowe,
  - najpierw wyliczamy pierwsze, potem drugie wyrażenie,
  - błąd przy wyliczaniu wyrażenia przerywa dalsze wykonywanie tej instrukcji,
  - część else \<instrukcje\> można pominąć.
- Przypisanie wartości na zmienną \<nazwa\> \<wyr\>
  - nadaje zmiennej wartość równą wyliczonej wartości wyrażenia,
  - błąd przy wyliczaniu wyrażenia przerywa dalsze wykonywanie tej instrukcji (tzn. w takiej sytuacji wartość zmiennej nie zostaje zmieniona),
  - kończy się błędem, jeśli w tym miejscu programu nie ma widocznej żadnej zadeklarowanej zmiennej o podanej nazwie.
- Wypisanie wartości wyrażenia print \<wyr\>
  - wyliczana jest wartość wyrażenia, a następnie wypisywana w kolejnym wierszu standardowego wyjścia.
  - jeśli wyliczanie wyrażenie nie powiedzie się, ta instrukcja niczego nie wypisuje.

W blokach występują deklaracje:
- Deklaracja zmiennej \<nazwa\> \<wyr\>
  - wprowadza zmienną do bieżącego zakresu widoczności (związanego z zawierającym tę deklarację blokiem) i inicjuje ją wyliczoną wartością wyrażenia,
  - w jednym bloku nie można zadeklarować dwu (lub więcej) zmiennych o tej samej nazwie.
  - nazwy zmiennych z różnych bloków mogą być takie same, w szczególności może dochodzić do przesłaniania zmiennych (instrukcje i wyrażenia zawsze widzą tę zmienną, która była zadeklarowana w najciaśniej otaczającym bloku).
  - deklarować zmienne można jedynie na początku bloku (tzn. w ciągu deklaracji zmiennych).
  - błąd przy wyliczaniu wyrażenia przerywa dalsze przetwarzanie deklaracji (tzn. w takiej sytuacji zmienna nie zostaje zadeklarowana).

Jeśli w trakcie wykonywania instrukcji nastąpi błąd, wykonywanie programu jest przerywane oraz
wypisywany jest komunikat zawierający wartości wszystkich zmiennych widocznych w bloku, w którym
nastąpił błąd (te zmienne mogą być zadeklarowane w tym lub w otaczających blokach) oraz wypisywana
jest instrukcja, która bezpośrednio spowodowała błąd.

W języku Macchiato wszystkie wyrażenia mają wartości całkowite. Wyrażenie może mieć jedną z
następujących postaci:
- Literał całkowity
  - wartością takiego wyrażenia jest wartość literału. Składnia i zakres literałów jest taka sama jak w Javie dla typu int.
- Zmienna
  - wartością takiego wyrażenia jest wartość zmiennej widocznej w danym miejscu programu. Jeśli w tym miejscu nie ma żadnej widocznej zmiennej o podanej nazwie, wyliczenie wartości zmiennej kończy się błędem.
- Dodawanie \<wyr1\> \<wyr2\>
  - suma dwóch wyrażeń, najpierw wyliczamy pierwsze, potem drugie wyrażenie, następnie wykonujemy dodawania uzyskanych wartości. Przy przekroczenia zakresu należy podać taki wynik jaki dla takich samych wartości daje Java.
- Odejmowanie \<wyr1\> \<wyr2\>
  - różnica dwóch wyrażeń, najpierw wyliczamy pierwsze, potem drugie wyrażenie, następnie od wartości pierwszego odejmujemy wartość drugiego. Przy przekroczenia zakresu należy podać taki wynik jaki dla takich samych wartości daje Java.
- Mnożenie \<wyr1\> \<wyr2\>
  - iloczyn dwóch wyrażeń, najpierw wyliczamy pierwsze, potem drugie wyrażenie, następnie wykonujemy mnożenie uzyskanych wartości. Przy przekroczenia zakresu należy podać taki wynik jaki dla takich samych wartości daje Java.
- Dzielenie \<wyr1\> \<wyr2\>
  - dzielenie całkowite dwóch wyrażeń, najpierw wyliczamy pierwsze, potem drugie wyrażenie, następnie wartość pierwszego dzielimy przez wartość drugiego. Dla liczb ujemnych lub różnych znaków należy podać taki wynik jaki dla takich samych wartości daje Java. Wyliczanie kończy się błędem, gdy wartością drugiego wyrażenia jest zero.
- Modulo \<wyr1\> \<wyr2\>
  - reszta z dzielenia całkowitego dwóch wyrażeń, najpierw wyliczamy pierwsze, potem drugie wyrażenie, następnie wartość pierwszego dzielimy przez wartość drugiego, wynikiem jest reszta. Dla liczb ujemnych lub różnych znaków należy podać taki wynik jaki dla takich samych wartości daje Java. Wyliczanie kończy się błędem, gdy wartością drugiego wyrażenia jest zero.

Przykładowy program w języku Macchiato zapisany w metaskładni (przypominamy, że Państwa rozwiązanie **nie** ma wczytywać tekstu programów w Macchiato, zatem konkretna składnia nie ma w tym zadaniu znaczenia):
```
begin block
  var n 30
  for k n-1
    begin block
      var p 1
      k := k+2
      for i k-2
        i := i+2
        if k % i = 0
          p := 0
      if p = 1
        print k
    end block
end block
```
Jako rozwiązanie należy oddać zestaw pakietów i klas umożliwiających wykonywania i śledzenie programów w Macchiato wraz z podanym powyżej przykładowym programem w tym języku utworzonym w funkcji main.

Życzymy powodzenia!

# Część 2.

*Macchiato 1.1*

*Info.PO.22/23, v1.2*

W tym zadaniu należy zaimplementować nową wersję języka Macchiato, na którą będą się składały następujące nowe funkcjonalności języka, a także usprawnienia w jego ekosystemie:

## 1. Procedury
Macchiato w wersji 1.1 ma procedury. O procedurach myśleć można jak o funkcjach, które mają zero lub więcej parametrów oraz nie dają żadnej wartości (innymi słowy są typu void). Można je zadeklarować, a następnie w ciągu instrukcji wywołać z odpowiednimi argumentami.

Deklaracja procedury zawiera:
- nagłówek, czyli informacje o nazwie procedury i jej parametrach (wszystkie są typu całkowitego). Nazwa procedury jest niepustym ciągiem liter alfabetu angielskiego od 'a' do 'z', natomiast nazwy parametrów podlegają takim ograniczeniom jak nazwy zmiennych, to znaczy są jednoliterowe. Wszystkie nazwy parametrów muszą być różne. Parametry są przekazywane przez wartość. Deklaracje procedur działają w sposób podobny do deklaracji zmiennych, to jest:
  - znajdują się w bloku w tym samym miejscu, co deklaracje zmiennych, to znaczy na początku bloku w ciągu deklaracji zmiennych i procedur,
  - są widoczne do końca swojego bloku,
  - mogą być przesłaniane,
  - w tym samym bloku nie można zadeklarować dwa razy procedury o tej samej nazwie,
- treść procedury, która składa się z ciągu deklaracji, po których następuje ciąg instrukcji wykonywanych w momencie wywołania procedury.

Wywołanie procedury jest instrukcją, która zawiera:
- nazwę procedury,
- argumenty, będące wyrażeniami języka Macchiato.

Argumenty wyliczane są w momencie wywołania procedury (w kolejności w jakiej zostały zapisane, tj. od pierwszego do ostatniego) i w jej treści dostępne są jako wartości zmiennych odpowiadających parametrom procedury. Wywołanie procedury skutkuje wykonaniem po kolei instrukcji z jej treści, przy czym dzieje się to w sposób zgodny z dynamicznym wiązaniem zmiennych. To znaczy, że jeśli w treści procedury występuje odwołanie do zmiennej, to ustalanie, o którą zmienną chodzi (jeśli jest w programie więcej niż jedna o tej samej nazwie i czy w ogóle taka zmienna jest dostępna) dzieje się to w trakcie wykonania procedury. Wykorzystywana jest zmienna widoczna obecnie w środowisku wykonania procedury. Uwaga: dynamiczne wiązanie zmiennych jest łatwiejsze do zaimplementowania (i dlatego wybrano je w Macchiato), ale praktycznie nie jest stosowane. W językach takich jak C, Java czy Python występuje statyczne wiązanie zmiennych.

## 2. Nowe polecenie debuggera
Debugger dla Macchiato w wersji 1.1 zawiera wsparcie dla nowego polecenia dump, umożliwiającego wykonanie zrzutu pamięci programu do pliku. Polecenie to ma symbol m i wymaga jednego parametru będącego ścieżką do pliku. Efektem działania polecenia powinno być zapisanie w podanym pliku w formie tekstowej zrzutu pamięci programu. Na zrzut pamięci programu składają się:
- widoczne deklaracje procedur, to jest ich nazwy wraz z nazwami parametrów (bez treści),
- bieżące wartościowanie zmiennych (takie jak w poleceniu d 0).

## 3. Wygodne tworzenie programów Macchiato w Javie
Programy Macchiato w wersji 1.1 mogą być tworzone w sposób dużo wygodniejszy niż w poprzedniej wersji. Zestaw klas będących niewielkim SDK dla Macchiato zapewnia możliwość tworzenia programów oraz ich poszczególnych części w sposób podobny do DSL, pozwalający na dodawanie po kolei poszczególnych deklaracji i instrukcji dzięki wywołaniom odpowiednich metod (zob. wzorzec projektowy "budowniczy"), a także tworzenie wyrażeń za pomocą czytelnych, statycznych funkcji (zob. wzorzec projektowy "fabryka"). Stworzenie programu o następującej meta składni:

```
begin block
    var x 101
    var y 1
    proc out(a)
        print a+x
    end proc
    x := x - y
    out(x)
    out(100)  // tu powinno wypisać 200
    begin block
        var x 10
        out(100) // tu statycznie wciąż 200, dynamicznie 110
    end block
end block
```

mogłoby przebiegać na przykład tak:

```
var program = new ProgramBuilder()
    .declareVariable('x', Constant.of(101))
    .declareVariable('y', Constant.of(1))
    .declareProcedure('out', List.of('a'), new BlockBuilder()
         .print(Addition.of(Variable.named('a'), Variable.named('x')))
         .build()
    )  
    .assign('x', Subtraction.of(Variable.named('x'), Variable.named('y')))
    .invoke('out', List.of(Variable.named('x')))
    .invoke('out', List.of(Constant.of(100)))
    .block(new BlockBuilder()
        .declareVariable('x', Constant.of(10))
        .invoke('out', List.of(Constant.of(100)))
        .build() 
    )
    .build();
```

## Testy

Projekt z rozwiązaniem należy uzupełnić o testy JUnit. Każda konstrukcja składniowa języka Macchiato 1.1 powinna być uzupełniona jednym testem.

## Forma rozwiązania zadania

Zadanie polega na zaimplementowaniu Macchiato w wersji 1.1 zgodnie z podaną specyfikacją. Rozwiązanie powinno być w formie projektu o nazwie po_macchiato stworzonego na wydziałowym Gitlabie. Należy zacząć od utworzenia pustego projektu i umieszczenia w nim swojego rozwiązania pierwszej części zadania, w formie pierwszego commita na gałęzi master. Przy pracy nad rozwiązaniem drugiej części należy przestrzegać dobrych praktyk związanych z systemem kontroli wersji dotyczących na przykład nazw commitów, a także ich struktury (na potrzeby tego zadania uznaje się, że powinno być widać pracę w formie mniejszych commitów, a nie jeden ogromny commit z całym rozwiązaniem drugiej części zadania). Co więcej, należy korzystać z gałęzi (tzw. feature branch) podczas dodawania poszczególnych funkcjonalności.

Jako rozwiązanie należy oddać zestaw pakietów i klas umożliwiających wykonywanie i śledzenie programów w Macchiato 1.1 wraz z podanym w pkt. 3 przykładowym programem w tym języku, utworzonym w funkcji main. Wobec tego w repozytorium Git mogą się znaleźć jedynie niezbędne pliki .java oraz .gitignore. Nie wolno natomiast umieszczać tam plików binarnych, tymczasowych, ani specyficznych dla IDE plików / folderów.

Finalne rozwiązanie powinno znaleźć się jako commit w gałęzi master, który musi być wysłany do repozytorium (git push) przed terminem podanym w Moodle. Identyfikator tego commita (np. 518507a7e9ea50e099b33cb6ca3d3141bc1d6638) wraz z adresem repozytorium postaci https://gitlab.mimuw.edu.pl/login/po_macchiato.git należy przesłać na Moodle. Trzeba pamiętać, żeby projekt Gitlab miał ustawiony prywatny poziom widoczności oraz żeby prowadzący zajęcia był dołączony jako współpracownik w projekcie z dostępem na poziomie Developer.

Osoby, które nie mają swojego rozwiązania pierwszego zadania mogą:
- napisać teraz tylko pierwsze zadanie, zostanie ono ocenione w skali 0-10, czyli tak, jakby to było oddanie (samego) drugiego zadania,
- napisać niezbędne części pierwszego zadania i całe drugie, wtedy punkty przyznawane będą tylko za drugie zadanie.

Historia modyfikacji:
- 10 VI 2023, w. 1.2: Dodana nowa, zalecana wersja przykładu (wymaganego w kodzie). Ta wersja ilustruje różnicę między dynamicznym i statycznym wiązaniem zmiennych. Będziemy akceptować też wiązanie statyczne (w treści jest mowa o dynamicznym), prosimy tylko wyraźnie zaznaczyć w kodzie, że je właśnie Państwo zaimplementowali.

Stara wersja przykładu, choć nie zalecana, dalej jest akceptowana (bez wpływu na punktację).

Oto treść (tylko dla pełności historii zmian) starej wersji:

```
begin block
    var x 57
    var y 15
    proc out(a)
        print a
    end proc
    x := x - y
    out(x)
    out(125)
end block
```

i stary przykładowy kod

```
var program = new ProgramBuilder()
    .declareVariable('x', Constant.of(57))
    .declareVariable('y', Constant.of(15))
    .declareProcedure(
      'out', List.of('a'),
      new BlockBuilder().print(Variable.named('a')).buildProc()
    )  
    .assign('x', Subtraction.of(Variable.named('x'), Variable.named('y')))
    .invoke('out', List.of(Variable.named('x')))
    .invoke('out', List.of(Constant.of(125)))
    .build();
```
