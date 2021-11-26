//////////////////////////GUI Buttons///////////////////////////////////////////////
package com.UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Main extends Application {
    private int rows = 22, columns = 83;
    private Grid_Button[][] grid_buttons = new Grid_Button[rows][columns];
    private GridPane gameGrid;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public GridPane GetButtons() {
        // Button Declaration
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        Button next = new Button("Next");
        Button restart = new Button("Restart");
        Button lexicon = new Button("Lexicon");
        Button manage = new Button("Manage");
        
        Slider slider1 = new Slider(1, 2, 0.5);
        
        // enable the marks
        slider1.setShowTickMarks(true);
        
        // enable the Labels
        slider1.setShowTickLabels(true);
        
        // set Major tick unit
        slider1.setMajorTickUnit(0.25f);
        
        // sets the value of the property
        // blockIncrement
        slider1.setBlockIncrement(0.1f);
        
        Slider slider2 = new Slider(1, 2, 0.5);
        
        // enable the marks
        slider2.setShowTickMarks(true);
        
        // enable the Labels
        slider2.setShowTickLabels(true);
        
        // set Major tick unit
        slider2.setMajorTickUnit(0.25f);
        
        // sets the value of the property
        // blockIncrement
        slider2.setBlockIncrement(0.1f);
        
        // Button Set Width
        start.setMinWidth(100);
        stop.setMinWidth(100);
        next.setMinWidth(100);
        restart.setMinWidth(100);
        lexicon.setMinWidth(100);
        manage.setMinWidth(100);
        
        start.setStyle("-fx-background-color: #25a379");
        stop.setStyle("-fx-background-color: #25a379");
        next.setStyle("-fx-background-color: #25a379");
        restart.setStyle("-fx-background-color: #25a379");
        lexicon.setStyle("-fx-background-color: #25a379");
        manage.setStyle("-fx-background-color: #25a379");
        
        // GridPane Declaration
        GridPane gridPane = new GridPane();
        
        // Adding Buttons in GridPane
        gridPane.add(start, 0, 0);
        gridPane.add(stop, 1, 0);
        gridPane.add(next, 2, 0);
        gridPane.add(restart, 3, 0);
        gridPane.add(lexicon, 4, 0);
        gridPane.add(manage, 5, 0);
        gridPane.add(slider1, 1, 1, 2, 1);
        gridPane.add(slider2, 3, 1, 2, 1);
        
        // Setting Horizontal Gap Between Buttons
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        
        // Setting Alignment and Padding of GridPane
        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.setPadding(new Insets(20));
        
        return gridPane;
    }
    
    public GridPane GetBoard() {
        GridPane gridPane = new GridPane();
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid_buttons[i][j] = new Grid_Button(i, j);
    
                grid_buttons[i][j].setStyle("-fx-background-color: #ffffff");
                grid_buttons[i][j].setStyle("-fx-border-width: 1.5");
                grid_buttons[i][j].setStyle("-fx-border-color: #000000");
    
                gridPane.add(grid_buttons[i][j], j, i);
            }
        }
    
        return gridPane;
        /*
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        Affine affine = new Affine();
        affine.appendScale(400 / 10, 400 / 10);
        
        Board simulation = new Board(10, 10);
        simulation.setAlive(2, 2);
        
        g.transform(affine);
        g.setFill(Paint.valueOf("#5c6159"));// color of the canvas
        g.fillRect(0, 0, 400, 400);
        g.setFill(Paint.valueOf("#e0d251"));//color of alive cell
        for (int i = 0; i < simulation.getColumns(); i++) {
            for (int j = 0; j < simulation.getRows(); j++) {
                if (simulation.getState(i, j) == true) {
                    g.fillRect(i, i, 1, 1);
                }
            }
        }
        
        // to separate cells by lines
        g.setStroke(Paint.valueOf("#d4cfcf"));
        g.setLineWidth(0.05f);
        for (int x = 0; x <= simulation.getColumns(); x++) {
            g.strokeLine(x, 0, x, 20);
        }
        for (int y = 0; y <= simulation.getRows(); y++) {
            g.strokeLine(0, y, 20, y);
        }
        
        gridPane.add(canvas, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        
        */
    }
    
    public GridPane MergeGridPanes(GridPane BtnGrid, GridPane BoardGrid) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        
        RowConstraints Board = new RowConstraints();
        Board.setPercentHeight(80);
        
        RowConstraints button = new RowConstraints();
        button.setPercentHeight(20);
        
        gridPane.getRowConstraints().addAll(Board, button);
        
        gridPane.add(BoardGrid, 0, 0);
        gridPane.add(BtnGrid, 0, 1);
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #484a49");
        return gridPane;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Game Of Life");
    
        GridPane BtnGrid = GetButtons();
        GridPane BoardGrid = GetBoard();
    
        gameGrid = MergeGridPanes(BtnGrid, BoardGrid);
    
        Scene scene = new Scene(gameGrid, 1, 1);
    
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
