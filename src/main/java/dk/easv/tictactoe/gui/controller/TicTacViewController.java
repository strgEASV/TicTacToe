package dk.easv.tictactoe.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

import dk.easv.tictactoe.bll.GameBoard;
import dk.easv.tictactoe.bll.IGameBoard;

public class TicTacViewController implements Initializable {
    @FXML
    private Label lblPlayer;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnRandomAIGame;

    @FXML
    private GridPane gridPane;

    @FXML
    private StackPane rootPane; // <- musí být ve FXML jako parent, obsahuje gridPane i čáry

    private static final String TXT_PLAYER = "Player: ";
    private IGameBoard game;
    private Line winLine;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;

            if (game.play(c, r)) {
                int player = game.getNextPlayer();
                Button btn = (Button) event.getSource();
                String xOrO = player == 0 ? "X" : "O";
                btn.setText(xOrO);
                int typeOfGame = game.getTypeOfGame();
                if (game.getTypeOfGame() == 1) { // Random AI game
                    if (!game.isGameOver()) {
                        boolean moved = false;
                        int attempts = 0;
                        while (!moved && attempts < 50) { //ensure it doesnt try playing if its over (can cause crash)
                            int randomRow = (int) (Math.random() * 3);
                            int randomCol = (int) (Math.random() * 3);
                            moved = game.play(randomCol, randomRow);
                            attempts++;
                            if (moved) {
                                game.AIPlay(randomCol, randomRow);
                                btn = (Button) getNodeFromGrid(randomCol, randomRow);
                                xOrO = "O";
                                btn.setText(xOrO);
                            }
                        }
                    }
                }


                if (game.isGameOver()) {
                    int winner = game.getWinner();
                    if (winner != -1) { // <- make sure there is a real winner
                        displayWinner(winner);
                        drawWinningLine();
                    } else {
                        lblPlayer.setText("U suck lol!");
                    }
                } else {
                    setPlayer();
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleNewGame(ActionEvent event) {
        game.newGame();
        setPlayer();
        clearBoard();
        clearWinningLine();
    }

    @FXML
    private void handleRandomAIMove(ActionEvent event) {
        game.newRandomAIGame();
        setPlayer();
        clearBoard();
        clearWinningLine();
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new GameBoard();
        setPlayer();
    }

    private void setPlayer() {
        lblPlayer.setText(TXT_PLAYER + game.getNextPlayer());
    }

    private void displayWinner(int winner) {
        String message;
        switch (winner) {
            case -1:
                message = "Bro y'all suck lol XD!";
                break;
            default:
                message = "Yo yo yo, " + winner + " won!!! Nerd...";
                break;
        }
        lblPlayer.setText(message);
    }

    private void clearBoard() {
        for (Node n : gridPane.getChildren()) {
            if (n instanceof Button btn) {
                btn.setText("");
            }
        }
    }

    private void drawWinningLine() {
        int[][] combo = ((GameBoard) game).getWinningCombination();
        if (combo == null || combo.length != 3) return; // check for valid combo

        Node startNode = getNodeFromGrid(combo[0][1], combo[0][0]);
        Node endNode   = getNodeFromGrid(combo[2][1], combo[2][0]);
        if (startNode == null || endNode == null) return;

        double startX = startNode.getLayoutX() + startNode.getBoundsInParent().getWidth() / 2;
        double startY = startNode.getLayoutY() + startNode.getBoundsInParent().getHeight() / 2;
        double endX   = endNode.getLayoutX() + endNode.getBoundsInParent().getWidth() / 2;
        double endY   = endNode.getLayoutY() + endNode.getBoundsInParent().getHeight() / 2;

        winLine = new Line(startX, startY, endX, endY);
        winLine.setStyle("-fx-stroke: red; -fx-stroke-width: 4px;");
        rootPane.getChildren().add(winLine);
    }


    private void clearWinningLine() {
        if (winLine != null) {
            rootPane.getChildren().remove(winLine);
            winLine = null;
        }
    }

    private Node getNodeFromGrid(int col, int row) {
        for (Node n : gridPane.getChildren()) {
            Integer c = GridPane.getColumnIndex(n);
            Integer r = GridPane.getRowIndex(n);
            if ((c == null ? 0 : c) == col && (r == null ? 0 : r) == row) {
                return n;
            }
        }
        return null;
    }
}