import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class InformationSpreadTest {

    InformationSpread inf;
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
    public void testFindMaxVertex() {
        inf.loadGraphFromDataSet(root + "test.mtx");
        boolean[] visited = new boolean[] {false, true, true, false, false, false};
        double[] weight = new double[] {Double.MIN_VALUE, 0.7, 0.5, 0.4, 0.3, 0.2};
        assertEquals(3, inf.findMaxVertex(visited, weight));

    }

    @Test
    public void testMaxSpanningTreePrim() {
        inf.loadGraphFromDataSet(root + "test.mtx");
        int[] parent = inf.maxSpanningTreePrim();
        assertEquals(1, parent[3]);
        assertEquals(5, parent[4]);
        assertEquals(5, parent[2]);
        assertEquals(3, parent[5]);
//        for (int i = 1; i < parent.length; i++) {
//            System.out.println(i + "-" + parent[i]);
//        }
    }

    @Test
    public void testWillCatchtheDisease() {
        inf.loadGraphFromDataSet(root + "test.mtx");
        assertTrue(inf.willCatchtheDisease(0.502, 0.500));
        assertFalse(inf.willCatchtheDisease(0.35, 0.5));

    }

    @Test
    public void testTranfectionRate() {
    	inf.loadGraphFromDataSet(root + "test.mtx");
    	assertEquals(0.8, inf.transfectionRate(1, 0.15), 0.01);
    	inf.loadGraphFromDataSet(root + "test.mtx");
    	assertEquals(0.6, inf.transfectionRate(1, 0.16), 0.01);
    	inf.loadGraphFromDataSet(root + "test.mtx");
    	assertEquals(0.4, inf.transfectionRate(1, 0.21), 0.01);
    	inf.loadGraphFromDataSet(root + "test.mtx");
    	assertEquals(1, inf.transfectionRate(1, 0.1), 0.01);
    }

    @Test
    public void testDegree() {
    	inf.loadGraphFromDataSet(root + "test.mtx");
    	assertEquals(3, inf.degree(1));
    	assertEquals(-1, inf.degree(6));
    	assertEquals(3, inf.degree(5));
    	assertEquals(2, inf.degree(2));
    }

    @Test
    public void testDegreeNodes() {
        inf.loadGraphFromDataSet(root + "./test.mtx");
        assertEquals(0, inf.degreeNodes(0).size());
        assertEquals(3, inf.degreeNodes(2).size());
        assertEquals(2, inf.degreeNodes(3).size());
        assertEquals(0, inf.degreeNodes(4).size());
    }

    @Test
    public void testRemoveNodesDegree() {
        inf.loadGraphFromDataSet(root + "./test.mtx");
        Set<Integer> set1 = (Set<Integer>) inf.removeNodesDegree(3);
        assertEquals(2, set1.size());
    }


    @Test
    public void testTranfectionRateDegree() {
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(0.86, inf.transfectionRate(1, 0.15), 0.01);
        assertEquals(0, inf.transfectionRateDegree(1, 0.15, 5), 0.01);
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(0.57, inf.transfectionRateDegree(1, 0.15, 2), 0.01);
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(0.29, inf.transfectionRateDegree(1, 0.15, 3), 0.01);
    }

    @Test
    public void testClustCoeff() {
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(0.33, inf.clustCoeff(4), 0.01);
        assertEquals(0.67, inf.clustCoeff(6), 0.01);
        assertEquals(0.30, inf.clustCoeff(1), 0.01);
    }

    @Test
    public void testClustCoeffNodes() {
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(2, inf.clustCoeffNodes(0, 0.2).size());
        assertEquals(3, inf.clustCoeffNodes(0.30, 0.5).size());
        assertEquals(5, inf.clustCoeffNodes(0.30, 0.7).size());
    }

    @Test
    public void testRemoveNodesCC() {
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(2, inf.removeNodesCC(0, 0.2).size());
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(3, inf.removeNodesCC(0.30, 0.5).size());
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(5, inf.removeNodesCC(0.30, 0.7).size());
    }

    @Test
    public void testTranfectionRateCC() {
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        assertEquals(0.57, inf.transfectionRateCC(1, 0.15, 0, 0.2), 0.01);
        assertEquals(0, inf.transfectionRateCC(1, 0.15, 0.3, 0.5), 0.01);
    }

    @Test
    public void testTranfectionRateVaccine() {
        inf.loadGraphFromDataSet(root + "./test1.mtx");
        List<Integer> immunized = new ArrayList<>();
        immunized.add(2);
        immunized.add(3);
        immunized.add(4);
        assertEquals(0.43, inf.transfectionRateVaccine(1, 0.15, immunized), 0.01);
    }

}
