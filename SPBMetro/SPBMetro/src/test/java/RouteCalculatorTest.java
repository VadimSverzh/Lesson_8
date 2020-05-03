import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.*;
import java.util.stream.Collectors;

public class RouteCalculatorTest extends TestCase {
    private Line taganskoKrasn;
    private Line sokolnich;
    private Line kolcevaya;

    private Station kuzminki;
    private Station tekstilshiki;
    private Station volgogradskiyProspekt;
    private Station proletarskaya;
    private Station taganskayaTaganskoKrasn;
    private Station kitayGorod;

    private Station tretyakovskaya;
    private Station oktyabrskayaOne;
    private Station shabolovskaya;
    private Station leninskiyProspekt;

    private Station oktyabrskayaTwo;
    private Station serpuhovskaya;
    private Station paveleckaya;
    private Station taganskayaKolcevaya;
    private Station kurskaya;

    private StationIndex stationIndexTest = new StationIndex();
    private RouteCalculator testRouteCalculator;
    private ArrayList<Station> expectedShortestRouteOnLine = new ArrayList<>();
    private ArrayList<Station> expectedShortestRouteWithOneConnection = new ArrayList<>();
    private ArrayList<Station> expectedShortestRouteWithTwoConnections = new ArrayList<>();
    private ArrayList<Station> expectedShortestRouteFromAtoA = new ArrayList<>();

    protected void setUp() throws Exception{

        //создал 3 линии метро
        taganskoKrasn = new Line(1, "Таганско-Краснопресненская");
        sokolnich = new Line(2, "Сокольническая");
        kolcevaya = new Line(3, "Кольцевая");

        //создал и добавил станции в каждую из линий
        kuzminki = new Station("Кузьминки", taganskoKrasn);
        taganskoKrasn.addStation(kuzminki);
        tekstilshiki = new Station("Текстильщики", taganskoKrasn);
        taganskoKrasn.addStation(tekstilshiki);
        volgogradskiyProspekt = new Station("Волгоградский проспект", taganskoKrasn);
        taganskoKrasn.addStation(volgogradskiyProspekt);
        proletarskaya = new Station("Пролетарская", taganskoKrasn);
        taganskoKrasn.addStation(proletarskaya);
        taganskayaTaganskoKrasn = new Station("Таганская", taganskoKrasn);
        taganskoKrasn.addStation(taganskayaTaganskoKrasn);
        kitayGorod = new Station("Китай-Город", taganskoKrasn);
        taganskoKrasn.addStation(kitayGorod);

        tretyakovskaya = new Station("Третьяковская", sokolnich);
        sokolnich.addStation(tretyakovskaya);
        oktyabrskayaOne = new Station("Октябрьская-1", sokolnich);
        sokolnich.addStation(oktyabrskayaOne);
        shabolovskaya = new Station("Шаболовская", sokolnich);
        sokolnich.addStation(shabolovskaya);
        leninskiyProspekt = new Station("Ленинский проспект", sokolnich);
        sokolnich.addStation(leninskiyProspekt);

        oktyabrskayaTwo = new Station("Октябрьская-2", kolcevaya);
        kolcevaya.addStation(oktyabrskayaTwo);
        serpuhovskaya = new Station("Серпуховская", kolcevaya);
        kolcevaya.addStation(serpuhovskaya);
        paveleckaya = new Station("Павелецкая", kolcevaya);
        kolcevaya.addStation(paveleckaya);
        taganskayaKolcevaya = new Station("Таганская", kolcevaya);
        kolcevaya.addStation(taganskayaKolcevaya);
        kurskaya = new Station("Курская", kolcevaya);
        kolcevaya.addStation(kurskaya);

        //добавил все линии в коллекцию всех линий
        ArrayList <Line> allLines = new ArrayList<>();
        allLines.add(kolcevaya);
        allLines.add(sokolnich);
        allLines.add(taganskoKrasn);

        TreeMap<Station, TreeSet<Station>> connectionsTest = new TreeMap<>();

        TreeSet<Station> taganskoKrasn2Kolcevaya = new TreeSet<>();
        TreeSet<Station> kolcevaya2TaganskoKrasn = new TreeSet<>();
        TreeSet<Station> kolcevaya2Sokolnicheskaya = new TreeSet<>();
        TreeSet<Station> soklonicheskaya2Kolcevaya = new TreeSet<>();

        taganskoKrasn2Kolcevaya.add(taganskayaKolcevaya);
        kolcevaya2TaganskoKrasn.add(taganskayaTaganskoKrasn);
        kolcevaya2Sokolnicheskaya.add(oktyabrskayaTwo);
        soklonicheskaya2Kolcevaya.add(oktyabrskayaOne);

        connectionsTest.put(taganskayaTaganskoKrasn, taganskoKrasn2Kolcevaya);
        connectionsTest.put(taganskayaKolcevaya, kolcevaya2TaganskoKrasn);
        connectionsTest.put(oktyabrskayaOne, kolcevaya2Sokolnicheskaya);
        connectionsTest.put(oktyabrskayaTwo, soklonicheskaya2Kolcevaya);

        HashMap <Integer, Line> number2lineTest = new HashMap<>();
        number2lineTest.put(taganskoKrasn.getNumber(), taganskoKrasn);
        number2lineTest.put(sokolnich.getNumber(), sokolnich);
        number2lineTest.put(kolcevaya.getNumber(), kolcevaya);

        TreeSet<Station>stationsTest = new TreeSet<>();
        allLines.forEach(a -> stationsTest.addAll(a.getStations()));

        stationIndexTest.number2line = number2lineTest;
        stationIndexTest.connections = connectionsTest;
        stationIndexTest.stations = stationsTest;

        testRouteCalculator = new RouteCalculator(stationIndexTest);

        expectedShortestRouteFromAtoA.add(kuzminki);

        expectedShortestRouteOnLine.addAll(taganskoKrasn.getStations());

        expectedShortestRouteWithOneConnection.addAll(taganskoKrasn.getStations().stream()
                .filter(station -> !station.equals(kitayGorod)).collect(Collectors.toList()));
        expectedShortestRouteWithOneConnection.addAll((kolcevaya.getStations()).stream()
                .filter(station -> station.equals(taganskayaKolcevaya) || station.equals(kurskaya)).collect(Collectors.toList()));

        expectedShortestRouteWithTwoConnections.addAll((sokolnich.getStations()).stream()
                .filter(station -> !station.equals(leninskiyProspekt) && !station.equals(shabolovskaya)).collect(Collectors.toList()));
        expectedShortestRouteWithTwoConnections.addAll((kolcevaya.getStations()).stream()
                .filter(station -> !station.equals(kurskaya)).collect(Collectors.toList()));
        expectedShortestRouteWithTwoConnections.addAll((taganskoKrasn.getStations()).stream()
                .filter(station -> station.equals(kitayGorod) || station.equals(taganskayaTaganskoKrasn)).collect(Collectors.toList()));

    }

    public void testGetShortestRouteFromAtoA() {
        List<Station> actual = testRouteCalculator.getShortestRoute(kuzminki, kuzminki);
        List<Station> expected = expectedShortestRouteFromAtoA;
        assertEquals(expected,actual);
    }

    public void testGetShortestRouteOnTheLine() {
        List<Station> actual = testRouteCalculator.getShortestRoute(kuzminki, kitayGorod);
        List<Station> expected = expectedShortestRouteOnLine;
        assertEquals(expected,actual);
    }

    public void testGetShortestRouteWithOneConnection() {
        List<Station> actual = testRouteCalculator.getShortestRoute(kuzminki, kurskaya);
        List<Station> expected = expectedShortestRouteWithOneConnection;
        assertEquals(expected, actual);
    }

    public void testGetShortestRouteWithTwoConnections() {
        List<Station> actual = testRouteCalculator.getShortestRoute(tretyakovskaya, kitayGorod);
        List<Station> expected = expectedShortestRouteWithTwoConnections;
        assertEquals(expected, actual);
    }

    public void testCalculateDurationFromAtoA() {
        double actual = RouteCalculator.calculateDuration(testRouteCalculator.getShortestRoute(kurskaya, kurskaya));
        double expected = 0.0;
        assertEquals(expected,actual);
    }

    public void testCalculateDurationOnTheLine() {
        double actual = RouteCalculator.calculateDuration(testRouteCalculator.getShortestRoute(oktyabrskayaTwo, kurskaya));
        double expected = 10.0;
        assertEquals(expected,actual);
    }

    public void testCalculateDurationWithOneConnection() {
        double actual = RouteCalculator.calculateDuration(testRouteCalculator.getShortestRoute(oktyabrskayaTwo, kuzminki));
        double expected = 21.0;
        assertEquals(expected,actual);
    }

    public void testCalculateDurationWithTwoConnections() {
        double actual = RouteCalculator.calculateDuration(testRouteCalculator.getShortestRoute(tretyakovskaya, kitayGorod));
        double expected = 19.5;
        assertEquals(expected,actual);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
