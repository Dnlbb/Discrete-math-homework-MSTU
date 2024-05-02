import java.util.*;

public class Kruskal {
    static int[] p;
    static int[] r;
    public static double MinDist(int n, int[][] points) {
        List<Edge> edges = new ArrayList<>();
        int i = 0;
        while (i < n) {
            int j = i + 1;
            while (j < n) {
                double distance = rst(points[i], points[j]);
                edges.add(new Edge(i, j, distance));
                j++;
            }
            i++;
        }
        Collections.sort(edges);
        p = new int[n];
        r = new int[n];
        i = 0;
        while (i < n) {
            p[i] = i;
            r[i] = 1;
            i++;
        }
        double minDistance = 0.0;
        for (Edge edge : edges) {
            int u = edge.u;
            int v = edge.v;
            double weight = edge.weight;
            if (!union(u, v)) {
                minDistance += weight;
            }
        }
        return minDistance;
    }
    static class Edge implements Comparable<Edge> {
        int u;
        int v;
        double weight;
        Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }
    public static double rst(int[] point1, int[] point2) {
        return Math.sqrt(Math.pow(point2[0] - point1[0], 2) + Math.pow(point2[1] - point1[1], 2));
    }
    public static int find(int component) {
        return p[component] == component ? component : (p[component] = find(p[component]));
    }
    public static boolean union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        if (pu == pv) {
            return true;
        }
        if (r[pu] < r[pv]) {
            p[pu] = pv;
        } else if (r[pu] > r[pv]) {
            p[pv] = pu;
        } else {
            p[pu] = pv;
            r[pv]++;
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] points = new int[n][2];
        int i = 0;
        while (i < n) {
            points[i][0] = scanner.nextInt();
            points[i][1] = scanner.nextInt();
            i++;
        }
        double minDistance = MinDist(n, points);
        System.out.printf("%.2f", minDistance);
    }
}
