import java.util.*;

public class Ideal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Map<Integer, List<Pair>> graph = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int A = scanner.nextInt();
            int B = scanner.nextInt();
            int C = scanner.nextInt();
            graph.computeIfAbsent(A, k -> new ArrayList<>()).add(new Pair(B, C));
            graph.computeIfAbsent(B, k -> new ArrayList<>()).add(new Pair(A, C));
        }
        List<Integer> res = BFS(graph, 1, n);
        System.out.println(res.size());
        res.forEach(color -> System.out.print(color + " "));
        scanner.close();
    }

    private static List<Integer> BFS(Map<Integer, List<Pair>> graph, int start, int end) {
        PriorityQueue<Path> pq = new PriorityQueue<>();
        Map<Integer, Path> bestPaths = new HashMap<>();
        pq.add(new Path(start, new ArrayList<>()));
        bestPaths.put(start, new Path(start, new ArrayList<>()));
        while (!pq.isEmpty()) {
            Path current = pq.poll();
            int currentRoom = current.room;
            List<Integer> currentColors = current.colors;
            if (currentRoom == end) {
                return currentColors;
            }
            for (Pair neighbor : graph.getOrDefault(currentRoom, new ArrayList<>())) {
                List<Integer> newPath = new ArrayList<>(currentColors);
                newPath.add(neighbor.color);
                Path newP = new Path(neighbor.room, newPath);
                if (!bestPaths.containsKey(neighbor.room) || newP.compareTo(bestPaths.get(neighbor.room)) < 0) {
                    bestPaths.put(neighbor.room, newP);
                    pq.add(newP);
                }
            }
        }
        return new ArrayList<>();
    }
}

class Pair {
    int room;
    int color;
    Pair(int room, int color) {
        this.room = room;
        this.color = color;
    }
}

class Path implements Comparable<Path> {
    int room;
    List<Integer> colors;
    Path(int room, List<Integer> colors) {
        this.room = room;
        this.colors = new ArrayList<>(colors);
    }


    public int compareTo(Path other) {
        if (this.colors.size() != other.colors.size()) {
            return Integer.compare(this.colors.size(), other.colors.size());
        }
        for (int i = 0; i < this.colors.size(); i++) {
            if (!this.colors.get(i).equals(other.colors.get(i))) {
                return Integer.compare(this.colors.get(i), other.colors.get(i));
            }
        }
        return 0;
    }
}
