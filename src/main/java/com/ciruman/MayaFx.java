package com.ciruman;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MayaFx extends Application {

    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        root.getStyleClass().add("background");

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        root.getChildren().add(createCanvas(visualBounds));
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());
        scene.getStylesheets().add("style.css");

        stage.getIcons().add(new Image(MayaFx.class.getResourceAsStream("/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    private Group createCanvas(Rectangle2D visualBounds) {
        Group canvas = new Group(createRootNode());
        canvas.setLayoutX(visualBounds.getWidth()/2.0);
        canvas.setLayoutY(visualBounds.getHeight()/2.0);
        return canvas;
    }

    private Label createRootNode() {
        Label rootNode = new Label("Start here!");
        rootNode.getStyleClass().add("node");
        return rootNode;
    }
}
