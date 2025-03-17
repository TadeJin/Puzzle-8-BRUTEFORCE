package com.javafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class Controller {

    @FXML
    private TextField matrixInput;

    @FXML
    private Label preview1;

    @FXML
    private Label preview2;

    @FXML
    private Label preview3;

    @FXML
    private Label preview4;

    @FXML
    private Label preview5;

    @FXML
    private Label preview6;

    @FXML
    private Label preview7;

    @FXML
    private Label preview8;

    @FXML
    private Label preview9;

    @FXML
    private Label unsolvableWarning;

    @FXML
    private Button solveButton;

    @FXML
    private Label stepCount;

    @FXML
    private Label final1;

    @FXML
    private Label final2;

    @FXML
    private Label final3;

    @FXML
    private Label final4;

    @FXML
    private Label final5;

    @FXML
    private Label final6;

    @FXML
    private Label final7;

    @FXML
    private Label final8;
    
    @FXML
    private Label final9;

    @FXML
    private ListView stepList;

    private Node result;
    private ArrayList<Node> parentNodes;
    private Timeline timeline = new Timeline();

    @FXML
    private Button stopAnim;

    @FXML
    private Button playAnim;
    


    public void fillMatrixPreview() {
        String input = matrixInput.getText();

        if (0 < input.length()) {
            preview1.setText(Character.toString(input.charAt(0)));
        } else {
            preview1.setText("–");
        }
        
        if (1 < input.length()) {
            preview2.setText(Character.toString(input.charAt(1)));
        } else {
            preview2.setText("–");
        }
        
        if (2 < input.length()) {
            preview3.setText(Character.toString(input.charAt(2)));
        } else {
            preview3.setText("–");
        }
        
        if (3 < input.length()) {
            preview4.setText(Character.toString(input.charAt(3)));
        } else {
            preview4.setText("–");
        }
        
        if (4 < input.length()) {
            preview5.setText(Character.toString(input.charAt(4)));
        } else {
            preview5.setText("–");
        }
        
        if (5 < input.length()) {
            preview6.setText(Character.toString(input.charAt(5)));
        } else {
            preview6.setText("–");
        }
        
        if (6 < input.length()) {
            preview7.setText(Character.toString(input.charAt(6)));
        } else {
            preview7.setText("–");
        }
        
        if (7 < input.length()) {
            preview8.setText(Character.toString(input.charAt(7)));
        } else {
            preview8.setText("–");
        }
        
        if (8 < input.length()) {
            preview9.setText(Character.toString(input.charAt(8)));
        } else {
            preview9.setText("–");
        }

        if (input.length() == 9) {
            if(isSolvable()) {
                unsolvableWarning.setVisible(false);
                solveButton.setDisable(false);
            } else {
                unsolvableWarning.setVisible(true);
                solveButton.setDisable(true);
            }
        } else {
            unsolvableWarning.setVisible(true);
            solveButton.setDisable(true);
        }
    }

    public void fillMatrixFinal(String[][] finalMatrix) {
        StringBuilder sb = new StringBuilder();
        String input = "";
        
        if (finalMatrix != null) {
            for (int i = 0; i < finalMatrix.length; i++) {
                for (int j = 0; j < finalMatrix[i].length; j++) {
                    sb.append(finalMatrix[i][j]);
                }
            }

            input = sb.toString();
        }

        if (0 < input.length()) {
            final1.setText(Character.toString(input.charAt(0)));
        } else {
            final1.setText("–");
        }
        
        if (1 < input.length()) {
            final2.setText(Character.toString(input.charAt(1)));
        } else {
            final2.setText("–");
        }
        
        if (2 < input.length()) {
            final3.setText(Character.toString(input.charAt(2)));
        } else {
            final3.setText("–");
        }
        
        if (3 < input.length()) {
            final4.setText(Character.toString(input.charAt(3)));
        } else {
            final4.setText("–");
        }
        
        if (4 < input.length()) {
            final5.setText(Character.toString(input.charAt(4)));
        } else {
            final5.setText("–");
        }
        
        if (5 < input.length()) {
            final6.setText(Character.toString(input.charAt(5)));
        } else {
            final6.setText("–");
        }
        
        if (6 < input.length()) {
            final7.setText(Character.toString(input.charAt(6)));
        } else {
            final7.setText("–");
        }
        
        if (7 < input.length()) {
            final8.setText(Character.toString(input.charAt(7)));
        } else {
            final8.setText("–");
        }
        
        if (8 < input.length()) {
            final9.setText(Character.toString(input.charAt(8)));
        } else {
            final9.setText("–");
        }
    }

    public void fillMatrixSelect() {
        int currentlySelected = Integer.parseInt(stepList.getSelectionModel().getSelectedItem().toString());

        fillMatrixFinal(this.parentNodes.get(currentlySelected).getMatrix());
    }

    public void fillMatrixSelect(int index) {

        fillMatrixFinal(this.parentNodes.get(index).getMatrix());
    }


    public boolean isSolvable() {
        int inversions = 0;

        String puzzle = matrixInput.getText();

        for (int i = 0; i < puzzle.length(); i++) {
            for (int j = i + 1; j < puzzle.length(); j++) {
                if (puzzle.charAt(i) != '0' && puzzle.charAt(j) != '0' && puzzle.charAt(i) > puzzle.charAt(j)) {
                    inversions++;
                }
            }
        }

        return inversions % 2 == 0;
    }

    @SuppressWarnings("unchecked")
    public void solve() {
        resetUI();
        String input = matrixInput.getText();

        String[][] inputMatrix = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inputMatrix[i][j] = Character.toString(input.charAt(i * 3 + j));
            }
        }
        
        Node root = new Node(inputMatrix, null);
        this.result = solvePuzzle(root);

        int parentCount = getParentCount(this.result);

        stepCount.setText("Total step count: " + parentCount);
        fillMatrixFinal(this.result.getMatrix());

        this.parentNodes = new ArrayList<>();

        this.parentNodes.add(result);

        Node node = result;

        while (node.getParent() != null) {
            this.parentNodes.add(node.getParent());
            node = node.getParent();
        }

        Collections.reverse(this.parentNodes);

        String[] array = new String[parentCount+1];
        for (int i = 0; i <= parentCount;i++) {
            array[i] = Integer.toString(i);
        }

        stepList.getItems().addAll(Arrays.asList(array));
        playAnim.setDisable(false);
    }


    public Node solvePuzzle(Node inputNode) {

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

    public static String[][] deepCopy(String[][] original) {
        String[][] copy = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    public static boolean visitedContains(String[][] inputMatrix) {
        for (Node i : Node.getVisited()) {
            if (Arrays.deepEquals(inputMatrix, i.getMatrix())) {
                return true;
            }
        }

        return false;
    }

    public static int getParentCount(Node node) {
        int parentCount = 0;
        while (node.getParent() != null) {
            parentCount++;
            node = node.getParent();
        }

        return parentCount;
    }

    public void playSolveAnim() {
        timeline = new Timeline();
        for (int i = 0; i < getParentCount(this.result) + 1;i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * 500), e -> {
                fillMatrixSelect(index);
                stepList.getSelectionModel().select(index);
            });
            this.timeline.getKeyFrames().add(keyFrame);
        }

        this.timeline.setCycleCount(1);
        this.timeline.play();
        stopAnim.setDisable(false);
        playAnim.setDisable(true);
    }

    public void stopAnimation() {
        timeline.stop();
        stopAnim.setDisable(true);
        playAnim.setDisable(false);
    }

    public void resetUI() {
        fillMatrixPreview();
        fillMatrixFinal(null);
        stepCount.setText("Total step count: 0");
        solveButton.setDisable(true);
        this.result = null;
        this.parentNodes = new ArrayList<>();
        stepList.getItems().clear();
        Node.setVisited(new ArrayList<>());
        this.timeline.stop();
        stopAnim.setDisable(true);
        playAnim.setDisable(true);
    }
}
