//////////////////////////GUI Buttons///////////////////////////////////////////////
package com.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridClass grids = new GridClass();  //creates grid obj


        //-----grids---//


       Scene scene = new Scene(grids, 640,600);
       primaryStage.setScene(scene);
       primaryStage.show();
        grids.draw();
        //---buttons---//

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
       Stage buttonStage = new Stage();
        buttonStage.setTitle("GUI_Buttons ");

        Scene buttonScene = new Scene(root, 640,600);
        buttonStage.setScene(buttonScene);
        buttonStage.show();

        


    }

}
