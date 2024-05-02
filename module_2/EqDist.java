import java.util.*;

public class EqDist{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numVertices = scanner.nextInt();
        int numEdges = scanner.nextInt();

        List<List<Integer>> Lst = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            Lst.add(new ArrayList<>());
        }

        for (int i = 0; i < numEdges; i++) {
            int startVertex = scanner.nextInt();
            int endVertex = scanner.nextInt();
            Lst.get(startVertex).add(endVertex);
            Lst.get(endVertex).add(startVertex);
        }

        int numBases = scanner.nextInt();
        int[] baseVertices = new int[numBases];
        for (int i = 0; i < numBases; i++) {
            baseVertices[i] = scanner.nextInt();
        }

        int[][] allDistances = new int[numBases][numVertices];
        for (int i = 0; i < numBases; i++) {
            Arrays.fill(allDistances[i], -1);
            bfs(Lst, baseVertices[i], allDistances[i]);
        }
        List<Integer> equidistantVertices = findEquidistantVertices(numVertices, numBases, allDistances);
        printResults(equidistantVertices);
    }
    private static void bfs(List<List<Integer>> graph, int source, int[] distances) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        distances[source] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            for (int adjacentVertex : graph.get(currentVertex)) {
                if (distances[adjacentVertex] == -1) {
                    distances[adjacentVertex] = distances[currentVertex] + 1;
                    queue.offer(adjacentVertex);
                }
            }
        }
    }

    private static List<Integer> findEquidistantVertices(int numVertices, int numBases, int[][] allDistances) {
        List<Integer> result = new ArrayList<>();
        for (int vertex = 0; vertex < numVertices; vertex++) {
            boolean isEquidistant = true;
            int distance = allDistances[0][vertex];
            for (int baseIndex = 1; baseIndex < numBases && isEquidistant; baseIndex++) {
                isEquidistant = allDistances[baseIndex][vertex] == distance;
            }
            if (isEquidistant && distance != -1) {
                result.add(vertex);
            }
        }
        return result;
    }

    private static void printResults(List<Integer> results) {
        if (results.isEmpty()) {
            System.out.println("-");
        } else {
            Collections.sort(results);
            results.forEach(v -> System.out.print(v + " "));
        }
    }
}
