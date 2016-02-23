package com.ciruman.ui;

import com.ciruman.calculations.Angle;
import com.ciruman.model.MindMapNode;
import com.ciruman.model.MindMapNodeDirection;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class MindMapNodeManager {

    private final MindMapNode mindMapNode;
    private Angle angle;
    private MindMapUINode mindMapUINode;

    public MindMapNodeManager(MindMapNode mindMapNode, Angle angle) {
        this.mindMapNode = mindMapNode;
        this.angle = angle;
        initSubNodes();
    }

    private void initSubNodes() {
        mindMapNode.leftChildsProperty().addListener(new ListChangeListener<MindMapNode>() {
            @Override
            public void onChanged(Change<? extends MindMapNode> c) {
                while (c.next()){
                    if (c.wasAdded()){
                        c.getAddedSubList().forEach(new Consumer<MindMapNode>() {
                            @Override
                            public void accept(MindMapNode node) {
                                MindMapNodeManager mindMapNodeManager = new MindMapNodeManager(node, angle);
                                getOrInitializeMindMapUINode().addNode(mindMapNodeManager.getNode(), createPoint2DProperty(node, mindMapNode.leftChildsProperty()));
                            }
                        });
                    }
                    if(c.wasRemoved()){

                    }
                }
            }
        });
        mindMapNode.rightChildsProperty().addListener(new ListChangeListener<MindMapNode>() {
            @Override
            public void onChanged(Change<? extends MindMapNode> c) {
                while (c.next()){
                    if (c.wasAdded()){
                        c.getAddedSubList().forEach(new Consumer<MindMapNode>() {
                            @Override
                            public void accept(MindMapNode node) {
                                MindMapNodeManager mindMapNodeManager = new MindMapNodeManager(node, angle);
                                getOrInitializeMindMapUINode().addNode(mindMapNodeManager.getNode(), createPoint2DProperty(node, mindMapNode.rightChildsProperty()));
                            }
                        });
                    }
                    if(c.wasRemoved()){

                    }
                }
            }
        });
        getOrInitializeMindMapUINode().setOnAction(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==1) {
                    MindMapNode newMindMapNode = new MindMapNode("Hola mundo");
                    newMindMapNode.setLevel(mindMapNode.getLevel()+1);
                    newMindMapNode.setMainBranch(calculateMainBranch());
                    mindMapNode.addChilds(newMindMapNode);
                }
            }
        });
    }

    public Node getNode() {
        MindMapUINode mindMapUINode = getOrInitializeMindMapUINode();
        return mindMapUINode.getUI();
    }

    private MindMapUINode getOrInitializeMindMapUINode() {
        if (mindMapUINode == null) {
            mindMapUINode = new MindMapUINode();
            mindMapUINode.setText(mindMapNode.getText());
            mindMapUINode.setStyling(mindMapNode.getLevel(), mindMapNode.getMainBranch());
        }
        return mindMapUINode;
    }

    private ObjectBinding<Point2D> createPoint2DProperty(final MindMapNode node, final ObservableList<MindMapNode> childs) {
        return Bindings.createObjectBinding(new Callable<Point2D>() {
            @Override
            public Point2D call() throws Exception {
                int numberOfNodesInTheSameDirection = childs.size();
                int indexInTheSameDirection = childs.indexOf(node);
                double angleOffsetWithParent = getAngleOffsetWithParent(node)-90;
                return angle.calculatePosition(indexInTheSameDirection, numberOfNodesInTheSameDirection, node.getMindMapNodeDirection(), node.getLevel(), angleOffsetWithParent);
            }
        }, childs, getOrInitializeMindMapUINode().getUI().layoutXProperty(), getOrInitializeMindMapUINode().getUI().layoutYProperty());
    }

    private double getAngleOffsetWithParent(MindMapNode node) {
        if(mindMapNode.isRoot()){
            return node.getMindMapNodeDirection()== MindMapNodeDirection.RIGHT?0:180;
        }
        double layoutX = getOrInitializeMindMapUINode().getUI().getLayoutX();
        double layoutY = getOrInitializeMindMapUINode().getUI().getLayoutY();
        return angle.calculateAngle(new Point2D(layoutX, layoutY));
    }

    public int calculateMainBranch() {
        return mindMapNode.getMainBranch()==MindMapNode.ROOT_NODE?mindMapNode.leftChildsProperty().size()+mindMapNode.rightChildsProperty().size():mindMapNode.getMainBranch();
    }
}
