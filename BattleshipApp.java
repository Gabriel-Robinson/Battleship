import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class BattleshipApp extends Application {

    private Game game = new Game();
    private Button[][] playerButtons = new Button[10][10];
    
    private TextArea playerLog = new TextArea();
    private TextArea oppLog = new TextArea();
    private int turnNum = 0;
    private Button[][] oppButtons = new Button[10][10];

    private GridPane createOpponentGrid() {
        GridPane grid = new GridPane();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button cell = new Button();
                cell.setPrefSize(40, 40);

                int r = row;
                int c = col;

                cell.setOnAction(e -> {
                    handlePlayerMove(r, c, cell);
                });
                oppButtons[r][c] = cell;
                grid.add(cell, col, row);
            }
        }

        return grid;
    }

    private void handlePlayerMove(int r, int c, Button cell) {
        if (game.isGameOver()) {
            return;
        }

        Coordinate coord = new Coordinate(r, c);
        TurnResult result = game.playTurn(coord);

        // Player Result
        if (result.playerResult.contains("Hit")) {
            cell.setText("X");
        } else {
            cell.setText("O");
        }

        turnNum++;
        cell.setDisable(true);

        addLog("Turn " + turnNum + ": " + result.playerResult, playerLog);

        // AI RESULT
        if (!result.aiResult.equals("")) {
            int aiRow = result.aiRow;
            int aiCol = result.aiCol;

            if (result.aiResult.contains("Hit")) {
                playerButtons[aiRow][aiCol].setText("X");
                addLog("Turn " + turnNum + ": Opponent hit you", oppLog);
            } else {
                playerButtons[aiRow][aiCol].setText("O");
                addLog("Turn " + turnNum + ": Opponent missed", oppLog);
            }
        }

        if (game.isGameOver()) {
            addLog("Game Over! You Win!", playerLog);
            disableButtons(oppButtons);
            disableButtons(playerButtons);
            return;
        }

    }

    private void disableButtons(Button[][] butn) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                butn[row][col].setDisable(true);
            }
        }
    }

    private void addLog(String message, TextArea log) {
        log.setText(log.getText() + "\n" + message);
        log.positionCaret(log.getText().length());
    }

    @Override
    public void start(Stage stage) {
        GridPane oppGrid = createOpponentGrid();

        GridPane playerGrid = new GridPane();

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                Button cell = new Button();
                cell.setPrefSize(40, 40);

                int r = row;
                int c = column;

                char currCell = game.getCurrentPlayer().getBoard().getCell(r, c);

                if (currCell == 'S') {
                    cell.setText("S");
                } else {
                    cell.setText("-");
                }

                playerButtons[r][c] = cell;
                playerGrid.add(cell, column, row);

                cell.setDisable(true);
            }
        }

        VBox gameLog = new VBox();
        gameLog.getChildren().addAll(new Label("Player Log: "), playerLog, new Label("Opponenet Log: "), oppLog);

        VBox playerSide = new VBox();
        playerSide.getChildren().addAll(new Label("Your Board: "), playerGrid);

        VBox oppSide = new VBox();
        oppSide.getChildren().addAll(new Label("Opponent Board: "), oppGrid);

        HBox root = new HBox(50);
        root.getChildren().addAll(playerSide, oppSide, gameLog);

        Scene scene = new Scene(root, 1000, 500);

        playerLog.setEditable(false);
        oppLog.setEditable(false);

        stage.setTitle("BattleShip");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}