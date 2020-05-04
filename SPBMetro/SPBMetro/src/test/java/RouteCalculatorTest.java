import core.Line;
import core.Station;
import junit.framework.TestCase;

import java.util.*;

public class RouteCalculatorTest extends TestCase {
    /*  O Ленинский проспект
     *  |
     *  |
     *  O Шаболовская                                                   O Китай-город
     *  |                                                               |
     *  |   Октябрьская-2      Серпуховская       Павелецкая            |Таганская            Курская
     *  O---------------------O-------------------O--------------------O----------------------O         КОЛЬЦЕВАЯ
     * Октябрьская-1                                                   | Таганская
     * |                                                               |
     * |                                                               |
     * O Третьяковская                                                 O Пролетарская
     *                                                                 |
     *  СОКОЛЬНИЧЕСКАЯ ЛИНИЯ                                           |
     *                                                                 O Волгоградский проспект
     *                                                                 |
     *                                                                 |
     *                                                                 O Текстильщики
     *                                                                 |
     *                                                                 |
     *                                                                 O Кузьминки
     *
     *                                                                  ТАГАНСКО-КРАСНОПРЕСНЕНСКАЯ ЛИНИЯ
     */
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

        taganskoKrasn = new Line(1, "Таганско-Краснопресненская");
        sokolnich = new Line(2, "Сокольническая");
        kolcevaya = new Line(3, "Кольцевая");

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

        List<Station> connectionsKolcevayaKrasnopresn = new ArrayList<>();
        connectionsKolcevayaKrasnopresn.add(taganskayaKolcevaya);
        connectionsKolcevayaKrasnopresn.add(taganskayaTaganskoKrasn);

        List<Station> connectionsKolcevayaSokolnich = new ArrayList<>();
        connectionsKolcevayaSokolnich.add(oktyabrskayaOne);
        connectionsKolcevayaSokolnich.add(oktyabrskayaTwo);

        stationIndexTest.addConnection(connectionsKolcevayaKrasnopresn);
        stationIndexTest.addConnection(connectionsKolcevayaSokolnich);

        stationIndexTest.addLine(taganskoKrasn);
        stationIndexTest.addLine(sokolnich);
        stationIndexTest.addLine(kolcevaya);

        taganskoKrasn.getStations().forEach(station -> stationIndexTest.addStation(station));
        sokolnich.getStations().forEach(station -> stationIndexTest.addStation(station));
        kolcevaya.getStations().forEach(station -> stationIndexTest.addStation(station));

        testRouteCalculator = new RouteCalculator(stationIndexTest);
    }

    public void testGetShortestRouteFromAtoA() {

        expectedShortestRouteFromAtoA.add(kuzminki);

        List<Station> actual = testRouteCalculator.getShortestRoute(kuzminki, kuzminki);
        List<Station> expected = expectedShortestRouteFromAtoA;

        assertEquals(expected,actual);
    }

    public void testGetShortestRouteOnTheLine() {

        expectedShortestRouteOnLine.addAll(taganskoKrasn.getStations());

        List<Station> actual = testRouteCalculator.getShortestRoute(kuzminki, kitayGorod);
        List<Station> expected = expectedShortestRouteOnLine;

        assertEquals(expected,actual);
    }

    public void testGetShortestRouteWithOneConnection() {

        expectedShortestRouteWithOneConnection.add(kuzminki);
        expectedShortestRouteWithOneConnection.add(tekstilshiki);
        expectedShortestRouteWithOneConnection.add(volgogradskiyProspekt);
        expectedShortestRouteWithOneConnection.add(proletarskaya);
        expectedShortestRouteWithOneConnection.add(taganskayaTaganskoKrasn);
        expectedShortestRouteWithOneConnection.add(taganskayaKolcevaya);
        expectedShortestRouteWithOneConnection.add(kurskaya);

        List<Station> actual = testRouteCalculator.getShortestRoute(kuzminki, kurskaya);
        List<Station> expected = expectedShortestRouteWithOneConnection;

        assertEquals(expected, actual);
    }

    public void testGetShortestRouteWithTwoConnections() {

        expectedShortestRouteWithTwoConnections.add(tretyakovskaya);
        expectedShortestRouteWithTwoConnections.add(oktyabrskayaOne);
        expectedShortestRouteWithTwoConnections.add(oktyabrskayaTwo);
        expectedShortestRouteWithTwoConnections.add(serpuhovskaya);
        expectedShortestRouteWithTwoConnections.add(paveleckaya);
        expectedShortestRouteWithTwoConnections.add(taganskayaKolcevaya);
        expectedShortestRouteWithTwoConnections.add(taganskayaTaganskoKrasn);
        expectedShortestRouteWithTwoConnections.add(kitayGorod);

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
}
