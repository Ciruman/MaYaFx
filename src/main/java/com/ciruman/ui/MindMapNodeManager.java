package com.ciruman.ui;

import com.ciruman.model.MindMapNode;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class MindMapNodeManager {

    private final MindMapNode mindMapNode;
    private MindMapUINode mindMapUINode;

    public MindMapNodeManager(MindMapNode mindMapNode){
        this.mindMapNode = mindMapNode;
        initSubNodes();
    }

    private void initSubNodes() {
        mindMapNode.childsProperty().forEach(new Consumer<MindMapNode>() {
            @Override
            public void accept(MindMapNode node) {
                MindMapNodeManager mindMapNodeManager = new MindMapNodeManager(node);
                getOrInitializeMindMapUINode().addNode(mindMapNodeManager.getUI(), createPoint2DProperty(mindMapNode.childsProperty().indexOf(node)));
            }
        });
    }

    public Node getUI(){
        MindMapUINode mindMapUINode = getOrInitializeMindMapUINode();
        return mindMapUINode.getUI();
    }

    private MindMapUINode getOrInitializeMindMapUINode() {
        if(mindMapUINode==null) {
            mindMapUINode = new MindMapUINode();
            mindMapUINode.setText(mindMapNode.getText());
        }
        return mindMapUINode;
    }

    private ObjectBinding<Point2D> createPoint2DProperty(final int i) {
        return Bindings.createObjectBinding(new Callable<Point2D>() {
            @Override
            public Point2D call() throws Exception {
                double angle = calculateAngle((i+1.0)/(mindMapNode.childsProperty().size()+1.0));
                return getPosition(new Point2D(40,0), 90, angle);
            }
        }, mindMapNode.childsProperty());
    }

    private double calculateAngle(double position) {
        return (180.0*position)-90;
    }

    private Point2D getPosition(Point2D center, double radius, double angle) {
        double x = center.getX() + radius * Math.cos(Math.toRadians(angle));
        double y = center.getY() + radius * Math.sin(Math.toRadians(angle));
        Point2D p = new Point2D(x, y);
        return p;
    }

}
