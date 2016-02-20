package com.ciruman.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.concurrent.Callable;

public class MindMapUINode {

    private Label label = new Label("Start here!");
    private Group root = new Group(label);

    public MindMapUINode() {
        label.getStyleClass().add("node");
        label.translateXProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return -(label.getLayoutBounds().getWidth()/2.0);
            }
        },label.layoutBoundsProperty()));
        label.translateYProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return -(label.getLayoutBounds().getHeight()/2.0);
            }
        },label.layoutBoundsProperty()));
    }

    public Node getUI(){
        return root;
    }

    public void setText(String text) {
        this.label.setText(text);
    }

    public void addNode(Node ui, final ObjectBinding<Point2D> offset) {
        handleConnector(ui, offset);
        handleNode(ui, offset);
        label.toFront();
    }

    private void handleConnector(final Node ui, final ObjectBinding<Point2D> offset) {
        MindMapNodeConnector connector = getConnector();
        connector.endXProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return offset.get().getX();
            }
        }, offset, ui.layoutBoundsProperty()));

        connector.endYProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return offset.get().getY();
            }
        }, offset, ui.layoutBoundsProperty()));
        connector.getRoot().toBack();
        root.getChildren().add(connector.getRoot());
    }

    private void handleNode(final Node ui, final ObjectBinding<Point2D> offset) {
        root.getChildren().add(ui);
        ui.layoutXProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return offset.get().getX();
            }
        }, offset));
        ui.layoutYProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return offset.get().getY();
            }
        }, offset));
        ui.toFront();
    }


    public MindMapNodeConnector getConnector(){
        MindMapNodeConnector mindMapNodeConnector = new MindMapNodeConnector();
        return mindMapNodeConnector;
    }

}
