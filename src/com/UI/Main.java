package com.UI;

import com.BL.DB_I;
import com.BL.UI_I;
import com.BL.UI_To_BL_Data_Transfer;
import com.FactoryImplementation.BL_Factory;
import com.FactoryImplementation.UI_Factory;
import com.JSON_API.Simple_API;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.File;

public class Main extends Application implements UI_To_BL_Data_Transfer, UI_I {
    ListView<String> deletelist = new ListView<String>();
    ListView<String> loadlist = new ListView<String>();
    
    Button start = new Button("Start");
    Button stop = new Button("Stop");
    Button next = new Button("Next");
    Button reset = new Button("Reset");
    Button manage = new Button("Manage");
    Button save = new Button("Save State");
    Button load = new Button("Load State");
    Button delete = new Button("Delete State");
    
    Button deleteState = new Button("Delete State");
    Button loadState = new Button("Load State");
    
    Button Submit = new Button("Submit");
    
    Slider Zoom = new Slider(0, 10, 0.5);
    Slider Speed = new Slider(1, 10, 0.5);
    
    Stage loadStates = new Stage();
    Stage manageStates = new Stage();
    Stage saveState = new Stage();
    Stage deleteStates = new Stage();
	
	private int rows = 20, columns = 75;
	private Grid_Button[][] grid_buttons = new Grid_Button[rows][columns];
	
	UI_Factory UIFactory = new UI_Factory();
	private BL_Factory game;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Main(DB_I obj) throws Exception {
		game = new BL_Factory(Simple_API.StringToJSON(String.valueOf(rows), "Row"), Simple_API.StringToJSON(String.valueOf(columns), "Column"), UIFactory);
		game.attachDB(obj);
		SetGenerations(Simple_API.StringToJSON("0", "Generations"));
		start(new Stage());
	}
	
	public void PlayMp3(Media media) {
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
	
	public boolean checkIsAnyAlive() {
		boolean isAnyAlive = false;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (Simple_API.JSONToBoolean(getCellStatus(Simple_API.StringToJSON(String.valueOf(i), "Row"), Simple_API.StringToJSON(String.valueOf(j), "Column")))) {
					isAnyAlive = true;
					break;
				}
			}
            if (isAnyAlive) {
                break;
            }
        }
        return isAnyAlive;
    }
    
    public ObservableList<String> UpdateList() {
        String[] List = GetStates();
        
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < List.length; i++) {
            items.add(List[i]);
        }
        return items;
    }
    
    public String[] GetStates() {
		String temp = Simple_API.JSONToString(game.view());
		int count = 0;
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) == '\\') {
				count++;
			}
		}
		String[] list = new String[count];
		int index = 0;
		for (int i = 0; i < count; i++) {
			String test = "";
			for (int j = 0; temp.charAt(index) != '\\' && temp.charAt(index) != '\0'; j++) {
				if (index != 0) {
					if (temp.charAt(index - 1) != '\\') {
						test += temp.charAt(index++);
					} else {
						index++;
					}
				} else {
					test += temp.charAt(index++);
				}
			}
			if (test.length() > 4) {
				test = test.substring(0, test.length() - 4);
				list[i] = test;
				index++;
			}
		}
        return list;
    }
    
    public void UpdateBoard() {
		boolean isAllDead = true, isSame = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
				if (!Simple_API.JSONToBoolean(getCellStatus(Simple_API.StringToJSON(String.valueOf(i), "Row"), Simple_API.StringToJSON(String.valueOf(j), "Column")))) {
					if (grid_buttons[i][j].getStyleClass().size() > 1) {
						if (grid_buttons[i][j].getStyleClass().get(1) == "SelectedButton") {
							isSame = false;
						}
					}
					grid_buttons[i][j].getStyleClass().removeAll("SelectedButton");
					grid_buttons[i][j].getStyleClass().add("UnselectedButton");
				} else {
					isAllDead = false;
					if (grid_buttons[i][j].getStyleClass().size() > 1) {
						if (grid_buttons[i][j].getStyleClass().get(1) == "UnselectedButton") {
							isSame = false;
						}
					}
					grid_buttons[i][j].getStyleClass().removeAll("UnselectedButton");
					grid_buttons[i][j].getStyleClass().add("SelectedButton");
				}
			}
		}
		if (isSame) {
			PlayMp3(new Media(new File("src\\com\\Additional\\Victory.mp3").toURI().toString()));
			setPlay(Simple_API.BooleanToJSON(false, "Play"));
		} else if (!isAllDead) {
			PlayMp3(new Media(new File("src\\com\\Additional\\Game Running.mp3").toURI().toString()));
		} else {
			PlayMp3(new Media(new File("src\\com\\Additional\\Cells Dead.mp3").toURI().toString()));
			setPlay(Simple_API.BooleanToJSON(false, "Play"));
		}
		SetGenerations(Simple_API.StringToJSON(String.valueOf(Integer.parseInt(Simple_API.JSONToString(GetGenerations())) + 1), "Generations"));
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
	
		setZoomFactor(Simple_API.StringToJSON("0", "Zoom"));
		setSpeedFactor(Simple_API.StringToJSON("1", "Speed"));
	
		start.setMinWidth(100);
		stop.setMinWidth(100);
		next.setMinWidth(100);
		reset.setMinWidth(100);
		manage.setMinWidth(100);
	
		start.getStyleClass().removeAll();
		start.getStyleClass().add("SimpleButton");

        stop.getStyleClass().removeAll();
        stop.getStyleClass().add("SimpleButton");
    
        next.getStyleClass().removeAll();
        next.getStyleClass().add("SimpleButton");
    
        reset.getStyleClass().removeAll();
        reset.getStyleClass().add("SimpleButton");
    
        manage.getStyleClass().removeAll();
        manage.getStyleClass().add("SimpleButton");
        manage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
				setPlay(Simple_API.BooleanToJSON(false, "Play"));
				manageStates.show();
			}
		});
	
	
		// GridPane Declaration
		GridPane gridPane = new GridPane();
	
		GridPane zoomIcon = new GridPane();
		GridPane speedIcon = new GridPane();
	
		zoomIcon.add(new ImageView("com/Additional/zoom.png"), 0, 0);
		zoomIcon.add(Zoom, 1, 0);
		speedIcon.add(new ImageView("com/Additional/speed.png"), 0, 0);
		speedIcon.add(Speed, 1, 0);
	
		zoomIcon.setAlignment(Pos.TOP_RIGHT);
		speedIcon.setAlignment(Pos.TOP_RIGHT);
	
	
		// Adding Buttons in GridPane
		gridPane.add(start, 0, 0);
		gridPane.add(stop, 1, 0);
		gridPane.add(next, 2, 0);
		gridPane.add(reset, 3, 0);
        gridPane.add(manage, 4, 0);
        gridPane.add(zoomIcon, 0, 1, 2, 1);
        //gridPane.add(Zoom, 1, 1, 2, 1);
        gridPane.add(speedIcon, 2, 1, 2, 1);
        //gridPane.add(Speed, 3, 1, 2, 1);
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
						if (game.getBoard().getCell(Simple_API.StringToJSON(String.valueOf(finalI), "I"), Simple_API.StringToJSON(String.valueOf(finalJ), "J")).isAlive() == false) {
							grid_buttons[finalI][finalJ].getStyleClass().removeAll("UnselectedButton");
							grid_buttons[finalI][finalJ].getStyleClass().add("SelectedButton");
							setAlive(Simple_API.StringToJSON(String.valueOf(finalI), "Row"), Simple_API.StringToJSON(String.valueOf(finalJ), "Column"));
						} else {
							grid_buttons[finalI][finalJ].getStyleClass().removeAll("SelectedButton");
							grid_buttons[finalI][finalJ].getStyleClass().add("UnselectedButton");
							setDead(Simple_API.StringToJSON(String.valueOf(finalI), "Row"), Simple_API.StringToJSON(String.valueOf(finalJ), "Column"));
						}
                    }
                });

                next.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
						step();
						UpdateBoard();
						PlayMp3(new Media(new File("src\\com\\Additional\\Game Running.mp3").toURI().toString()));
					}
                });

                reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
						setPlay(Simple_API.BooleanToJSON(false, "State"));
						SetGenerations(Simple_API.StringToJSON("0", "Generations"));
						PlayMp3(new Media(new File("src\\com\\Additional\\Cells Dead.mp3").toURI().toString()));
						for (int i = 0; i < rows; i++) {
							for (int j = 0; j < columns; j++) {
								grid_buttons[i][j].getStyleClass().removeAll("SelectedButton");
								grid_buttons[i][j].getStyleClass().add("UnselectedButton");
								setDead(Simple_API.StringToJSON(String.valueOf(i), "Row"), Simple_API.StringToJSON(String.valueOf(j), "Column"));
							}
						}
					}
                });

                start.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
						setPlay(Simple_API.BooleanToJSON(true, "State"));
						StartGame();
					}
                });

                stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
						setPlay(Simple_API.BooleanToJSON(false, "State"));
						if (checkIsAnyAlive()) {
							PlayMp3(new Media(new File("src\\com\\Additional\\Victory.mp3").toURI().toString()));
						} else {
							PlayMp3(new Media(new File("src\\com\\Additional\\Stop Press.mp3").toURI().toString()));
						}
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
	
		Speed.setValue(Float.parseFloat(Simple_API.JSONToString(getSpeedFactor())) / 500);
		Zoom.setValue(Float.parseFloat(Simple_API.JSONToString(getZoomFactor())));
	
		Speed.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				setSpeedFactor(Simple_API.StringToJSON(String.valueOf((float) Speed.getValue()), "Speed"));
			}
		});
	
		Zoom.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
            public void handle(MouseEvent mouseEvent) {
				double prev = Float.parseFloat(Simple_API.JSONToString(getZoomFactor())), next = Zoom.getValue();
                gridPane.setScaleX(gridPane.getScaleX() + (next - prev));
				gridPane.setScaleY(gridPane.getScaleY() + (next - prev));
				setZoomFactor(Simple_API.StringToJSON(String.valueOf((float) next), "Zoom"));
            }
        });

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
				setPlay(Simple_API.BooleanToJSON(false, "Play"));
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
				setPlay(Simple_API.BooleanToJSON(false, "Play"));
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
    
        loadState.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
				game.load(Simple_API.StringToJSON(loadlist.getSelectionModel().getSelectedItem() + ".txt", "StateName"));
				UpdateBoard();
				setZoomFactor(Simple_API.StringToJSON("0", "Zoom"));
				setSpeedFactor(Simple_API.StringToJSON("1", "Speed"));
				loadStates.close();
				manageStates.close();
			}
        });
        deleteState.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
				game.delete(Simple_API.StringToJSON(loadlist.getSelectionModel().getSelectedItem() + ".txt", "StateName"));
				loadlist.setItems(UpdateList());
				deletelist.setItems(UpdateList());
				loadStates.close();
				manageStates.close();
			}
        });
    
        loadlist.setItems(UpdateList());
    
        gridPane.add(loadlist, 0, 0, 1, 3);
    
        gridPane.add(loadState, 1, 0);
    
        gridPane.add(deleteState, 1, 1);
    
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
    
        Submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
				game.save(Simple_API.StringToJSON(textField.getText(), "StateName"));
				loadlist.setItems(UpdateList());
				deletelist.setItems(UpdateList());
				saveState.close();
				manageStates.close();
			}
        });
    
    
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
    
    
        deletelist.setItems(UpdateList());
        loadlist.setItems(UpdateList());
    
        deleteSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
				game.delete(Simple_API.StringToJSON(deletelist.getSelectionModel().getSelectedItem() + ".txt", "StateName"));
				deletelist.setItems(UpdateList());
				loadlist.setItems(UpdateList());
				deleteStates.close();
				manageStates.close();
			}
        });
    
        gridPane.add(deletelist, 0, 0, 1, 3);
    
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
		primaryStage.getIcons().add(new Image("com/Additional/Icon.png"));
	
		UIFactory.setUI(this);
	
		Scene scene = new Scene(MergeGridPanes(GetButtons(), GetBoard()), 1, 1);
		Scene manageState = new Scene(ManageGridPane(), 500, 200);
		Scene viewStates = new Scene(ViewStates(), 400, 200);
		Scene saveStates = new Scene(SavesState(), 400, 200);
		Scene deleteState = new Scene(DeleteState(), 400, 200);
	
		scene.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
		manageState.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
		viewStates.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
		saveStates.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
		deleteState.getStylesheets().add((new File("src\\com\\UI\\StyleSheet.css")).toURI().toURL().toString());
	
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	
		manageStates.setTitle("Manage States");
		manageStates.setScene(manageState);
		manageStates.getIcons().add(new Image("com/Additional/Icon.png"));
	
		loadStates.setTitle("All States");
		loadStates.setScene(viewStates);
		loadStates.getIcons().add(new Image("com/Additional/Icon.png"));
	
		saveState.setTitle("Save A State");
		saveState.setScene(saveStates);
		saveState.getIcons().add(new Image("com/Additional/Icon.png"));
	
		deleteStates.setTitle("Delete A State");
		deleteStates.setScene(deleteState);
		deleteStates.getIcons().add(new Image("com/Additional/Icon.png"));
	}
	
	@Override
	public void setAlive(JSONObject row, JSONObject column) {
		game.getBoard().getCell(row, column).setAlive(Simple_API.BooleanToJSON(true, "State"));
	}
	
	@Override
	public void setDead(JSONObject row, JSONObject column) {
		game.getBoard().getCell(row, column).setDead(Simple_API.BooleanToJSON(false, "State"));
	}
	
	@Override
	public JSONObject getCellStatus(JSONObject row, JSONObject column) {
		return Simple_API.BooleanToJSON(game.getBoard().getCell(row, column).isAlive(), "State");
	}
	
	@Override
	public void step() {
		game.getBoard().step();
	}
	
	@Override
	public JSONObject getPlay() {
		return Simple_API.BooleanToJSON(game.getControl().getplay(), "State");
	}
	
	@Override
	public void setPlay(JSONObject play) {
		game.getControl().setPlay(play);
	}
	
	@Override
	public JSONObject getZoomFactor() {
		return Simple_API.StringToJSON(String.valueOf(game.getControl().getZoomfactor()), "Zoom");
	}
	
	@Override
	public void setZoomFactor(JSONObject zf) {
		game.getControl().setZoomFactor(zf);
	}
	
	@Override
	public JSONObject getSpeedFactor() {
		return Simple_API.StringToJSON(String.valueOf(game.getControl().getSpeedFactor()), "Speed");
	}
	
	@Override
	public void setSpeedFactor(JSONObject sf) {
		game.getControl().setSpeedFactor(sf);
	}
	
	@Override
	public void StartGame() {
		game.StartGame();
	}
	
	@Override
	public void SetGenerations(JSONObject generations) {
		game.getControl().setGenerations(generations);
	}
	
	@Override
	public JSONObject GetGenerations() {
		return Simple_API.StringToJSON(String.valueOf(game.getControl().getGenerations()), "Generations");
	}
}