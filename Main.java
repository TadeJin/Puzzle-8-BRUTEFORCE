import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        String[][] inputMatrix = {{"1","2","4"},
                            {"3","5","6"},
                            {"8","7","0"}};

        Node root = new Node(inputMatrix, null);
        Node result = solve(root);

        if (result != null) {
            System.out.println("SOLUTION FOUND");
            System.out.println(Arrays.deepToString(result.getMatrix()));

            System.out.println("Step count: " + getParentCount(result));
        } else {
            System.out.println("Solution not found");
        }
        
    }

    public static Node solve(Node root) {
        if (isSolvable(root.getMatrix())) {
            return solvePuzzle(root);
        }

        return null;
    }

    public static boolean isSolvable(String[][] inputMatrix) {
        int inversions = 0;
        String[] array = new String[inputMatrix.length*inputMatrix[0].length-1];
        int cnt = 0;

        for(int i = 0; i < inputMatrix.length; i++) {
            for(int j = 0; j < inputMatrix[i].length; j++) {
                if (!inputMatrix[i][j].equals("0")) {
                    array[cnt] = inputMatrix[i][j];
                    cnt++;
                }
            }
        }

        for (int i = 0; i < array.length-1;i++) {
            if (Integer.parseInt(array[i]) > Integer.parseInt(array[i+1])) {
                inversions++;
            }
        }

        return inversions % 2 == 0;
    }

    public static Node solvePuzzle(Node inputNode) {

        if (isSolved(inputNode)) {
            return inputNode;
        }

        int zeroRow = -1;
        int zeroCol = -1;

        for (int i = 0; i < inputNode.getMatrix().length;i++) {
            for (int j = 0; j < inputNode.getMatrix()[i].length; j++) {
                if (inputNode.getMatrix()[i][j].equals("0")) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }

        Node.addVisitedNode(inputNode);

        ArrayList<Node> nodeList = new ArrayList<>();

        //LEFT
        if (zeroCol - 1 >= 0) {
            String[][] swapped = deepCopy(inputNode.getMatrix());
            String tmp = swapped[zeroRow][zeroCol - 1];

            swapped[zeroRow][zeroCol] = tmp;
            swapped[zeroRow][zeroCol - 1] = "0";

            if(!visitedContains(swapped)) {
                nodeList.add(new Node(swapped, inputNode));
            }
        }

        //RIGHT
        if (zeroCol + 1 < inputNode.getMatrix()[0].length) {
            String[][] swapped = deepCopy(inputNode.getMatrix());
            String tmp = swapped[zeroRow][zeroCol + 1];

            swapped[zeroRow][zeroCol] = tmp;
            swapped[zeroRow][zeroCol + 1] = "0";

            if(!visitedContains(swapped)) {
                nodeList.add(new Node(swapped, inputNode));
            }
        }

        //UP
        if (zeroRow - 1 >= 0) {
            String[][] swapped = deepCopy(inputNode.getMatrix());
            String tmp = swapped[zeroRow - 1][zeroCol];

            swapped[zeroRow][zeroCol] = tmp;
            swapped[zeroRow - 1][zeroCol] = "0";

            if(!visitedContains(swapped)) {
                nodeList.add(new Node(swapped, inputNode));
            }
        }

        //DOWN
        if (zeroRow + 1 < inputNode.getMatrix().length) {
            String[][] swapped = deepCopy(inputNode.getMatrix());
            String tmp = swapped[zeroRow + 1][zeroCol];

            swapped[zeroRow][zeroCol] = tmp;
            swapped[zeroRow + 1][zeroCol] = "0";

            if(!visitedContains(swapped)) {
                nodeList.add(new Node(swapped, inputNode));
            }
        }

        Collections.sort(nodeList);
            
        for (Node i : nodeList) {
            Node result = solvePuzzle(i);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    public static boolean isSolved(Node inputNode) {
        String[][] correct = {{"1","2","3"},
                            {"4","5","6"},
                            {"7","8","0"}};

        return Arrays.deepEquals(inputNode.getMatrix(), correct);
    }

    private static String[][] deepCopy(String[][] original) {
        String[][] copy = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    private static boolean visitedContains(String[][] inputMatrix) {
        for (Node i : Node.getVisited()) {
            if (Arrays.deepEquals(inputMatrix, i.getMatrix())) {
                return true;
            }
        }

        return false;
    }

    private static int getParentCount(Node node) {
        int parentCount = 0;
        while (node.getParent() != null) {
            parentCount++;
            node = node.getParent();
        }

        return parentCount;
    }

    // public static Node solvePuzzleBruteForce(Node inputNode) {
    //     // System.out.println("SOLVING:");
    //     // printMatrix(inputNode.getMatrix());

    //     if (isSolved(inputNode)) {
    //         return inputNode;
    //     }

    //     int zeroRow = -1;
    //     int zeroCol = -1;

    //     for (int i = 0; i < inputNode.getMatrix().length;i++) {
    //         for (int j = 0; j < inputNode.getMatrix()[i].length; j++) {
    //             if (inputNode.getMatrix()[i][j].equals("0")) {
    //                 zeroRow = i;
    //                 zeroCol = j;
    //                 break;
    //             }
    //         }
    //     }

    //     Node.addVisitedNode(inputNode);

    //     ArrayList<Node> nodeList = new ArrayList<>();


    //     //RIGHT
    //     if (zeroCol + 1 < inputNode.getMatrix()[0].length) {
    //         String[][] swapped = deepCopy(inputNode.getMatrix());
    //         String tmp = swapped[zeroRow][zeroCol + 1];

    //         swapped[zeroRow][zeroCol] = tmp;
    //         swapped[zeroRow][zeroCol + 1] = "0";

    //         if(!visitedContains(swapped)) {
    //             nodeList.add(new Node(swapped, inputNode));
    //         }
    //     }

    //     //LEFT
    //     if (zeroCol - 1 >= 0) {
    //         String[][] swapped = deepCopy(inputNode.getMatrix());
    //         String tmp = swapped[zeroRow][zeroCol - 1];

    //         swapped[zeroRow][zeroCol] = tmp;
    //         swapped[zeroRow][zeroCol - 1] = "0";

    //         if(!visitedContains(swapped)) {
    //             nodeList.add(new Node(swapped, inputNode));
    //         }
    //     }

    //     //UP
    //     if (zeroRow - 1 >= 0) {
    //         String[][] swapped = deepCopy(inputNode.getMatrix());
    //         String tmp = swapped[zeroRow - 1][zeroCol];

    //         swapped[zeroRow][zeroCol] = tmp;
    //         swapped[zeroRow - 1][zeroCol] = "0";

    //         if(!visitedContains(swapped)) {
    //             nodeList.add(new Node(swapped, inputNode));
    //         }
    //     }

    //     //DOWN
    //     if (zeroRow + 1 < inputNode.getMatrix().length) {
    //         String[][] swapped = deepCopy(inputNode.getMatrix());
    //         String tmp = swapped[zeroRow + 1][zeroCol];

    //         swapped[zeroRow][zeroCol] = tmp;
    //         swapped[zeroRow + 1][zeroCol] = "0";

    //         if(!visitedContains(swapped)) {
    //             nodeList.add(new Node(swapped, inputNode));
    //         }
    //     }

    //     for (Node i : nodeList) {
    //         Node result = solvePuzzle(i);
    //         if (result != null) {
    //             return result;
    //         }
    //     }

    //     return null;
    // }
}
