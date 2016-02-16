package com.ciruman.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MindMapNode {

    ObservableList<MindMapNode> childs = FXCollections.observableArrayList();

    public ObservableList<MindMapNode> getChilds() {
        return childs;
    }

    public void setChilds(ObservableList<MindMapNode> childs) {
        this.childs = childs;
    }

    public ObservableList<MindMapNode> childsProperty(){
        return childs;
    }
}
