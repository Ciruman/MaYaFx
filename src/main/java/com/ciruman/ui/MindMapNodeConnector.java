package com.ciruman.ui;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

public class MindMapNodeConnector {

    CubicCurve cubicCurve = new CubicCurve();
    private ObjectProperty<Node> root = new SimpleObjectProperty<Node>(cubicCurve);

    public MindMapNodeConnector() {
        cubicCurve.setFill(Color.TRANSPARENT);
        cubicCurve.setStroke(Color.YELLOWGREEN);
        cubicCurve.setStrokeWidth(2.0);
        cubicCurve.controlX1Property().bind(cubicCurve.endXProperty());
        cubicCurve.controlY2Property().bind(cubicCurve.endYProperty());
    }

    public DoubleProperty startXProperty(){
        return cubicCurve.startXProperty();
    }

    public DoubleProperty startYProperty(){
        return cubicCurve.startYProperty();
    }

    public DoubleProperty endXProperty(){
        return cubicCurve.endXProperty();
    }

    public DoubleProperty endYProperty(){
        return cubicCurve.endYProperty();
    }

    public Node getRoot() {
        return root.get();
    }

    public ObjectProperty<Node> rootProperty() {
        return root;
    }
}
