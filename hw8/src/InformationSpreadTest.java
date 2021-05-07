import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class InformationSpreadTest {

    IInformationSpread inf;
    String root;

    @Before
    public void setUp() throws Exception {
        inf = new InformationSpread();
        //root = "/autograder/submission/";
        root = "datasets/";
    }

    @Test
    public void testLoadGraphFromDataSet() {
        assertEquals(5, inf.loadGraphFromDataSet(root + "test.mtx"));
    }

    @Test
    public void testGetNeighbors() {
        inf.loadGraphFromDataSet(root + "test.mtx");
        int[] neighbours = inf.getNeighbors(1);
        assertEquals(2, neighbours[0]);
        assertEquals(3, neighbours[1]);
        assertEquals(4, neighbours[2]);
        neighbours = inf.getNeighbors(2);
        assertEquals(1, neighbours[0]);
        assertEquals(5, neighbours[1]);
        neighbours = inf.getNeighbors(3);
        assertEquals(1, neighbours[0]);
        assertEquals(5, neighbours[1]);
        neighbours = inf.getNeighbors(4);
        assertEquals(1, neighbours[0]);
        assertEquals(5, neighbours[1]);
        neighbours = inf.getNeighbors(5);
        assertEquals(2, neighbours[0]);
        assertEquals(3, neighbours[1]);
    }

    @Test
    public void testLongestTransmissionPath() {
        inf.loadGraphFromDataSet(root + "test.mtx");
        Collection<Integer> path = inf.longestTransmissionPath(1, 5);
        Iterator<Integer> iter = path.iterator();
        int node = iter.next();
        assertEquals(1, node);
        node = iter.next();
        assertEquals(3, node);
        node = iter.next();
        assertEquals(5, node);
        assertTrue(inf.longestTransmissionPath(1, 1).isEmpty());
    }

    @Test
    public void testWillCatchtheDisease() {
        fail("Not yet implemented");
    }

    @Test
    public void testTranfectionRate() {
        fail("Not yet implemented");
    }

    @Test
    public void testDegree() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveNodesDegree() {
        fail("Not yet implemented");
    }

    @Test
    public void testTranfectionRateDegree() {
        fail("Not yet implemented");
    }

    @Test
    public void testClustCoeff() {
        fail("Not yet implemented");
    }

    @Test
    public void testClustCoeffNodes() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveNodesCC() {
        fail("Not yet implemented");
    }

    @Test
    public void testTranfectionRateCC() {
        fail("Not yet implemented");
    }

    @Test
    public void testTranfectionRateVaccine() {
        fail("Not yet implemented");
    }

}