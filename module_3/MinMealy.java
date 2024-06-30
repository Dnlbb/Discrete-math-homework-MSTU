import java.util.*;

public class MinMealy {
    static int[] pr;
    static int[] rk;

  public static void dfs(int v, int[] count, Pair[][] table, boolean[] visited, int[] numeration, int[] antinumeration) {
        visited[v] = true;
        numeration[count[0]] = v;
        antinumeration[v] = count[0];
        count[0]++;
        int i = 0;
        do {
            int to = table[v][i].to;
            if (!visited[to]) {
                dfs(to, count, table, visited, numeration, antinumeration);
            }
            i++;
        } while (i < table[v].length);
    }

    public static void mkSet(int v) {
        pr[v] = v;
        rk[v] = 0;
    }
    public static int fdSet(int v) {
        return v == pr[v] ? v : (pr[v] = fdSet(pr[v]));
    }
     public static void un(int a, int b) {
        a = fdSet(a);
        b = fdSet(b);
        if (a != b) {
            if (rk[a] < rk[b]) un(b, a);
            else {
                pr[b] = a;
                rk[a] += rk[a] == rk[b] ? 1 : 0;
            }
        }
    }
    static class Pair {
        int to;
        String signal;
        Pair(int to, String signal) {
            this.to = to;
            this.signal = signal;
        }
    }
    public static int split1(Pair[][] table, int[] arr) {
        int m = table.length;
        int i = 0;
        do {
            mkSet(i);
            i++;
        } while (i < table.length);

        i = 0;
        do {
            int j = 0;
            do {
                if (fdSet(i) != fdSet(j)) {
                    boolean eq = true;
                    int k = 0;
                    do {
                        if (!table[i][k].signal.equals(table[j][k].signal)) {
                            eq = false;
                            break;
                        }
                        k++;
                    } while (k < table[0].length);
                    if (eq) {
                        un(i, j);
                        m--;
                    }
                }
                j++;
            } while (j < table.length);
            i++;
        } while (i < table.length);

        i = 0;
        do {
            arr[i] = fdSet(i);
            i++;
        } while (i < table.length);
        return m;
    }

    public static int split(Pair[][] table, int[] arr) {
        int m = table.length;
        int i = 0;
        do {
            mkSet(i);
            i++;
        } while (i < table.length);

        i = 0;
        do {
            int j = 0;
            do {
                if (arr[i] == arr[j] && fdSet(i) != fdSet(j)) {
                    boolean eq = true;
                    int k = 0;
                    do {
                        int w1 = table[i][k].to;
                        int w2 = table[j][k].to;
                        if (arr[w1] != arr[w2]) {
                            eq = false;
                            break;
                        }
                        k++;
                    } while (k < table[0].length);
                    if (eq) {
                        un(i, j);
                        m--;
                    }
                }
                j++;
            } while (j < table.length);
            i++;
        } while (i < table.length);

        i = 0;
        do {
            arr[i] = fdSet(i);
            i++;
        } while (i < table.length);
        return m;
    }

    public static Pair[][] minimize(Pair[][] table, int[] backmapping, int[] arr) {
        int m = split1(table, arr);
        while (true) {
            int m1 = split(table, arr);
            if (m == m1) {
                break;
            }
            m = m1;
        }

        Pair[][] tableNew = new Pair[m][table[0].length];
        boolean[] used = new boolean[table.length];
        int cur = 0;

        int i = 0;
        do {
            int i1 = arr[i];
            backmapping[i] = cur;
            if (!used[i1]) {
                used[i1] = true;
                cur++;
            }
            i++;
        } while (i < table.length);

        cur = 0;
        Arrays.fill(used, false);
        i = 0;
        do {
            int i1 = arr[i];
            if (!used[i1]) {
                used[i1] = true;
                int j = 0;
                do {
                    tableNew[cur][j] = new Pair(backmapping[arr[table[i][j].to]], table[i][j].signal);
                    j++;
                } while (j < table[0].length);
                cur++;
            }
            i++;
        } while (i < table.length);

        return tableNew;
    }

    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int q0 = sc.nextInt();
        Pair[][] table = new Pair[n][m];

        int i = 0;
        do {
            int j = 0;
            do {
                int to = sc.nextInt();
                table[i][j] = new Pair(to, "");
                j++;
            } while (j < m);
            i++;
        } while (i < n);

        i = 0;
        do {
            int j = 0;
            do {
                String signal = sc.next();
                table[i][j].signal = signal;
                j++;
            } while (j < m);
            i++;
        } while (i < n);

        pr = new int[n];
        rk = new int[n];
        int[] backmapping = new int[n];
        int[] arr = new int[n];

        Pair[][] tableNew = minimize(table, backmapping, arr);

        int[] numeration = new int[n];
        int[] antinumeration = new int[n];
        boolean[] visited = new boolean[n];
        int[] count = {0};

        q0 = backmapping[arr[q0]];
        dfs(q0, count, tableNew, visited, numeration, antinumeration);

        System.out.println("digraph {");
        System.out.println("\trankdir = LR");

        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        i = 0;
        do {
            int j = 0;
            do {
                System.out.printf("\t%d -> %d [label = \"%c(%s)\"]\n", i, antinumeration[tableNew[numeration[i]][j].to], alphabet.charAt(j), tableNew[numeration[i]][j].signal);
                j++;
            } while (j < tableNew[i].length);
            i++;
        } while (i < count[0]);
        System.out.println("}");
    }
}
