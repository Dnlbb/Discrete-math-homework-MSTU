import java.util.*;

public class BridgeNum {
    private ArrayList<ArrayList<Integer>> adjacencyList;
    private int[] discoveryTimes;
    private int[] earliestReachable;
    private int[] parentVertex;
    private boolean[] visitedNodes;
    private int discoveryTimeCounter = 0;
    private int totalBridges = 0;

    public BridgeNum(int vertexCount) {
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        discoveryTimes = new int[vertexCount];
        earliestReachable = new int[vertexCount];
        parentVertex = new int[vertexCount];
        visitedNodes = new boolean[vertexCount];
    }

    public void connectVertices(int vertex1, int vertex2) {
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1);
    }

    private void depthFirstSearch(int currentVertex) {
        visitedNodes[currentVertex] = true;
        discoveryTimes[currentVertex] = earliestReachable[currentVertex] = ++discoveryTimeCounter;

        for (int adjacentVertex : adjacencyList.get(currentVertex)) {
            if (!visitedNodes[adjacentVertex]) {
                parentVertex[adjacentVertex] = currentVertex;
                depthFirstSearch(adjacentVertex);

                earliestReachable[currentVertex] = Math.min(earliestReachable[currentVertex], earliestReachable[adjacentVertex]);

                if (earliestReachable[adjacentVertex] > discoveryTimes[currentVertex]) {
                    totalBridges++;
                }
            } else if (adjacentVertex != parentVertex[currentVertex]) {
                earliestReachable[currentVertex] = Math.min(earliestReachable[currentVertex], discoveryTimes[adjacentVertex]);
            }
        }
    }

    public int calculateBridges() {
        Arrays.fill(parentVertex, -1);
        Arrays.fill(visitedNodes, false);
        Arrays.fill(discoveryTimes, -1);
        Arrays.fill(earliestReachable, -1);

        for (int i = 0; i < adjacencyList.size(); i++) {
            if (!visitedNodes[i]) {
                depthFirstSearch(i);
            }
        }
        return totalBridges;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfVertices = scanner.nextInt();
        int numberOfEdges = scanner.nextInt();
        BridgeNum bridgenum = new BridgeNum(numberOfVertices);

        for (int i = 0; i < numberOfEdges; i++) {
            int startVertex = scanner.nextInt();
            int endVertex = scanner.nextInt();
            bridgenum.connectVertices(startVertex, endVertex);
        }

        int result = bridgenum.calculateBridges();
        System.out.println(result);
    }
}
