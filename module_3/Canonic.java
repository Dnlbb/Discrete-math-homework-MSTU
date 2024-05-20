import java.util.*;

public class Canonic {
    static int ind;
    static {
        ind = 0;
    }

    public static void dfs(int start, int[] arr, int[] pos, int m, int[][] tr) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (arr[node] == -1) {
                arr[node] = ind;
                pos[ind] = node;
                ind++;
                for (int i = m - 1; i >= 0; i--) {
                    if (arr[tr[node][i]] == -1) {
                        stack.push(tr[node][i]);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        int q0 = scan.nextInt();
        int[][] tr = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                tr[i][j] = scan.nextInt();
            }
        }
        String[][] outp = new String[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                outp[i][j] = scan.next();
            }
        }
        int[] arr = new int[n];
        int[] pos = new int[n];
        Arrays.fill(arr, -1);
        dfs(q0, arr, pos, m, tr);
        System.out.println(n);
        System.out.println(m);
        System.out.println(0); 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(arr[tr[pos[i]][j]] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(outp[pos[i]][j] + " ");
            }
            System.out.println();
        }
        scan.close();
    }
}
