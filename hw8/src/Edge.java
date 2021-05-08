public class Edge { // Doubly linked list node
    int vertex;
    double weight;
    Edge prev;
    Edge next;
    int vertex2;

    public Edge(int v, double w, Edge p, Edge n) {
        vertex = v;
        weight = w;
        prev = p;
        next = n;
    }
    
    public Edge(int u, int v, double w) { 
        vertex = u;
        vertex2 = v;
        weight = w;
    }
    
    public Edge(int u, int v) { 
        vertex = u;
        vertex2 = v;
    }
    
    public int getLeft() {
        return this.vertex;
    }
    
    public int getRight() {
        return this.vertex2;
    }
    
    public double getWeight() {
        return this.weight;
    }
    
    public boolean equals(Object o) {
        Edge other = (Edge) o;
        if (vertex == other.getLeft() && vertex2 == other.getRight()) {
            return true;
        }
        if (vertex == other.getRight() && vertex2 == other.getLeft()) {
            return true;
        }
        return false;
    }
}