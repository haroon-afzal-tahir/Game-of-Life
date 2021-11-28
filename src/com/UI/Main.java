//////////////////////////GUI Buttons///////////////////////////////////////////////
package com.UI;

import com.BL.Game;
import com.FactoryImplementation.Factory;
import com.Interfaces.GetFromBL.UI.UI_To_BL_Data_Transfer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application implements UI_To_BL_Data_Transfer {
    
    Button start = new Button("Start");
    Button stop = new Button("Stop");
    Button next = new Button("Next");
    Button reset = new Button("Reset");
    Button lexicon = new Button("Lexicon");
    Button manage = new Button("Manage");
    Button save = new Button("Save State");
    Button load = new Button("Load State");
    Button delete = new Button("Delete State");
    
    Button deleteState = new Button("Delete State");
    Button loadState = new Button("Load State");
    
    Button loadLexicon = new Button("Load Lexicon");
    
    Button Submit = new Button("Submit");
    
    Slider Zoom = new Slider(0, 10, 0.5);
    Slider Speed = new Slider(1, 10, 0.5);
    
    Stage loadStates = new Stage();
    Stage manageStates = new Stage();
    Stage lexiconStates = new Stage();
    Stage saveState = new Stage();
    Stage deleteStates = new Stage();
    
    private int rows = 20, columns = 75;
    private Grid_Button[][] grid_buttons = new Grid_Button[rows][columns];
    
    Factory factory = new Factory();
    private Game game = new Game(rows, columns, factory);
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void UpdateBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!getCellStatus(i, j)) {
                    grid_buttons[i][j].getStyleClass().removeAll("SelectedButton");
                    grid_buttons[i][j].getStyleClass().add("UnselectedButton");
                } else {
                    grid_buttons[i][j].getStyleClass().removeAll("UnselectedButton");
                    grid_buttons[i][j].getStyleClass().add("SelectedButton");
                }
            }
        }
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
    
        setZoomFactor(0);
        setSpeedFactor(1);
    
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
        lexicon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setPlay(false);
                lexiconStates.show();
            }
        });
    
        manage.getStyleClass().removeAll();
        manage.getStyleClass().add("SimpleButton");
        manage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setPlay(false);
                manageStates.show();
            }
        });


        // GridPane Declaration
        GridPane gridPane = new GridPane();

        GridPane zoomIcon = new GridPane();
        GridPane speedIcon = new GridPane();

        zoomIcon.add(new ImageView("com/Images/zoom.png"), 0, 0);
        speedIcon.add(new ImageView("com/Images/speed.png"), 0, 0);
        zoomIcon.setAlignment(Pos.TOP_RIGHT);
        speedIcon.setAlignment(Pos.TOP_RIGHT);

        // Adding Buttons in GridPane
        gridPane.add(start, 0, 0);
        gridPane.add(stop, 1, 0);
        gridPane.add(next, 2, 0);
        gridPane.add(reset, 3, 0);
        gridPane.add(lexicon, 4, 0);
        gridPane.add(manage, 5, 0);
        gridPane.add(zoomIcon, 0, 1, 1, 1);
        gridPane.add(Zoom, 1, 1, 2, 1);
        gridPane.add(speedIcon, 3, 1, 1, 1);
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
                            setAlive(finalI, finalJ);
                        } else {
                            grid_buttons[finalI][finalJ].getStyleClass().removeAll("SelectedButton");
                            grid_buttons[finalI][finalJ].getStyleClass().add("UnselectedButton");
                            setDead(finalI, finalJ);
                        }
                    }
                });

                next.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        step();
                        UpdateBoard();
                    }
                });

                reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < columns; j++) {
                                grid_buttons[i][j].getStyleClass().removeAll("SelectedButton");
                                grid_buttons[i][j].getStyleClass().add("UnselectedButton");
                                setDead(i, j);
                            }
                        }
                    }
                });

                start.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        setPlay(true);
                        //if (startBtn.isAlive() == false) {
                        //startBtn = new Thread(Main.this);
                        //startBtn.start();
                        //}
                        StartGame();
                    }
                });

                stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        setPlay(false);
                    }
                });

                int finalI1 = i;
                int finalJ1 = j;
                grid_buttons[i][j].setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        gridPane.setPadding(new Insets(mouseEvent.getSceneY(), 20, 20, mouseEvent.getSceneX()));
                    }
                });
    
                gridPane.add(grid_buttons[i][j], j, i);
            }
        }

        Speed.setValue(getSpeedFactor() / 500);
        Zoom.setValue(getZoomFactor());
    
        Speed.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSpeedFactor(((float) Speed.getValue()));
            }
        });
    
        Zoom.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double prev = getZoomFactor(), next = Zoom.getValue();
                gridPane.setScaleX(gridPane.getScaleX() + (next - prev));
                gridPane.setScaleY(gridPane.getScaleY() + (next - prev));
                setZoomFactor((float) next);
            }
        });

        //gridPane.setPadding(new Insets(100, 20, 20, 20));

        return gridPane;
    }

    public GridPane MergeGridPanes(GridPane BtnGrid, GridPane BoardGrid) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));

        RowConstraints Label1 = new RowConstraints();
        Label1.setPercentHeight(5);

        RowConstraints Board = new RowConstraints();
        Board.setPercentHeight(85);

        RowConstraints button = new RowConstraints();
        button.setPercentHeight(10);

        gridPane.getRowConstraints().addAll(Label1, Board, button);

        GridPane LabelPane = new GridPane();

        Label label = new Label("Game of Life");
        label.setFont(Font.font(20.0));
        label.setTextFill(Color.WHITE);


        LabelPane.add(label, 0, 0);
        LabelPane.setAlignment(Pos.CENTER);
        LabelPane.setPadding(new Insets(20, 20, 30, 20));

        gridPane.add(LabelPane, 0, 0);
        gridPane.add(BoardGrid, 0, 1);
        gridPane.add(BtnGrid, 0, 2);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #484a49");
        return gridPane;
    }

    public GridPane ManageGridPane() {
        GridPane gridPane = new GridPane();
    
        save.setMinWidth(100);
        load.setMinWidth(100);
        delete.setMinWidth(100);
    
        save.getStyleClass().removeAll();
        save.getStyleClass().add("SimpleButton");
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setPlay(false);
                saveState.show();
            }
        });
    
    
        load.getStyleClass().removeAll();
        load.getStyleClass().add("SimpleButton");
    
        delete.getStyleClass().removeAll();
        delete.getStyleClass().add("SimpleButton");
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setPlay(false);
                deleteStates.show();
            }
        });
    
    
        load.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loadStates.show();
            }
        });
    
        gridPane.add(save, 0, 0);
        gridPane.add(load, 1, 0);
        gridPane.add(delete, 2, 0);

        gridPane.setHgap(20);
        gridPane.setVgap(20);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(30));
        gridPane.setStyle("-fx-background-color: #484a49");

        return gridPane;
    }

    public GridPane ViewStates() {
        GridPane gridPane = new GridPane();

        loadState.setMinWidth(100);
        deleteState.setMinWidth(100);

        loadState.getStyleClass().removeAll();
        loadState.getStyleClass().add("SimpleButton");

        deleteState.getStyleClass().removeAll();
        deleteState.getStyleClass().add("SimpleButton");

        ListView<String> list = new ListView<String>();

        ObservableList<String> items = FXCollections.observableArrayList(
                "State 1", "State 2", "State 3", "State 4", "State 5"
        );



        list.setItems(items);

        gridPane.add(list, 0 , 0, 1, 3);

        gridPane.add(loadState, 1, 0);
    
        gridPane.add(deleteState, 1, 1);
    
        gridPane.setHgap(20);
        gridPane.setVgap(20);
    
        gridPane.setPadding(new Insets(30));
        gridPane.setStyle("-fx-background-color: #484a49");
    
        return gridPane;
    }
    
    public GridPane LexiconStates() {
        GridPane gridPane = new GridPane();
        
        loadLexicon.setMinWidth(100);
        
        loadLexicon.getStyleClass().removeAll();
        loadLexicon.getStyleClass().add("SimpleButton");
        
        ListView<String> list = new ListView<String>();
        
        ObservableList<String> items = FXCollections.observableArrayList(
                "Lexicon 1", "Lexicon 2", "Lexicon 3", "Lexicon 4", "Lexicon 5"
        );
        
        
        list.setItems(items);
        
        gridPane.add(list, 0, 0, 1, 3);
        
        gridPane.add(loadLexicon, 1, 0);
    
        gridPane.setHgap(20);
        gridPane.setVgap(20);
    
        gridPane.setPadding(new Insets(30));
        gridPane.setStyle("-fx-background-color: #484a49");
    
        return gridPane;
    }
    
    public GridPane SavesState() {
        GridPane gridPane = new GridPane();
        
        Label label = new Label("Enter State Name:");
        label.setTextFill(Color.WHITE);
        TextField textField = new TextField();
        
        Submit.setMinWidth(100);
        Submit.getStyleClass().removeAll();
        Submit.getStyleClass().add("SimpleButton");
        
        gridPane.add(label, 0, 0);
        gridPane.add(textField, 0, 1);
        gridPane.add(Submit, 0, 2);
        
        gridPane.setAlignment(Pos.CENTER);
        
        gridPane.setVgap(20);
        
        gridPane.setPadding(new Insets(30));
        gridPane.setStyle("-fx-background-color: #484a49");
        
        return gridPane;
    }
    
    public GridPane DeleteState() {
        GridPane gridPane = new GridPane();
        
        Button deleteSave = new Button("Delete State");
        
        deleteSave.setMinWidth(100);
        
        deleteSave.getStyleClass().removeAll();
        deleteSave.getStyleClass().add("SimpleButton");
        
        ListView<String> list = new ListView<String>();
        
        ObservableList<String> items = FXCollections.observableArrayList(
                "State 1", "State 2", "State 3", "State 4", "State 5"
        );
        
        list.setItems(items);
        
        gridPane.add(list, 0, 0, 1, 3);
        
        gridPane.add(deleteSave, 1, 0);
        
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        
        gridPane.setPadding(new Insets(30));
        gridPane.setStyle("-fx-background-color: #484a49");
        
        return gridPane;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Game Of Life");
        
        factory.setUI(this);
        
        Scene scene = new Scene(MergeGridPanes(GetButtons(), GetBoard()), 1, 1);
        Scene manageState = new Scene(ManageGridPane(), 500, 200);
        Scene viewStates = new Scene(ViewStates(), 400, 200);
        Scene lexiconScene = new Scene(LexiconStates(), 400, 200);
        Scene saveStates = new Scene(SavesState(), 400, 200);
        Scene deleteState = new Scene(DeleteState(), 400, 200);
        
        scene.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
        manageState.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
        viewStates.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
        lexiconScene.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
        saveStates.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
        deleteState.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
        
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        manageStates.setTitle("Manage States");
        manageStates.setScene(manageState);
        
        loadStates.setTitle("All States");
        loadStates.setScene(viewStates);
        
        lexiconStates.setTitle("Lexicons");
        lexiconStates.setScene(lexiconScene);
        
        saveState.setTitle("Save A State");
        saveState.setScene(saveStates);
        
        deleteStates.setTitle("Delete A State");
        deleteStates.setScene(deleteState);
    }
    
    @Override
    public void setAlive(int row, int column) {
        game.getBoard().getCell(row, column).setAlive(true);
    }
    
    @Override
    public void setDead(int row, int column) {
        game.getBoard().getCell(row, column).setDead(false);
    }
    
    @Override
    public boolean getCellStatus(int row, int column) {
        return game.getBoard().getCell(row, column).isAlive();
    }
    
    @Override
    public void step() {
        game.getBoard().step();
    }
    
    @Override
    public boolean getPlay() {
        return game.getControl().getplay();
    }
    
    @Override
    public void setPlay(boolean play) {
        game.getControl().setPlay(play);
    }
    
    @Override
    public float getZoomFactor() {
        return game.getControl().getZoomfactor();
    }
    
    @Override
    public void setZoomFactor(float zf) {
        game.getControl().setZoomFactor(zf);
    }
    
    @Override
    public float getSpeedFactor() {
        return game.getControl().getSpeedFactor();
    }
    
    @Override
    public void setSpeedFactor(float sf) {
        game.getControl().setSpeedFactor(sf);
    }
    
    @Override
    public void StartGame() {
        game.StartGame();
    }
}