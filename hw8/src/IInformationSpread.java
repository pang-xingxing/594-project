import java.util.Collection;

/**
 * @author ericfouh
 */
public interface IInformationSpread {

    /**
     * 
     */
    public static final Object VISITED  = -5;
    /**
     * 
     */
    public static final int    INFINITY = Integer.MAX_VALUE;

    /**
     * Create a graph representation of the dataset. The first line of the file
     * contains the number of nodes add 1 to the number of nodes in the graph
     * since there is no node with id 0
     * 
     * @param filePath the path of the data
     * @return the number of entries (nodes) in the dataset (graph)
     */
    int loadGraphFromDataSet(String filePath);

    /**
     * Return the neighbors ids of a specific node
     * 
     * @param id the id of the page
     * @return the array of neighbor(s)
     */
    int[] getNeighbors(int id);

    /**
     * The path of transmission with highest probability
     * @param source      - an infected node that could spread the disease
     * @param destination - the id of the destination node
     * @return the path to spread disease from source to destination
     *         with the highest transmission probability
     */
    Collection<Integer> longestTransmissionPath(int source, int destination);

    /**
     * @param probability  - the probability of a node to catch the disease from its neighbor
     * @param threshold
     * @return the true if probability >= threshold.
     */
    boolean willCatchtheDisease(double probability, double threshold);


    /**
     * Suppose a node will be infected if the highest probability for it to catch the disease exceeds the threshold
     * @param source - an infected node that will spread the disease
     * @return the percentage of the nodes that will eventually catch the disease.
     */
    double transfectionRate(int source, double threshold);

    // -- Degree
    /**
     * @param n the node
     * @return the degree of the node
     */
    int degree(int n);

    /**
     * Remove the nodes with the given degree
     * @param d         - the degree of the nodes to be removed
     * @return the removed nodes
     */
    Collection<Integer> removeNodesDegree(int d);

    /**
     * Suppose a node will be infected if the probability for it to catch the disease exceeds the threshold
     * even when the node is under the best protection it could have, by the best protection we meant the node
     * will only take the path of transmission with lowest probability
     * @param source - an infected node that will spread the disease
     * @param threshold - an infected node that will spread the disease
     * @param d - remove nodes of degree d
     * @return the percentage of the nodes that will eventually catch the disease after we remove the vertices with
     * degree d.
     */
    double transfectionRateDegree(int source, double threshold, int d);



    // -- CLustering Coefficient

    /**
     * nodes with degree 0 or 1 have a cc of 0
     * @param n the node
     * @return the  clustering coefficient of n
     */
    double clustCoeff(int n);

    /**
     * precision: 0.01 (use when comparing CC values)
     * @param low - the lower bound (inclusive) of the cc range
     * @param high - the upper bound (inclusive) of the cc range
     * @return a collection of nodes with a clustering coefficient
     * within [low, high]
     */
    Collection<Integer> clustCoeffNodes(double low, double high);

    /**
     * Remove the nodes with clustering coefficients within the given range
     * @param low  - the lower bound
     * @param high - the higher bound
     * @return the removed nodes
     */
    Collection<Integer> removeNodesCC(double low, double high);


    /**
     * Suppose a node will be infected if the probability for it to catch the disease exceeds the threshold
     * even when the node is under the best protection it could have, by the best protection we meant the node
     * will only take the path of transmission with lowest probability
     * @param source - an infected node that will spread the disease
     * @param threshold - an infected node that will spread the disease
     * @param low - remove nodes of degree d
     * @param high - remove nodes of degree d
     * @return the percentage of the nodes that will eventually catch the disease after we remove the vertices with
     * cc in between low and high.
     */
    double transfectionRateCC(int source, double threshold, double low, double high);

    /**
     * Suppose a node will be infected if the probability for it to catch the disease exceeds the threshold
     * even when the node is under the best protection it could have, by the best protection we meant the node
     * will only take the path of transmission with lowest probability
     * @param source - an infected node that will spread the disease
     * @param threshold - an infected node that will spread the disease
     * @param vaccinated - vaccinated population in the group
     * @return the percentage of the nodes that will eventually catch the disease after we vaccinate some population.
     * Those people will stop the spread of disease starting from them.
     */
    double transfectionRateVaccine(int source, double threshold, Collection<Integer> vaccinated);



}
