import java.util.*;
public class Prim{
    static List<List<Edge>> adjacency_lst;
    static int V;
    static int prim() {
        boolean[] visit = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int Weigh = 0;
        visit[0] = true;
        for (Edge e : adjacency_lst.get(0))
            pq.offer(e);
        while (!pq.isEmpty()) {
            Edge curr = pq.poll();
            if (visit[curr.v])
                continue;
            visit[curr.v] = true;
            Weigh += curr.weight;
            for (Edge e : adjacency_lst.get(curr.v)) {
                if (!visit[e.v])
                    pq.offer(e);
            }
        }
        return Weigh;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        int E = sc.nextInt();
        adjacency_lst = new ArrayList<>(V);
        for (int i = 0; i < V; i++)
            adjacency_lst.add(new ArrayList<>());
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int weight = sc.nextInt();
            adjacency_lst.get(u).add(new Edge(u, v, weight));
            adjacency_lst.get(v).add(new Edge(v, u, weight));
        }
        System.out.println(prim());
    }
}
class Edge implements Comparable<Edge> {
    int u, v, weight;
    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}
