import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
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
                int w = Integer.parseInt(words[2]);
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

	@Override
	public Collection<Integer> lowestTransmissionPath(int source, int destination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean willCatchtheDisease(double probability, double threshold) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double tranfectionRate(int source, double threshold) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int degree(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Integer> removeNodesDegree(int d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double tranfectionRateDegree(int source, double threshold, int d) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double clustCoeff(int n) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Integer> clustCoeffNodes(double low, double high) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Integer> removeNodesCC(double low, double high) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double tranfectionRateCC(int source, double threshold, double low, double high) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double tranfectionRateVaccine(int source, double threshold, Collection<Integer> vaccinated) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}
