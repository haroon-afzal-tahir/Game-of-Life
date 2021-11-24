package com.UI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.transform.Affine;

import java.awt.*;

public class GridClass extends VBox {
    private Canvas canvas; // can draw lines/boxes/etc on a canvas
   private Simulation simulation;
   private Affine affine;
    public GridClass(){
        this.canvas = new Canvas(400,400);

        this.getChildren().addAll(this.canvas);
        this.affine = new Affine(); // for transformation

        this.affine.appendScale(400/10, 400/10);
        this.simulation = new Simulation(10,10);

        simulation.setAlive(2,2);// dummy test
    }
    public void draw(){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.transform(this.affine);
        g.setFill(Color.DARKGREY);// color of the canvas
        g.fillRect(0,0,400,400);
        g.setFill((Color.DARKBLUE));//color of alive cell
        for (int i = 0; i <this.simulation.width ; i++) {
            for (int j = 0; j <this.simulation.height ; j++) {
                if(this.simulation.getState(i,j) == 1){
                    g.fillRect(i,i,1,1);
                }
                }
            }

    // to separate cells by lines
    g.setStroke(Color.WHEAT);
    g.setLineWidth(0.05f);
        for (int x = 0; x <=this.simulation.width ; x++) {
            g.strokeLine(x,0,x,10);
        }
        for (int y = 0; y <= this.simulation.height; y++) {
            g.strokeLine(0,y,10,y);

        }
    }


}
