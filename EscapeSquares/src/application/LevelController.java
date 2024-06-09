package application;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class LevelController {

    @FXML
    TextField nameTextField;
    
    private Stage stage;
    private Scene scene;

    @FXML
    private Button resetButton;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane scenePane;
    
    @FXML
    private GridPane gridPaneDesign;
    @FXML
    private ImageView player; 
    @FXML
    private ImageView enemy; 
    @FXML
    private Button startButton;
    @FXML
    private Label timerLabel;
    
    private Timeline enemyTimeline;
    
    @FXML
    private Label moveLabel;
    
    private int playerRow = 0;
    private int playerColumn = 0;

    private int moveCount = 0;
    
    private Timeline timeline;
    private Integer timeSeconds = 0;
    
    @FXML
    public void initialize() {
        moveLabel.setText("Ronda: 0");
        timerLabel.setText("00:00:00");
        gridPaneDesign.add(player, playerColumn, playerRow);
        GridPane.setHalignment(player, HPos.CENTER);
        GridPane.setValignment(player, VPos.CENTER);

        gridPaneDesign.add(enemy, gridPaneDesign.getColumnConstraints().size() - 1, gridPaneDesign.getRowConstraints().size() - 1);
        GridPane.setHalignment(enemy, HPos.CENTER);
        GridPane.setValignment(enemy, VPos.CENTER);

        gridPaneDesign.add(startButton, gridPaneDesign.getColumnCount() / 2, gridPaneDesign.getRowCount() / 2);
        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setValignment(startButton, VPos.CENTER);
        resetButton.setOnAction(event -> resetGame());
        startButton.setOnAction(event -> {
            startButton.setVisible(false);
            startTimer();
            scenePane.getScene().setOnKeyPressed(this::handleKey);

            enemyTimeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
                int playerRow = GridPane.getRowIndex(player);
                int playerCol = GridPane.getColumnIndex(player);
                int enemyRow = GridPane.getRowIndex(enemy);
                int enemyCol = GridPane.getColumnIndex(enemy);

                if (playerRow < enemyRow) {
                    GridPane.setRowIndex(enemy, enemyRow - 1);
                } else if (playerRow > enemyRow) {
                    GridPane.setRowIndex(enemy, enemyRow + 1);
                } else if (playerCol < enemyCol) {
                    GridPane.setColumnIndex(enemy, enemyCol - 1);
                } else if (playerCol > enemyCol) {
                    GridPane.setColumnIndex(enemy, enemyCol + 1);
                }

                if (GridPane.getRowIndex(enemy).equals(GridPane.getRowIndex(player)) && GridPane.getColumnIndex(enemy).equals(GridPane.getColumnIndex(player))) {
                    resetGame();
                }
            }));
            enemyTimeline.setCycleCount(Timeline.INDEFINITE);
            enemyTimeline.play();
        });

        Platform.runLater(() -> {
            scenePane.getScene().setOnKeyPressed(this::handleKey);
        });
    }

    
    
    
    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = 0;
        timerLabel.setText(formatTime(timeSeconds));
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(10), event -> {
                    timeSeconds += 10; 
                    timerLabel.setText(formatTime(timeSeconds));
                })
        );
        timeline.playFromStart();
    }

    private String formatTime(int millis) {
        int milliseconds = millis % 1000 / 10;
        int seconds = (millis / 1000) % 60;
        int minutes = (millis / (1000 * 60)) % 60;
        return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
    }
    
    private void resetGame() {
        gridPaneDesign.getChildren().remove(player);
        playerRow = 0;
        playerColumn = 0;
        moveCount = 0;
        moveLabel.setText("Ronda: 0");
        gridPaneDesign.add(player, playerColumn, playerRow);
        GridPane.setHalignment(player, HPos.CENTER);
        GridPane.setValignment(player, VPos.CENTER);

        gridPaneDesign.getChildren().remove(enemy);
        int enemyRow = gridPaneDesign.getRowConstraints().size() - 1;
        int enemyColumn = gridPaneDesign.getColumnConstraints().size() - 1;
        if (enemyTimeline != null) {
            enemyTimeline.stop();
        }
        gridPaneDesign.add(enemy, enemyColumn, enemyRow);
        GridPane.setHalignment(enemy, HPos.CENTER);
        GridPane.setValignment(enemy, VPos.CENTER);

        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = 0;
        timerLabel.setText(timeSeconds.toString());
        timerLabel.setText("00:00:00");
        startButton.setVisible(true);

        scenePane.getScene().setOnKeyPressed(null);
    }
    
    public void handleKey(javafx.scene.input.KeyEvent event) {
        switch (event.getCode()) {
            case W:
                if (playerRow > 0) {
                    gridPaneDesign.getChildren().remove(player);
                    gridPaneDesign.add(player, playerColumn, --playerRow);
                    GridPane.setHalignment(player, HPos.CENTER);
                    GridPane.setValignment(player, VPos.CENTER);
                    if (playerRow == gridPaneDesign.getRowConstraints().size() - 1 && playerColumn == gridPaneDesign.getColumnConstraints().size() - 1) {
                        showVictoryMessage();
                    }
                    moveCount++;
                }
                break;
            case S: 
                if (playerRow < gridPaneDesign.getRowConstraints().size() - 1) {
                    gridPaneDesign.getChildren().remove(player);
                    gridPaneDesign.add(player, playerColumn, ++playerRow);
                    GridPane.setHalignment(player, HPos.CENTER);
                    GridPane.setValignment(player, VPos.CENTER);
                    if (playerRow == gridPaneDesign.getRowConstraints().size() - 1 && playerColumn == gridPaneDesign.getColumnConstraints().size() - 1) {
                        showVictoryMessage();
                    }
                    moveCount++;
                }
                break;
            case A:
                if (playerColumn > 0) {
                    gridPaneDesign.getChildren().remove(player);
                    gridPaneDesign.add(player, --playerColumn, playerRow);
                    GridPane.setHalignment(player, HPos.CENTER);
                    GridPane.setValignment(player, VPos.CENTER);
                    if (playerRow == gridPaneDesign.getRowConstraints().size() - 1 && playerColumn == gridPaneDesign.getColumnConstraints().size() - 1) {
                        showVictoryMessage();
                    }
                    moveCount++;
                }
                break;
            case D:
                if (playerColumn < gridPaneDesign.getColumnConstraints().size() - 1) {
                    gridPaneDesign.getChildren().remove(player);
                    gridPaneDesign.add(player, ++playerColumn, playerRow);
                    GridPane.setHalignment(player, HPos.CENTER);
                    GridPane.setValignment(player, VPos.CENTER);
                    if (playerRow == gridPaneDesign.getRowConstraints().size() - 1 && playerColumn == gridPaneDesign.getColumnConstraints().size() - 1) {
                        showVictoryMessage();
                    }
                    moveCount++;
                }
                break;
        }
        if (moveCount % 3 == 0) {
            moveLabel.setText("Ronda: " + (moveCount / 3));
        }
    }

    private void showVictoryMessage() {
    	if (enemyTimeline != null) {
            enemyTimeline.pause();
        }
    	  if (timeline != null) {
              timeline.stop();
          }
    	
       
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Image icon = new Image("Icon.png");
        Image icono = new Image("gg.png");
        
        ImageView iconLeave = new ImageView(icono);
		iconLeave.setFitHeight(80); 
		iconLeave.setFitWidth(140);
		
		alert.setGraphic(iconLeave);
        alert.setTitle("Victoria");
        alert.setHeaderText(null);
        alert.setContentText("¡Has ganado!");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(icon);
		
		alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        ButtonType buttonTypeReset = new ButtonType("Reset");
        alert.getButtonTypes().setAll(buttonTypeReset);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeReset){
            resetGame();
        }
    }
    
    public void LeaveToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
