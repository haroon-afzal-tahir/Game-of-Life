package com.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Grid {
    Rectangle rectangle;

    Grid(){

        Rectangle rectangle = new Rectangle(10, 10, 10, 10);
        System.out.println("Hello");

    }

    public static void main(String[] args) {
        Grid g1 = new Grid();
    }

}
