package com.ciruman;

import com.ciruman.model.MindMapNode;
import com.ciruman.ui.MindMapNodeManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
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

    private Node createRootNode() {
        MindMapNode mindMapNode = new MindMapNode("Start here!");
        MindMapNode mindMapNode1 = new MindMapNode("Children 1");
        MindMapNode mindMapNode2 = new MindMapNode("Children 2");
        mindMapNode2.addChilds(new MindMapNode("Children"));
        mindMapNode.addChilds(mindMapNode1, mindMapNode2);
        MindMapNodeManager mindMapNodeManager = new MindMapNodeManager(mindMapNode);
        return mindMapNodeManager.getUI();
    }
}
