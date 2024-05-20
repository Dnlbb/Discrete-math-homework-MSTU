import java.util.*;

public class GraphBase {
    private static int time = 1;
    private static int componentCount = 1;
    private static void visitNode(GraphNode[] graph, int index, Deque<Integer> stack) {
        GraphNode node = graph[index];
        node.discoveryTime = time;
        node.lowLink = time;
        time++;
        stack.push(index);
        int j = 0;
        do {
            if (j < node.edges.size()) {
                int neighborIndex = node.edges.get(j);
                GraphNode neighbor = graph[neighborIndex];
                if (neighbor.discoveryTime == 0) {
                    visitNode(graph, neighborIndex, stack);
                    node.lowLink = Math.min(node.lowLink, neighbor.lowLink);
                } else if (neighbor.component == 0) {
                    node.lowLink = Math.min(node.lowLink, neighbor.discoveryTime);
                }
                j++;
            }
        } while (j < node.edges.size());
        if (node.discoveryTime == node.lowLink) {
            int currentIndex;
            do {
                currentIndex = stack.pop();
                graph[currentIndex].component = componentCount;
            } while (currentIndex != index);
            componentCount++;
        }
    }
    private static void Tarjan(GraphNode[] graph) {
        Deque<Integer> stack = new ArrayDeque<>();
        int i = 0;
        do {
            if (graph[i].discoveryTime == 0) {
                visitNode(graph, i, stack);
            }
            i++;
        } while (i < graph.length);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        GraphNode[] graph = new GraphNode[n];
        int i = 0;
        do {
            graph[i] = new GraphNode();
            i++;
        } while (i < n);
        int j = 0;
        if (m > 0) {
            do {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                graph[u].edges.add(v);
                j++;
            } while (j < m);
        }  
        Tarjan(graph);
        int[] minIndices = new int[componentCount];
        boolean[] isComponentBase = new boolean[componentCount];
        Arrays.fill(minIndices, -1);
        Arrays.fill(isComponentBase, true);
        int k = 0;
        do {
            int comp = graph[k].component;
            if (minIndices[comp] == -1) {
                minIndices[comp] = k;
            }
            k++;
        } while (k < n);
        int l = 0;
        do {
            GraphNode node = graph[l];
            int p = 0;
            do {
                if (p < node.edges.size()) {
                    int neighborIndex = node.edges.get(p);
                    int neighborComp = graph[neighborIndex].component;
                    if (node.component != neighborComp) {
                        isComponentBase[neighborComp] = false;
                    }
                    p++;
                }
            } while (p < node.edges.size());
            l++;
        } while (l < n);
        List<Integer> baseVertices = new ArrayList<>();
        int q = 1;
        do {
            if (isComponentBase[q]) {
                baseVertices.add(minIndices[q]);
            }
            q++;
        } while (q < componentCount);
        if (m == 0) {
            baseVertices.clear();
            for (int x = 0; x < n; x++) {
                baseVertices.add(x);
            }
        }
        Collections.sort(baseVertices);
        int r = 0;
        do {
            System.out.print(baseVertices.get(r) + " ");
            r++;
        } while (r < baseVertices.size());
    

        System.out.println();
        scanner.close();


    }
}

class GraphNode {
    List<Integer> edges = new ArrayList<>();
    int discoveryTime = 0;
    int lowLink = 0;
    int component = 0;
}
