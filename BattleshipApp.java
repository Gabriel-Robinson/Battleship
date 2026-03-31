import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;


public class BattleshipApp extends Application {

    private Game game = new Game();
    private Button[][] playerButtons = new Button[10][10];
    private Random rand = new Random();
    private TextArea playerLog = new TextArea();
    private TextArea oppLog = new TextArea();
    private int turnNum = 0;
    private Button[][] oppButtons = new Button[10][10];
    



    private void disableButtons(Button[][] butn) {
        for(int row = 0; row < 10; row++) {
            for(int col = 0; col < 10; col++) {
                butn[row][col].setDisable(true);
            }
        }
    }

    private void addLog(String message, TextArea log) {
        log.setText(log.getText() + "\n" + message);
        log.positionCaret(log.getText().length());
    }

    private void aiMove() {
        int row = rand.nextInt(10);
        int col = rand.nextInt(10);

        Coordinate coord = new Coordinate(row, col);

        String result = game.AiAttack(coord);

        if(result.contains("already")) {
            aiMove();
            return;
        }
        turnNum++;
        if(result.contains("Hit")) {
            String text = playerButtons[row][col].getText();
            if(text.contains("S")) {
                addLog("Turn " + turnNum + ":" + " The Opponent hit you", oppLog);
                System.out.println("The Opponent hit you");
            }
            playerButtons[row][col].setText("X");
            if(game.isGameOver()) {
                addLog("Game Over! AI Wins", oppLog);
                disableButtons(oppButtons);
                return ;
            }
        } else {
            addLog("Turn " + turnNum + ":" + " Miss", oppLog);
            playerButtons[row][col].setText("O");
        }
    }

    @Override
    public void start(Stage stage) {
        GridPane oppGrid = new GridPane();

        for(int row = 0; row < 10; row++) {
            for(int column = 0; column < 10; column++) {
                Button cell = new Button();
                cell.setPrefSize(40, 40);

                int r = row;
                int c = column;

                cell.setOnAction(e -> {
                    Coordinate coord = new Coordinate(r, c);
                    String result = game.playerAttack(coord);
                    if(result.contains("Hit")) {
                        cell.setText("X");
                    } else {
                        cell.setText("O");
                    }

                    cell.setDisable(true);
                    turnNum++;
                    addLog("turn " + turnNum + ": " + result, playerLog);

                    if(game.isGameOver()) {
                        addLog("Game Over! You Win!", playerLog);
                        disableButtons(oppButtons);
                        return ;
                    }
                    aiMove();
                    System.out.println(result);

                });
                oppButtons[r][c] = cell;
                oppGrid.add(cell, column, row);
            }
        }


        GridPane playerGrid = new GridPane();
        
        for(int row = 0; row < 10; row++) {
            for(int column = 0; column < 10; column++) {
                Button cell = new Button();
                cell.setPrefSize(40, 40);

                int r = row;
                int c = column;

                char currCell = game.getCurrentPlayer().getBoard().getCell(r, c);

                if(currCell == 'S') {
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