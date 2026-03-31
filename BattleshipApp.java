import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class BattleshipApp extends Application {

    private Game game = new Game();

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
                    String result = game.getOpponent().getBoard().attack(coord);
                    if(result.contains("Hit")) {
                        cell.setText("X");
                    } else {
                        cell.setText("O");
                    }

                    cell.setDisable(true);
                    System.out.println(result);

                });
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
                playerGrid.add(cell, column, row);

                Coordinate coord = new Coordinate(r, c);
                cell.setDisable(true);
            }
        }
        VBox playerSide = new VBox();
        playerSide.getChildren().addAll(new Label("Your Board: "), playerGrid);

        VBox oppSide = new VBox();
        oppSide.getChildren().addAll(new Label("Opponent Board: "), oppGrid);

        HBox root = new HBox(50);
        root.getChildren().addAll(playerSide, oppSide);

        Scene scene = new Scene(root, 1000, 500);

        stage.setTitle("BattleShip");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}