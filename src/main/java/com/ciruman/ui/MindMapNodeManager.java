package com.ciruman.ui;

import com.ciruman.model.MindMapNode;
import com.ciruman.ui.MindMapUINode;
import javafx.scene.Node;

public class MindMapNodeManager {

    private final MindMapNode mindMapNode;

    public MindMapNodeManager(MindMapNode mindMapNode){
        this.mindMapNode = mindMapNode;
    }

    public Node getUI(){
        MindMapUINode mindMapUINode = new MindMapUINode();
        mindMapUINode.setText(mindMapNode.getText());
        return mindMapUINode.getUI();
    }

}
