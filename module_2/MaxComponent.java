import java.util.*;

public class MaxComponent {
    static class Graph {
        int n;
        List<List<Integer>> adjList;
        boolean[] visited;
        List<Integer> componentNodes;
        LinkedHashSet<String> componentEdges;
        List<String> allEdges;

        public Graph(int n) {
            this.n = n;
            adjList = new ArrayList<>();
            visited = new boolean[n];
            allEdges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        void addEdge(int u, int v) {
            adjList.get(u).add(v);
            adjList.get(v).add(u);
            String edge = u < v ? u + " " + v : v + " " + u;
            allEdges.add(edge);
        }

        List<Integer> bfs(int s) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(s);
            visited[s] = true;
            List<Integer> nodes = new ArrayList<>();
            LinkedHashSet<String> edges = new LinkedHashSet<>();

            while (!queue.isEmpty()) {
                int current = queue.poll();
                nodes.add(current);
                for (int neighbor : adjList.get(current)) {
                    String edge = current < neighbor ? current + " " + neighbor : neighbor + " " + current;
                    edges.add(edge);
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            componentNodes = nodes;
            componentEdges = edges;
            return nodes;
        }

        void findLargestComponent() {
            int maxSize = 0;
            int maxEdges = 0;
            LinkedHashSet<String> largestEdgeSet = new LinkedHashSet<>();
            List<Integer> largestComponent = new ArrayList<>();
            int minVertexIndex = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    List<Integer> component = bfs(i);
                    int size = component.size();
                    int edgeCount = componentEdges.size();
                    if (size > maxSize || (size == maxSize && edgeCount > maxEdges) ||
                            (size == maxSize && edgeCount == maxEdges && i < minVertexIndex)) {
                        maxSize = size;
                        maxEdges = edgeCount;
                        minVertexIndex = i;
                        largestComponent = new ArrayList<>(componentNodes);
                        largestEdgeSet = new LinkedHashSet<>(componentEdges);
                    }
                }
            }

            System.out.println("graph {");
            for (int i = 0; i < n; i++) {
                if (largestComponent.contains(i))
                    System.out.println("    " + i + " [color = red]");
                else
                    System.out.println("    " + i);
            }
            for (String edge : allEdges) {
                String[] nodes = edge.split(" ");
                if (largestEdgeSet.contains(edge)) {
                    System.out.println("    " + nodes[0] + " -- " + nodes[1] + " [color = red]");
                } else {
                    System.out.println("    " + nodes[0] + " -- " + nodes[1]);
                }
            }
            System.out.println("}");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();

        Graph graph = new Graph(N);
        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }

        graph.findLargestComponent();
        scanner.close();
    }
}
