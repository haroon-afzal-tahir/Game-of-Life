//////////////////////////GUI Buttons///////////////////////////////////////////////
package com.UI;

import com.BL.Game;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    
    private int rows = 20, columns = 75;
    private Grid_Button[][] grid_buttons = new Grid_Button[rows][columns];
    
    Button start = new Button("Start");
    Button stop = new Button("Stop");
    Button next = new Button("Next");
    Button reset = new Button("Reset");
    Button lexicon = new Button("Lexicon");
    Button manage = new Button("Manage");
    Slider Zoom = new Slider(0, 10, 0.5);
    Slider Speed = new Slider(0, 10, 0.5);
    
    private Game game = new Game(rows, columns);
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public GridPane GetButtons() {
        Zoom.setShowTickMarks(true);
        Zoom.setShowTickLabels(true);
        Zoom.setMajorTickUnit(0.25f);
        Zoom.setBlockIncrement(0.1f);
        
        Speed.setShowTickMarks(true);
        Speed.setShowTickLabels(true);
        Speed.setMajorTickUnit(0.25f);
        Speed.setBlockIncrement(0.1f);
        
        game.getControl().setZoomFactor(0);
        game.getControl().setSpeedFactor(0);
        
        
        start.setMinWidth(100);
        stop.setMinWidth(100);
        next.setMinWidth(100);
        reset.setMinWidth(100);
        lexicon.setMinWidth(100);
        manage.setMinWidth(100);
        
        start.getStyleClass().removeAll();
        start.getStyleClass().add("SimpleButton");
        
        stop.getStyleClass().removeAll();
        stop.getStyleClass().add("SimpleButton");
        
        next.getStyleClass().removeAll();
        next.getStyleClass().add("SimpleButton");
        
        reset.getStyleClass().removeAll();
        reset.getStyleClass().add("SimpleButton");
        
        lexicon.getStyleClass().removeAll();
        lexicon.getStyleClass().add("SimpleButton");
        
        manage.getStyleClass().removeAll();
        manage.getStyleClass().add("SimpleButton");
        
        // GridPane Declaration
        GridPane gridPane = new GridPane();
        
        // Adding Buttons in GridPane
        gridPane.add(start, 0, 0);
        gridPane.add(stop, 1, 0);
        gridPane.add(next, 2, 0);
        gridPane.add(reset, 3, 0);
        gridPane.add(lexicon, 4, 0);
        gridPane.add(manage, 5, 0);
        gridPane.add(new ImageView("com/Images/zoom.png"), 0, 1, 2, 1);
        gridPane.add(Zoom, 1, 1, 2, 1);
        gridPane.add(new ImageView("com/Images/speed.png"), 3, 1, 2, 1);
        gridPane.add(Speed, 4, 1, 2, 1);
        
        // Setting Horizontal Gap Between Buttons
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        
        // Setting Alignment and Padding of GridPane
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        
        return gridPane;
    }
    
    public GridPane GetBoard() {
        GridPane gridPane = new GridPane();
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid_buttons[i][j] = new Grid_Button(i, j);
    
                int finalJ = j;
                int finalI = i;
                grid_buttons[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (game.getBoard().getCell(finalI, finalJ).isAlive() == false) {
                            grid_buttons[finalI][finalJ].getStyleClass().removeAll("UnselectedButton");
                            grid_buttons[finalI][finalJ].getStyleClass().add("SelectedButton");
                            game.getBoard().setAlive(finalI, finalJ);
                        } else {
                            grid_buttons[finalI][finalJ].getStyleClass().removeAll("SelectedButton");
                            grid_buttons[finalI][finalJ].getStyleClass().add("UnselectedButton");
                            game.getBoard().setDead(finalI, finalJ);
                        }
                    }
                });
    
                next.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        game.getBoard().step();
                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < columns; j++) {
                                if (!game.getBoard().getCell(i, j).isAlive()) {
                                    grid_buttons[i][j].getStyleClass().removeAll("SelectedButton");
                                    grid_buttons[i][j].getStyleClass().add("UnselectedButton");
                                } else {
                                    grid_buttons[i][j].getStyleClass().removeAll("UnselectedButton");
                                    grid_buttons[i][j].getStyleClass().add("SelectedButton");
                                }
                            }
                        }
                    }
                });
    
                reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < columns; j++) {
                                grid_buttons[i][j].getStyleClass().removeAll("SelectedButton");
                                grid_buttons[i][j].getStyleClass().add("UnselectedButton");
                                game.getBoard().setDead(i, j);
                            }
                        }
                    }
                });
    
                gridPane.add(grid_buttons[i][j], j, i);
            }
        }
        /*
     
     
         */
        Speed.setValue(game.getControl().getSpeedFactor());
        Zoom.setValue(game.getControl().getZoomfactor());
    
        Speed.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                game.getControl().setSpeedFactor((float) Speed.getValue());
            }
        });
    
        Zoom.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double prev = game.getControl().getZoomfactor(), next = Zoom.getValue();
                gridPane.setScaleX(gridPane.getScaleX() + (next - prev));
                gridPane.setScaleY(gridPane.getScaleY() + (next - prev));
                game.getControl().setZoomFactor((float) next);
            }
        });
    
        return gridPane;
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
        primaryStage.setTitle("Game Of Life");
    
        GridPane BtnGrid = GetButtons();
        GridPane BoardGrid = GetBoard();
    
        GridPane gameGrid = MergeGridPanes(BtnGrid, BoardGrid);
    
        Scene scene = new Scene(gameGrid, 1, 1);
    
        scene.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
    
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}