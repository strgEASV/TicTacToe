package dk.easv.tictactoe.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingPageController {

    @FXML
    private StackPane root;



    @FXML
    private Button btnMultiplayer;

    @FXML
    private Button btnRandomAI;

    @FXML
    private Button btnSmartAI;

    private static int selectedGameMode = 0; // 0 = multiplayer, 1 = random AI, 2 = smart AI

    @FXML
    private void initialize() {
        // Inicializace už není potřeba - pozadí je nastaveno v FXML
    }

    @FXML
    private void handleMultiplayer(ActionEvent event) {
        selectedGameMode = 0;
        loadGameView(event);
    }

    @FXML
    private void handleRandomAI(ActionEvent event) {
        selectedGameMode = 1;
        loadGameView(event);
    }

    @FXML
    private void handleSmartAI(ActionEvent event) {
        selectedGameMode = 2;
        loadGameView(event);
    }

    private void loadGameView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/TicTacView.fxml"));
            Parent gameView = loader.load();

            // Předání herního režimu
            TicTacViewController controller = loader.getController();
            controller.initializeGameMode(selectedGameMode);

            Scene gameScene = new Scene(gameView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(gameScene);
            stage.setResizable(false);
            stage.setTitle("Tic Tac Toe - " + getGameModeName());
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getGameModeName() {
        return switch (selectedGameMode) {
            case 1 -> "Random AI";
            case 2 -> "Smart AI";
            default -> "Multiplayer";
        };
    }

    @FXML
    private void handleButtonHover(MouseEvent event) {
        Button button = (Button) event.getSource();
        
        if (button == btnMultiplayer) {
            button.setStyle("-fx-background-color: rgba(0, 255, 136, 0.2); " +
                          "-fx-border-color: #00ff88; " +
                          "-fx-border-width: 3px; " +
                          "-fx-border-radius: 15; " +
                          "-fx-background-radius: 15; " +
                          "-fx-text-fill: #00ff88; " +
                          "-fx-font-size: 24px; " +
                          "-fx-font-weight: bold; " +
                          "-fx-cursor: hand;");
        } else if (button == btnRandomAI) {
            button.setStyle("-fx-background-color: rgba(0, 212, 255, 0.2); " +
                          "-fx-border-color: #00d4ff; " +
                          "-fx-border-width: 3px; " +
                          "-fx-border-radius: 15; " +
                          "-fx-background-radius: 15; " +
                          "-fx-text-fill: #00d4ff; " +
                          "-fx-font-size: 24px; " +
                          "-fx-font-weight: bold; " +
                          "-fx-cursor: hand;");
        } else if (button == btnSmartAI) {
            button.setStyle("-fx-background-color: rgba(255, 0, 170, 0.2); " +
                          "-fx-border-color: #ff00aa; " +
                          "-fx-border-width: 3px; " +
                          "-fx-border-radius: 15; " +
                          "-fx-background-radius: 15; " +
                          "-fx-text-fill: #ff00aa; " +
                          "-fx-font-size: 24px; " +
                          "-fx-font-weight: bold; " +
                          "-fx-cursor: hand;");
        }
    }

    @FXML
    private void handleButtonExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        
        if (button == btnMultiplayer) {
            button.setStyle("-fx-background-color: transparent; " +
                          "-fx-border-color: #00ff88; " +
                          "-fx-border-width: 3px; " +
                          "-fx-border-radius: 15; " +
                          "-fx-background-radius: 15; " +
                          "-fx-text-fill: #00ff88; " +
                          "-fx-font-size: 24px; " +
                          "-fx-font-weight: bold; " +
                          "-fx-cursor: hand;");
        } else if (button == btnRandomAI) {
            button.setStyle("-fx-background-color: transparent; " +
                          "-fx-border-color: #00d4ff; " +
                          "-fx-border-width: 3px; " +
                          "-fx-border-radius: 15; " +
                          "-fx-background-radius: 15; " +
                          "-fx-text-fill: #00d4ff; " +
                          "-fx-font-size: 24px; " +
                          "-fx-font-weight: bold; " +
                          "-fx-cursor: hand;");
        } else if (button == btnSmartAI) {
            button.setStyle("-fx-background-color: transparent; " +
                          "-fx-border-color: #ff00aa; " +
                          "-fx-border-width: 3px; " +
                          "-fx-border-radius: 15; " +
                          "-fx-background-radius: 15; " +
                          "-fx-text-fill: #ff00aa; " +
                          "-fx-font-size: 24px; " +
                          "-fx-font-weight: bold; " +
                          "-fx-cursor: hand;");
        }
    }
}