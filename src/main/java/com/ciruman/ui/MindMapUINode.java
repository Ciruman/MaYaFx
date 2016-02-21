package com.ciruman.ui;

import com.ciruman.model.MindMapNode;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.concurrent.Callable;

public class MindMapUINode {

    private Label label = new Label("Start here!");
    private Group root = new Group(label);

    public MindMapUINode() {
        label.translateXProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return -(label.getLayoutBounds().getWidth() / 2.0);
            }
        }, label.layoutBoundsProperty()));
        label.translateYProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return -(label.getLayoutBounds().getHeight() / 2.0);
            }
        }, label.layoutBoundsProperty()));
    }

    public void setOnAction(EventHandler<MouseEvent> onAction) {
        label.setOnMouseClicked(onAction);
    }

    public Node getUI() {
        return root;
    }

    public void setText(String text) {
        this.label.setText(text);
    }

    public void addNode(Node ui, final ObjectBinding<Point2D> offset) {
        handleConnector(offset);
        handleNode(ui, offset);
        label.toFront();
    }

    private MindMapNodeConnector handleConnector(ObjectBinding<Point2D> offset) {
        MindMapNodeConnector mindMapNodeConnector = new MindMapNodeConnector();
        mindMapNodeConnector.getRoot().toBack();
        new LayoutTransition(offset, mindMapNodeConnector.endXProperty(), mindMapNodeConnector.endYProperty());
        root.getChildren().add(mindMapNodeConnector.getRoot());
        return mindMapNodeConnector;
    }

    private void handleNode(Node ui, ObjectBinding<Point2D> offset) {
        root.getChildren().add(ui);
        new LayoutTransition(offset, ui.layoutXProperty(), ui.layoutYProperty());
        ui.toFront();
    }

    public void setStyling(int level, int mainBranch) {
        if (mainBranch == MindMapNode.ROOT_NODE) {
            label.getStyleClass().add("node-root");
        }
        label.getStyleClass().add("node-" + mainBranch + "-" + level);
    }
}
