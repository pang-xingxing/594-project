import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InformationSpread implements IInformationSpread {

    private Graph graph;

    public InformationSpread() {
        graph = new GraphL(); // could change to matrix
    }

    @Override
    public int loadGraphFromDataSet(String filePath) {
        try {
            BufferedReader r = new BufferedReader(new FileReader(filePath));
            String line = r.readLine();
            String[] nums = line.split(" ");
            int v = Integer.parseInt(nums[0]);
            graph.init(v + 1);
            line = r.readLine();
            while (line != null) {
                String[] words = line.split(" ");
                int v1 = Integer.parseInt(words[0]);
                int v2 = Integer.parseInt(words[1]);
                double w = -1 * Math.log(Double.parseDouble(words[2])); // update
                if (v1 == 0 || v2 == 0) {
                    line = r.readLine();
                    continue;
                }
                graph.addEdge(v1, v2, w);
                graph.addEdge(v2, v1, w);
                line = r.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph.nodeCount() - 1;
    }

    @Override
    public int[] getNeighbors(int id) {
        return graph.neighbors(id);
    }

    private void storepath(Collection<Integer> p, int[] pred, int destination) {
        if (destination == 0) {
            return;
        }
        storepath(p, pred, pred[destination]);
        p.add(destination);
    }
    
    /**
     * The path of transmission with highest probability
     * @param source      - an infected node that could spread the disease
     * @param destination - the id of the destination node
     * @return the path to spread disease from source to destination
     *         with the highest transmission probability
     */

    @Override
    public Collection<Integer> longestTransmissionPath(int source, int destination) {
        Collection<Integer> p = new ArrayList<>();
        if (source == destination) {
            return p;
        }
        for (int i = 1; i < graph.nodeCount(); i++) {
            graph.setValue(i, Double.POSITIVE_INFINITY);
        }
        int[] pred = new int[graph.nodeCount()];
        Arrays.fill(pred, 0);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        graph.setValue(source, 0);
        pq.add(source);
        while (!pq.isEmpty()) {
            int node = pq.poll();
            //System.out.println(node + ": " + graph.getValue(node)); //for test
            if (node == destination) {
                storepath(p, pred, destination);
                break;
            }
            for (int neighbor : this.getNeighbors(node)) {
                
                double temp = graph.getValue(node) + graph.weight(node, neighbor);
                if (temp < graph.getValue(neighbor)) {
                    graph.setValue(neighbor, temp);
                    pq.add(neighbor);
                    pred[neighbor] = node;
                }
            }
        }
        return p;
    }



    public int findMaxVertex(boolean[] visited, double[] weight) {
        int index = -1;
        double maxW = Double.MIN_VALUE;
        for (int i = 1; i < this.graph.nodeCount(); i++) {
            if (!visited[i] && weight[i] > maxW) {
                maxW = weight[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * The path of transmission with highest probability
     * @return the max spanning tree of this graph, representing the path
     *  that spans the entire graph with the highest transmission probability
     */
    public int[] maxSpanningTreePrim() {
        int n = this.graph.nodeCount();
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        double[] currWeight = new double[n];
        for (int u = 0; u < n; u++) {
            visited[u] = false;
            currWeight[u] = Double.MIN_VALUE;
        }
        currWeight[1] = Double.MAX_VALUE;
        parent[1] = 1;
        for (int i = 0; i < n; i++) {
            int maxVertex = findMaxVertex(visited, currWeight);
            if (maxVertex < 0) break;
            visited[maxVertex] = true;
            for (int neighbor: this.graph.neighbors(maxVertex)) {
                if (!visited[neighbor]) {
                    double weight = Math.exp(-1 * this.graph.weight(maxVertex, neighbor));
                    if (weight > currWeight[neighbor]) {
                        currWeight[neighbor] = weight;
                        parent[neighbor] = maxVertex;
                    }
                }
            }
        }
        return parent;
    }
    
    /**
     * @param probability  - the probability of a node to catch the disease from its neighbor
     * @param threshold
     * @return the true if probability >= threshold (precision = 0.01).
     */

    @Override
    public boolean willCatchtheDisease(double probability, double threshold) {
        return probability * 100 >= threshold * 100;
    }
    
    /**
     * Suppose a node will be infected if the highest probability for it to catch the disease exceeds the threshold
     * @param source - an infected node that will spread the disease
     * @return the percentage of the nodes that will eventually catch the disease.
     */
    
    @Override
    public double transfectionRate(int source, double threshold) {
    	double count = 0;
        for (int i = 1; i < graph.nodeCount(); i++) {
            graph.setValue(i, Double.POSITIVE_INFINITY);
        }
        int[] pred = new int[graph.nodeCount()];
        Arrays.fill(pred, 0);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        graph.setValue(source, 0);
        pq.add(source);
        while (!pq.isEmpty()) {
            int node = pq.poll();
            for (int neighbor : this.getNeighbors(node)) {
                double temp = graph.getValue(node) + graph.weight(node, neighbor);
                if (temp < graph.getValue(neighbor)) {
                    graph.setValue(neighbor, temp);
                    pq.add(neighbor);
                    pred[neighbor] = node;
                }
            }
        }
        
        for (int i = 1; i < graph.nodeCount(); i++) {
            if (willCatchtheDisease(Math.exp(-1 * graph.getValue(i)), threshold)) {
            	count++;
            }
        }
        
        
        return count / (graph.nodeCount() - 1);
        
     }
    
    
    /**
     * @param n the node
     * @return the degree of the node
     */

    @Override
    public int degree(int n) {
    	if (n <= 0 || n > graph.nodeCount() - 1) {
            return -1;
        }
        return graph.neighbors(n).length;
    }


    public Collection<Integer> degreeNodes(int d) {
        List<Integer> nodes = new ArrayList<>();
        for (int i = 1; i < this.graph.nodeCount(); i++) {
            if (degree(i) == d) {
                nodes.add(i);
            }
        }
        return nodes;
    }

    /**
     * Remove the nodes with the given degree
     * @param d         - the degree of the nodes to be removed
     * @return the removed nodes
     */

    @Override
    public Collection<Integer> removeNodesDegree(int d) {
        List<Integer> nodes = (List<Integer>) degreeNodes(d);
        Set<Integer> set = new HashSet<>();
        set.addAll(nodes);
        for (int node : set) {
            for (int neighbor : getNeighbors(node)) {
                this.graph.removeEdge(node, neighbor);
                this.graph.removeEdge(neighbor, node);
            }
        }
        return set;
    }



    /**
     * Suppose a node will be infected if the probability for it to catch the disease exceeds the threshold
     * even when the node is under the best protection it could have, by the best protection we meant the node
     * will only take the path of transmission with lowest probability
     *
     * @param source    - an infected node that will spread the disease
     * @param threshold
     * @param d         - remove nodes of degree d
     * @return the percentage of the nodes that will eventually catch the disease after we remove the vertices with
     * degree d.
     */
    @Override
    public double transfectionRateDegree(int source, double threshold, int d) {
        if (source <= 0 || source >= this.graph.nodeCount() || threshold < 0 || threshold > 1) {
            return -1;
        }
        Set<Integer> removedNodes = (Set<Integer>) removeNodesDegree(d);
        if (removedNodes.contains(source)) {
            return 0;
        }
        if (removedNodes.size() == 0) {
            return -1;
        }
        return transfectionRate(source, threshold);
    }

    /**
     * nodes with degree 0 or 1 have a cc of 0
     *
     * @param n the node
     * @return the  clustering coefficient of n
     */
    @Override
    public double clustCoeff(int n) {
        if (n <= 0 || n >= this.graph.nodeCount()) {
            return 0;
        }
        int numOfNeighbor = this.graph.neighbors(n).length;
        if (numOfNeighbor <= 1) {
            return 0;
        }
        int totalConnections = numOfNeighbor * (numOfNeighbor - 1) / 2;
        int connections = 0;
        for (int v : this.graph.neighbors(n)) {
            for (int w : this.graph.neighbors(n)) {
                if (v != w && this.graph.hasEdge(v, w)) {
                    connections++;
                }
            }
        }
        return  (connections / 2.0) / totalConnections;
    }

    /**
     * precision: 0.01 (use when comparing CC values)
     *
     * @param low  - the lower bound (inclusive) of the cc range
     * @param high - the upper bound (inclusive) of the cc range
     * @return a collection of nodes with a clustering coefficient
     * within [low, high]
     */
    @Override
    public Collection<Integer> clustCoeffNodes(double low, double high) {
        List<Integer> nodes = new ArrayList<>();
        if (low < 0 || high < 0 || high > 1 || high < low) {
            return nodes;
        }
        for (int i = 1; i < this.graph.nodeCount(); i++) {
            double cc = clustCoeff(i);
            if ((int) (cc * 100) >= (int) (low * 100)
                    && (int) (cc * 100) <= (int) (high * 100)) {
                nodes.add(i);
            }
        }
        return nodes;
    }

    /**
     * Remove the nodes with clustering coefficients within the given range
     *
     * @param low  - the lower bound
     * @param high - the higher bound
     * @return the removed nodes
     */
    @Override
    public Collection<Integer> removeNodesCC(double low, double high) {
        List<Integer> nodes = (List<Integer>) clustCoeffNodes(low, high);
        Set<Integer> set = new HashSet<>();
        set.addAll(nodes);
        for (int node : set) {
            for (int neighbor : this.graph.neighbors(node)) {
                this.graph.removeEdge(node, neighbor);
                this.graph.removeEdge(neighbor, node);
            }
        }
        return set;
    }

    /**
     * Suppose a node will be infected if the probability for it to catch the disease exceeds the threshold
     * even when the node is under the best protection it could have, by the best protection we meant the node
     * will only take the path of transmission with lowest probability
     *
     * @param source    - an infected node that will spread the disease
     * @param threshold - an infected node that will spread the disease
     * @param low       - remove nodes of degree d
     * @param high      - remove nodes of degree d
     * @return the percentage of the nodes that will eventually catch the disease after we remove the vertices with
     * cc in between low and high.
     */
    @Override
    public double transfectionRateCC(int source, double threshold, double low, double high) {
        if (source <= 0 || source >= this.graph.nodeCount() || low < 0 || high < 0
                || high > 1 || threshold < 0 || threshold > 1) {
            return -1;
        }
        Set<Integer> removedNodes = (Set<Integer>) removeNodesCC(low, high);
        if (removedNodes.contains(source)) {
            return 0;
        } else if (removedNodes.size() == 0) {
            return -1;
        }
        return transfectionRate(source, threshold);
    }

    /**
     * Remove the nodes with clustering coefficients within the given range
     *
     * @param vaccinated  - a collection of nodes getting the vaccination
     */
    public void removeVaccinated(Collection<Integer> vaccinated) {
        Set<Integer> set = new HashSet<>();
        set.addAll(vaccinated);
        for (int node : set) {
            for (int neighbor : this.graph.neighbors(node)) {
                this.graph.removeEdge(node, neighbor);
                this.graph.removeEdge(neighbor, node);
            }
        }
    }

    /**
     * Suppose a node will be infected if the probability for it to catch the disease exceeds the threshold
     * even when the node is under the best protection it could have, by the best protection we meant the node
     * will only take the path of transmission with lowest probability
     *
     * @param source     - an infected node that will spread the disease
     * @param threshold  - an infected node that will spread the disease
     * @param vaccinated - vaccinated population in the group
     * @return the percentage of the nodes that will eventually catch the disease after we vaccinate some population.
     * Those people will stop the spread of disease starting from them.
     */
    @Override
    public double transfectionRateVaccine(int source, double threshold, Collection<Integer> vaccinated) {
        if (source <= 0 || source >= this.graph.nodeCount() || vaccinated.contains(source)
                || vaccinated.size() == 0 || threshold < 0 || threshold > 1) {
            return -1;
        }
        removeVaccinated(vaccinated);
        return transfectionRate(source, threshold);
    }


    public static void main(String[] args) {
        IInformationSpread inf = new InformationSpread();
        inf.loadGraphFromDataSet("datasets/test.mtx");
        Collection<Integer> path = inf.longestTransmissionPath(1, 5);
        System.out.println(path);

        
    }

}
