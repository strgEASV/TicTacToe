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
    private ImageView backgroundImage;

    @FXML
    private Button btnMultiplayer;

    @FXML
    private Button btnRandomAI;

    @FXML
    private Button btnSmartAI;

    private static int selectedGameMode = 0; // 0 = multiplayer, 1 = random AI, 2 = smart AI

    @FXML
    private void initialize() {
        // Načti obrázek a nastav ho jako pozadí
        Image image = new Image(getClass().getResourceAsStream("/images/bg.png"));
        backgroundImage.setImage(image);

        // Zajistí, že obrázek přesně pokryje 400x500 px
        backgroundImage.setFitHeight(500);
        backgroundImage.setFitWidth(300);
        backgroundImage.setPreserveRatio(false);
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
        String currentStyle = button.getStyle();

        if (button == btnMultiplayer) {
            button.setStyle(currentStyle + " -fx-background-color: #27ae60;");
        } else if (button == btnRandomAI) {
            button.setStyle(currentStyle + " -fx-background-color: #2980b9;");
        } else if (button == btnSmartAI) {
            button.setStyle(currentStyle + " -fx-background-color: #c0392b;");
        }
    }

    @FXML
    private void handleButtonExit(MouseEvent event) {
        Button button = (Button) event.getSource();
        String currentStyle = button.getStyle();

        if (button == btnMultiplayer) {
            button.setStyle(currentStyle.replace("-fx-background-color: #27ae60;", "-fx-background-color: #2ecc71;"));
        } else if (button == btnRandomAI) {
            button.setStyle(currentStyle.replace("-fx-background-color: #2980b9;", "-fx-background-color: #3498db;"));
        } else if (button == btnSmartAI) {
            button.setStyle(currentStyle.replace("-fx-background-color: #c0392b;", "-fx-background-color: #e74c3c;"));
        }
    }
}