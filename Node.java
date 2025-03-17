import java.util.ArrayList;
import java.util.Arrays;

public class Node implements Comparable<Node>{
    private static ArrayList<Node> visited = new ArrayList<>();
    private String[][] matrix;
    private Node parent;
    private int cost;


    public Node(String[][] matrix, Node parent) {
        this.matrix = matrix;
        this.parent = parent;
        this.cost = calculateCost();
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public Node getParent() {
        return parent;
    }

    public int getCost() {
        return cost;
    }

    public static ArrayList<Node> getVisited() {
        return visited;
    }

    public static void addVisitedNode(Node visitedNode) {
        visited.add(visitedNode);
    }

    private int calculateCost() {
        String[][] correct = {{"1","2","3"},
                            {"4","5","6"},
                            {"7","8","0"}};

        int correctlyPlaced = 0;
        for (int i = 0; i < this.matrix.length;i++) {
            for (int j = 0; j < this.matrix[i].length;j++) {
                if(correct[i][j].equals(this.matrix[i][j])) {
                    correctlyPlaced++;
                }
            }
        }

        return (this.matrix.length * this.matrix[0].length) - correctlyPlaced - 1;
    }

    @Override
    public String toString() {
        return "Node [matrix=" + Arrays.deepToString(matrix) + ", parent=" + parent + "]";
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.cost, o.getCost());
    }
}
