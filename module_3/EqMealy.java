import java.util.*;

public class EqMealy {
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

    public static String GraphString(int[] numeration, int[] antinumeration, Pair[][] tableNew, int[] count) {
        StringBuilder result = new StringBuilder();
        result.append("digraph {\n");
        result.append("\trankdir = LR\n");

        for (int i = 0; i < count[0]; i++) {
            for (int j = 0; j < tableNew[i].length; j++) {
                result.append(String.format("\t%d -> %d [label = \"%d(%s)\"]\n", 
                        i, 
                        antinumeration[tableNew[numeration[i]][j].to], 
                        j, 
                        tableNew[numeration[i]][j].signal));
            }
        }
        result.append("}");
        
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n1 = sc.nextInt();
        int m1 = sc.nextInt();
        int q0_1 = sc.nextInt();
        Pair[][] table1 = new Pair[n1][m1];

        int i = 0;
        do {
            int j = 0;
            do {
                int to = sc.nextInt();
                table1[i][j] = new Pair(to, "");
                j++;
            } while (j < m1);
            i++;
        } while (i < n1);

        i = 0;
        do {
            int j = 0;
            do {
                String signal = sc.next();
                table1[i][j].signal = signal;
                j++;
            } while (j < m1);
            i++;
        } while (i < n1);
        int n2 = sc.nextInt();
        int m2 = sc.nextInt();
        int q0_2 = sc.nextInt();
        Pair[][] table2 = new Pair[n2][m2];

        i = 0;
        do {
            int j = 0;
            do {
                int to = sc.nextInt();
                table2[i][j] = new Pair(to, "");
                j++;
            } while (j < m2);
            i++;
        } while (i < n2);

        i = 0;
        do {
            int j = 0;
            do {
                String signal = sc.next();
                table2[i][j].signal = signal;
                j++;
            } while (j < m2);
            i++;
        } while (i < n2);
        pr = new int[n1];
        rk = new int[n1];
        int[] backmapping1 = new int[n1];
        int[] arr1 = new int[n1];

        Pair[][] tableNew1 = minimize(table1, backmapping1, arr1);

        int[] numeration1 = new int[n1];
        int[] antinumeration1 = new int[n1];
        boolean[] visited1 = new boolean[n1];
        int[] count1 = {0};

        q0_1 = backmapping1[arr1[q0_1]];
        dfs(q0_1, count1, tableNew1, visited1, numeration1, antinumeration1);

        String String1 = GraphString(numeration1, antinumeration1, tableNew1, count1);
        pr = new int[n2];
        rk = new int[n2];
        int[] backmapping2 = new int[n2];
        int[] arr2 = new int[n2];

        Pair[][] tableNew2 = minimize(table2, backmapping2, arr2);

        int[] numeration2 = new int[n2];
        int[] antinumeration2 = new int[n2];
        boolean[] visited2 = new boolean[n2];
        int[] count2 = {0};

        q0_2 = backmapping2[arr2[q0_2]];
        dfs(q0_2, count2, tableNew2, visited2, numeration2, antinumeration2);

        String String2 = GraphString(numeration2, antinumeration2, tableNew2, count2);
        if (String1.equals(String2)) {
            System.out.println("EQUAL");
        } else {
            System.out.println("NOT EQUAL");
        }
    }
}
