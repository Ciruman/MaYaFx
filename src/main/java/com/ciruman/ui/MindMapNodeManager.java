package com.ciruman.ui;

import com.ciruman.calculations.Angle;
import com.ciruman.model.MindMapNode;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;

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
        mindMapNode.leftChildsProperty().forEach(new Consumer<MindMapNode>() {
            @Override
            public void accept(MindMapNode node) {
                MindMapNodeManager mindMapNodeManager = new MindMapNodeManager(node, angle);
                getOrInitializeMindMapUINode().addNode(mindMapNodeManager.getNode(), createPoint2DProperty(node, mindMapNode.leftChildsProperty()));
            }
        });
        mindMapNode.rightChildsProperty().forEach(new Consumer<MindMapNode>() {
            @Override
            public void accept(MindMapNode node) {
                MindMapNodeManager mindMapNodeManager = new MindMapNodeManager(node, angle);
                getOrInitializeMindMapUINode().addNode(mindMapNodeManager.getNode(), createPoint2DProperty(node, mindMapNode.rightChildsProperty()));
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
        }
        return mindMapUINode;
    }

    private ObjectBinding<Point2D> createPoint2DProperty(final MindMapNode node, final ObservableList<MindMapNode> childs) {
        return Bindings.createObjectBinding(new Callable<Point2D>() {
            @Override
            public Point2D call() throws Exception {
                int numberOfNodesInTheSameDirection = childs.size();
                int indexInTheSameDirection = childs.indexOf(node);
                return angle.calculatePosition(indexInTheSameDirection, (int) numberOfNodesInTheSameDirection, node.getMindMapNodeDirection());
            }
        }, childs);
    }

}
