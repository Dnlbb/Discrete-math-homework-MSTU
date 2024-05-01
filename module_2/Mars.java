import java.util.*;

public class Mars {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();

        char[][] conflicts = new char[n][n];
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().replace(" ", "");
            conflicts[i] = line.toCharArray();
        }

        List<Integer> bestGroup1 = null;
        int minDifference = Integer.MAX_VALUE;


        for (int i = 1; i < (1 << n); i++) {
            List<Integer> group1 = new ArrayList<>();
            List<Integer> group2 = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    group1.add(j + 1);
                } else {
                    group2.add(j + 1);
                }
            }

            if (isValidGroup(group1, conflicts) && isValidGroup(group2, conflicts)) {
                int difference = Math.abs(group1.size() - group2.size());
                if (difference < minDifference ||
                        (difference == minDifference && isLexicographicallySmaller(group1, bestGroup1))) {
                    minDifference = difference;
                    if (group1.size() <= group2.size()) {
                        bestGroup1 = new ArrayList<>(group1);
                    } else {
                        bestGroup1 = new ArrayList<>(group2);
                    }
                }
            }
        }

        if (bestGroup1 == null) {
            System.out.println("No solution");
        } else {
            Collections.sort(bestGroup1);
            for (int member : bestGroup1) {
                System.out.print(member + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValidGroup(List<Integer> group, char[][] conflicts) {
        for (int i = 0; i < group.size(); i++) {
            for (int j = i + 1; j < group.size(); j++) {
                if (conflicts[group.get(i) - 1][group.get(j) - 1] == '+') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isLexicographicallySmaller(List<Integer> group1, List<Integer> bestGroup1) {
        if (bestGroup1 == null) return true;
        int size = Math.min(group1.size(), bestGroup1.size());
        for (int i = 0; i < size; i++) {
            if (!group1.get(i).equals(bestGroup1.get(i))) {
                return group1.get(i) < bestGroup1.get(i);
            }
        }
        return group1.size() < bestGroup1.size();
    }
}
